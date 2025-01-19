package com.teammanagerui.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.teammanagerui.model.Employee;
import com.teammanagerui.model.User;
import com.teammanagerui.model.enums.Roles;
import com.teammanagerui.service.EmployeeService;
import com.teammanagerui.utils.SessionManager;

import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;

@Slf4j
public class EmployeesPanel extends JPanel {

    private User user;
    private final EmployeeService employeeService;
    private JTable usersTable;
    private DefaultTableModel tableModel;
    private List<Employee> employeeList;

    public EmployeesPanel() {
        this.employeeService = new EmployeeService();
        this.user = SessionManager.getUser();
        setLayout(new MigLayout("fill", "[grow]", "[][][grow]"));

        add(new JLabel("Employees"), "wrap, growx");
        if (!user.getRole().getName().equals(Roles.ROLE_MANAGER.name())) {
            JButton addEmployeeButton = new JButton("Add Employee");
            addEmployeeButton.addActionListener(e -> openAddEmployeeModal());
            add(addEmployeeButton, "wrap, align right");
        }

        JTextField searchField = new JTextField("", 20);
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                fetchAndDisplayEmployees(query);
            }
        });

        add(searchField, "growx, wrap");
        tableModel = new DefaultTableModel(
                new String[] { "ID", "Full Name", "Job", "Department", "Status", "Hire Date" }, 0);
        usersTable = new JTable(tableModel);
        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(usersTable), "grow");

        // Add row selection listener
        usersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && usersTable.getSelectedRow() != -1) {
                    int selectedRow = usersTable.getSelectedRow();
                    Employee selectedEmployee = employeeList.get(selectedRow);
                    openEmployeeDetails(selectedEmployee);
                }
            }
        });

        fetchAndDisplayEmployees();

    }

    private void fetchAndDisplayEmployees(String query) {
        SwingUtilities.invokeLater(() -> {
            try {
                employeeList = (query == null || query.isEmpty())
                        ? employeeService.fetchAllEmployees()
                        : employeeService.searchEmployees(query);
                populateTable(employeeList);
            } catch (Exception e) {
                log.error("Error fetching employees", e);
                JOptionPane.showMessageDialog(this,
                        "Failed to fetch employees: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    public void fetchAndDisplayEmployees() {
        SwingUtilities.invokeLater(() -> {
            try {
                employeeList = employeeService.fetchAllEmployees();
                populateTable(employeeList);
            } catch (Exception e) {
                log.error("error", e);
                JOptionPane.showMessageDialog(this, "Failed to fetch employees: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void populateTable(List<Employee> employees) {
        tableModel.setRowCount(0); // Clear existing data
        for (Employee employee : employees) {
            tableModel.addRow(new Object[] {
                    employee.getId(),
                    employee.getFullName(),
                    employee.getJobTitle(),
                    employee.getDepartment(),
                    employee.getEmploymentStatus().name(),
                    employee.getHireDate()
            });
        }
    }

    private void openAddEmployeeModal() {
        new AddEmployeeForm(this).setVisible(true);
    }

    private void openEmployeeDetails(Employee employee) {
        new EditEmployeeForm(employee, this).setVisible(true);
    }

}