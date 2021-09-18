package com.evil.inc.taskman.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class SimpleThreadPool {
    private static final Logger log = LoggerFactory.getLogger(SimpleThreadPool.class);
    private static final AtomicInteger numberOfPools = new AtomicInteger(0);

    private static SimpleThreadPool INSTANCE;
    private final AtomicBoolean isRunning;
    private final ConcurrentLinkedQueue<Runnable> tasks;
    private final List<SimpleWorker> workers;

    private SimpleThreadPool(int threadCount) {
        numberOfPools.incrementAndGet();
        this.tasks = new ConcurrentLinkedQueue<>();
        this.isRunning = new AtomicBoolean(true);
        this.workers = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            SimpleWorker worker = new SimpleWorker("SimpleThreadPool-[" + numberOfPools.get() + "]-Thread-[" + i + "]",
                                                   this.isRunning, this.tasks);
            this.workers.add(worker);
            worker.start();
        }
    }

    public static synchronized SimpleThreadPool getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SimpleThreadPool(Runtime.getRuntime().availableProcessors());
        }

        return INSTANCE;
    }

    public static synchronized SimpleThreadPool getInstance(int numberOfThreads) {
        if (INSTANCE == null) {
            INSTANCE = new SimpleThreadPool(numberOfThreads);
        }

        return INSTANCE;
    }

    public synchronized void submit(Runnable runnable) {
        if (this.isRunning.get()) {
            synchronized (tasks) {
                tasks.add(runnable);
                tasks.notify();
            }
        } else {
            throw new IllegalStateException("ThreadPool terminating, unable to execute runnable");
        }
    }

    private synchronized void awaitTermination() {
        if (!this.isRunning.get()) {
            throw new IllegalStateException("ThreadPool not terminated before awaiting termination");
        }
        while (this.tasks.size() > 0) {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                throw new SimpleThreadPoolException(e);
            }
        }
    }

    public synchronized void shutdown() {
        isRunning.set(false);
        for (final SimpleWorker worker : workers) {
            worker.terminate();
        }
        tasks.clear();
    }

    public synchronized void awaitShutdown() {
        awaitTermination();
        isRunning.set(false);
        for (final SimpleWorker worker : workers) {
            worker.terminate();
        }
        tasks.clear();
    }

}