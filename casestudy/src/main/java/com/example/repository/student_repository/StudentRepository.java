package com.example.repository.student_repository;

import com.example.model.Clazz;
import com.example.model.Student;
import com.example.repository.BaseRepository;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository implements IStudentRepository {

    private static BaseRepository baseRepository;
    {
        try {
            baseRepository = new BaseRepository();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final String Get_id_student = " CALL GesStudentById(?)";

    private static final String FIND_ALL = "CALL  GetAllStudents()";

    private static final String INSERT_STUDENT =
            "INSERT INTO student(name, email, gender, point)" + "VALUES(?,?,?,?)";

    @Override
    public List<Student> findAll()  {
        Connection connection = baseRepository.getConnection();
        List<Student> list = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id_student");
                String name = resultSet.getString("student_name");
                String email = resultSet.getString("email");
                int gender = resultSet.getInt("gender");
                int point = resultSet.getInt("student_point");
                int class_id = resultSet.getInt("id_class");
                String class_name = resultSet.getString("class_name");
//                name, email, gender, point , clazz
                list.add(new Student(id, name,email,gender,point,new Clazz(class_id, class_name) ));
            }
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public Student getStudentById(int id) {
        Connection connection = baseRepository.getConnection();
        List<Student> list = new ArrayList<>();
        try{
            PreparedStatement ps = connection.prepareStatement(Get_id_student);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                int studentId = resultSet.getInt("id_student");
                String name = resultSet.getString("student_name");
                String email = resultSet.getString("email");
                int gender = resultSet.getInt("gender");
                int point = resultSet.getInt("student_point");
                int classId = resultSet.getInt("id_class");
                String className = resultSet.getString("class_name");
//                name, email, gender, point , clazz
                return (new Student(studentId,name, email,gender,point, new Clazz(classId, className)));
            }
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void addNewStudent(Student student) {
        Connection connection = baseRepository.getConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("INSERT_STUDENT");
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setInt(3, student.getGender());
            ps.setInt(4, student.getPoint());
            ps.executeUpdate();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void save(Student student) {

    }

    @Override
    public void deleteStudent(int id) {
        Connection connection = baseRepository.getConnection();
        try{
            PreparedStatement ps = connection.prepareStatement("DELETE FROM student WHERE id_student =?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Clazz> findALLClass() {
        Connection connection = baseRepository.getConnection();
        List<Clazz> list = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM class");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_class");
                String name = resultSet.getString("class_name");
                list.add(new Clazz(id, name));
            }
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return list;
    }
}
