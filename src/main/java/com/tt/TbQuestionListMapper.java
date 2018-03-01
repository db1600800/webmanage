package com.tt;
import java.util.List;
import java.util.Map;
//题目信息表
public interface TbQuestionListMapper {
	List<TbQuestionListBean> TbQuestionListSelect(Map para);
	int TbQuestionListSelectCount(Map para);
	int TbQuestionListSelectMax(Map para);
	void TbQuestionListInsert(TbQuestionListBean bean);
	void TbQuestionListUpdate(TbQuestionListBean bean);
	void TbQuestionListDelete(TbQuestionListBean bean);
}

