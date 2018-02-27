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
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import com.tools.CommonFunction;
import com.tools.PaginationUtil;
import com.tools.StrutsParamUtils;
import com.tools.hibernate.ObjectDao;

//题目信息表
//@SuppressWarnings("unchecked")
//@Namespace(value = "/mgr")
@Action(value = "TbQuestionListAction", results = {
		@Result(name = "tbquestionlist", location = "/WEB-INF/mgr/tbquestionlist.jsp"),
		@Result(name = "tbquestionlistSetting", location = "/WEB-INF/mgr/tbquestionlistSetting.jsp"),
		@Result(name = "tbquestionlistAdd", location = "/WEB-INF/mgr/tbquestionlistAdd.jsp"),

})
public class TbQuestionListAction {

	private TbQuestionListBean entity;

	public TbQuestionListBean getEntity() {
		return entity;
	}

	public void setEntity(TbQuestionListBean tbquestionlist) {
		this.entity = tbquestionlist;
	}

	public String index() {
		HttpServletRequest request = StrutsParamUtils.getRequest();
		int question_id = Integer.valueOf(StrutsParamUtils.getPraramValue("question_id", "0"));
		request.setAttribute("question_id", question_id);
		return "tbquestionlist";
	}

	// 题目信息表列表
	public void list() {
		HttpServletRequest request = StrutsParamUtils.getRequest();
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
		paraMap.put("pageSize", pageSize);
		
		
		String question_id = StrutsParamUtils.getPraramValue("question_id", "");
		if (StringUtils.isBlank(question_id)) {
			// return;
		} else {
			paraMap.put("question_id", Integer.valueOf(question_id));
		}
		String question_msg = StrutsParamUtils.getPraramValue("question_msg", "");
		if (StringUtils.isBlank(question_msg)) {
			// return;
		} else {
			paraMap.put("question_msg", question_msg);
		}
		String question_img = StrutsParamUtils.getPraramValue("question_img", "");
		if (StringUtils.isBlank(question_img)) {
			// return;
		} else {
			paraMap.put("question_img", question_img);
		}
		String question_answers = StrutsParamUtils.getPraramValue("question_answers", "");
		if (StringUtils.isBlank(question_answers)) {
			// return;
		} else {
			paraMap.put("question_answers", question_answers);
		}
		String question_true_answer = StrutsParamUtils.getPraramValue("question_true_answer", "");
		if (StringUtils.isBlank(question_true_answer)) {
			// return;
		} else {
			paraMap.put("question_true_answer", question_true_answer);
		}
		String question_score = StrutsParamUtils.getPraramValue("question_score", "");
		if (StringUtils.isBlank(question_score)) {
			// return;
		} else {
			paraMap.put("question_score", Integer.valueOf(question_score));
		}
		String last_modify_tlr_id = StrutsParamUtils.getPraramValue("last_modify_tlr_id", "");
		if (StringUtils.isBlank(last_modify_tlr_id)) {
			// return;
		} else {
			paraMap.put("last_modify_tlr_id", last_modify_tlr_id);
		}
		String last_modify_prg_id = StrutsParamUtils.getPraramValue("last_modify_prg_id", "");
		if (StringUtils.isBlank(last_modify_prg_id)) {
			// return;
		} else {
			paraMap.put("last_modify_prg_id", last_modify_prg_id);
		}
		String last_modify_tm = StrutsParamUtils.getPraramValue("last_modify_tm", "");
		if (StringUtils.isBlank(last_modify_tm)) {
			// return;
		} else {
			paraMap.put("last_modify_tm", last_modify_tm);
		}
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
			count = tbQuestionListService1.getCount( paraMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String pageString = PaginationUtil.getPaginationHtml(Integer.valueOf(count), Integer.valueOf(pageSize),
				Integer.valueOf(pageNo), Integer.valueOf(2), Integer.valueOf(5),
				"javascript:getAll('TbQuestionListAction!list?question_id=" + question_id + "%26question_msg="
						+ question_msg + "%26question_img=" + question_img + "%26question_answers=" + question_answers
						+ "%26question_true_answer=" + question_true_answer + "%26question_score=" + question_score
						+ "%26last_modify_tlr_id=" + last_modify_tlr_id + "%26last_modify_prg_id=" + last_modify_prg_id
						+ "%26last_modify_tm=" + last_modify_tm + "%26pageNo=" + pageNo + "",
				true);
		pageString = pageString.replace(".html", "");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", tbquestionlistBeans);
		jsonObject.put("pageString", pageString);
		jsonObject.put("count", count);
		try {
			StrutsParamUtils.getResponse().setCharacterEncoding("UTF-8");
			StrutsParamUtils.getResponse().getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 跳到修改页
	public String toUpdate() {
		HttpServletRequest request = StrutsParamUtils.getRequest();
		// 选择器数据
		// 页面数据
		Map paraMap = new HashMap();
		String question_id = StrutsParamUtils.getPraramValue("question_id", "");
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
			return "tbquestionlistSetting";
		} else {
			return "tbquestionlistAdd";
		}

	}

	public String doUpdate() throws IOException {
		HttpServletRequest request = StrutsParamUtils.getRequest();
		TbQuestionListBean tbQuestionListBean = new TbQuestionListBean();
		String question_id = StrutsParamUtils.getPraramValue("question_id", "");
		if (StringUtils.isBlank(question_id)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionId(Integer.valueOf(question_id));
		}
		String question_msg = StrutsParamUtils.getPraramValue("question_msg", "");
		if (StringUtils.isBlank(question_msg)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionMsg(question_msg);
		}
		String question_img = StrutsParamUtils.getPraramValue("question_img", "");
		if (StringUtils.isBlank(question_img)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionImg(question_img);
		}
		String question_answers = StrutsParamUtils.getPraramValue("question_answers", "");
		if (StringUtils.isBlank(question_answers)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionAnswers(question_answers);
		}
		String question_true_answer = StrutsParamUtils.getPraramValue("question_true_answer", "");
		if (StringUtils.isBlank(question_true_answer)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionTrueAnswer(question_true_answer);
		}
		String question_score = StrutsParamUtils.getPraramValue("question_score", "");
		if (StringUtils.isBlank(question_score)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionScore(Integer.valueOf(question_score));
		}
		String last_modify_tlr_id = StrutsParamUtils.getPraramValue("last_modify_tlr_id", "");
		if (StringUtils.isBlank(last_modify_tlr_id)) {
			// return;
		} else {
			tbQuestionListBean.setLastModifyTlrId(last_modify_tlr_id);
		}
		String last_modify_prg_id = StrutsParamUtils.getPraramValue("last_modify_prg_id", "");
		if (StringUtils.isBlank(last_modify_prg_id)) {
			// return;
		} else {
			tbQuestionListBean.setLastModifyPrgId(last_modify_prg_id);
		}
		String last_modify_tm = StrutsParamUtils.getPraramValue("last_modify_tm", "");
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
		return "tbquestionlist";
	}

	// 跳到新增页
	public String toAdd() {
		HttpServletRequest request = StrutsParamUtils.getRequest();
		// 选择器数据
		// 页面数据
		Map paraMap = new HashMap();
		String question_id = StrutsParamUtils.getPraramValue("question_id", "");
		if (StringUtils.isBlank(question_id)) {
			// return;
		} else {
			request.setAttribute("question_id", Integer.valueOf(question_id));
		}
		return "tbquestionlistAdd";

	}

	public String doAdd() throws IOException {
		HttpServletRequest request = StrutsParamUtils.getRequest();
		TbQuestionListBean tbQuestionListBean = new TbQuestionListBean();
		String question_id = StrutsParamUtils.getPraramValue("question_id", "");
		if (StringUtils.isBlank(question_id)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionId(Integer.valueOf(question_id));
		}
		String question_msg = StrutsParamUtils.getPraramValue("question_msg", "");
		if (StringUtils.isBlank(question_msg)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionMsg(question_msg);
		}
		String question_img = StrutsParamUtils.getPraramValue("question_img", "");
		if (StringUtils.isBlank(question_img)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionImg(question_img);
		}
		String question_answers = StrutsParamUtils.getPraramValue("question_answers", "");
		if (StringUtils.isBlank(question_answers)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionAnswers(question_answers);
		}
		String question_true_answer = StrutsParamUtils.getPraramValue("question_true_answer", "");
		if (StringUtils.isBlank(question_true_answer)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionTrueAnswer(question_true_answer);
		}
		String question_score = StrutsParamUtils.getPraramValue("question_score", "");
		if (StringUtils.isBlank(question_score)) {
			// return;
		} else {
			tbQuestionListBean.setQuestionScore(Integer.valueOf(question_score));
		}
		String last_modify_tlr_id = StrutsParamUtils.getPraramValue("last_modify_tlr_id", "");
		if (StringUtils.isBlank(last_modify_tlr_id)) {
			// return;
		} else {
			tbQuestionListBean.setLastModifyTlrId(last_modify_tlr_id);
		}
		String last_modify_prg_id = StrutsParamUtils.getPraramValue("last_modify_prg_id", "");
		if (StringUtils.isBlank(last_modify_prg_id)) {
			// return;
		} else {
			tbQuestionListBean.setLastModifyPrgId(last_modify_prg_id);
		}
		String last_modify_tm = StrutsParamUtils.getPraramValue("last_modify_tm", "");
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
		return "tbquestionlist";
	}

	public void doDelete() throws IOException {
		HttpServletRequest request = StrutsParamUtils.getRequest();
		TbQuestionListBean tbQuestionListBean = new TbQuestionListBean();
		String question_id = StrutsParamUtils.getPraramValue("question_id", "");
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
		StrutsParamUtils.getResponse().getWriter().write("success");
		return;
	}

}
