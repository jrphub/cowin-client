package com.iamjrp.cowin.service;

import com.iamjrp.cowin.client.TelegramClientDefault;
import com.iamjrp.cowin.client.TelegramClientKhurda18;
import com.iamjrp.cowin.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class QueueConsumerThread1 implements Runnable {
    private final Logger LOG = LoggerFactory.getLogger(QueueConsumerThread1.class);

    private final BlockingQueue<Message> queue;

    public QueueConsumerThread1(BlockingQueue<Message> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        TelegramClientKhurda18 telegramClient = new TelegramClientKhurda18();
        while(true) {
            try {
                Message takenMsg = queue.take();
                LOG.info("Taken Message :{}", takenMsg);
                telegramClient.publishMessage(takenMsg);
            } catch (InterruptedException e) {
                LOG.error(e.getMessage());
            }
        }
    }
}
