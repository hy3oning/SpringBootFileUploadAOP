<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
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

input[type="text"], textarea {
	width: 100%;
	padding: 8px;
	box-sizing: border-box;
}

textarea {
	height: 200px;
	resize: vertical;
}

.btn-area {
	text-align: right;
}

button {
	padding: 10px 20px;
	cursor: pointer;
	background-color: #007bff;
	color: white;
	border: none;
	border-radius: 4px;
}
</style>
</head>
<body>

	<div class="container">
		<h2>자료실 업로드</h2>
		<form action="/item/create" method="post"
			enctype="multipart/form-data">
			<div class="form-group">
				<label for="name">상품명</label> <input type="text" id="name"
					name="name" placeholder="자료이름을 입력하시오" required>
			</div>

			<div class="form-group">
				<label for="price">가격</label> <input type="number" id="price"
					name="price" placeholder="상품가격을 입력하세요" required>
			</div>

			<div class="form-group">
				<label for="picture">상품파일</label> <input type="file" id="picture"
					name="picture" required>
			</div>
			<div class="form-group">
				<label for="description">상품설명</label>
				<textarea id="description" name="description" rows="5" cols="20"></textarea>


			</div>

			<div class="btn-area">
				<button type="submit">등록하기</button>
				<button type="reset" style="background-color: #6c757d;">취소</button>
			</div>
		</form>
	</div>

</body>
</html>