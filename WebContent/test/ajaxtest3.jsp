<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- fetch -> IE 에서는 작동안됨(관공서에서 쓸 수 없다) -->
	<h1>Fetch API 사용하기 - Promise</h1>
	<button onClick="getNum1()">데이터 가져오기</button>

	<script>
		let num1 = 0;
		let num2 = 0;
		let sum = 0;

		function getNum1() {
			//처음 fetch로 들고온 것은 헤더부터시작해서 잡다한 정보가 엄청 많다.
			//그래서 .then()이후 .then()으로 받아서 걸러준다.
			fetch("http://localhost:8000/blog/test2", {
				method : "POST"
			//변수는 내 마음대로
			}).then(function(data) {
				console.log(data);
				//text로 받으면 절대 오류안남 , json으로 받으면 상대가 json으로 안보냈으면 오류날 수도 있다
				//모르겠으면 text로 받자
				return data.text();
				//return data.json();

				//return된 데이터는 다음 then으로 주입 된다.
			}).then(function(data) {
				num1 = data;//100
				console.log("num1:" + data);
				//완벽하게 데이터를 받은 후에 getNum2를 호출
				getNum2();
			});
		}

		function getNum2() {
			//처음 fetch로 들고온 것은 헤더부터시작해서 잡다한 정보가 엄청 많다.
			//그래서 .then()이후 0.then()으로 받아서 걸러준다.
			fetch("http://localhost:8000/blog/test3", {
				method : "POST",
				body : "안녕"//Json, String, Object, Number
			//변수는 내 마음대로
			}).then(function(data) {
				
				//text로 받으면 절대 오류안남 , json으로 받으면 상대가 json으로 안보냈으면 오류날 수도 있다
				//모르겠으면 text로 받자
				return data.text();
				//return data.json();

				//return된 데이터는 다음 then으로 주입 된다.
			}).then(function(data) {
				num2 = data; //50
				console.log("num2:" +data);
				sum = Number(num1) + Number(num2);
				console.log("sum :" + sum);
			});
		}
	</script>

</body>
</html>
