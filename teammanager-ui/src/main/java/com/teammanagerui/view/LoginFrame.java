package com.teammanagerui.view;

import com.teammanagerui.controller.LoginController;
import com.teammanagerui.model.LoginModel;
import com.teammanagerui.service.AuthService;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    private final LoginController controller;

    public LoginFrame() {
        // Create model, service, and controller
        LoginModel model = new LoginModel();
        AuthService authService = new AuthService();
        this.controller = new LoginController(model, authService);

        initUI();
    }

    private void initUI() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout("", "[][]", "[][][]"));

        add(new JLabel("Username:"));
        txtUsername = new JTextField(20);
        add(txtUsername, "wrap");

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField(20);
        add(txtPassword, "wrap");

        btnLogin = new JButton("Login");
        add(btnLogin, "span, align center");

        btnLogin.addActionListener(e -> onLoginClick());
        setLocationRelativeTo(null);
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
