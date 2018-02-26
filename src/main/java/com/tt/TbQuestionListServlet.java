package com.tt;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.tools.CommonFunction;
import com.tools.PaginationUtil;

//题目信息表
public class TbQuestionListServlet extends HttpServlet {

	private TbQuestionListBean entity;

	public TbQuestionListBean getEntity() {
		return entity;
	}

	public void setEntity(TbQuestionListBean tbquestionlist) {
		this.entity = tbquestionlist;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取对应的请求参数
		String method = request.getParameter("method");
		// 根据请求参数去调用对应的方法。
		if ("index".equals(method)) {
			index(request, response);
		}
		if ("list".equals(method)) {
			list(request, response);
		}
		if ("toUpdate".equals(method)) {
			toUpdate(request, response);
		}
		if ("doUpdate".equals(method)) {
			doUpdate(request, response);
		}
		if ("toAdd".equals(method)) {
			toAdd(request, response);
		}
		if ("doAdd".equals(method)) {
			doAdd(request, response);
		}
		if ("doDelete".equals(method)) {
			doDelete(request, response);
		}
	}

	/*
	 * private void demo(HttpServletRequest request, HttpServletResponse response) {
	 * 
	 * //第一步 取值 //取值 ajax提交的数据 request.setCharacterEncoding("UTF-8"); String method
	 * = request.getParameter("method"); //参数用html里name=""的值
	 * 
	 * 
	 * //取值 form表单提交的数据 method="post" enctype="multipart/form-data"
	 * if(ServletFileUpload.isMultipartContent(request)) { FileItemFactory factory =
	 * new DiskFileItemFactory(); ServletFileUpload upload = new
	 * ServletFileUpload(factory); List<FileItem> items =
	 * upload.parseRequest(request); for(FileItem i: items) { i.getFieldName();
	 * //参数名 //i.getString(); //参数值（返回字符串），如果是上传文件，则为文件内容 //i.get();
	 * //参数值（返回字节数组），如果是上传文件，则为文件内容 //i.getInputStream();//上传文件内容 //i.getSize();
	 * //参数值的字节大小 //i.getName(); //上传文件的文件名 //i.getContentType(); //上传文件的内容类型
	 * if(!i.isFormField()&&i.getSize()>0) //简单参数返回true，文件返回false { ServletContext
	 * servletContext = request.getSession().getServletContext();
	 * //2.调用realPath方法，获取根据一个虚拟目录得到的真实目录 String realPath =
	 * servletContext.getRealPath("/WEB-INF/file"); //3.如果这个真实的目录不存在，需要创建 File file
	 * = new File(realPath ); if(!file.exists()){ file.mkdirs(); }
	 * myfile.renameTo(new File(file,myfileFileName)); } } }
	 * 
	 * 
	 * //第二步 发网络请求或发数据库请求
	 * 
	 * 
	 * //第三步 正确跳转到哪 错误跳转到哪 一般用forward //A跳到新页面 // 1. try {
	 * response.sendRedirect("/a.jsp");//servlet?name=tom
	 * 通过get方法传递数据到下个页面(本域名下页面或跨域页面) 跳转后浏览器地址栏变化。 } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * //2. request.setAttribute("strRequest", ""); RequestDispatcher dispatcher =
	 * request.getRequestDispatcher("/a.jsp");//本域 跳转后浏览器地址栏不会变化。 try { dispatcher
	 * .forward(request, response); } catch (ServletException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * 
	 * //B跳回到本页面(带参数) response.setCharacterEncoding("utf-8");
	 * response.setContentType("application/json"); PrintWriter out =
	 * response.getWriter();
	 * 
	 * JSONArray jsonArray = JSONArray.fromObject(rows);
	 * 
	 * out.write("{"returnCode":"00","info":"成功。","returnData":" +
	 * jsonArray.toString()+ "}"); out.flush(); out.close();
	 * 
	 * 
	 * 
	 * }
	 * 
	 * }
	 */
	public void index(HttpServletRequest request, HttpServletResponse response) {
		int question_id = request.getParameter("question_id") == null ? 0
				: Integer.valueOf(request.getParameter("question_id"));
		request.setAttribute("question_id", question_id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/tbquestionlist.jsp");// 本域 跳转后浏览器地址栏不会变化。
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 题目信息表列表
	public void list(HttpServletRequest request, HttpServletResponse response) {
		String pageNo = request.getParameter("pageNo");
		if (StringUtils.isBlank(pageNo)) {// 判断某字符串是否为空或长度为0或由空白符(whitespace) 构成
			pageNo = "1";
			request.setAttribute("pageNo", pageNo);
		}
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
			request.setAttribute("pageSize", pageSize);
		}
		Map paraMap = new HashMap();
		paraMap.put("currIndex", (Integer.valueOf(pageNo) - 1) * Integer.valueOf(pageSize));
		paraMap.put("pageSize", Integer.valueOf(pageSize));
//		String question_id = request.getParameter("question_id");
//		if (StringUtils.isBlank(question_id)) {
//			// return;
//		} else {
//			paraMap.put("question_id", Integer.valueOf(question_id));
//		}
//		String question_msg = request.getParameter("question_msg");
//		if (StringUtils.isBlank(question_msg)) {
//			// return;
//		} else {
//			paraMap.put("question_msg", question_msg);
//		}
//		String question_img = request.getParameter("question_img");
//		if (StringUtils.isBlank(question_img)) {
//			// return;
//		} else {
//			paraMap.put("question_img", question_img);
//		}
//		String question_answers = request.getParameter("question_answers");
//		if (StringUtils.isBlank(question_answers)) {
//			// return;
//		} else {
//			paraMap.put("question_answers", question_answers);
//		}
//		String question_true_answer = request.getParameter("question_true_answer");
//		if (StringUtils.isBlank(question_true_answer)) {
//			// return;
//		} else {
//			paraMap.put("question_true_answer", question_true_answer);
//		}
//		String question_score = request.getParameter("question_score");
//		if (StringUtils.isBlank(question_score)) {
//			// return;
//		} else {
//			paraMap.put("question_score", Integer.valueOf(question_score));
//		}
//		String last_modify_tlr_id = request.getParameter("last_modify_tlr_id");
//		if (StringUtils.isBlank(last_modify_tlr_id)) {
//			// return;
//		} else {
//			paraMap.put("last_modify_tlr_id", last_modify_tlr_id);
//		}
//		String last_modify_prg_id = request.getParameter("last_modify_prg_id");
//		if (StringUtils.isBlank(last_modify_prg_id)) {
//			// return;
//		} else {
//			paraMap.put("last_modify_prg_id", last_modify_prg_id);
//		}
//		String last_modify_tm = request.getParameter("last_modify_tm");
//		if (StringUtils.isBlank(last_modify_tm)) {
//			// return;
//		} else {
//			paraMap.put("last_modify_tm", last_modify_tm);
//		}
		TbQuestionListService tbQuestionListService = new TbQuestionListServiceImpl();
		List<TbQuestionListBean> tbquestionlistBeans = null;
		try {
			tbquestionlistBeans = tbQuestionListService.get(paraMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbQuestionListService tbQuestionListService1 = new TbQuestionListServiceImpl();
		int count = 0;
		try {
			count = tbQuestionListService1.getCount(paraMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String pageString =
				PaginationUtil.getPaginationHtml(Integer.valueOf(count), Integer.valueOf(pageSize),
				Integer.valueOf(pageNo), Integer.valueOf(2), Integer.valueOf(5),
				"javascript:getAll('TbQuestionListAction!list?"
//				        +"question_id=" + question_id 
//				        + "%26question_msg="
//						+ question_msg + "%26question_img=" + question_img + "%26question_answers=" + question_answers
//						+ "%26question_true_answer=" + question_true_answer + "%26question_score=" + question_score
//						+ "%26last_modify_tlr_id=" + last_modify_tlr_id + "%26last_modify_prg_id=" + last_modify_prg_id
//						+ "%26last_modify_tm=" + last_modify_tm 
						+ "pageNo=" + pageNo + "",
				true);
		pageString = pageString.replace(".html", "");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", tbquestionlistBeans);
		jsonObject.put("pageString", pageString);
		jsonObject.put("count", count);
		try {
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(jsonObject.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 跳到修改页
	public void toUpdate(HttpServletRequest request, HttpServletResponse response) {
		// 选择器数据
		// 页面数据
		Map paraMap = new HashMap();
		String question_id = request.getParameter("question_id");
		if (StringUtils.isBlank(question_id)) {
			// return;
		} else {
			paraMap.put("question_id", Integer.valueOf(question_id));
		}
		TbQuestionListService tbQuestionListService = new TbQuestionListServiceImpl();
		List<TbQuestionListBean> tbquestionlistBeans = null;
		try {
			tbquestionlistBeans = tbQuestionListService.get(paraMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (tbquestionlistBeans != null && tbquestionlistBeans.size() == 1) {
			request.setAttribute("entity", (TbQuestionListBean) tbquestionlistBeans.get(0));
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/tbquestionlistSetting.jsp");// 本域
																											// 跳转后浏览器地址栏不会变化。
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/tbquestionlistAdd.jsp");// 本域
																										// 跳转后浏览器地址栏不会变化。
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void doUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TbQuestionListBean tbQuestionListBean = new TbQuestionListBean();
		String question_id = request.getParameter("question_id");
		if (StringUtils.isBlank(question_id)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionId(Integer.valueOf(question_id));
		}
		String question_msg = request.getParameter("question_msg");
		if (StringUtils.isBlank(question_msg)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionMsg(question_msg);
		}
		String question_img = request.getParameter("question_img");
		if (StringUtils.isBlank(question_img)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionImg(question_img);
		}
		String question_answers = request.getParameter("question_answers");
		if (StringUtils.isBlank(question_answers)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionAnswers(question_answers);
		}
		String question_true_answer = request.getParameter("question_true_answer");
		if (StringUtils.isBlank(question_true_answer)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionTrueAnswer(question_true_answer);
		}
		String question_score = request.getParameter("question_score");
		if (StringUtils.isBlank(question_score)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionScore(Integer.valueOf(question_score));
		}
		String last_modify_tlr_id = request.getParameter("last_modify_tlr_id");
		if (StringUtils.isBlank(last_modify_tlr_id)) {
			// return;
		} else {
			tbQuestionListBean.setLastModifyTlrId(last_modify_tlr_id);
		}
		String last_modify_prg_id = request.getParameter("last_modify_prg_id");
		if (StringUtils.isBlank(last_modify_prg_id)) {
			// return;
		} else {
			tbQuestionListBean.setLastModifyPrgId(last_modify_prg_id);
		}
		String last_modify_tm = request.getParameter("last_modify_tm");
		if (StringUtils.isBlank(last_modify_tm)) {
			// return;
		} else {
			tbQuestionListBean.setLastModifyTm(last_modify_tm);
		}
		TbQuestionListService tbQuestionListService = new TbQuestionListServiceImpl();
		try {
			tbQuestionListService.update(tbQuestionListBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/tbquestionlist.jsp");// 本域 跳转后浏览器地址栏不会变化。
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 跳到新增页
	public void toAdd(HttpServletRequest request, HttpServletResponse response) {
		// 选择器数据
		// 页面数据
		Map paraMap = new HashMap();
		String question_id = request.getParameter("question_id");
		if (StringUtils.isBlank(question_id)) {
			// return;
		} else {
			request.setAttribute("question_id", Integer.valueOf(question_id));
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/tbquestionlistAdd.jsp");// 本域 跳转后浏览器地址栏不会变化。
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TbQuestionListBean tbQuestionListBean = new TbQuestionListBean();
		String question_id = request.getParameter("question_id");
		if (StringUtils.isBlank(question_id)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionId(Integer.valueOf(question_id));
		}
		String question_msg = request.getParameter("question_msg");
		if (StringUtils.isBlank(question_msg)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionMsg(question_msg);
		}
		String question_img = request.getParameter("question_img");
		if (StringUtils.isBlank(question_img)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionImg(question_img);
		}
		String question_answers = request.getParameter("question_answers");
		if (StringUtils.isBlank(question_answers)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionAnswers(question_answers);
		}
		String question_true_answer = request.getParameter("question_true_answer");
		if (StringUtils.isBlank(question_true_answer)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionTrueAnswer(question_true_answer);
		}
		String question_score = request.getParameter("question_score");
		if (StringUtils.isBlank(question_score)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionScore(Integer.valueOf(question_score));
		}
		String last_modify_tlr_id = request.getParameter("last_modify_tlr_id");
		if (StringUtils.isBlank(last_modify_tlr_id)) {
			// return;
		} else {
			tbQuestionListBean.setLastModifyTlrId(last_modify_tlr_id);
		}
		String last_modify_prg_id = request.getParameter("last_modify_prg_id");
		if (StringUtils.isBlank(last_modify_prg_id)) {
			// return;
		} else {
			tbQuestionListBean.setLastModifyPrgId(last_modify_prg_id);
		}
		String last_modify_tm = request.getParameter("last_modify_tm");
		if (StringUtils.isBlank(last_modify_tm)) {
			// return;
		} else {
			tbQuestionListBean.setLastModifyTm(last_modify_tm);
		}
		TbQuestionListService tbQuestionListService = new TbQuestionListServiceImpl();
		try {
			tbQuestionListService.insert(tbQuestionListBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/tbquestionlist.jsp");// 本域 跳转后浏览器地址栏不会变化。
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TbQuestionListBean tbQuestionListBean = new TbQuestionListBean();
		String question_id = request.getParameter("question_id");
		if (StringUtils.isBlank(question_id)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionId(Integer.valueOf(question_id));
		}
		TbQuestionListService tbQuestionListService = new TbQuestionListServiceImpl();
		try {
			tbQuestionListService.delete(tbQuestionListBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write("success");
		out.flush();
		out.close();
	}

}
