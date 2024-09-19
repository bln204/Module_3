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
//                showUpdateForm(request, response);
                break;
            case "delete":
                deleteStudent(request, response);
                break;
            default:
                findAll(request, response);
        }
    }


    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Clazz> clazzList = iStudentService.findAllClass();
        request.setAttribute("list", clazzList);
        request.getRequestDispatcher("/create.jsp").forward(request, response);
    }
//    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int id = Integer.parseInt(request.getParameter("id"));
//        Student students = iStudentService.getStudentById(id);
//        if (!students.isEmpty()) {
//            request.setAttribute("student", students.get(0));
//            request.getRequestDispatcher("/update.jsp").forward(request, response);
//        }
//    }

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

    private void save(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        int gender = Integer.parseInt(request.getParameter("gender"));
        int point = Integer.parseInt(request.getParameter("point"));

        int clazzId = Integer.parseInt(request.getParameter("clazzId"));
        Clazz clazz = new Clazz(clazzId, "");
        Student student = new Student(id, name, email, gender, clazz, point);
        iStudentService.save(student);
        try {
            response.sendRedirect(request.getContextPath() + "/list");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void addNewStudent(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException   {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        int gender = Integer.parseInt(request.getParameter("gender"));
        int point = Integer.parseInt(request.getParameter("point"));

        int clazzId = Integer.parseInt(request.getParameter("clazzId"));
        Clazz clazz = new Clazz(clazzId);

        Student student = new Student(name, email, gender, point, clazz);
        iStudentService.addNewStudent(student);
        try {
            response.sendRedirect(request.getContextPath() + "/list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
