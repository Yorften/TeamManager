package com.teammanagerui.view;

import com.teammanagerui.controller.LoginController;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final LoginController controller;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginFrame() {
        controller = new LoginController();
        
        // Frame setup
        setTitle("Employee Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 200));
        setLocationRelativeTo(null);

        // Create components
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        // Layout setup
        JPanel mainPanel = new JPanel(new MigLayout("fillx, insets 20", "[right][grow]"));
        
        // Add components
        mainPanel.add(new JLabel("Username:"), "");
        mainPanel.add(usernameField, "growx, wrap");
        mainPanel.add(new JLabel("Password:"), "");
        mainPanel.add(passwordField, "growx, wrap");
        mainPanel.add(loginButton, "skip, right, wrap");

        // Add action listener
        loginButton.addActionListener(e -> handleLogin());

        // Final setup
        add(mainPanel);
        pack();
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Username and password are required",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (controller.authenticate(username, password)) {
            JOptionPane.showMessageDialog(this,
                "Login successful!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            // TODO: Open main application window
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid credentials",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
