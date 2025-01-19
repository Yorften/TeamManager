package com.teammanagerui.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teammanagerui.config.EnvConfig;
import com.teammanagerui.model.User;
import com.teammanagerui.utils.SessionManager;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
public class UserService {

    private final OkHttpClient client;
    private final String baseUrl;

    public UserService() {
        this.client = new OkHttpClient();
        this.baseUrl = EnvConfig.get("BASE_URL");
    }

    public User fetchUserInfo() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "/users/@me")
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                User user = objectMapper.readValue(response.body().string(), User.class);
                log.info(user.toString());
                SessionManager.setUser(user);
                return user;
            } else {
                throw new IOException("Failed to fetch user info: " + response.code());
            }
        }
    }

    public List<User> fetchAllUsers() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "/users")
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.body().string(), new TypeReference<List<User>>() {
                });
            } else {
                throw new IOException("Failed to fetch users: " + response.code());
            }
        }
    }

}
