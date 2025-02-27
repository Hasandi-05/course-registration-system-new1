package edu.ijse.layered.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentView extends JFrame {
    private static final String URL = "jdbc:mysql://localhost:3306/course_registration?useSSL=false&serverTimezone=UTC";
    private static final String USER = "your_user";  // Replace with actual username
    private static final String PASSWORD = "your_password"; // Replace with actual password
    private final JTable tblStudents;
    private final DefaultTableModel tableModel;
    private final JTextField txtSearch;
    private final JButton btnSearch;

    public StudentView() {
        setTitle("Student Records");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelSearch = new JPanel();
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Search");
        panelSearch.add(new JLabel("Search by Name or Email:"));
        panelSearch.add(txtSearch);
        panelSearch.add(btnSearch);
        add(panelSearch, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Name", "Email", "Contact", "DOB"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tblStudents = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblStudents);
        add(scrollPane, BorderLayout.CENTER);

        fetchStudents("");
        btnSearch.addActionListener(e -> fetchStudents(txtSearch.getText()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentView().setVisible(true));
    }

    private void fetchStudents(String searchQuery) {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM student WHERE name LIKE ? OR email LIKE ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "%" + searchQuery + "%");
                stmt.setString(2, "%" + searchQuery + "%");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("contact"),
                            rs.getString("dob")
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
