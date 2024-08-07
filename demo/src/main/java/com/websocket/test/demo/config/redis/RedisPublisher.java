package com.websocket.test.demo.config.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.test.demo.config.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisPublisher {
    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public void publish(MessageDto msgDto) throws JsonProcessingException {
        log.info("channelTopic.getTopic() : {}",channelTopic.getTopic());
        String jsonString = objectMapper.writeValueAsString(msgDto);
        log.info("Publishing message: {}", jsonString);
        redisTemplate.convertAndSend(channelTopic.getTopic(), jsonString);
    }

}
