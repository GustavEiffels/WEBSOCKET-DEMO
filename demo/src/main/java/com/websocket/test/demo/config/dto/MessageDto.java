package com.websocket.test.demo.config.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageDto {
    public String name;
    public String room;
    public String message;
}
