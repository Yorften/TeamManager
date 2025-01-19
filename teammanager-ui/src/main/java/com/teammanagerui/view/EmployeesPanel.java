package com.teammanagerui.view;

import javax.swing.*;
import java.awt.*;

public class EmployeesPanel extends JPanel {
    public EmployeesPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("Employees works!", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
