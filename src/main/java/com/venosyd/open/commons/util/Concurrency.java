package com.venosyd.open.commons.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.venosyd.open.commons.log.Debuggable;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Utilities for threads
 */
public class Concurrency implements Debuggable {

    /** segura a execucao da thread em x milissegundos */
    public static void wait(int millisseconds) {
        try {
            Thread.sleep(millisseconds);
        } catch (InterruptedException e) {
            err.tag("UTIL-CONCURRENCY").ln("Problems at putting a thread to sleep: " + e);
            Thread.currentThread().interrupt();
        }
    }

    /** segura a execucao da thread em x segundos */
    public static void waitSeconds(int seconds) {
        wait(seconds * 1000);
    }

    /** segura a execucao da thread em x minutos */
    public static void waitMinutes(int minutes) {
        wait(minutes * 1000 * 60);
    }

    /** segura a execucao da thread em 1 segundo */
    public static void waitASecond() {
        wait(1000);
    }

    /** segura a execucao da thread em 1 minuto */
    public static void waitAMinute() {
        wait(60 * 1000);
    }

    /** cria uma piscina de threads de tamanho determinado */
    public static ThreadPoolExecutor createPool(int poolSize) {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool((int) poolSize);
    }

    /** segura a execucao ate a finalizacao da piscina de threads */
    public static void waitWorkFinish(ThreadPoolExecutor executor, int seconds) {
        executor.shutdown();

        try {
            if (!executor.awaitTermination(seconds, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
