package com.teammanagerui.service;

import java.io.IOException;

import com.teammanagerui.config.EnvConfig;
import com.teammanagerui.utils.SessionManager;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
public class LogService {

    private final OkHttpClient client;
    private final String baseUrl;

    public LogService() {
        this.client = new OkHttpClient();
        this.baseUrl = EnvConfig.get("BASE_URL");
    }

    public String fetchEmployeeAuditLogs() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "/logs/employee-audit")
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string(); // Plain text logs
            } else {
                throw new IOException("Failed to fetch logs: " + response.code());
            }
        }
    }

}
