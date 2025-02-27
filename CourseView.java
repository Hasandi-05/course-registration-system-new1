package edu.ijse.layered.view;

import edu.ijse.layered.dto.CourseDto;
import edu.ijse.layered.service.custom.CourseService;
import edu.ijse.layered.service.custom.impl.CourseServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CourseView extends JFrame {
    private final CourseService courseService = new CourseServiceImpl();
    private JTextField txtCourseId, txtCourseName, txtDescription, txtDuration;
    private JTable tableCourses;
    private DefaultTableModel tableModel;

    public CourseView() {
        setTitle("Course Management");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        loadDataToTable();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CourseView().setVisible(true));
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("Course ID:"));
        txtCourseId = new JTextField();
        panelForm.add(txtCourseId);

        panelForm.add(new JLabel("Course Name:"));
        txtCourseName = new JTextField();
        panelForm.add(txtCourseName);

        panelForm.add(new JLabel("Description:"));
        txtDescription = new JTextField();
        panelForm.add(txtDescription);

        panelForm.add(new JLabel("Duration (in days):"));
        txtDuration = new JTextField();
        panelForm.add(txtDuration);

        JPanel panelButtons = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new AddCourseActionListener());
        panelButtons.add(btnAdd);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new UpdateCourseActionListener());
        panelButtons.add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new DeleteCourseActionListener());
        panelButtons.add(btnDelete);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new SearchCourseActionListener());
        panelButtons.add(btnSearch);

        tableModel = new DefaultTableModel(new String[]{"Course ID", "Course Name", "Description", "Duration"}, 0);
        tableCourses = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableCourses);

        add(panelForm, BorderLayout.NORTH);
        add(panelButtons, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void loadDataToTable() {
        try {
            List<CourseDto> courses = courseService.getAllCourses();
            tableModel.setRowCount(0);
            for (CourseDto course : courses) {
                tableModel.addRow(new Object[]{
                        course.getCourseId(),
                        course.getCourseName(),
                        course.getDescription(),
                        course.getDuration()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load course data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class AddCourseActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String courseId = txtCourseId.getText().trim();
                String courseName = txtCourseName.getText().trim();
                String description = txtDescription.getText().trim();
                String durationText = txtDuration.getText().trim();

                if (courseId.isEmpty() || courseName.isEmpty() || description.isEmpty() || durationText.isEmpty()) {
                    JOptionPane.showMessageDialog(CourseView.this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int duration = Integer.parseInt(durationText);
                CourseDto course = new CourseDto(courseId, courseName, description, duration);
                courseService.addCourse(course);
                JOptionPane.showMessageDialog(CourseView.this, "Course added successfully!");
                loadDataToTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(CourseView.this, "Invalid duration format. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(CourseView.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
