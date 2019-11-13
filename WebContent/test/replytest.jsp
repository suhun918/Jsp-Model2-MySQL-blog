<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
	let replyJS = {
			id: null,
			boardId: 5,
			userId: 3,
			content: "글이 참 좋습니다.",
			createDate: null
	}
	
	function send(){
		//replyJS를 -> json으로 변환(Json.stringify())	
 		//let replyString = JSON.stringify(replyJS);
		//ajax를 호출 -> /blog/test/reply -> POST -> application/json, utf-8
/*  		$.ajax({
			type: "POST",
			url: "/blog/test/reply",
			contentType: "application/json; charset=utf-8",
			data: replyString,
			success: function(r){
				console.log(r);
			},
			error: function(xhr){
				console.log("xhr :" + xhr);
				console.log("xhr.status :"+xhr.status);
			}
		}); */
		fetch("/blog/test/reply",{
			headers:{
				'Content-Type': 'application/json; charset=utf-8'
			},
			method: "POST",
			body: JSON.stringify(replyJS),
		}).then(function(r){
			return r.text();
		}).then(function(r){
			console.log(r)
		});
	}  
		
	
</script>
</head>
<body>
<button onClick="send()">전송</button>
</body>
</html>