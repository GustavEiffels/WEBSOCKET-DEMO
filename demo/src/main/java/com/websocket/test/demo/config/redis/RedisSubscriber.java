package com.websocket.test.demo.config.redis;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.test.demo.config.dto.MessageDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisSubscriber {
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    public void sendMessage(String publishMessage) throws JsonProcessingException {
        log.info("publishMessage : {}",publishMessage);
        MessageDto messageDto = objectMapper.readValue(publishMessage, MessageDto.class);
        log.info("messageDto : {}",messageDto);
        messagingTemplate.convertAndSend("/topic/room/"+messageDto.getRoom(),messageDto);
    }    
}
