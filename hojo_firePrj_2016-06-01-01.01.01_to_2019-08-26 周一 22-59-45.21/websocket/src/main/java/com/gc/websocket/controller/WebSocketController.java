package com.gc.websocket.controller;

import com.gc.websocket.core.WebSocketServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {
    @PostMapping("/websocket")
    public ResponseEntity<?> sendAll(@RequestBody String data){
        WebSocketServer.sendInfo(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
