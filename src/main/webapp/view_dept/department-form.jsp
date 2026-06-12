<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${dept.id == null ? "Thêm Phòng Ban" : "Sửa Phòng Ban"}</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="container mt-5">

<div class="card">
    <div class="card-header bg-primary text-white">
        <h3 class="mb-0">${dept.id == null ? "➕ THÊM PHÒNG BAN MỚI" : "✏️ CHỈNH SỬA PHÒNG BAN"}</h3>
    </div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/departments" method="post">
            <div class="form-group">
                <label for="id">Mã Phòng Ban</label>
                <input type="text" class="form-control" id="id" name="id" value="${dept.id}" ${dept.id == null ? "" : "readonly"} required>
                <c:if test="${dept.id != null}">
                    <small class="text-muted">Không thể thay đổi mã phòng ban khi đang chỉnh sửa.</small>
                </c:if>
            </div>
            <div class="form-group">
                <label for="name">Tên Phòng Ban</label>
                <input type="text" class="form-control" id="name" name="name" value="${dept.name}" required>
            </div>
            <div class="form-group">
                <label for="description">Mô Tả</label>
                <textarea class="form-control" id="description" name="description" rows="3">${dept.description}</textarea>
            </div>

            <div class="mt-4">
                <button type="submit" class="btn btn-success">💾 Lưu Thông Tin</button>
                <a href="${pageContext.request.contextPath}/departments" class="btn btn-secondary">🔙 Quay Lại</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
