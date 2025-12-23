package com.example.first_vigna;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {

    @GetMapping("/")
    public String page() {
        return """
          <!DOCTYPE html>
          <html>
          <head>
            <meta charset="utf-8">
            <title>Browser Terminal</title>

            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xterm/css/xterm.css" />
            <script src="https://cdn.jsdelivr.net/npm/xterm/lib/xterm.js"></script>
          </head>
          <body style="margin:0; background:black;">

          <div id="term" style="width:100vw; height:100vh;"></div>

          <script>
            const term = new Terminal({ cursorBlink: true });
            term.open(document.getElementById("term"));

            console.log("Terminal initialized");

            const ws = new WebSocket("ws://" + location.host + "/terminal");
            ws.binaryType = "arraybuffer";

            ws.onopen = () => {
              console.log("WebSocket OPEN");
            };

            ws.onerror = (e) => {
              console.error("WebSocket ERROR", e);
            };

            ws.onmessage = (e) => {
              console.log("Received from Spring", e.data);
              term.write(new Uint8Array(e.data));
            };

            term.onData(data => {
              console.log("Typed:", data);   // 🔥 THIS MUST PRINT
              ws.send(data);
            });
          </script>



          </body>
          </html>

        """;
    }
}
