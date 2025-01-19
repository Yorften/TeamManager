package com.teammanagerui.view;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class EmployeesPanel extends JPanel {
    public EmployeesPanel() {
        setLayout(new MigLayout("fill", "[grow]", "[][][grow]"));

        add(new JLabel("Employees"), "wrap");
        add(new JTextField("Search Employees", 20), "growx, wrap");
        add(new JScrollPane(new JTable()), "grow");
    }
}