package com.iamjrp.cowin.service;

import com.iamjrp.cowin.client.TelegramClient;
import com.iamjrp.cowin.model.BMessage;
import com.iamjrp.cowin.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueConsumerThread implements Runnable {
    private final Logger LOG = LoggerFactory.getLogger(QueueConsumerThread.class);

    private final BlockingQueue<BMessage> queue;

    public QueueConsumerThread(BlockingQueue<BMessage> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        while(true) {
            try {
                BMessage takenMsgs = queue.take();
                if (takenMsgs != null) {
                    List<Message> messages = takenMsgs.getMessages();
                    TelegramClient telegramClient = takenMsgs.getTelegramClient();
                    telegramClient.publishMessage(messages);
                }
            } catch (InterruptedException e) {
                LOG.error(e.getMessage());
            }
        }
    }
}
