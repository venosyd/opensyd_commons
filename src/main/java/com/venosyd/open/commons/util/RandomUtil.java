package com.venosyd.open.commons.util;

import java.util.Random;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public class RandomUtil {

    private static Random rand = new Random();

    /**
     * insira o menor valor, depois o maior e ele retorna um dentro do intervalo
     */
    public static int nextInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}
