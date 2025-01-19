package com.teammanagerui.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.teammanagerui.model.Employee;
import com.teammanagerui.model.enums.Department;
import com.teammanagerui.model.enums.EmploymentStatus;
import com.teammanagerui.service.EmployeeService;

import net.miginfocom.swing.MigLayout;

public class EditEmployeeForm extends JDialog {
    private final Employee employee;
    private final EmployeesPanel employeesPanel; // Reference to refresh table
    private final EmployeeService employeeService;
    private JTextField txtFullName;
    private JTextField txtJobTitle;
    private JComboBox<Department> cmbDepartment;
    private JComboBox<EmploymentStatus> cmbEmploymentStatus;
    private DatePicker datePickerHireDate;
    private JTextField txtContactInfo;
    private JTextField txtAddress;

    public EditEmployeeForm(Employee employee, EmployeesPanel employeesPanel) {
        this.employeeService = new EmployeeService();
        this.employee = employee;
        this.employeesPanel = employeesPanel;
        setTitle("Edit Employee");
        setSize(400, 500);
        setLayout(new MigLayout("fill", "[grow]", "[][][][][][][][][grow][]"));
        setLocationRelativeTo(null);

        // Initialize date picker
        DatePickerSettings datePickerSettings = new DatePickerSettings();
        datePickerSettings.setAllowKeyboardEditing(false);
        datePickerHireDate = new DatePicker(datePickerSettings);

        // Form fields
        txtFullName = new JTextField(employee.getFullName(), 20);
        txtJobTitle = new JTextField(employee.getJobTitle(), 20);
        cmbDepartment = new JComboBox<>(Department.values());
        cmbDepartment.setSelectedItem(employee.getDepartment());
        cmbEmploymentStatus = new JComboBox<>(EmploymentStatus.values());
        cmbEmploymentStatus.setSelectedItem(employee.getEmploymentStatus());
        datePickerHireDate.setDate(employee.getHireDate());
        txtContactInfo = new JTextField(employee.getContactInformation(), 20);
        txtAddress = new JTextField(employee.getAddress(), 20);

        add(new JLabel("Full Name:"), "wrap");
        add(txtFullName, "growx, wrap");
        add(new JLabel("Job Title:"), "wrap");
        add(txtJobTitle, "growx, wrap");
        add(new JLabel("Department:"), "wrap");
        add(cmbDepartment, "growx, wrap");
        add(new JLabel("Employment Status:"), "wrap");
        add(cmbEmploymentStatus, "growx, wrap");
        add(new JLabel("Hire Date (YYYY-MM-DD):"), "wrap");
        add(datePickerHireDate, "growx, wrap");
        add(new JLabel("Contact Information:"), "wrap");
        add(txtContactInfo, "growx, wrap");
        add(new JLabel("Address:"), "wrap");
        add(txtAddress, "growx, wrap");

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(e -> handleSubmit());
        add(btnSubmit, "span, growx, align center");
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> handleDelete());
        add(btnDelete, "span, growx, align center");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void handleSubmit() {
        try {
            employee.setFullName(txtFullName.getText());
            employee.setJobTitle(txtJobTitle.getText());
            employee.setDepartment(cmbDepartment.getSelectedItem().toString());
            employee.setEmploymentStatus((EmploymentStatus) cmbEmploymentStatus.getSelectedItem());
            employee.setHireDate(datePickerHireDate.getDate());
            employee.setContactInformation(txtContactInfo.getText());
            employee.setAddress(txtAddress.getText());

            employeeService.updateEmployee(employee);

            JOptionPane.showMessageDialog(this,
                    "Employee updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            employeesPanel.fetchAndDisplayEmployees(); // Refresh table
            dispose(); // Close the dialog
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to update employee: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        try {
            
            employeeService.deleteEmployee(employee);

            JOptionPane.showMessageDialog(this,
                    "Employee deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            employeesPanel.fetchAndDisplayEmployees(); // Refresh table
            dispose(); // Close the dialog
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to update employee: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
