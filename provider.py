import os
import pty
import asyncio
import subprocess
import websockets

# Configuration: Replace with your actual server URL
SERVER_WS_URL = "ws://localhost:8080/provider"

async def terminal_worker():
    try:
        async with websockets.connect(SERVER_WS_URL) as ws:
            print(f"Connected to {SERVER_WS_URL}")

            # 1. Create the PTY (Pseudo-Terminal)
            master_fd, slave_fd = pty.openpty()

            # 2. Start the Docker process
            # We connect stdin, stdout, and stderr to the slave end of the PTY
            process = subprocess.Popen(
                ["docker", "run", "-it", "--rm", "alpine", "sh"],
                stdin=slave_fd,
                stdout=slave_fd,
                stderr=slave_fd,
                close_fds=True
            )

            # Close the slave descriptor in the parent process 
            os.close(slave_fd)

            loop = asyncio.get_event_loop()

            # Task: Read from Docker (PTY) and send to Server
            async def pipe_pty_to_ws():
                try:
                    while process.poll() is None:
                        # os.read is blocking, so we run it in an executor
                        data = await loop.run_in_executor(None, os.read, master_fd, 1024)
                        if not data:
                            break
                        await ws.send(data)
                except Exception as e:
                    print(f"PTY Read Error: {e}")

            # Task: Read from Server and write to Docker (PTY)
            async def pipe_ws_to_pty():
                try:
                    async for message in ws:
                        # Ensure data is bytes for os.write
                        if isinstance(message, str):
                            message = message.encode()
                        os.write(master_fd, message)
                except Exception as e:
                    print(f"WS Read Error: {e}")

            # Run both communication directions concurrently
            await asyncio.gather(pipe_pty_to_ws(), pipe_ws_to_pty())

    except Exception as e:
        print(f"Connection failed: {e}")

if __name__ == "__main__":
    asyncio.run(terminal_worker())