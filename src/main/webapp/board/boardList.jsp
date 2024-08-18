<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
body{
	font-size:9pt;
}
button{
	font-size:9pt;
}
table{
	width:600px;
	border-collapse:collapse; /* cell 간격 없앰 */
}
th,td{
	border:1px solid #cccccc;
	padding:3px;
}
.input1{
	width:98%;
}
.textarea{
	width:98%;
	height:70px;
}
</style>
<body>
<%@include file="../include/topmenu.jsp" %>
<br><br>
<!-- 번호,제목,글쓴이,조회수,등록일,조회수 -->
<table>
	<caption>
		<div>일반게시판 목록</div>
		<div align="left">Total : ${total}</div>
		<div align="left">
		<form name="searchFrm" method="post" action="boardList.do">
			<select name="searchGubun" id="searchGubun">
				<option value="title">제목</option>
				<option value="name">글쓴이</option>
				<option value="content">내용</option>
			</select>
			<input type="text" name="searchText" id="searchText">
			<button type="submit">검색</button>
		</div>
		</form>
	</caption>
	<tr>
		<th width="15%">번호</th>
		<th width="40%">제목</th>
		<th width="15%">글쓴이</th>
		<th width="15%">등록일</th>
		<th width="15%">조회수</th>
	</tr>
	
	<c:set var="cnt" value="${rowNumber}"/>
	
	<c:forEach var="result" items="${resultList}">
	<tr align="center">
		<td>${cnt}</td>
		<td align="left">
		<a href="boardDetail.do?unq=${result.unq}">${result.title}</a>
		</td>
		<td>${result.name}</td>
		<td>${result.rdate}</td>
		<td>${result.hits}</td>
	</tr>
	
		<c:set var="cnt" value="${cnt-1}"/>
	
	</c:forEach>
</table>

<div style="width:600px;margin-top:5px; text-align:center;">
	
	<c:forEach var="i" begin="1" end="${totalPage}">
		
		<a href="boardList.do?viewPage=${i}">${i}</a>
		
	</c:forEach>
	
</div>

<div style="width:600px;margin-top:5px; text-align:right;">
	<button type="button" onclick="location='boardWrite.do'">글쓰기</button>
</div>





</body>
</html>



