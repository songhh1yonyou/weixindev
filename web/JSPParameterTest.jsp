<%@ page language="java" contentType="text/html;" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP ��������</title>
</head>
<body>
	<h1>׼�����Ʊ����Ϣ��</h1>
	<table border="1">
		<thead>
			<tr>
				<th>���</th>
				<th>����</th>
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