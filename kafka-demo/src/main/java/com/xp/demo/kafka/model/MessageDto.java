package com.xp.demo.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
public class MessageDto {
    private long id;
    private String title;
}
