<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách lớp học</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
<div class="container">
    <table class="table table-striped table-bordered table-hover">
        <thead class="thead-dark">
        <tr>
            <h1 class="text-center" >Danh sách lớp học</h1></tr>
        <tr>
            <th>ID</th>
            <th>Tên</th>
            <th>Email</th>
            <th>Giới tính</th>
            <th>Điểm</th>
            <th>Xếp loại</th>
            <th>Lớp học</th>
            <th>Chỉnh sửa</th>
            <th>Xóa</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${studentList}" var="s">
            <tr>
                <td>${s.id}</td>
                <td>${s.name}</td>
                <td>${s.email}</td>
            <td>
            <c:if test="${s.gender == 1}">
                Nam
            </c:if>
            <c:if test="${s.gender == 0}">
                Nữ
            </c:if>
            </td>
                <td>${s.point}</td>
                <td>
                    <c:choose>
                        <c:when test="${s.point > 8.9}">
                            Loại giỏi
                        </c:when>
                        <c:when test="${s.point > 7.9}">
                            Loại khá
                        </c:when>
                        <c:when test="${s.point > 6.9}">
                            Loại Trung binh
                        </c:when>
                        <c:when test="${s.point < 6.9}">
                            Yếu
                        </c:when>
                    </c:choose>
                </td>
                <td>
                    ${s.clazz.name}
                </td>
                <td><a href="?action=edit&id=${s.id}" class="btn btn-primary">
                    <i class="fas fa-edit"></i> Chỉnh sửa</a></td>
            <td><button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal" data-id="${s.id}" data-name="${s.name}">
                <i class="fas fa-trash-alt"></i> Xóa
            </button></td>
                </c:forEach>
        </tbody>
    </table>
    <div>
        <a href="create.jsp" class="btn btn-primary">Thêm mới học viên</a>
    </div>
</div>

</body>
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteModalLabel">Xác nhận xóa</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Bạn có chắc chắn muốn xóa sinh viên <strong id="studentName"></strong> không?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>

                <a id="confirmDelete" href="#" class="btn btn-danger">Xóa</a>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>

    $('#deleteModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var studentId = button.data('id');
        var studentName = button.data('name');

        var modal = $(this);
        modal.find('.modal-body #studentName').text(studentName);

        var deleteUrl = '?action=delete&id=' + studentId;
        modal.find('.modal-footer #confirmDelete').attr('href', deleteUrl);
    });
</script>
</html>