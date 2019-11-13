<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/include/nav.jsp"%>

<!--================Blog Area =================-->
<section class="blog_area single-post-area">
	<div class="container">
		<div class="row">
			<div class="col-lg-2"></div>
			<div class="col-lg-8">
				<div class="main_blog_details">

					<a href="#"><h4>${board.title}</h4></a>
					<div class="user_details">
						<div class="float-left">

							<c:if test="${board.userId eq sessionScope.user.id}">
								<a href="/blog/board?cmd=updateForm&id=${board.id}">Update</a>
								<a href="/blog/board?cmd=delete&id=${board.id}">Delete</a>
							</c:if>

						</div>
						<div class="float-right">
							<div class="media">
								<div class="media-body">
									<h5>${board.user.username}</h5>
									<p>${board.createDate}</p>
								</div>
								<div class="d-flex">
									<img src="${board.user.userProfile}" width="50px" height="50px" style="border-radius: 50%;">
								</div>
							</div>
						</div>
					</div>
					<!-- 본문시작 -->
					<p>${board.content}</p>
					<!-- 본문 끝 -->
					<hr />
				</div>

				<!-- 댓글 시작 -->
				<!--  before -->
				<div class="comments-area" id="comments-area">
					<!-- prepend -->
					<c:forEach var="comment" items="${comments}">
						<!-- 댓글 아이템 시작 -->
						<div class="comment-list" style="padding: 15px;" id="comment-id-${comment.id}">
							<!-- ID 동적으로 만들기 -->
							<div class="reply-btn">
								<c:if test="${comment.userId eq sessionScope.user.id}">
								<button onClick="commentDelete(${comment.id})" class="btn-reply text-uppercase" style="float: right; margin-right: 10px;">댓글삭제</button>
								</c:if>
								<button id ="comment-button-${comment.id}" onClick="replyListShow(this,${comment.id})" class="btn-reply text-uppercase" style="float: right; margin-right: 10px;">댓글보기</button>
								<c:if test ="${not empty sessionScope.user}">
								<button onClick="replyForm(this,${comment.id})" class="btn-reply text-uppercase" style="float: right; margin-right: 10px;">댓글쓰기</button>
								</c:if>
							</div>
							<div class="single-comment justify-content-between d-flex">
								<div class="user justify-content-between d-flex">
									<div class="thumb">
										<img src="${comment.user.userProfile}" width="50px" height="50px" style="border-radius: 50%;">
									</div>
									<div class="desc">
										<h5>
											<a href="#">${comment.user.username}</a>
										</h5>
										<p class="date">${comment.createDate}</p>
										<p class="comment">${comment.content}</p>
									</div>
								</div>
							</div>
						</div>
						<!-- 댓글 아이템 끝 -->
					</c:forEach>
					<!--  append -->
				</div>


				<!-- 댓글 쓰기 시작 -->
				<div class="comment-form" style="margin-top: 0px;">
					<h4 style="margin-bottom: 20px">Leave a Comment</h4>
					<form id="comment-submit">
						<input type="hidden" name="userId" value="${sessionScope.user.id}" /> <input type="hidden" name="boardId" value="${board.id}" />
						<div class="form-group">
							<textarea id="content" style="height: 60px" class="form-control mb-10" rows="2" name="content" placeholder="content" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Messege'"
								required="required"></textarea>
						</div>
						<!-- 버튼 타입 submit을해주면 부조건 새로고침되서 onClick를 쓴다. -->
						<!-- 버튼 타입을 button을해주면 자바스크립트에서 바로 submit 되는걸 막아줌 -->
						<c:choose>
							<c:when test="${not empty sessionScope.user}">
								<button type="button" onClick="commentWrite()" class="primary-btn submit_btn">Post Comment</button>
							</c:when>
							<c:otherwise>
								<button type="button" onClick="" class="primary-btn submit_btn">로그인하세요</button>
							</c:otherwise>
						</c:choose>

					</form>
				</div>
				<!-- 댓글 쓰기 끝 -->
			</div>
			<!-- 댓글 끝 -->
			<div class="col-lg-2"></div>
		</div>
	</div>
</section>


<!--================Blog Area =================-->
<%@ include file="/include/footer.jsp"%>

