package com.venosyd.open.commons.util;

import com.venosyd.open.commons.log.Debuggable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         utilidades de IP
 */
public final class IPUtil implements Debuggable {

    /**
     * retirado de
     * http://stackoverflow.com/questions/2939218/getting-the-external-ip-
     * address-in-java
     */
    public static String getPublicIP() {
        String ip = "0.0.0.0";

        try {
            var whatismyip = new URL("http://checkip.amazonaws.com");
            var in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            ip = in.readLine();
        } catch (Exception e) {
            err.tag("GETPUBLICIP").ln("Cant get the public IP: " + e);
            e.printStackTrace();
        }

        return ip;
    }

    /**
     * retorna o ip em que a maquina esta rodando, primeiro via eth0 e se nao
     * estiver conectado via cabo, pela wlan0
     */
    public static String getLocalIP() {
        return "0.0.0.0";
    }
}
