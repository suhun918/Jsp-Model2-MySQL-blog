<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/nav.jsp"%>
<!--================Contact Area =================-->
<section class="contact_area">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">



				<form class="row contact_form" action="/blog/user?cmd=profile" method="POST" enctype="multipart/form-data">
					<input type="hidden" name="username" value="${sessionScope.user.username}">
					<div class="col-md-12 text-right">
						<div class="form-group float-right">
							<input class="blog_btn" id="img__input" type="file" name="userProfile" />
						</div>
					</div>
					<div class="col-md-12 text-right">
						<div class="form-group float-right">
							<img id="img__wrap" />
						</div>
					</div>

					<div class="col-md-12 text-right">
						<button type="submit" value="submit" class="btn submit_btn">Save</button>
					</div>

				</form>
			</div>
		</div>
	</div>
</section>
<!--================Contact Area =================-->
<br />
<br />
<script src="/blog/js/jquery-3.2.1.min.js"></script>
<script>
	$("#img__input").on("change", handleImgFile); //1. 어떤 변화가 있을지 2.변화가 있을때 뭘 수행할지(함수)

	function handleImgFile(e) {
		/* console.log(e);
		console.log(e.target.files);//배열
		console.log(e.target.files[0]); //배열에서 첫번쨰 추출 */
		var f = e.target.files[0];

		//이미지가 아닌 파일 집어넣으면 걍 리턴시켜버린다
		if (!f.type.match("image.*")) {
			/* console.log("이미지파일이 아닙니다.") */
			return;
		}

		var reader = new FileReader();

		//얘는 지나갔다가 아래에서 사진이 다 읽어지길 기다렸다가 작동한다
		reader.onload = function(e) {
			/* console.log("==========");
			console.log(e.target);
			console.log(e.target.result); //파일 로딩이 성공한 결과 */
			$("#img__wrap").attr("src", e.target.result);
		}

		//여기서 사진을 다 읽는다
		reader.readAsDataURL(f);
	}
</script>
<%@ include file="/include/footer.jsp"%>
