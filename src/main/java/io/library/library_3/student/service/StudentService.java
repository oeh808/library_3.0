package io.library.library_3.student.service;

import java.util.List;

import io.library.library_3.student.entity.Student;

public interface StudentService {
    // Create
    public Student signUpStudent(Student student);

    // Read
    public List<Student> getStudents();

    public List<Student> getStudentsByAdress(String address);

    public List<Student> getStudentsByCollege(String college);

    public List<Student> getRegisteredStudents();

    public Student getStudent(int id);

    // Update
    public Student updateStudent(Student student);

    public Student approveStudent(int id);

    // Delete
    public void removeStudent(int id);
}
