package io.library.library_3.student.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.library.library_3.error_handling.exceptions.EntityNotFoundException;
import io.library.library_3.student.StudentExceptionMessages;
import io.library.library_3.student.entity.Student;
import io.library.library_3.student.repo.StudentRepo;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepo studentRepo;

    public StudentServiceImpl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public Student signUpStudent(Student student) {
        return studentRepo.save(student);
    }

    @Override
    public List<Student> getStudents() {
        return studentRepo.findAll();
    }

    @Override
    public List<Student> getStudentsByName(String name) {
        return studentRepo.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Student> getStudentsByAdress(String address) {
        return studentRepo.findByAddressIgnoreCase(address);
    }

    @Override
    public List<Student> getStudentsByCollege(String college) {
        return studentRepo.findByCollegeIgnoreCase(college);
    }

    @Override
    public List<Student> getRegisteredStudents() {
        return studentRepo.findByRegistered(true);
    }

    @Override
    public Student getStudent(int id) {
        Optional<Student> opStudent = studentRepo.findById(id);
        if (opStudent.isPresent()) {
            return opStudent.get();
        } else {
            throw new EntityNotFoundException(StudentExceptionMessages.ID_NOT_FOUND(id));
        }
    }

    @Override
    public Student updateStudent(Student student) {
        getStudent(student.getId());
        return studentRepo.save(student);
    }

    @Override
    public Student approveStudent(int id) {
        Student student = getStudent(id);
        student.setRegistered(true);

        return studentRepo.save(student);
    }

    @Override
    public void removeStudent(int id) {
        getStudent(id);
        studentRepo.deleteById(id);
    }

}
