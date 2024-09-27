<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm học viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2 class="my-4 text-center">Thêm mới học viên</h2>
    <form action="?action=create" method="post">
        <div class="mb-3">
            <label for="name" class="form-label">Tên học viên:</label>
            <input type="text" class="form-control" id="name" name="name" value="${s.name}" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email học viên:</label>
            <input type="email" class="form-control" id="email" value="${s.email}"name="email" required>
        </div>
        <div class="mb-3">
            <label for="gender" class="form-label">Giới tính:</label>
            <select class="form-select" id="gender" name="gender" value="${s.gender}" required>
                <option value="1">Nam</option>
                <option value="0">Nữ</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="point" class="form-label">Điểm:</label>

            <input type="number" class="form-control" id="point" name="point" min="0" value="${s.point}" required>
        </div>
        <div class="mb-3">
            <label for="clazzId" class="form-label">Chọn lớp</label>
            <select class="form-select" id="clazzId" name="clazzId" >
                <c:forEach items="${list}" var="clazz">
                    <option value="${clazz.id}"> ${clazz.name} </option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Thêm mới</button>
    </form>
</div>
    <!-- Modal thông báo lỗi -->
<div class="modal fade" id="emailErrorModal" tabindex="-1" aria-labelledby="emailErrorModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="emailErrorModalLabel">Lỗi</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                ${errorMessage}
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    <% if (request.getAttribute("errorMessage") != null) { %>
    let emailErrorModal = new bootstrap.Modal(document.getElementById('emailErrorModal'));
    emailErrorModal.show();
    <% } %>
</script>
</body>
</html>