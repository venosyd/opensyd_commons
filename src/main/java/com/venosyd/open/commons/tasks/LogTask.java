package com.venosyd.open.commons.tasks;

import java.util.Timer;
import java.util.TimerTask;

import com.venosyd.open.commons.log.Debuggable;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public class LogTask implements Runnable, Debuggable {

    /**  */
    static final int SEGUNDO = 1000;

    /**  */
    static final int MINUTO = (SEGUNDO * 60);

    /**  */
    static final int HORA = (MINUTO * 60);

    /**  */
    static final int DIA = (HORA * 24);

    @Override
    public void run() {
        var timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                out.tag("LOGTASK").tag("RUNNING TASK TESTING").ln();
            }
        }, 0, HORA);

    }

}