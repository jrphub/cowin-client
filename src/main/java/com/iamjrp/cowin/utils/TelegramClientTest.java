package com.iamjrp.cowin.utils;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.io.IOException;

public class TelegramClientTest {
    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot("1783244153:AAHlr8fSauey9F1aNi0GWSHoJMgIXc_FJrQ");
        SendMessage request = new SendMessage(-501833274, "<b>Hello From Java API</b>")
                .parseMode(ParseMode.HTML);
                /*.disableWebPagePreview(true)
                .disableNotification(true)
                .replyToMessageId(1)
                .replyMarkup(new ForceReply());*/

// sync
        SendResponse sendResponse = bot.execute(request);
        boolean ok = sendResponse.isOk();
        Message message = sendResponse.message();
        System.out.println(message);

// async
        /*bot.execute(request, new Callback<SendMessage, SendResponse>() {
            @Override
            public void onResponse(SendMessage request, SendResponse response) {
                System.out.println("Message Sent Successfully");
                System.out.println(response.message());
            }

            @Override
            public void onFailure(SendMessage request, IOException e) {
                System.out.println("Message Sent failed");
                System.out.println(e);
            }
        });*/

    }
}
