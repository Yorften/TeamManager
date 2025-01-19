package com.teammanagerui.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.teammanagerui.config.EnvConfig;
import com.teammanagerui.model.Employee;
import com.teammanagerui.utils.SessionManager;

import okhttp3.*;

public class EmployeeService {
    private final OkHttpClient client;
    private final String baseUrl;
    private final ObjectMapper objectMapper;

    public EmployeeService() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        this.client = new OkHttpClient();
        this.baseUrl = EnvConfig.get("BASE_URL");
    }

    public void createEmployee(Employee employee) throws Exception {
        String json = objectMapper.writeValueAsString(employee);

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(baseUrl + "/employees")
                .post(body)
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to create employee: " + response.code());
            }
        }
    }

    public List<Employee> fetchAllEmployees() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "/employees")
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return objectMapper.readValue(response.body().string(), new TypeReference<List<Employee>>() {
                });
            } else {
                throw new IOException("Failed to fetch users: " + response.code());
            }
        }
    }

    public void updateEmployee(Employee employee) throws IOException {
        String json = objectMapper.writeValueAsString(employee);

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(baseUrl + "/employees/" + employee.getId())
                .put(body)
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to update employee: " + response.body().string());
            }
        }
    }

    public void deleteEmployee(Employee employee) throws IOException {

        Request request = new Request.Builder()
                .url(baseUrl + "/employees/" + employee.getId())
                .delete()
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to update employee: " + response.code());
            }
        }
    }

    public List<Employee> searchEmployees(String query) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "/employees/search?fullName=" + query)
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return objectMapper.readValue(
                        response.body().string(),
                        new TypeReference<List<Employee>>() {
                        });
            } else {
                throw new IOException("Failed to search employees: " + response.code());
            }
        }
    }

}
