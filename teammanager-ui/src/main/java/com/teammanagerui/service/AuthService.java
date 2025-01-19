package com.teammanagerui.service;

import java.io.IOException;

import org.json.JSONObject;

import com.teammanagerui.config.EnvConfig;
import com.teammanagerui.utils.SessionManager;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public class AuthService {

    private final OkHttpClient client;
    private final String baseUrl;

    public AuthService() {
        this.client = new OkHttpClient();
        this.baseUrl = EnvConfig.get("BASE_URL");
    }

    public String login(String username, String password) throws IOException {
        // Create JSON body
        String jsonBody = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        RequestBody body = RequestBody.create(
                jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(baseUrl + "/auth/login")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                String token = new JSONObject(responseBody).getString("accessToken");
                SessionManager.setToken(token); // Store token globally
                return token;
            } else {
                throw new IOException("Failed to login: " + response.code() + " " + response.message());
            }
        }
    }

}
