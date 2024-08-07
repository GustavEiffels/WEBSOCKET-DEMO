package com.websocket.test.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.websocket.test.demo.config.dto.MessageDto;
import com.websocket.test.demo.config.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class MainController {

    private final SimpMessageSendingOperations sendingOperations;
    private final RedisPublisher redisPublisher;

    @MessageMapping("/send")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody MessageDto messageDto) throws JsonProcessingException {
        log.info("messageDto : {}",messageDto);
        redisPublisher.publish(messageDto);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }
}
