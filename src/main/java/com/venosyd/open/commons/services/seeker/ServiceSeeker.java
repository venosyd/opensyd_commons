package com.venosyd.open.commons.services.seeker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Service Seeker
 */
public abstract class ServiceSeeker {

    /**
     * registra o servico no service seeker
     */
    public static String register(String service, String privateURL) {
        return ServiceRegistry.INSTANCE.registerInSS(service, privateURL, new ArrayList<>());
    }

    /**
     * registra o servico no service seeker
     */
    public static String register(String service, String privateURL, List<String> publicURLs) {
        return ServiceRegistry.INSTANCE.registerInSS(service, privateURL, publicURLs);
    }

    /**
     * retorna um builder de requisicao
     */
    public static ServiceRequestBuilder builder() {
        return new ServiceRequestBuilderImpl();
    }

}
