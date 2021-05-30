package com.iamjrp.cowin.sandbox;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class TelegramClientTest {
    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot("1783244153:AAHlr8fSauey9F1aNi0GWSHoJMgIXc_FJrQ");
        SendMessage request = new SendMessage("@nojoinjrp", "[45+][751019][COVAXIN]\n" +
                " <b>date</b> : 31-05-2021\n" +
                " <b>dose1</b> : 0\n" +
                " <b>dose2</b> : 193\n" +
                " <b>address</b> : Patrapada Bhubaneswar\n" +
                " <b>feeType</b> : Free\n" +
                "\n" +
                "[45+][751019][COVAXIN]\n" +
                " <b>date</b> : 01-06-2021\n" +
                " <b>dose1</b> : 0\n" +
                " <b>dose2</b> : 196\n" +
                " <b>address</b> : Patrapada Bhubaneswar\n" +
                " <b>feeType</b> : Free\n" +
                "\n" +
                "[45+][751010][COVAXIN]\n" +
                " <b>date</b> : 31-05-2021\n" +
                " <b>dose1</b> : 1\n" +
                " <b>dose2</b> : 226\n" +
                " <b>address</b> : GGP Rasulgarh\n" +
                " <b>feeType</b> : Free\n" +
                "\n" );

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
