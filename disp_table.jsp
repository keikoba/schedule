<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="common.CommonInfo"%>
<%@ page import="beans.*"%>

<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Display Table全表示</title>
<link rel="stylesheet" type="text/css" href="samll_Table.css">
</head>
<body>
	<p>
		****** デバック 全表示****** DispTable.jsp<br> このページから月カレンダーに戻る<br>
		いつでのこのページを読み出せる<br> 1ページ前にSQL全取得があるので、ページを呼ぶ場合は取得必須 一覧から削除を選ぶパターン<br>
	</p>

	<form action="InputServlet" method="post">
		<table border="1">
			<tr>
				<td>No.</td>
				<%
					String eventColor = "";
					for (String str : CommonInfo.infoarrayCat2) {
				%>

				<th><%=str%></th>
				<%
					}
					//最後に削除ボタンゾーンを追加
				%>
				<th>削除</th>
			</tr>
			<%
				//レコードがある限り繰り返す
				@SuppressWarnings("unchecked")
				ArrayList<Sample_Javabeans> list = (ArrayList<Sample_Javabeans>) session
						.getAttribute("tablelist");
				int i = 0;
				for (Sample_Javabeans sjb : list) {
			%>
			<tr>

				<%
					//削除日付取得用
					String delDay ="";
					i++;
				%>
				<td><%=i%></td>
				<%
					int k = 0;
						for (String str : sjb.getAll()) {
							k++;
							//日付取得、1要素目に日付、削除キーに使用
							if(k==1){
								delDay=str;
							}
							//色項目なら
							if (k == 4) {
								eventColor = " bgcolor=\"" + str + "\"";
							} else {
								eventColor = "";
							}
				%>
				<td <%=eventColor%>><%=str%></td>
				<%
					}
				%>
				<td>削除日<br> <input type="submit" value="<%=delDay %>"
					name="disp">
				</td>
			</tr>
			<%
				}
			%>

			<tr>

				<td align="center" colspan="2"><input type="hidden"
					name="page_flag" value="21" /> <input type="submit"
					value="カレンダーに戻る" name="disp"> <input type="reset"
					value="戻る" onclick="javascript:history.back()"></td>

			</tr>
		</table>
	</form>
</body>
</html>
