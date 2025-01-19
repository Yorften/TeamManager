package com.teammanagerui.view;

import java.io.IOException;

import javax.swing.*;

import com.teammanagerui.service.LogService;

import net.miginfocom.swing.MigLayout;

public class LogPanel extends JPanel {

   private final JTextPane logTextPane;
    private final LogService logService;

    public LogPanel() {
        logService = new LogService();
        setLayout(new MigLayout("fill", "[grow]", "[][][grow]"));

        add(new JLabel("Audit Trail"), "wrap");

        logTextPane = new JTextPane();
        logTextPane.setEditable(false); // Disable editing

        JScrollPane scrollPane = new JScrollPane(logTextPane);
        add(scrollPane, "grow");

        fetchLogs();
    }

    private void fetchLogs() {
        SwingUtilities.invokeLater(() -> {
            try {
                String logs = logService.fetchEmployeeAuditLogs();
                logTextPane.setText(logs);
            } catch (IOException e) {
                logTextPane.setText("Failed to fetch logs: " + e.getMessage());
            }
        });
    }

}
