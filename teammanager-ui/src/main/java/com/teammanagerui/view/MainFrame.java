package com.teammanagerui.view;

import javax.swing.*;

import com.teammanagerui.controller.UserController;
import com.teammanagerui.model.User;
import com.teammanagerui.service.UserService;
import com.teammanagerui.utils.SessionManager;

import net.miginfocom.swing.MigLayout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private User currentUser;
    private final ExecutorService executor;
    private final UserController userController;

    public MainFrame() {
        setTitle("Employee Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        UserService userService = new UserService();
        this.userController = new UserController(userService);

        executor = Executors.newSingleThreadExecutor();
        initUI();

        // Show loading state and fetch user info
        fetchUserInfo();
    }

    private void initUI() {
        // Sidebar Panel with responsive layout
        sidebarPanel = new JPanel(new MigLayout("fill, insets 10, gap 1", "[]", "[top][][]"));
        sidebarPanel.setPreferredSize(new Dimension(getWidth() / 4, getHeight()));
        sidebarPanel.setBackground(Color.LIGHT_GRAY);

        // Content Panel with CardLayout
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Add content panels
        contentPanel.add(new EmployeesPanel(), "Employees");
        contentPanel.add(new UsersPanel(), "Users");

        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void fetchUserInfo() {
        sidebarPanel.removeAll();
        sidebarPanel.add(new JLabel("Loading..."), "center");
        sidebarPanel.revalidate();
        sidebarPanel.repaint();

        executor.submit(() -> {
            try {
                currentUser = userController.getUserInfo();

                SwingUtilities.invokeLater(this::updateSidebarWithUserInfo);
            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                        this,
                        "Failed to fetch user info: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE));
            }
        });
    }

    private void updateSidebarWithUserInfo() {
        sidebarPanel.removeAll();

        // Top navigation buttons
        JButton btnEmployees = new JButton("Employees");
        btnEmployees.addActionListener(e -> cardLayout.show(contentPanel, "Employees"));
        sidebarPanel.add(btnEmployees, "growx, wrap");

        if (currentUser != null && currentUser.getRole().getName().equals("ROLE_ADMIN")) {
            JButton btnUsers = new JButton("Users");
            btnUsers.addActionListener(e -> cardLayout.show(contentPanel, "Users"));
            sidebarPanel.add(btnUsers, "growx, wrap");
        }

        // User info section
        JPanel userInfoPanel = new JPanel(new MigLayout("fillx, insets 10, gap 5", "[][right]", "[]"));
        userInfoPanel.setBackground(Color.LIGHT_GRAY);

        // Username
        JLabel lblUserName = new JLabel(currentUser != null ? currentUser.getUsername() : "Unknown User");
        lblUserName.setFont(new Font("Arial", Font.BOLD, 14));
        userInfoPanel.add(lblUserName, "growx");

        // Logout button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> handleLogout());
        userInfoPanel.add(logoutBtn);

        // Add user info panel to the sidebar
        sidebarPanel.add(userInfoPanel, "dock south, gapbottom 10");

        sidebarPanel.revalidate();
        sidebarPanel.repaint();
    }

    private void handleLogout() {
        SessionManager.logout();
        dispose();
        new LoginFrame().setVisible(true);
    }
}
