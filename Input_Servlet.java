/*
 * セッションはクライアントが終了するかタイマーでの寿命
 * 常にメモリ圧迫するので、必要最小限にする
 *
 */
package sample;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Sample_Javabeans;
import common.CommonInfo;
import model.InfoMain;

/**
 * サーブレット、コントローラ<br>
 * JSPでpostしてServletに移動した場合、
 * 自動的にdoPostメソッドが呼ばれる
 * getの場合も同様にdoGetが呼ばれる
 *
 * Servlet implementation class InputServlet
 */
@WebServlet("/InputServlet") // JSPでInputServletが呼ばれるとここに来る
public class InputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InputServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 移動先指定
	 * @param url
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forwardJSP(String url, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	/**
	 * get使用時自動実行メソッド
	 * 実行終了後、この場所に戻らないメソッド
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * post使用時自動実行メソッド
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// getSession(true)セッションを返す
		// セッションがなければ新規に作りセッションを返す
		HttpSession session = request.getSession(true);

		String jspname = null;

		// page_flagごとに仕分け
		switch (request.getParameter("page_flag")) {

		case "0":
			System.out.println("case0");
			//inputの内容を書き出し
			for (int i = 1; i <= 5; i++) {
				System.out.println("in" + request.getParameter("name" + i));
			}

			ArrayList<String> list = new ArrayList<>();
			// CommonInfo.infoarray入力データーそのlength要素数
			for (int i = 1; i <= CommonInfo.infoarrayCat2.length; i++) {
				// sessionのinputdataにrequestのnameのvaleを入れる
				session.setAttribute("inputdata" + i,
						request.getParameter("name" + i));
				list.add(request.getParameter("name" + i));

			}
			// ？？inputdataの繰り返しと、listは重複？
			// どちらかいらないのでは？
			session.setAttribute("List", list);
			// 移動先の名前
			jspname = "Cf_JSP.jsp";
			break;

		case "1":
			System.out.println("case1");

			@SuppressWarnings("unchecked")
			ArrayList<String> list1 = (ArrayList<String>) session
			.getAttribute("List");
			InfoMain infomain = new InfoMain();
			//			infomain.getData(list1);

			beans.Sample_Javabeans beans1 = new beans.Sample_Javabeans();
			beans1.setAll(list1);
			infomain.insertData(beans1);

			jspname = "FX_JSP.jsp";
			break;

		case "2":
			System.out.println("case2");

			InfoMain infomain2 = new InfoMain();
			ArrayList<Sample_Javabeans> list2 = infomain2.selectAll();

			session.setAttribute("tablelist", list2);

			jspname = "DispTable.jsp";
			break;

			//最初に開くページtop
			//一番最初だけ来る
		case "10":

			// デバック top.jsp
			System.out.println("top->top");
			//年currentYear
			//月currentMonth
			//日currentDay
			System.out.println(request.getParameter("arrow"));
			//日付切り出し
			session.setAttribute("currentYear", request.getParameter("arrow").substring(0, 4));
			session.setAttribute("currentMonth", request.getParameter("arrow").substring(5, 7));
			session.setAttribute("currentDay", request.getParameter("arrow").substring(8, 10));
			//デバック
			System.out.println(session.getAttribute("currentYear"));
			System.out.println(session.getAttribute("currentMonth"));
			System.out.println(session.getAttribute("currentDay"));

			//イベント日時取得
			InfoMain infomain10 =new InfoMain();
			session.setAttribute("cMonthEList", infomain10.selectDyasByEventDist());
			session.setAttribute("cMonthDayEList", infomain10.selectDyasByEventDist());

			//月のイベント取得
			session.setAttribute("selectMonth", currentToSelectMonth((String)session.getAttribute("currentMonth")));
			String ymd = (String)session.getAttribute("currentYear") +"-" + (String)session.getAttribute("selectMonth");
			infomain10.selectDayByEventMonth(ymd);
			System.out.println(ymd);

			//デバック
			@SuppressWarnings("unchecked")
			ArrayList<String> sMEList =(ArrayList<String>)session.getAttribute("cMonthEList");
			for(String str:sMEList) {
				System.out.println(str);
			}





			// 移動先の名前
			jspname = "samll_top2.jsp";
			break;

			// top2 2番目画面 top2.jsp
		case "11":
			System.out.println("case11");
			//javaとの時間のずれのない月+1
			//			session.setAttribute("selectYear", "2019");
			//			session.setAttribute("selectMonth", "02");
			String yymm = (String) session.getAttribute("selectYear")
					+ (String) session.getAttribute("selectMonth");
			System.out.println(yymm);

			String selectStr = "";
			selectStr = request.getParameter("arrow");
			//			session.setAttribute("selectDay", selectStr);

			//カレンダー日付ボタン
			if (!(selectStr.equals("m+1") || selectStr.equals("m-1"))) {
				jspname = "samll_SelectTime_JSP.jsp";
				session.setAttribute("selctDay",
						request.getParameter("arrow"));
			}

			int mm = Integer.parseInt((String) session.getAttribute("currentMonth"));
			//月進む
			System.out.println(mm);
			if (selectStr.equals("m+1")) {
				// 移動先の名前
				jspname = "samll_top2.jsp";
				//月+
				mm++;
				if (mm >= 12) {
					mm = 0;
					int yy = Integer.parseInt((String) session.getAttribute("currentYear"));
					yy++;
					session.setAttribute("currentYear", String.valueOf(yy));
				}
				System.out.println("mm++" + mm);
				session.setAttribute("currentMonth", chkSubForStr(mm));
				System.out.println(chkSubForStr(mm));
			}
			System.out.println(selectStr);
			//月戻る
			if (selectStr.equals("m-1")) {
				// 移動先の名前
				jspname = "samll_top2.jsp";
				//月-
				mm--;
				if (mm < 0) {
					mm = 11;
					int yy = Integer.parseInt((String) session.getAttribute("currentYear"));
					session.setAttribute("currentYear", String.valueOf(yy - 1));
				}
				System.out.println("mm--" + mm);
				session.setAttribute("currentMonth", chkSubForStr(mm));
				System.out.println(chkSubForStr(mm));
			}
			//javaとの時間のずれのない月+1
			System.out.println("currentMonth"+ (String)session.getAttribute("currentMonth"));
			int cm = chkSubForInt((String)session.getAttribute("currentMonth"))  + 1;
			System.out.println("cm"+chkSubForStr(cm));
			session.setAttribute("selectMonth", chkSubForStr(cm));
			System.out.println("selectMonth" + session.getAttribute("selectMonth"));

			//一覧を見るの場合
			if (selectStr.equals("一覧をみる")) {
				jspname = "samll_DispTable.jsp";
				//リスト全取得
				InfoMain infomain21 = new InfoMain();
				ArrayList<Sample_Javabeans> list21 = infomain21.selectAll();
				session.setAttribute("tablelist", list21);
			}
			break;

			//1日の予定 SelectTime_JSP.jsp
		case "13":

			String selectStr2 = "";
			selectStr2 = request.getParameter("button1");
			session.setAttribute("selctTime", selectStr2);
			System.out.println(selectStr2);

			ArrayList<String> list13 = new ArrayList<>();
			// CommonInfo.infoarray入力データーそのlength要素数
			for (int i = 1; i <= CommonInfo.infoarrayCat2.length; i++) {
				// sessionのinputdataにrequestのnameのvaleを入れる
				session.setAttribute("inputdata" + i,
						request.getParameter("name" + i));
				list13.add(request.getParameter("name" + i));
			}
			session.setAttribute("inputdata1", selectStr2);

			session.setAttribute("List", list13);
			jspname = "samll_FIX_JSP01.jsp";
			break;

		case "14":

			@SuppressWarnings("unchecked")
			ArrayList<String> list14 = (ArrayList<String>) session
			.getAttribute("List");
			InfoMain infomain14 = new InfoMain();
			//			infomain.getData(list1);

			beans.Sample_Javabeans beans14 = new beans.Sample_Javabeans();
			beans14.setAll(list14);
			infomain14.insertData(beans14);

			jspname = "samll_FIX_JSP02.jsp";
			break;

			//削除確認削除レコード表示
		case "21":
			System.out.println("case21" + "：削除レコード表示");

			//削除キー時間情報取得
			String delKyeId = "";
			delKyeId = (String) request.getParameter("disp");
			System.out.println("削除キー：" + delKyeId);
			InfoMain infomain21 = new InfoMain();

			//削除キーボタンなら
			if (!delKyeId.equals("カレンダーに戻る") || !delKyeId.equals("戻る")) {
				ArrayList<String> list21 = infomain21.selectRecord(delKyeId);

				//デバック
				for (String str : list21) {
					System.out.println("*" + str);
				}
				//削除データー取得
				for (int i = 1; i <= CommonInfo.infoarrayCat2.length; i++) {
					session.setAttribute("inputdata" + i, list21.get(i - 1));
					System.out.println(session.getAttribute("inputdata" + i));
				}
				jspname = "samll_Del_rec_Conf_JSP.jsp";
			}
			//カレンダーに戻るボタンなら
			if (delKyeId.equals("カレンダーに戻る")) {
				jspname = "samll_top2.jsp";
			}

			break;

			//削除決定
		case "22":
			//デバック
			System.out.println("case22");
			System.out.println("case22");

			//削除キー時間情報取得
			String delKyeId22 = "";
			delKyeId22 = (String) session.getAttribute("inputdata1");
			InfoMain infomain22 = new InfoMain();
			infomain22.deleteData(delKyeId22);
			jspname = "samll_FX_JSP.jsp";

			break;

			//書き込み前確認
		case "30":
			System.out.println("case30");
			//デバックinputの内容を書き出し
			for (int i = 1; i <= 7; i++) {
				System.out.println("in" + request.getParameter("name" + i));
			}

			ArrayList<String> list30 = new ArrayList<>();
			// CommonInfo.infoarray入力データーそのlength要素数
			for (int i = 1; i <= CommonInfo.infoarrayCat2.length; i++) {
				// sessionのinputdataにrequestのnameのvaleを入れる
				session.setAttribute("inputdata" + i,
						request.getParameter("name" + i));
				list30.add(request.getParameter("name" + i));

			}
			// ？？inputdataの繰り返しと、listは重複？
			// どちらかいらないのでは？
			session.setAttribute("List", list30);
			// 移動先の名前
			jspname = "samll_Cf_JSP.jsp";
			break;

			//書き込み確認
		case "31":
			System.out.println("case31");

			@SuppressWarnings("unchecked")
			ArrayList<String> list31 = (ArrayList<String>) session
			.getAttribute("List");
			InfoMain infomain31 = new InfoMain();
			//			infomain.getData(list1);

			beans.Sample_Javabeans beans31 = new beans.Sample_Javabeans();
			beans31.setAll(list31);
			infomain31.insertData(beans31);

			jspname = "samll_FX_JSP.jsp";
			break;

			//全表示、削除ボタン付き
		case "32":
			System.out.println("case32");

			InfoMain infomain32 = new InfoMain();
			ArrayList<Sample_Javabeans> list32 = infomain32.selectAll();

			session.setAttribute("tablelist", list32);

			jspname = "samll_DispTable.jsp";
			break;

		default:
			break;

		}

		// 移動メソッド呼び出し
		forwardJSP(jspname, request, response);

	}

	//文字列を数字にする、先頭の文字が0なら切り取る
	private int chkSubForInt(String str) {
		String sub = "";
		int num = 0;
		if (str.substring(0, 1) == "0") {
			sub = str.substring(1, 2);
			num = Integer.parseInt(sub);
		}else {
			num=Integer.parseInt(str);
		}
		return num;
	}

	//数字を文字列にする、文字数が1文字なら2文字にする
	private String chkSubForStr(int num) {
		String sub = String.valueOf(num);
		if (sub.length() == 1) {
			sub = "0" + sub;
		}
		return sub;
	}

	/**
	 * カレント月からセレクト月に+1
	 * @return
	 */
	public String currentToSelectMonth(String month) {
		int cm = chkSubForInt(month) + 1;
		System.out.println("cm"+chkSubForStr(cm));
		return chkSubForStr(cm);

	}


	//イベント日時を調べる
	public ArrayList<String> eventCheckByMonth(String date) {
		ArrayList<String> list = new ArrayList<String>();
		//
		//
		return list;

	}








}
