package com.iamjrp.cowin.service;

import com.iamjrp.cowin.client.TelegramClient;
import com.iamjrp.cowin.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueConsumerThread implements Runnable {
    private final Logger LOG = LoggerFactory.getLogger(QueueConsumerThread.class);

    private final BlockingQueue<List<Message>> queue;
    private TelegramClient telegramClient;

    public QueueConsumerThread(BlockingQueue<List<Message>> queue, TelegramClient telegramClient) {
        this.queue = queue;
        this.telegramClient = telegramClient;
    }
    @Override
    public void run() {
        while(true) {
            try {
                List<Message> takenMsgs = queue.take();
                if (!takenMsgs.isEmpty()) {
                    telegramClient.publishMessage(takenMsgs);
                }
            } catch (InterruptedException e) {
                LOG.error(e.getMessage());
            }
        }
    }
}
