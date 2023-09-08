<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/write.css">
<script
  src="https://code.jquery.com/jquery-3.7.0.js"
  integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
  crossorigin="anonymous"></script>
</head>
<body>
<form id="insertBoard" method="post" action="insertReply.do" enctype="multipart/form-data">
	<table>
		<tr>
			<td class="boardTab">제목</td><td><input class="inputText" name="title" type="text" maxlength="30"></td>
		</tr>
		<tr>
			<td class="boardTab">글쓴이</td><td><input class="inputText" name="name" type="text" maxlength="15"></td>
		</tr>
		<tr>
			<td class="boardTab">비밀번호</td><td><input class="inputText" name="pass" type="password"  maxlength="10" onkeyup="handleOnInput(this)"></td>
		</tr>
		<tr>
			<td colspan="2">내용</td>
		</tr>
		<tr>
			<td colspan="2"><textarea name="content"></textarea></td>
		</tr>
		<tr>
			<td colspan="2">파일첨부</td>
		</tr>
		<tr>
			<td colspan="2"><input id="realFileChange" name="boardfile" type="file"></td>
		</tr>
	</table>
	<input type="button" id="submitBtn" value="저장하기">
</form>
<script>
	$('input[name=pass]').keyup(function(e){
		if(e.key==""||e.keyCode==32){
			let passv = $('input[name=pass]').val();
			passv = passv.substring(0,passv.length-1);
			$('input[name=pass]').val(passv);
			
			alert('비밀번호에서 스페이스바를 사용할 수 없습니다.');
		}
	})
$('#submitBtn').click(function(){
		let titlev = $('input[name=title]').val().trim();
		$('input[name=title]').val(titlev);
		
		let namev = $('input[name=name]').val().trim();
		$('input[name=name]').val(namev);
		
		let passv = $('input[name=pass]').val().replaceAll(' ','');
		$('input[name=pass]').val(passv);
		
		const encoder = new TextEncoder();
		let titleb= encoder.encode($('input[name=title]').val());
		let nameb= encoder.encode($('input[name=name]').val());
		let passb= encoder.encode($('input[name=pass]').val());
		
		
		let contentv = $('textarea').val().trim().replaceAll('\n','<br>');
		if(titlev != "" && namev != "" && passv != "" && contentv !=""){
			if($('#realFileChange').val()!="" && $('#realFileChange')[0].files[0].size > 50000000){
					alert('파일은 50mb 미만으로 업로드해주세요.');
			}else{
				if(titleb.length > 100){
					alert('제목은 100Byte까지 입력 가능합니다.');
					
				}else if(nameb.length > 50){
					alert('이름은 50Byte까지 입력 가능합니다.');
					
				}else if(passb.length > 10){
					alert('비밀번호는 10Byte까지 입력 가능합니다.');
					
				}else{
					let originnoV = '${originno}';
					let replynoV = '${replyno}';
					let replylvV = '${replyseq}';
					
					let originno = document.createElement('input');
					originno.setAttribute('type','hidden');
					originno.setAttribute('name','originno');
					originno.setAttribute('value',originnoV);
					
					let replyno = document.createElement('input');
					replyno.setAttribute('type','hidden');
					replyno.setAttribute('name','replyno');
					replyno.setAttribute('value',replynoV);
					
					let replyseq = document.createElement('input');
					replyseq.setAttribute('type','hidden');
					replyseq.setAttribute('name','replylv');
					replyseq.setAttribute('value',replylvV);
					
					
					$('#insertBoard').append(originno);
					$('#insertBoard').append(replyno);
					$('#insertBoard').append(replyseq);
					$('#insertBoard').submit();
				};
			};
		}else{
			alert('모두 입력하여 주세요.');
		}
	});
	
	function handleOnInput(e)  {
		let testStr = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g;
  		if(testStr.test(e.value)){
  			alert('비밀번호는 한글 입력이 불가합니다.')
			e.value = e.value.replace(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '');
  		}
	}
</script>
</body>
</html>