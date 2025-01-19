package com.teammanagerui;

import com.formdev.flatlaf.FlatLightLaf;
import com.teammanagerui.utils.SessionManager;
import com.teammanagerui.view.LoginFrame;
import com.teammanagerui.view.MainFrame;

import javax.swing.*;

public class TeamManagerGUI {
    public static void main(String[] args) {
        try {
            // Set FlatLaf look and feel
            FlatLightLaf.setup();
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        SwingUtilities.invokeLater(() -> {
            if (SessionManager.isLoggedIn()) {
                new MainFrame().setVisible(true);
            } else {
                new LoginFrame().setVisible(true);
            }
        });

    }
}
