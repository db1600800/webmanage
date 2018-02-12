package com.tt;

import java.util.List;
import java.util.Map;

//题目信息表
public interface TbQuestionListService {
	List<TbQuestionListBean> get(Map para) throws Exception;

	int getCount(Map para) throws Exception;

	void insert(TbQuestionListBean bean);

	void update(TbQuestionListBean bean);

	void delete(TbQuestionListBean bean);
}