<!--================Comment Script =================-->
<script>

	function commentItemForm(id, username, content, createDate, userProfile){
	    var comment_list = "<div class='comment-list' style='padding: 15px;' id='comment-id-"+id+"'> ";
	    comment_list += "<div class='reply-btn'> ";
	    comment_list += "<button onClick='commentDelete("+id+")' class='btn-reply text-uppercase' style='float: right; margin-right: 10px;'>댓글삭제</button> ";
	    comment_list += "<button id = 'comment-button-"+id+"' onClick='replyListShow(this,"+id+")' class='btn-reply text-uppercase' style='float: right; margin-right: 10px;'>댓글보기</button> ";
	    comment_list += "<button onClick='replyForm(this,"+id+")' class='btn-reply text-uppercase' style='float: right; margin-right: 10px;'>댓글쓰기</button></div> ";
	    comment_list += "<div class='single-comment justify-content-between d-flex'> ";
	    comment_list += "<div class='user justify-content-between d-flex'> ";
	    comment_list += "<div class='thumb'><img src = '"+userProfile+"' width='50px' height='50px' style = 'border-radius: 50%;'></div> ";
	    comment_list += "<div class='desc'><h5><a href='#'>"+username+"</a></h5><p class='date'>"+createDate+"</p><p class='comment'>"+content+"</p></div></div></div></div> ";

	    return comment_list;
	}

	//comment 쓰기
   function commentWrite(){
      var comment_submit_string = $("#comment-submit").serialize();
      $.ajax({
         method: "POST",
         url: "/blog/api/comment?cmd=write",
         data: comment_submit_string,
         contentType: "application/x-www-form-urlencoded; charset=utf-8",
         dataType: "json",
         
         success: function(comment){
             //화면에 적용
          
            var comment_et = commentItemForm(comment.id, comment.user.username, comment.content, comment.createDate, comment.user.userProfile);
            $("#comments-area").prepend(comment_et);
            //입력폼 초기화하기
            $("#content").val("");
            
         },
         error: function(xhr){
            console.log(xhr.status);
            console.log(xhr);
         }
         
      });
      
   }


	//comment 삭제
	function commentDelete(comment_id){//자바스크립트는 하이픈 사용 불가
		$.ajax({
			method: "POST",
			url: "/blog/api/comment?cmd=delete",
			data: comment_id+"",
			contentType: "text/plain; charset=utf-8",
			success: function(result){
				//해당 엘레멘트(DOM)을 찾아서 remove()해주면됨.
				if(result === "ok"){
					$("#comment-id-"+comment_id).remove();	
				}
			},
			error: function(xhr){
				
			}
		});
	}
	//=================================================Reply==============================================================
		
	function replyItemForm(id, username, content, createDate, userProfile, userId){		
	      var replyItem = "<div class='comment-list left-padding' id='reply-id-"+id+"'>";
	      replyItem+= "<div class='single-comment justify-content-between d-flex'>";
	      replyItem+= "<div class='user justify-content-between d-flex'>";
	      replyItem+= "<div class='thumb'><img src='"+userProfile+"' width='30px' height='30px' style = 'border-radius: 50%;' alt=''></div>";
	      replyItem+= "<div class='desc'><h5><a href='#'>"+username+"</a></h5>";
	      replyItem+= "<p class='date'>"+createDate+"</p>";
	      replyItem+= "<p class='comment'>"+content+"</p></div></div>";
	      
	      var sessionUserId = ${sessionScope.user.id}+"";
	      if(sessionUserId == userId){
	    	  replyItem+= "<div class='reply-btn'><button type='button' onClick='replyDelete("+id+")' class='btn-reply text-uppercase'>삭제</button></div>";
	      }

	      replyItem+= "</div></div>";
	   
	      return replyItem;
	   }
	
	   //reply 보기 - ajax
	   //댓글들이 들어가는
	   function replyListShow(et, comment_id) {
		   if(et.innerHTML === "댓글보기"){
			   	et.innerHTML = "글감추기"
			      //comment_id 로 reply 전부다 select 해서 가져오기
			      $.ajax({
			         method: "POST",
			         url: "/blog/api/reply?cmd=list",
			         data: comment_id +"",
			         contentType: "text/plain; charset=utf-8",
			         dataType: "json",
			         success: function(replys){ //javascript object
			        	 
			        	 //div를 만들어서 먼저 after해주고 그 안에 for문돌려서 append 해주고
			        	 //마지막에 지금 만들어진 div를 remove
			        	 var comment_replys = document.createElement("div")
			        	 comment_replys.id="comment-replys-"+comment_id;
			         	 comment_replys.className = "comment-list";
			         	 
			            
			            for(reply of replys){
			               //잘 받았으면 화면에 표시하면 됨.
			               var reply_et = replyItemForm(reply.id, reply.user.username, reply.content, reply.createDate, reply.user.userProfile, reply.userId);
			               $("#comment-id-"+reply.commentId).after(comment_replys);
			               $("#comment-replys-"+comment_id).append(reply_et);
			            }
			         },
			         error: function(xhr){
			            console.log(xhr);
			         }
			      });
		   }else{
			   et.innerHTML = "댓글보기";
			   $("#comment-replys-"+comment_id).remove();
			  
		   }

	   }
  
	//reply쓰기폼 - 화면에로딩!!
 	function replyForm(et, comment_id) {
		if(et.innerHTML==="댓글쓰기"){
			
			et.innerHTML= "댓글닫기";
			
			var comment_form_inner = "<form id='reply-submit'><input type='hidden' name='userId' value='${sessionScope.user.id}'/><input type='hidden' name='commentId' value='"+comment_id+"'/><div class='form-group'><textarea id='reply-content' style='height:60px' class='form-control mb-10' rows='2' name='content' placeholder='Messege' required=''></textarea></div><button type ='button' onClick='replyWrite("+comment_id+")' class='primary-btn submit_btn'>Post Reply</button></form>";	
			
			
			
			//div 생성
			//<div id="comment-form-" class="comment-form" style="margin-top: 0px;"></div>
			var comment_form = document.createElement("div");
			comment_form.id = "comment-form-"+comment_id;
			comment_form.className = "comment-form";
			comment_form.style = "margin-top: 0px";
			
			comment_form.innerHTML = comment_form_inner;
			
			var comment_list = document.querySelector("#comment-id-"+comment_id);
			comment_list.append(comment_form); //append, prepend / after, before
			
		}else{
			
			et.innerHTML = "댓글쓰기";
			$("#comment-form-"+comment_id).remove();
			
			
		}
		
	}; 
	
	
	//reply 삭제
	function replyDelete(reply_id){
		$.ajax({
			method: "POST",
			url: "/blog/api/reply?cmd=delete",
			data: reply_id+"",
			contentType: "text/plain; charset=utf-8",
			success: function(result){
				if(result === "ok"){
					$("#reply-id-"+reply_id).remove();
				}
			},
			error: function(xhr){
				console.log(xhr);
			}
		});
	}

	//reply 쓰기
	function replyWrite(comment_id){
		var reply_submit_string = $("#reply-submit").serialize();
		$.ajax({
			method: "POST",
			url: "/blog/api/reply?cmd=write",
			data: reply_submit_string,
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			dataType: "json",
			success: function(replys){
				console.log(replys)
				
	        	 var comment_replys = document.createElement("div")
	        	 comment_replys.id="comment-replys-"+comment_id;
	         	 comment_replys.className = "comment-list";
	         	 
	         	 //대댓글 보기상태에서 쓸경우 겹쳐나오기때문에 미리지워준다
	         	$("#comment-replys-"+comment_id).remove();
	            for(reply of replys){
	               //잘 받았으면 화면에 표시하면 됨.
	               var reply_et = replyItemForm(reply.id, reply.user.username, reply.content, reply.createDate, reply.user.userProfile, reply.userId);
	               $("#comment-id-"+reply.commentId).after(comment_replys);
	               $("#comment-replys-"+comment_id).append(reply_et);
	            }
	            //댓글보기 버튼 글자를 글감추기로 변경시키기
	            $("#comment-button-"+comment_id).text("글감추기");
				//입력폼 초기화
				$("#reply-content").val("");			
			},
			error: function(xhr){
				console.log(xhr);
			}
		});
	}

</script>
</body>
</html>