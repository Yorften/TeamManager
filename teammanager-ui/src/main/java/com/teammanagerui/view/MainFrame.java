package com.teammanagerui.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        setTitle("Employee Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();

        setLocationRelativeTo(null); // Center the frame
    }

    private void initUI() {
        // Sidebar Panel
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));
        sidebarPanel.setBackground(Color.LIGHT_GRAY);

        // Content Panel with CardLayout
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        contentPanel.add(new EmployeesPanel(), "Employees");
        contentPanel.add(new UsersPanel(), "Users");

        JButton btnEmployees = new JButton("Employees");
        JButton btnUsers = new JButton("Users");

        btnEmployees.addActionListener(e -> cardLayout.show(contentPanel, "Employees"));
        btnUsers.addActionListener(e -> cardLayout.show(contentPanel, "Users"));

        sidebarPanel.add(Box.createVerticalStrut(20));
        sidebarPanel.add(btnEmployees);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnUsers);

        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }
}
