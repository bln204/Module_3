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

    private static final String Get_id_student = " CALL GetStudentById(?)";

    private static final String FIND_ALL = "CALL  GetAllStudents()";

    private static final String INSERT_STUDENT =
            "INSERT INTO student(student_name, email, gender, student_point, id_class)" + "VALUES(?,?,?,?,?)";

    private static final String UPDATE_STUDENT =
            "UPDATE student SET student_name = ?, email = ?, gender = ?, student_point=?, id_class =? WHERE id_student=?";

    private static final String SEARCH_STUDENT =
            "SELECT s.id_student, s.student_name, s.email, s.gender,s.student_point, c.id_class, c.class_name" +
                    "    FROM student s " +
                    "    JOIN class c ON s.id_class = c.id_class " +
                    "    WHERE s.student_name LIKE ?";

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
            PreparedStatement ps = connection.prepareStatement(INSERT_STUDENT);
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setInt(3, student.getGender());
            ps.setInt(4, student.getPoint());
            ps.setInt(5, student.getClazz().getId());
            ps.executeUpdate();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void save(Student student) {
        Connection connection = baseRepository.getConnection();
        try{
            PreparedStatement ps = connection.prepareStatement(UPDATE_STUDENT);
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setInt(3, student.getGender());
            ps.setInt(4, student.getPoint());
            ps.setInt(5, student.getClazz().getId());
            ps.setInt(6, student.getId());
            ps.executeUpdate();
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }

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

    @Override
    public List<Student> searchStudent(String name, String email) {
        Connection connection = baseRepository.getConnection();
        List<Student> students = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT student.id_student, student.student_name, student.email, student.gender, ");
        query.append("student.student_point, class.id_class, class.class_name ");
        query.append("FROM student JOIN class ON student.id_class = class.id_class ");
        query.append("WHERE 1=1 ");


        if (name != null && !name.isEmpty()) {
            query.append("AND student.student_name LIKE ? ");
        }
        if (email != null && !email.isEmpty()) {
            query.append("AND student.email LIKE ? ");
        }

        try (PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int parameterIndex = 1;


            if (name != null && !name.isEmpty()) {
                ps.setString(parameterIndex++, "%" + name + "%");
            }


            if (email != null && !email.isEmpty()) {
                ps.setString(parameterIndex++, "%" + email + "%");
            }

            ResultSet resultSet = ps.executeQuery();


            while (resultSet.next()) {
                int studentId = resultSet.getInt("id_student");
                String studentName = resultSet.getString("student_name");
                String studentEmail = resultSet.getString("email");
                int gender = resultSet.getInt("gender");
                int point = resultSet.getInt("student_point");
                int classId = resultSet.getInt("id_class");
                String className = resultSet.getString("class_name");

                students.add(new Student(studentId, studentName, studentEmail, gender, point, new Clazz(classId, className)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    @Override
    public Student findStudentByEmail(String email) {
        String FIND_BY_EMAIL = "SELECT * FROM student WHERE email = ?";
        Connection connection = baseRepository.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Student(
               resultSet.getInt("id_student"),
               resultSet.getString("student_name"),
                resultSet.getString("email"),
                resultSet.getInt("gender"),
               resultSet.getInt("student_point"),
               new Clazz(resultSet.getInt("id_class"))
                );
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

