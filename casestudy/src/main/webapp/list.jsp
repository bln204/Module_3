<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách lớp học</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>


<div class="container">
    <table class="table table-striped table-bordered table-hover" id="mainTable">
        <thead class="table-dark">
        <tr>
            <h1 class="text-center mb-4">Danh sách lớp học</h1>
        </tr>

        <tr>
            <div>
                <form action="?action=search" method="get" class="row g-3">
                    <input type="hidden" name="action" value="search">

                    <div class="col-sm-12 d-flex justify-content-start">
                        <input type="text" class="form-control  me-2" name="name" value="${key}" placeholder="Nhập tên để tìm kiếm">
                        <input type="text" class="form-control  me-2" name="email" value="${key}" placeholder="Nhập email để tìm kiếm">
                        <button type="submit" class="btn btn-primary btn-sm">
                            <i class="bi bi-search"></i>
                        </button>
                    </div>
                </form>
            </div>
        </tr>

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
                            Loại Trung bình
                        </c:when>
                        <c:when test="${s.point <= 6.9}">
                            Yếu
                        </c:when>
                    </c:choose>
                </td>
                <td>${s.clazz.name}</td>
                <td><a href="?action=edit&id=${s.id}" class="btn btn-primary">
                    <i class="fas fa-edit"></i> Chỉnh sửa</a></td>
                <td><button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteModal" data-id="${s.id}" data-name="${s.name}">
                    <i class="fas fa-trash-alt"></i> Xóa
                </button></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div>
        <a href="?action=create" class="btn btn-primary">Thêm mới học viên</a>
    </div>
</div>

<!-- Modal for deletion confirmation -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteModalLabel">Xác nhận xóa</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Bạn có chắc chắn muốn xóa sinh viên <strong id="studentName"></strong> không?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <a id="confirmDelete" href="#" class="btn btn-danger">Xóa</a>
            </div>
        </div>
    </div>
</div>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>

<script>
    $(document).ready(function() {
        $('#mainTable').DataTable({
            "dom": 'lrtip',
            "lengthChange": false,
            "pageLength": 5,
            "columnDefs": [
                { "orderable": false, "targets": 7 }
            ]
        });
    });

    // Handling modal data for delete
    var deleteModal = document.getElementById('deleteModal');
    deleteModal.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget;
        var studentId = button.getAttribute('data-id');
        var studentName = button.getAttribute('data-name');

        var modalBody = deleteModal.querySelector('.modal-body #studentName');
        modalBody.textContent = studentName;

        var confirmDeleteLink = deleteModal.querySelector('.modal-footer #confirmDelete');
        confirmDeleteLink.setAttribute('href', '?action=delete&id=' + studentId);
    });
</script>

</body>
</html>