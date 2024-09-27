<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật thông tin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1 class="my-4">Cập nhật thông tin học viên</h1>

    <form action="?action=edit" method="post">
        <div class="mb-3">
            <input type="hidden" name="id" value="${student.id}">
        </div>

        <div class="mb-3">
            <label for="name" class="form-label">Tên học viên:</label>
            <input type="text" class="form-control" id="name" name="name" value="${student.name}" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email học viên:</label>
            <input type="email" class="form-control" id="email" name="email" value="${student.email}" required>
        </div>
        <div class="mb-3">
            <label for="gender" class="form-label">Giới tính:</label>
            <select class="form-select" id="gender" name="gender" value="${student.gender}" required>
                <option value="0">Nam</option>
                <option value="1">Nữ</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="point" class="form-label">Điểm:</label>
            <input type="number" class="form-control" id="point" name="point" min="0" value="${student.point}" required>
        </div>
        <div class="mb-3">
            <label for="id_class" class="form-label">Chọn lớp:</label>
            <select class="form-select" name="clazzId" id="id_class" >
                <option value="">Chọn lớp</option>
                <c:forEach items="${list}" var="c">
                    <option value="${c.id}" ${c.id == student.clazz.id ? 'selected' : ''}>${c.name}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Cập nhật</button>
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

</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    <% if (request.getAttribute("errorMessage") != null) { %>
    let emailErrorModal = new bootstrap.Modal(document.getElementById('emailErrorModal'));
    emailErrorModal.show();
    <% } %>
</script>
</html>