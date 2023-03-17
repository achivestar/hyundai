<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html>
<html lang="ko">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
		<c:forEach var="memo" items="${memoList}">
				<div class="card-body">
					<h4 class="card-title">${memo.id}</h4>
					<a href="/post/${memo.id}" class="btn btn-secondary">${memo.memoTextselet }</a>
				</div>
			</c:forEach>
</body>
</html>
