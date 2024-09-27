package com.example.service;

import com.example.model.Clazz;
import com.example.model.Student;
import com.example.repository.student_repository.IStudentRepository;
import com.example.repository.student_repository.StudentRepository;

import java.util.List;

public class StudentService implements IStudentService {
    private final IStudentRepository iStudentRepository = new StudentRepository();

    @Override
    public void addNewStudent(Student student) {
        iStudentRepository.addNewStudent(student);
    }

    @Override
    public void deleteStudent(int id) {
        iStudentRepository.deleteStudent(id);
    }

    @Override
    public void save(Student student) {
        iStudentRepository.save(student);
    }

    @Override
    public List<Student> searchStudent(String name, String email) {
        return iStudentRepository.searchStudent(name, email);
    }

    @Override
    public Student getStudentById(int id) {
        return iStudentRepository.getStudentById(id);
    }

    @Override
    public List<Student> findAll() {
        return iStudentRepository.findAll();
    }

    @Override
    public List<Clazz> findAllClass() {
        return iStudentRepository.findALLClass();
    }

    @Override
    public Student findStudentByEmail(String email) {
        return iStudentRepository.findStudentByEmail(email);
    }
}
