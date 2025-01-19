package com.teammanagerui.view;

import javax.swing.*;
import java.awt.*;

public class UsersPanel extends JPanel {
    public UsersPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("Users works!", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
