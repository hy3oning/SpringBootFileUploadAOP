<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 수정</title>

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
input[type="password"],
input[type="file"] {
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

img {
	display: block;
	margin-top: 10px;
	max-width: 100%;
	height: auto;
	border-radius: 4px;
}
</style>
</head>

<body>

	<div class="container">
		<h2>${item.name} 상품 정보 수정</h2>

		<!-- 수정 폼 시작 -->
		<form:form modelAttribute="item"
			action="/item/update"
			method="post"
			enctype="multipart/form-data">

			<!-- 상품 ID -->
			<div class="form-group">
				<label>상품 아이디</label>
				<form:input path="id" readonly="true" />
			</div>

			<!-- 상품명 -->
			<div class="form-group">
				<label>상품명</label>
				<form:input path="name" required="required" />
			</div>

			<!-- 상품 가격 -->
			<div class="form-group">
				<label>상품 가격</label>
				<form:input path="price" />
			</div>

			<!-- 상품 이미지 -->
			<div class="form-group">
				<label for="picture">상품 이미지</label>

				<!-- 기존 이미지 출력 -->
				<c:if test="${not empty item.id}">
					<img alt="상품 이미지"
						src="/item/display?id=${item.id}" />
				</c:if>

				<!-- 새 이미지 업로드 -->
				<input type="file" name="picture" />
			</div>

			<!-- 상품 설명 -->
			<div class="form-group">
				<label>DESCRIPTION</label>
				<form:textarea path="description" rows="4" />
			</div>

			<!-- 버튼 영역 -->
			<div class="btn-area">
				<button type="submit">수정 등록</button>
				<button type="reset">수정 취소</button>
				<a href="/item/list">돌아가기</a>
			</div>

		</form:form>
		<!-- 수정 폼 끝 -->

	</div>

</body>
</html>