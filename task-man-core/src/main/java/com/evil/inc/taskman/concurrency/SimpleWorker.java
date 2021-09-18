package com.evil.inc.taskman.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleWorker extends Thread {
    private static final Logger log = LoggerFactory.getLogger(SimpleWorker.class);
    private final AtomicBoolean isRunning;
    private final ConcurrentLinkedQueue<Runnable> tasks;

    public SimpleWorker(String name, AtomicBoolean isRunning, ConcurrentLinkedQueue<Runnable> tasks) {
        super(name);
        this.isRunning = isRunning;
        this.tasks = tasks;
        log.info("Created {}", this);
    }

    @Override
    public void run() {
        Runnable task;

        while (isRunning.get()) {
            synchronized (tasks) {
                while (isRunning.get() && tasks.isEmpty()) {
                    try {
                        tasks.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                task = tasks.poll();
            }
            log.info("this {}", this);
            try {
                if (task != null) {
                    task.run();
                }
            } catch (RuntimeException e) {
                throw new SimpleThreadPoolException(e);
            }
        }
    }

    public synchronized void terminate() {
        isRunning.set(false);
        this.interrupt();
    }

    @Override
    public String toString() {
        return "SimpleWorker{" +
                "name=" + this.getName() +
                ", isRunning=" + isRunning +
                ", tasks=" + tasks.size() +
                '}';
    }
}