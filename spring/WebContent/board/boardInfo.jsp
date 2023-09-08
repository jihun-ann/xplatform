<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<script
  src="https://code.jquery.com/jquery-3.7.0.js"
  integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
  crossorigin="anonymous"></script>
<link rel="stylesheet" href="./css/boardinfo.css">
</head>
<body>
	<table>
		<tr>
			<td class="boardTab">제목</td><td><span><c:out value="${boardInfo.title}"></c:out></span></td>
		</tr>
		<tr>
			<td class="boardTab">글쓴이</td><td><span><c:out value="${boardInfo.name}"></c:out></span></td>
		</tr>
		<tr>
			<td colspan="2">내용</td>
		</tr>
		<tr>
			<td colspan="2"><div id="textarea"><span id="boardContent"><c:out value="${boardInfo.content}"></c:out></span></div></td>
		</tr>
		<tr>
			<td colspan="2">파일첨부</td>
		</tr>
		<tr>
			<td colspan="2">
				<c:if test="${filepath!=null}">
					<a id="fileDownload" href="<c:out value="${filepath}"></c:out>" name="boardfile" download="<c:out value="${filename}"/>">${filename}</a>
				</c:if>
			</td>
		</tr>
	</table>
	<div id="btnlst">
		<input type="button" id="replyBtn" class="btn" value="답글">
		<input type="button" id="updateBtn" class="btn" value="수정">
		<input type="button" id="deleteBtn" class="btn" value="삭제">
		<input type="button" id="listBtn" class="btn" value="목록">
	</div>
	
	<div class="modal-update modal">
		<div id="modal-update" class="modalIn">
			<p class="modalIn">비밀번호를 입력하세요</p>
			<input type="password" class="passU modalIn" />
			<div>
				<input type="button" class="modalUBtn-y modalIn" value="확인"/>
				<input type="button" class="modalUBtn-n" value="취소"/>
			</div>
		</div>
	</div>
	<div class="modal-delete modal">
		<div id="modal-delete" class="modalIn">
			<p class="modalIn">비밀번호를 입력하세요</p>
			<input type="password" class="passD modalIn" />
			<div>
				<input type="button" class="modalDBtn-y modalIn" value="확인"/>
				<input type="button" class="modalDBtn-n" value="취소"/>
			</div>
		</div>
	</div>
</body>

<script>
	$(document).ready(function(){
		let contentv = '${boardInfo.content}';
		$('#boardContent').html(contentv);
	});

	$('.btn').click(function(){
		if($(this).attr('id')=='updateBtn'){
			$('.modal-update').css({'display':'block'})
			$('#modal-update').css({'display':'flex'})
		}else if($(this).attr('id')=='deleteBtn'){
			$('.modal-delete').css({'display':'block'})
			$('#modal-delete').css({'display':'flex'})
		}else if($(this).attr('id')=='listBtn'){
			location.href="list.do";
		}else if($(this).attr('id')=='replyBtn'){
			let seq = ${boardInfo.seq};
			let form = document.createElement('form');
			form.setAttribute('method','post');
			form.setAttribute('action','boardReply.do');
			
			let value = document.createElement('input');
			value.setAttribute('type','hidden');
			value.setAttribute('name','seq');
			value.setAttribute('value',seq);
			
			form.appendChild(value);
			document.body.appendChild(form);
			form.submit();
		}
	});
	
	$('.modal').click(function(e){
		if(!$(e.target).hasClass('modalIn')){
			$('.modal-update').css({'display':'none'});
			$('.modal-delete').css({'display':'none'});
			$('.passU, .passD').val("");
			
		}
		if($(e.target).hasClass('modalDBtn-y')){
			let seq = ${boardInfo.seq};
			let pass = $('.passD').val();
			
			$.ajax({
				type:'POST',
				url:"boardCDelete.do",
				data: {"seq":seq,"pass":pass},
				success: function(t){
					if(t == 1){
						alert('삭제가 완료되었습니다.');
						window.location.href='list.do';
					}else{
						alert('비밀번호를 확인하여주세요.')
					}
				},
				error: function(t){
					console.log(t);
				}
			});
		}else if($(e.target).hasClass('modalUBtn-y')){
			let seq = ${boardInfo.seq};
			let pass = $('.passU').val();
			
			$.ajax({
				type:'POST',
				url:"boardCUpdate.do",
				data: {"seq":seq,"pass":pass},
				success: function(t){
					if(t == 1){
						let form = document.createElement('form');
						form.setAttribute('method','post');
						form.setAttribute('action','boardRetouchP.do');
						
						let value = document.createElement('input');
						value.setAttribute('type','hidden');
						value.setAttribute('name','seq');
						value.setAttribute('value',seq);
						
						form.appendChild(value);
						document.body.appendChild(form);
						form.submit();
					}else{
						alert('비밀번호를 확인하여주세요.')
					}
				},
				error: function(t){
					console.log(t);
				}
			})
		}
	});

</script>
</html>