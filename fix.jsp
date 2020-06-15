<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ page import="common.CommonInfo"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>デバック ***** 入力 FIX_JSP01</title>
<link rel="stylesheet" href="samll_Table2.css">
</head>
<body>

	<p>
		***** デバック ***** 入力 FIX_JSP01.jsp<br> 01 日付 全ページより自取得<br> 02
		カテゴリ<br> 03 タイトル<br> 04 色 type:color<br> 05 本文
		type:textaria<br> selctDay、1桁の場合は頭に0がついていない<br>
	</p>

	<%=session.getAttribute("currentYear")%>-<%=session.getAttribute("selectMonth")%>-<%=session.getAttribute("selctDay")%><br>
	<%=session.getAttribute("selctTime")%>:00:00



	<form action="InputServlet" method="post">
		<table border="1">
			<%
				String tD =(String)session.getAttribute("selctDay");
				//月の文字数チェック
				if (tD.length() == 1) {
					tD = "0" + tD;
				}
				int i = 0;
				String valuePius = "";
				// 時間フィールドへのインサート用文字列
				String insF = session.getAttribute("currentYear") + "-"
						+ session.getAttribute("selectMonth") + "-"
						+ tD + " "
						+ session.getAttribute("selctTime") + ":00:00";
				String eventColor = "";
				//typeコントロール
				String typeControl = "text";
				//typeコントロールワード
				String typeControlWrod = "";
				//後付け文字
				String afterWord = "";
				//mthodType
				String mthodType = "input";

				//要素ごとのformコントロール
				//				for (String str : CommonInfo.infoarrayCat2) {
				for (i = 1; i <= CommonInfo.infoarrayCat2.length; i++) {
					String str = CommonInfo.infoarrayCat2[i - 1];
					//色項目なら
					String cor = "#cccccc";
					if (i == 4) {
						eventColor = " bgcolor=\"" + cor + "\"";
						typeControl = "type=\"color\" value=\"#cceeff\"";
						typeControlWrod = "背景にしたい色選択";
					} else {
						eventColor = "";
						typeControl = "type=\"text\"";
						typeControlWrod = "";
					}
					//日付 自動挿入
					if (i == 1) {
						valuePius = "value =" + "\"" + insF + "\"";
					} else {
						valuePius = "";
					}

					//on項目なら
					if (i == 6) {
						typeControl = "type=\"radio\" value =\"off\"";
						typeControlWrod = "<input type=\"radio\" checked=\"checked\" value =\"on\" name =\"name6\">on";
						afterWord = "off";
					} else {
						eventColor = "";
						afterWord = "";
					}

					//本文項目なら
					if (i == 5) {
						typeControl = "maxlength=\"400\" placeholder=\"予定の詳細を書いてください\"";
						afterWord = "</textarea>";
						mthodType = "textarea";

					} else {
						mthodType = "input";
					}
			%>
			<tr>
				<th><%=str%></th>
				<td><%=typeControlWrod%> <<%=mthodType %> name="name<%=i%>"
						<%=typeControl%> size="40" <%=valuePius%>><%=afterWord%></td>

			</tr>
			<%
				}
			%>


			<tr>
				<td align="center" colspan="2"><input type="hidden"
					name="page_flag" value="30" /> <input type="submit" value="OK"
					name="button1"></td>
			</tr>
		</table>
	</form>
</body>
</html>
