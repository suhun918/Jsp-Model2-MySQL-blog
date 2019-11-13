<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<button onClick="sendPost3()">요청하기</button>

	<div id="demo"></div>
	<script>
		
		let postNum2 = 0;
		let postNum3 = 0;
		let sum = 0;
	
		function sendPost2() {
			var xhttp = new XMLHttpRequest();

			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					postNum2 = this.responseText;
					console.log("postNum2 : "+postNum2);
				}
			};

			xhttp.open("POST", "http://localhost:8000/blog/test2", false);
			xhttp.setRequestHeader("Content-type", "text/plain");
			xhttp.send();
		}
		
		function sendPost3() {
			sendPost2();
			
			var xhttp = new XMLHttpRequest();

			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					postNum3 = this.responseText;
					console.log("postNum3 : "+postNum3);
					//Number(String) 숫자로 변환해주는 방법
					sum = Number(postNum2) + Number(postNum3);
					console.log("sum : "+sum)
				}
			};

			xhttp.open("POST", "http://localhost:8000/blog/test3", true);
			xhttp.setRequestHeader("Content-type", "text/plain");
			xhttp.send();
		}
	</script>
</body>
</html>
