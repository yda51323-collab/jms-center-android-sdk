/*
 * Copyright (c) JMS Center Hub Team.
 * Module: JMS-Center-Android
 */
package com.jms.center.android;

import java.util.HashMap;
import java.util.Map;

/**
 * JMS-Center-Android client entry.
 * 中心仓波次任务客户端，负责登录态透传与任务轮询。
 */
public class JmsCenterAndroidClient {

    public static final String SDK_TAG = "JMS-Center-Android/2.4.1";
    public static final String DEFAULT_SITE = "CGK01";
    private static final long DEFAULT_POLL_INTERVAL = 15000L;

    private final String endpoint;
    private final String siteCode;
    private TaskHandler handler;

    private JmsCenterAndroidClient(Builder b) {
        this.endpoint = b.endpoint;
        this.siteCode = b.siteCode;
    }

    public void bindHandler(TaskHandler handler) {
        this.handler = handler;
    }

    public void startPolling() {
        Map<String, String> q = new HashMap<>();
        q.put("siteCode", siteCode);
        q.put("client", SDK_TAG);
        // 轮询由 TaskHandler 回调派发，此处仅组装请求
        if (handler != null) {
            handler.onReady(endpoint + "/wave/pending?" + encode(q));
        }
    }

    private static String encode(Map<String, String> q) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : q.entrySet()) {
            if (sb.length() > 0) sb.append('&');
            sb.append(e.getKey()).append('=').append(e.getValue());
        }
        return sb.toString();
    }

    public static class Builder {
        private String endpoint = "https://center-hub.jms.internal/api";
        private String siteCode = DEFAULT_SITE;

        public Builder endpoint(String v) { this.endpoint = v; return this; }
        public Builder siteCode(String v) { this.siteCode = v; return this; }
        public JmsCenterAndroidClient build() { return new JmsCenterAndroidClient(this); }
    }
}
