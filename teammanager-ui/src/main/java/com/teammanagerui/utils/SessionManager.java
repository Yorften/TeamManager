package com.teammanagerui.utils;

public class SessionManager {

    private static String token;

    public static void setToken(String token) {
        SessionManager.token = token;
    }

    public static String getToken() {
        return token;
    }

    public static boolean isLoggedIn() {
        return token != null && !token.isEmpty();
    }

    public static void logout() {
        token = null;
    }

}
