package com.venosyd.open.commons.services.seeker;

import java.util.List;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Service Register in SSK
 */
public interface ServiceRegistry {

    /**  */
    ServiceRegistry INSTANCE = new ServiceRegistryImpl();

    /**  */
    String registerInSS(String service, String privateURL, List<String> publicURLs);

    /**  */
    String getService(String service);

    /**  */
    String getHost(String host);

}
