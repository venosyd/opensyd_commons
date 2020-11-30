package com.venosyd.open.commons.services.seeker;

import java.util.Map;

/**
 * @author sergio lisan <sels@venosyd.com>
 * 
 *         Service Request Builder
 */
public interface ServiceRequestBuilder {

    /**  */
    ServiceRequestBuilder service(String service);

    /**  */
    ServiceRequestBuilder method(String method);

    /**  */
    ServiceRequestBuilder path(String path);

    /**  */
    ServiceRequestBuilder headers(Map<String, String> headers);

    /**  */
    ServiceRequestBuilder body(Map<String, Object> body);

    /**  */
    Map<String, Object> run();

}
