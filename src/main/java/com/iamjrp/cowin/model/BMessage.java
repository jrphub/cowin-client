package com.iamjrp.cowin.model;

import com.iamjrp.cowin.client.TelegramClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BMessage {
    private List<Message> messages;
    private TelegramClient telegramClient;
}
