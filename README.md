# Remote Provider Shell Access System (<b>CLOUD</b>)


#### Built a cloud-like remote shell platform inspired by EC2 / Azure VM, enabling real-time command execution on remote provider machines via backend.


### Overview
This project implements a distributed remote shell access system where provider machines expose isolated shell environments that can be accessed securely from a web browser.

A provider agent connects to a Spring Boot backend, allowing users to interact with the provider’s Linux or Docker-based shell in real time through the browser.

### Architecture & Features

1. Implemented a provider–agent architecture where remote machines register and maintain persistent connections with a Spring Boot server.
2. Enabled real-time browser-based shell access by streaming terminal input/output between:

    Browser ⇄ Spring Boot Server ⇄ Provider Agent

3. Implemented bidirectional, low-latency communication using WebSockets.

4. Integrated Docker-based shell execution to provide isolated and disposable execution environments.

5. Designed a session-based command streaming pipeline to handle interactive terminal sessions (stdin/stdout/stderr).

6. Ensured the backend acts as an intermediary, avoiding direct browser-to-provider exposure.

### Tech Stack

1. Backend: Java, Spring Boot, WebSockets, REST APIs.


2. Provider Agent: Python, pty, websockets.

3. Execution Environment: Docker, Linux Shell.


### How This Works

1. A provider machine runs the provider.py agent.

2. The agent establishes a persistent WebSocket connection with the Spring Boot backend.

3. Users access the shell through the browser UI, which communicates with the backend.

4. Commands are forwarded to the provider agent, executed inside a docker container. Command output is streamed back to the browser in real time.


### Setup Instructions
Provider Machine: Ensure Docker is installed on the provider machine for isolated execution. Also run below to install necessary packages.
```
pip install pty websockets
```
Run `python provider.py` and it will connect to springboot backend.



### Hacks

1. If you dont have docker, you can provide your provider machines bash(it will give access to entire provider machine). Just change `["docker", "run", "-it", "--rm", "alpine", "sh"]` to `["bash"]`.

2. Also provider docker runs alpine, if you want you can run ubuntu or any other os.






<!-- hchjccg


1. Built a system that allows browsers to securely access a provider machine’s Linux/Docker shell in real time using a Spring Boot backend.
2. Designed and implemented a provider–agent architecture where remote machines register and maintain persistent connections with a Spring Boot server.
3. Enabled real-time browser-based shell access by streaming terminal I/O between provider machines and users.
4. Implemented bidirectional communication between browser ↔ server ↔ provider using WebSockets.
5. Integrated Docker shell execution, allowing isolated command execution on provider machines.

#### Tech Stack Used: Java, Spring Boot, WebSockets, Docker, Linux Shell, REST APIs.


1. The `provider.py` file acts as agent, we need to run this file in provider machine.
2. The `provider.py` will connect to this springboot backend. Then we can access the shell in our browser.
3. we need to do `pip install pty websockets` to run provider. 
4. Also we need to have docker in our provider machine. If you dont have docker, you cant create isolated environment, but instead we can provide our systems bash, to give our systems bash, just change `["docker", "run", "-it", "--rm", "alpine", "sh"]` to `["bash"]`, but remember it will have security problems. -->