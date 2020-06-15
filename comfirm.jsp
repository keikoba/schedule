<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ page import="common.CommonInfo"%>


<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>デバック 確認 Cf_JSP.jsp</title>
<link rel="stylesheet" href="samll_Table2.css">
</head>
<body>
****** デバック 確認******　Cf_JSP.jsp<br>
このページの確認ボタンでSQLに書き込み<br>
重複チェックはSQL、現在は同じ時間なら内容が異なっても書き込まない
	<form action="InputServlet" method="post">
		<table border="1">
			<%
				//イベントカラーコントローラー
				String eventColor = "";
				int i = 0;
				for (String str : CommonInfo.infoarrayCat2) {
					i++;
					//色項目なら
					if (i == 4) {
						eventColor = " bgcolor=\"" + session.getAttribute("inputdata" + i) + "\"";
					} else {
						eventColor = "";
					}
			%>
			<tr>
				<th><%=str%></th>
				<td <%=eventColor %>><%=session.getAttribute("inputdata" + i)%></td>
			</tr>
			<%
				}
			%>
			<tr>
				<td align="center" colspan="2">
				<input type="hidden" name="page_flag" value="31" />
				<input type="submit" value="確定" name="button1">
			    <input type="reset" value="戻る" onclick="javascript:history.back()"></td>
			</tr>
		</table>
	</form>

</body>
</html>
