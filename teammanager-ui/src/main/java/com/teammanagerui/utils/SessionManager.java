package com.teammanagerui.utils;

import com.teammanagerui.model.User;

public class SessionManager {

    private static String token;

    private static User user;

    public static void setToken(String token) {
        SessionManager.token = token;
    }

    public static String getToken() {
        return token;
    }

    public static void setUser(User user) {
        SessionManager.user = user;
    }

    public static User getUser() {
        return user;
    }

    public static boolean isLoggedIn() {
        return token != null && !token.isEmpty();
    }

    public static void logout() {
        token = null;
        user = null;
    }

}
