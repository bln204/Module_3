package com.example.controller;


import com.example.model.Clazz;
import com.example.model.Student;
import com.example.service.IStudentService;
import com.example.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentServlet", value = "/")
public class StudentServlet extends HttpServlet {
    private final IStudentService iStudentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreateForm(request, response);
                break;
            case "edit":
                showUpdateForm(request, response);
                break;
            case "delete":
                deleteStudent(request, response);
                break;
            case "search":
                searchStudent(request, response);
                break;
            default:
                findAll(request, response);
        }
    }

    private void searchStudent(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        List<Student> studentList = iStudentService.searchStudent(name, email);
        request.setAttribute("studentList", studentList);
        request.setAttribute("name", name);
        request.setAttribute("email", email);
        try {
            request.getRequestDispatcher("/list.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }


    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Clazz> clazzList = iStudentService.findAllClass();
        request.setAttribute("list", clazzList);
        request.getRequestDispatcher("/create.jsp").forward(request, response);
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Student students = iStudentService.getStudentById(id);
        List<Clazz> classes = iStudentService.findAllClass();
        request.setAttribute("list", classes);
        request.setAttribute("student", students);
        request.getRequestDispatcher("/update.jsp").forward(request, response);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        iStudentService.deleteStudent(id);
        response.sendRedirect(request.getContextPath() + "/");
    }

    private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> studentList = iStudentService.findAll();
        request.setAttribute("studentList", studentList);
        request.getRequestDispatcher("/list.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                addNewStudent(request, response);
                break;
            case "edit":
                save(request, response);
                break;
            case "delete":
                deleteStudent(request, response);
                break;
            default:
                findAll(request, response);
        }
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        int gender = Integer.parseInt(request.getParameter("gender"));
        int point = Integer.parseInt(request.getParameter("point"));

        int clazzId = Integer.parseInt(request.getParameter("clazzId"));
        Clazz clazz = new Clazz(clazzId, "");
        if (validateName(name)) {
            request.setAttribute("errorMessage", "Tên chỉ chứa ký tự và khoảng trắng. Không vượt quá 50 ký tự!");
            forwardUpdate(request, response, name, email, gender, point, clazzId);
            return;
        }
        if (iStudentService.findStudentByEmail(email) != null && iStudentService.findStudentByEmail(email).getId() != id) {
            request.setAttribute("errorMessage", "Email đã tồn tại!");
            forwardUpdate(request, response, name, email, gender, point, clazzId);
            return;
        }
        if (point < 0 || point > 10){
            request.setAttribute("errorMessage", "Điểm phải nằm trong khoảng từ 0 đến 10!");
            forwardUpdate(request, response, name, email, gender, point, clazzId);
            return;
        }
        Student student = new Student(id, name, email, gender, clazz, point);
        iStudentService.save(student);
        try {
            response.sendRedirect(request.getContextPath() + "/list");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void addNewStudent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        int gender = Integer.parseInt(request.getParameter("gender"));
        int point = Integer.parseInt(request.getParameter("point"));
        int clazzId = Integer.parseInt(request.getParameter("clazzId"));
        Clazz clazz = new Clazz(clazzId);
        if (validateName(name)) {
            request.setAttribute("errorMessage", "Tên chỉ chứa ký tự và khoảng trắng. Không vượt quá 50 ký tự!");
            forwardCreate(request, response, name, email, gender, point, clazzId);
            return;
        }
        if (iStudentService.findStudentByEmail(email) != null) {
            request.setAttribute("errorMessage", "Email đã tồn tại!");
            forwardCreate(request, response, name, email, gender, point, clazzId);
            return;
        }
        if (point < 0 || point > 10){
            request.setAttribute("errorMessage", "Điểm phải nằm trong khoảng từ 0 đến 10!");
            forwardCreate(request, response, name, email, gender, point, clazzId);
            return;
        }

        Student student = new Student(name, email, gender, point, clazz);
        iStudentService.addNewStudent(student);
        try {
            List<Student> studentList = iStudentService.findAll();
            request.setAttribute("studentList", studentList);
            response.sendRedirect(request.getContextPath() + "/list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean validateName(String name) {
        return !name.matches("^[\\p{L}\\s]{1,50}$");
    }


    private void forwardCreate(HttpServletRequest request, HttpServletResponse response, String name, String email, int gender, int point, int classId) {
        Student student = new Student(name, email, gender, point, new Clazz(classId));
        request.setAttribute("s", student);
        List<Clazz> classList = iStudentService.findAllClass();
        request.setAttribute("list", classList);
        try {
            request.getRequestDispatcher("create.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void forwardUpdate(HttpServletRequest request, HttpServletResponse response, String name, String email, int gender, int point, int classId) {
        Student student = new Student(name, email, gender, point, new Clazz(classId));
        request.setAttribute("student", student);
        List<Clazz> classList = iStudentService.findAllClass();
        request.setAttribute("list", classList);
        try {
            request.getRequestDispatcher("update.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
