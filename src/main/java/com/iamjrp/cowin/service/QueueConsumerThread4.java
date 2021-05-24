package com.iamjrp.cowin.service;

import com.iamjrp.cowin.client.TelegramClientCuttack45;
import com.iamjrp.cowin.client.TelegramClientDefault;
import com.iamjrp.cowin.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class QueueConsumerThread4 implements Runnable {
    private final Logger LOG = LoggerFactory.getLogger(QueueConsumerThread4.class);

    private final BlockingQueue<Message> queue;

    public QueueConsumerThread4(BlockingQueue<Message> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        TelegramClientCuttack45 telegramClient = new TelegramClientCuttack45();
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
