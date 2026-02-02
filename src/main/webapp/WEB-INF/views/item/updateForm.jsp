<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 수정</title>

<style>
.container {
    width: 500px;
    margin: 20px auto;
}

.form-group {
    margin-bottom: 15px;
}

label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
}

input[type="text"],
input[type="password"] {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
}

.btn-area {
    text-align: right;
    margin-top: 20px;
}

button {
    padding: 10px 20px;
    cursor: pointer;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
}

button[type="reset"] {
    background-color: #6c757d;
}
</style>
</head>

<body>

<div class="container">
    <h2>${member.name} 님 정보 수정</h2>

    <!-- 수정 폼 시작 -->
    <form:form modelAttribute="member" action="/member/update" method="post">

        <!-- 회원 번호 -->
        <div class="form-group">
            <label>회원번호</label>
            <form:input path="no" readonly="true"/>
        </div>

        <!-- ID -->
        <div class="form-group">
            <label>ID</label>
            <form:input path="id"/>
        </div>

        <!-- 비밀번호 -->
        <div class="form-group">
            <label>PASSWORD</label>
            <form:password path="pw" placeholder="변경 시에만 입력"/>
        </div>

        <!-- 이름 -->
        <div class="form-group">
            <label>NAME</label>
            <form:input path="name" readonly="true"/>
        </div>

        <!-- 권한 목록 -->
        <div class="form-group">
            <label>권한</label>

            <c:forEach items="${member.authList}" var="auth" varStatus="status">
                <form:select path="authList[${status.index}].auth">
                    <form:option value="ROLE_USER" label="사용자"/>
                    <form:option value="ROLE_MEMBER" label="회원"/>
                    <form:option value="ROLE_ADMIN" label="관리자"/>
                </form:select>
            </c:forEach>

        </div>

        <!-- 버튼 영역 -->
        <div class="btn-area">
            <button type="submit">수정 등록</button>
            <button type="reset">수정 취소</button>
        </div>

    </form:form>
    <!-- 수정 폼 끝 -->

</div>

</body>
</html>
