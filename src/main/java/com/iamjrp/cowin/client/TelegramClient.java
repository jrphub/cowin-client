package com.iamjrp.cowin.client;

import com.iamjrp.cowin.model.Message;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class TelegramClient {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private String token;
    private String chatId;

    public TelegramClient(String token, String chatId) {
        this.token = token;
        this.chatId = chatId;
    }

    //https://github.com/pengrad/java-telegram-bot-api
    public void publishMessage(List<Message> messages){
        LOG.info("Sending to Telegram...");
        TelegramBot bot = new TelegramBot(this.token);
        String msgBatch = "";
        for (Message message : messages) {
            msgBatch += message;
        }
        SendMessage request = new SendMessage(this.chatId, msgBatch)
                .parseMode(ParseMode.HTML);
        bot.execute(request, new Callback<SendMessage, SendResponse>() {
            @Override
            public void onResponse(SendMessage request, SendResponse response) {
                LOG.info("Message Sent to Telegram:{}", request.getParameters());
            }

            @Override
            public void onFailure(SendMessage request, IOException e) {
                LOG.info("Message Not Sent to Telegram:{}", e);
            }
        });
    }
}
