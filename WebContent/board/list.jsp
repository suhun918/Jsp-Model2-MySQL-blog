<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/include/nav.jsp"%>



<!--================Blog Area =================-->
<section class="blog_area">
	<div class="container">
		<div class="row">
			<div class="col-lg-8">
				<div class="blog_left_sidebar">

					<!-- 1번 내가정하는 변수이름
					   2번 items에 컬렉션 담아준다.-->
					<c:forEach var="board" items="${boards}">
						<!-- 블로그 글 시작 -->
						<article class="blog_style1">
							<div class="blog_img">
								<img style="width: 100%; max-height: 269.347px;" class="img-fluid" src="${board.previewImg}" alt="">
							</div>
							<div class="blog_text">
								<div class="blog_text_inner">
									<div class="cat">
										<a class="cat_btn" href="#">${board.user.username}</a> <a href="#"><i class="fa fa-calendar" aria-hidden="true"></i> ${board.createDate}</a> <a href="#"><i class="fa fa-comments-o"
											aria-hidden="true"></i>${board.readCount}</a>
									</div>
									<a href="/blog/board?cmd=detail&id=${board.id}"><h4>${board.title}</h4></a>
									<!-- 썸머태그 안에 들어간 글은 자동으로 ptag가 붙으니까 제거해준다. -->
									<div
										style="display: -webkit-box; -webkit-box-orient: vertical; text-align: left; overflow: hidden; text-overflow: ellipsis; white-space: normal; line-height: 1.2; height: 2.6em; -webkit-line-clamp: 2; margin-bottom: 20px; word-break: break-all">${board.content}</div>
									<a class="blog_btn" href="/blog/board?cmd=detail&id=${board.id}">Read More</a>
								</div>
							</div>
						</article>
						<!-- 블로그 글 끝 -->
					</c:forEach>



					<!-- 페이징 start-->
					<nav class="blog-pagination justify-content-center d-flex">
						<ul class="pagination">

							<c:choose>
								<c:when test="${param.page%5==0}">
									<c:set var="pagesplice" value="${param.page-5}" />
								</c:when>
								<c:otherwise>
									<c:set var="pagesplice" value="${param.page-param.page%5}" />
								</c:otherwise>
							</c:choose>

							<c:if test="${param.page-5>0}">
								<li class="page-item"><a href="/blog/board?cmd=list&page=${pagesplice}&search=${search}" class="page-link" aria-label="Previous"> <span aria-hidden="true"> <span class="lnr lnr-chevron-left"></span>
									</span>
								</a></li>
							</c:if>



							<c:forEach var="i" begin="1" end="5">
								<c:choose>
									<c:when test="${pagesplice+i>count}">
									</c:when>
									<c:when test="${param.page!=pagesplice+i}">
										<li class="page-item"><a href="/blog/board?cmd=list&page=${pagesplice+i}&search=${search}" class="page-link">${pagesplice+i}</a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item active"><a href="/blog/board?cmd=list&page=${pagesplice+i}&search=${search}" class="page-link">${pagesplice+i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>


							<c:if test="${pagesplice+5<count}">
								<li class="page-item"><a href="/blog/board?cmd=list&page=${pagesplice+6}&search=${search}" class="page-link" aria-label="Next"> <span aria-hidden="true"> <span class="lnr lnr-chevron-right"></span>
									</span>
								</a></li>
							</c:if>

						</ul>
					</nav>

					<!-- 페이징 end-->
				</div>
			</div>
			<div class="col-lg-4">
				<div class="blog_right_sidebar">
					<aside class="single_sidebar_widget search_widget">
						<div class="input-group">
						
							<form action="/blog/board?cmd=list&page=1" method="post">
								<input type="text" name="search" class="form-control" placeholder="Search Posts">
								 <span class="input-group-btn">
									<button class="btn btn-default" type="submit">
										<i class="lnr lnr-magnifier"></i>
									</button>
								</span>
							</form>
							
						</div>
						<!-- /input-group -->
						<div class="br"></div>
					</aside>

					<aside class="single_sidebar_widget popular_post_widget">
						<h3 class="widget_title">Popular Posts</h3>

						<c:forEach var="hotBoard" items="${hotBoards}">
							<div class="media post_item">
								<a href="/blog/board?cmd=detail&id=${hotBoard.id}"><img src="${hotBoard.previewImg}" width='100px' height='80px' alt="post"></a>
								<div class="media-body">
									<a href="/blog/board?cmd=detail&id=${hotBoard.id}"><h3>${hotBoard.title}</h3></a>
									<p>${hotBoard.createDate}</p>
								</div>
							</div>
						</c:forEach>


						<div class="br"></div>
					</aside>


				</div>
			</div>
		</div>
	</div>
</section>
<!--================Blog Area =================-->

<%@ include file="/include/footer.jsp"%>
</body>
</html>