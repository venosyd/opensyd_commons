package com.venosyd.open.commons.util;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

/**
 * @author sergio lisan <sels@venosyd.com>
 */
public class DistanceCalculator {

    // raio da terra em metros
    public static final double EARTH_RADIUS = 6378137;

    /**
     * verifica para cada profissional de uma especialidade identificado pelo seu
     * email se ele esta dentro de um raio de X km definidos na configuracao
     * (radius: X) proximos da localizacao do usuario (longi, lat)
     */
    public static List<Pair<Double, Double>> calculateWhoSNext(List<Pair<Double, Double>> localizacoes, int radius,
            double cLat, double cLong) {
        var nearby = new ArrayList<Pair<Double, Double>>();

        for (var localizacao : localizacoes) {
            double pLat = localizacao.getLeft();
            double pLong = localizacao.getRight();

            boolean inside = calculateProximity(cLat, cLong, pLat, pLong, radius);

            if (inside) {
                nearby.add(localizacao);
            }
        }

        return nearby;
    }

    /** WHO'S NEXT */
    public static List<String> calculateWhoSNext(Map<String, List<Double>> emails, int radius, double cLat,
            double cLong) {
        var nearby = new ArrayList<String>();

        for (var email : emails.keySet()) {
            double pLat = emails.get(email).get(0);
            double pLong = emails.get(email).get(1);

            boolean inside = calculateProximity(cLat, cLong, pLat, pLong, radius);

            if (inside) {
                nearby.add(email);
            }
        }

        return nearby;
    }

    /**
     * equacao que calcula proximidade
     */
    public static boolean calculateProximity(double clat, double clong, double plat, double plong, int radius) {
        double dLat = toRadians(plat - clat);
        double dLong = toRadians(plong - clong);
        double a = sin(dLat / 2) * sin(dLat / 2)
                + cos(toRadians(clat)) * cos(toRadians(plat)) * sin(dLong / 2) * sin(dLong / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        double d = EARTH_RADIUS * c;

        return d <= (radius * 1000);
    }

    /**
     * ORIGIN: https://www.geodatasource.com/developers/java
     */
    public static double distanceBetweenMapPositionsInKM(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515 * 1.609344;

            return dist;
        }
    }

}
