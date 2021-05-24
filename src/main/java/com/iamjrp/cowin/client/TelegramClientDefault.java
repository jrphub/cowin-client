package com.iamjrp.cowin.client;

import com.iamjrp.cowin.model.Message;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TelegramClientDefault {
    private final Logger LOG = LoggerFactory.getLogger(TelegramClientDefault.class);
    private static String TOKEN = "1783244153:AAHlr8fSauey9F1aNi0GWSHoJMgIXc_FJrQ";
    private static long chatId = -501833274;

    //https://github.com/pengrad/java-telegram-bot-api
    public void publishMessage(Message message){
        LOG.info("Sending to Telegram...");
        TelegramBot bot = new TelegramBot(TOKEN);
        SendMessage request = new SendMessage(chatId, message.toString())
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
