<%@ page language="java" contentType="text/html;" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 参数测试</title>
</head>
<body>
	<h1>准描述黄斌的信息：</h1>
	<table border="1">
		<thead>
			<tr>
				<th>编号</th>
				<th>描述</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${a}" var="item">
				<tr>
					<td>21</td>
					<td>${item}</td>
				<tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>