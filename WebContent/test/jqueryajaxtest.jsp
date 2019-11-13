<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
	//해당문서가 다 로드되면(그래서 위에다가 js를 걸어도된다.)
	$(document).ready(function() {
		//내부에 사용하고싶은 액션을 넣어주면 된다.
		$("#my_hide").click(function() {
			$("p").hide();
		});
		
		$("#my_animate").click(function(){
			//오브젝트를 파라메터로 넣으려면 중괄호를 쓰고 작성하면 된다.
			$("div").animate({
				//300px를 이동해라
				left: '-300px'
			});
		});
	});
</script>
</head>
<body>
	<h2>This is a heading</h2>

	<p>This is a paragraph.</p>
	<p>This is another paragraph.</p>

	<button id="my_hide">Click me to hide paragraphs</button>
	<br /><br />
	
	<button id="my_animate">Start Animation</button>
	<div style="background: #98bf21; height: 100px; width: 100px; position: relative;"></div>
	


</body>
</html>