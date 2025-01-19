package com.teammanagerui.view;

import javax.swing.*;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.teammanagerui.model.Employee;
import com.teammanagerui.model.enums.Department;
import com.teammanagerui.model.enums.EmploymentStatus;
import com.teammanagerui.model.enums.Roles;
import com.teammanagerui.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;
import net.miginfocom.swing.MigLayout;

@Slf4j
public class AddEmployeeForm extends JDialog {

    private final EmployeesPanel employeesPanel;
    private JTextField txtFullName;
    private JTextField txtJobTitle;
    private JComboBox<Department> cmbDepartment;
    private JComboBox<EmploymentStatus> cmbEmploymentStatus;
    private DatePicker datePickerHireDate;
    private JTextField txtContactInfo;
    private JTextField txtAddress;
    private JComboBox<Roles> cmbRoles;

    public AddEmployeeForm(EmployeesPanel employeesPanel) {
        this.employeesPanel = employeesPanel;
        setTitle("Add Employee");
        setSize(400, 500);
        setLayout(new MigLayout("fill", "[grow]", "[][][][][][][][][grow][]"));
        setLocationRelativeTo(null);

        DatePickerSettings datePickerSettings = new DatePickerSettings();
        datePickerSettings.setAllowKeyboardEditing(false); // Disable manual text input
        datePickerHireDate = new DatePicker(datePickerSettings);

        // Form fields
        txtFullName = new JTextField(20);
        txtJobTitle = new JTextField(20);
        cmbDepartment = new JComboBox<>(Department.values());
        cmbEmploymentStatus = new JComboBox<>(EmploymentStatus.values());
        txtContactInfo = new JTextField(20);
        txtAddress = new JTextField(20);
        cmbRoles = new JComboBox<>(Roles.values());

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
        add(new JLabel("Contact Information (+123456789):"), "wrap");
        add(txtContactInfo, "growx, wrap");
        add(new JLabel("Address:"), "wrap");
        add(txtAddress, "growx, wrap");
        add(new JLabel("Role:"), "wrap");
        add(cmbRoles, "growx, wrap");

        // Submit button
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(e -> handleSubmit());
        add(btnSubmit, "span, align center");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void handleSubmit() {
        try {
            Employee employee = new Employee();
            employee.setFullName(txtFullName.getText());
            employee.setJobTitle(txtJobTitle.getText());
            employee.setDepartment(cmbDepartment.getSelectedItem().toString());
            employee.setEmploymentStatus((EmploymentStatus) cmbEmploymentStatus.getSelectedItem());
            employee.setHireDate(datePickerHireDate.getDate());
            employee.setContactInformation(txtContactInfo.getText());
            employee.setAddress(txtAddress.getText());
            employee.setUserId(null); // Set appropriate user ID if needed

            EmployeeService employeeService = new EmployeeService();
            employeeService.createEmployee(employee);

            JOptionPane.showMessageDialog(this, "Employee created successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                    
            employeesPanel.fetchAndDisplayEmployees();

            dispose(); // Close the dialog
        } catch (Exception e) {
            log.error("error", e);
            JOptionPane.showMessageDialog(this, "Failed to create employee: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
