package com.elecredit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Condition STOP = LOCK.newCondition();

    public static void main(String[] args) {
//        System.getProperties().put("DUBBO_IP_TO_BIND","192.168.1.66");
//        System.getProperties().put("DUBBO_IP_TO_REGISTRY","192.168.1.66");
//        System.getProperties().put("DUBBO_PORT_TO_REGISTRY","20881");
//        System.getProperties().put("DUBBO_PORT_TO_BIND","20880");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"application.xml"});
        context.start();
        logger.info("open-platform-service-provider started.");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            context.stop();
            try {
                LOCK.lock();
                STOP.signal();
            } finally {
                LOCK.unlock();
            }
        }));
        try {
            LOCK.lock();
            STOP.await();
        } catch (InterruptedException e) {
            logger.warn("user service stopped, interrupted by other thread!", e);
        } finally {
            LOCK.unlock();
        }
    }
}
