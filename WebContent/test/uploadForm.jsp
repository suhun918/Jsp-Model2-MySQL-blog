<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- img__input의 변화를 인지해서 바로 집어넣어주게 -->
	<div>
		<img id ="img__wrap"/>
	</div>

	<!-- enctype는 mime타입에서 찾아봐서 써주자 -->
	<form action="uploadAction.jsp" method ="POST" enctype="multipart/form-data">
		username : <input type="text" name="username" /><br />
		image : <input id ="img__input" type="file" name="userProfile"/><br />
		<input type = "submit" value="전송"/>
	</form>
<script src="/blog/js/jquery-3.2.1.min.js"></script>
<script>
	$("#img__input").on("change", handleImgFile); //1. 어떤 변화가 있을지 2.변화가 있을때 뭘 수행할지(함수)
	
	function handleImgFile(e){
		console.log(e);
		console.log(e.target.files);//배열
		console.log(e.target.files[0]); //배열에서 첫번쨰 추출
		var f = e.target.files[0];
		
		//이미지가 아닌 파일 집어넣으면 걍 리턴시켜버린다
		if(!f.type.match("image.*")){
			console.log("이미지파일이 아닙니다.")
			return;			
		}
		
		var reader = new FileReader();
		
		//얘는 지나갔다가 아래에서 사진이 다 읽어지길 기다렸다가 작동한다
		reader.onload = function(e){
			console.log("==========");
			console.log(e.target);
			console.log(e.target.result); //파일 로딩이 성공한 결과
			$("#img__wrap").attr("src",e.target.result);
		}
		
		//여기서 사진을 다 읽는다
		reader.readAsDataURL(f);
	}
</script>	
</body>
</html>