package com.teammanagerui.view;

import com.teammanagerui.controller.AuthController;
import com.teammanagerui.model.LoginModel;
import com.teammanagerui.service.AuthService;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton loginButton;

    private final AuthController controller;

    public LoginFrame() {
        // Create model, service, and controller
        LoginModel model = new LoginModel();
        AuthService authService = new AuthService();
        this.controller = new AuthController(model, authService);

        initUI();
    }

    private void initUI() {
        setTitle("Employee Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 200));
        setLocationRelativeTo(null);

        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        loginButton = new JButton("Login");

        JPanel mainPanel = new JPanel(new MigLayout("fillx, insets 20", "[right][grow]"));

        mainPanel.add(new JLabel("Username:"), "");
        mainPanel.add(txtUsername, "growx, wrap");
        mainPanel.add(new JLabel("Password:"), "");
        mainPanel.add(txtPassword, "growx, wrap");
        mainPanel.add(loginButton, "skip, right, wrap");

        loginButton.addActionListener(e -> onLoginClick());

        add(mainPanel);
        pack();

    }

    private void onLoginClick() {
        controller.getModel().setUsername(txtUsername.getText());
        controller.getModel().setPassword(new String(txtPassword.getPassword()));

        if (controller.login()) {
            // Close login frame and open main application frame To-DO
            dispose();
            new MainFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
