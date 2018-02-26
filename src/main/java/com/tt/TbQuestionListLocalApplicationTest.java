package com.tt;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import com.tools.CommonFunction;
import com.tools.PaginationUtil;

//题目信息表
public class TbQuestionListLocalApplicationTest {

	public static void main(String[] args) {
		TbQuestionListLocalApplicationTest test = new TbQuestionListLocalApplicationTest();
		try {
			test.doPost();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost() throws IOException {
		doAdd();
		list();
		//doUpdate();
		//list();
		//doDelete();
		//list();
	}

	// 题目信息表列表
	public void list() {
		Map paraMap = new HashMap();
		paraMap.put("currIndex", Integer.valueOf("0"));
		paraMap.put("pageSize", Integer.valueOf("10"));
		//paraMap.put("question_id", Integer.valueOf(1));
//		paraMap.put("question_msg", " question_msg");
//		paraMap.put("question_img", " question_img");
//		paraMap.put("question_answers", " question_answers");
//		paraMap.put("question_true_answer", " question_true_answer");
//		paraMap.put("question_score", Integer.valueOf(1));
//		paraMap.put("last_modify_tlr_id", " last_modify_tlr_id");
//		paraMap.put("last_modify_prg_id", " last_modify_prg_id");
//		paraMap.put("last_modify_tm", " last_modify_tm");
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

	}

	public void doUpdate() throws IOException {
		TbQuestionListBean tbQuestionListBean = new TbQuestionListBean();
		tbQuestionListBean.setQuestionId(Integer.valueOf(1));
	tbQuestionListBean.setQuestionMsg("question_msg1");
//		tbQuestionListBean.setQuestionImg("question_img");
//		tbQuestionListBean.setQuestionAnswers("question_answers");
//		tbQuestionListBean.setQuestionTrueAnswer("question_true_answer");
//		tbQuestionListBean.setQuestionScore(Integer.valueOf(2));
//		tbQuestionListBean.setLastModifyTlrId("last_modify_tlr_id");
//		tbQuestionListBean.setLastModifyPrgId("last_modify_prg_id");
//		tbQuestionListBean.setLastModifyTm("last_modify_tm");
		TbQuestionListService tbQuestionListService = new TbQuestionListServiceImpl();
		try {
			tbQuestionListService.update(tbQuestionListBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doAdd() throws IOException {
		TbQuestionListBean tbQuestionListBean = new TbQuestionListBean();
		tbQuestionListBean.setQuestionId(Integer.valueOf(1));
		tbQuestionListBean.setQuestionMsg("question_msg");
		tbQuestionListBean.setQuestionImg("question_img");
		tbQuestionListBean.setQuestionAnswers("question_answers");
		tbQuestionListBean.setQuestionTrueAnswer("question_true_answer");
		tbQuestionListBean.setQuestionScore(Integer.valueOf(1));
		tbQuestionListBean.setLastModifyTlrId("last_modify_tlr_id");
		tbQuestionListBean.setLastModifyPrgId("last_modify_prg_id");
		tbQuestionListBean.setLastModifyTm("last_modify_tm");
		TbQuestionListService tbQuestionListService = new TbQuestionListServiceImpl();
		try {
			tbQuestionListService.insert(tbQuestionListBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doDelete() throws IOException {
		TbQuestionListBean tbQuestionListBean = new TbQuestionListBean();
		tbQuestionListBean.setQuestionId(Integer.valueOf(1));
		TbQuestionListService tbQuestionListService = new TbQuestionListServiceImpl();
		try {
			tbQuestionListService.delete(tbQuestionListBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
