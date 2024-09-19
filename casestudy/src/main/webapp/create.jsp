<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm học viên</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2 class="my-4 text-center">Thêm mới học viên</h2>
    <form action="?action=create" method="post">
        <div class="form-group">
            <label for="name">Tên học viên:</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="email">Email học viên:</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="gender">Giới tính:</label>
            <select class="form-control" id="gender" name="gender" required>
                <option value="1">Nam</option>
                <option value="0">Nữ</option>
            </select>
        </div>
        <div class="form-group">
            <label for="point">Điểm:</label>
            <input type="number" class="form-control" id="point" name="point" min="0" required>
        </div>
        <div class="form-group">
            <label for="id_class">Chọn lớp </label>
            <select name="id_class" id="id_class">
                <c:forEach items="${list}" var="clazz">
                    <option value="${clazz.id}"> ${clazz.name} </option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Thêm mới</button>
    </form>
</div>

</body>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</html>
