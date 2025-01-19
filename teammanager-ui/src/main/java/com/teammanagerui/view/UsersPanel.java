package com.teammanagerui.view;

import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.teammanagerui.model.User;
import com.teammanagerui.service.UserService;

import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;

@Slf4j
public class UsersPanel extends JPanel {

    private final UserService userService;
    private JTable usersTable;
    private DefaultTableModel tableModel;

    public UsersPanel() {
        this.userService = new UserService();
        setLayout(new MigLayout("fill", "[grow]", "[][][grow]"));

        // Add title
        add(new JLabel("Users"), "wrap");

        // Create and add the table
        tableModel = new DefaultTableModel(new String[] { "ID", "Username", "Email", "Role" }, 0);
        usersTable = new JTable(tableModel);
        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(usersTable), "grow");

        // Add row selection listener
        usersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && usersTable.getSelectedRow() != -1) {
                    int selectedRow = usersTable.getSelectedRow();
                    String userId = tableModel.getValueAt(selectedRow, 0).toString();
                    openUserDetails(userId);
                }
            }
        });

        // Fetch and display users
        fetchAndDisplayUsers();
    }

    private void fetchAndDisplayUsers() {
        SwingUtilities.invokeLater(() -> {
            try {
                List<User> users = userService.fetchAllUsers();
                populateTable(users);
            } catch (Exception e) {
                log.error("error", e);
                JOptionPane.showMessageDialog(this, "Failed to fetch users: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void populateTable(List<User> users) {
        int i = 1;

        tableModel.setRowCount(0); // Clear existing data
        for (User user : users) {
            tableModel.addRow(new Object[] {
                    i,
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole().getName()
            });
            i++;
        }
    }

    private void openUserDetails(String userId) {
        // TODO: Implement logic to open the user details window/panel
        JOptionPane.showMessageDialog(this, "Opening details for User ID: " + userId, "User Details",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
