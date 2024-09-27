CREATE DATABASE casestudy;

USE casestudy;

CREATE TABLE class (
    id_class INT PRIMARY KEY,
    class_name VARCHAR(50)
);

CREATE TABLE student (
    id_student INT PRIMARY KEY AUTO_INCREMENT,
    student_name VARCHAR(50),
    email VARCHAR(30),
    gender BIT,
    student_point DOUBLE,
    id_class INT,
    FOREIGN KEY (id_class) REFERENCES class(id_class)
);
INSERT INTO class (id_class, class_name) VALUES 
(1, 'Class A'),
(2, 'Class B'),
(3, 'Class C');

INSERT INTO student (student_name, email, gender, student_point, id_class) VALUES 
('Alice', 'alice@example.com', 0, 85.5, 1),
('Bob', 'bob@example.com', 1, 90.0, 1),
('Charlie', 'charlie@example.com', 0, 78.5, 2),
('David', 'david@example.com', 1, 88.0, 2),
('Eva', 'eva@example.com', 0, 92.5, 3),
('Frank', 'frank@example.com', 1, 80.0, 3),
('Grace', 'grace@example.com', 0, 75.0, 1),
('Hannah', 'hannah@example.com', 0, 95.0, 2),
('Ivy', 'ivy@example.com', 0, 82.0, 3),
('Jack', 'jack@example.com', 1, 89.0, 1); 

DELIMITER //
CREATE PROCEDURE GetAllStudents()
BEGIN
    SELECT 
        s.id_student, 
        s.student_name, 
        s.email,
        s.gender,
        s.student_point,
        c.id_class,
        c.class_name
    FROM 
        student s
    JOIN 
        class c ON s.id_class = c.id_class;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE GetStudentById(IN id int)
BEGIN
    SELECT 
        s.id_student, 
        s.student_name, 
        s.email,
        s.gender,
        s.student_point,
        c.id_class,
        c.class_name
    FROM 
        student s
    JOIN 
        class c ON s.id_class = c.id_class
	WHERE s.id_student = id;
END //
DELIMITER ;

