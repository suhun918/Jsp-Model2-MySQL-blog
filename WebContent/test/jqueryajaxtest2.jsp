<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$.ajax({
			type : "POST",
			//만들어뒀던 50을 리턴해주는 친구
			url : "/blog/test3",

			//서버에게 기대하는 데이터(서버가 이렇게 준다는 보장은 없다)
			dataType : "text",

			//내가 보내는 데이터가 뭔지 알려준다(MIME)
			contentType : "text/plain; charset=utf-8",

			//내가 보내고싶은 데이터(뭘 넣어도 된다.)
			data : "i love you",

			//xhttp.onreadystatechange = function() {
			//if (this.readyState == 4 && this.status == 200) {
			//연결이 성공했을 때					
			success : function(r) {
				console.log(r);
			},
			//실패했을 때
			error : function() {
				alert("실패");
			}
		});
	});

	function send() {
		$.ajax({
			type : "POST",
			url : "/blog/test6",

			//serialize -> 키/밸류 형식으로 만들어줌
			data : $("#user").serialize(),
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			success : function(r) {
				alert(r);
			},
			error : function(xhr) {
				console.log("xhr :" + xhr);
				console.log("xhr.status :"+xhr.status);

			}
		});
	}
</script>
</head>
<body>
	<form id="user">
		<input type="text" name="username" value="cos" /><br />
		<input type="text" name="password" value="1234" /><br /> 
		<input type="button" value="전송" onClick="send()" />
	</form>

	<div id="demo"></div>
</body>
</html>