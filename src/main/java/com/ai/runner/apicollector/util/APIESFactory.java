package com.ai.runner.apicollector.util;

import java.util.HashMap;

import com.ai.runner.apicollector.vo.ESConfig;

public final class APIESFactory {

    private static ElasticSearchHandler ESHANDLER = null;

    private static HashMap<String, ElasticSearchHandler> m = new HashMap<String, ElasticSearchHandler>();

    public static ElasticSearchHandler getElasticSearchHandler(ESConfig esconfig) {
        StringBuffer sb = new StringBuffer();
        sb.append(esconfig.getIp()).append(esconfig.getPort()).append(esconfig.getClusterName());
        String h = sb.toString();
        if (!m.containsKey(h)) {
            synchronized (APIESFactory.class) {
                if (!m.containsKey(h)) {
                    ESHANDLER = new ElasticSearchHandler(esconfig);
                    m.put(h, ESHANDLER);
                }
            }
        }
        return ESHANDLER;
    }

}
