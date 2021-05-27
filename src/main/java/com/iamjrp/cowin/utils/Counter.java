package com.iamjrp.cowin.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Counter {
    private AtomicInteger count = new AtomicInteger();

    public void increment() {
        count.incrementAndGet();
    }

    public AtomicInteger getCount() {
        return this.count;
    }

    public void reset() {
        this.count = new AtomicInteger();
    }
}
