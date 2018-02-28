package com.tt;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tools.mybatis.MybatisUtil;

//题目信息表
public class TbQuestionListServiceImpl implements TbQuestionListService {
	private final static Logger logger = LoggerFactory.getLogger(TbQuestionListServiceImpl.class);

	private TbQuestionListMapper mapper;

	private SqlSessionFactory sessionFactory = MybatisUtil.getInstance();
	SqlSession session = sessionFactory.openSession();

	@Override
	public List<TbQuestionListBean> get(Map para) throws Exception {
		// TODO Auto-generated method stub
		mapper = session.getMapper(TbQuestionListMapper.class);

		List list = null;
		try {
			list = mapper.TbQuestionListSelect(para);
		} finally {
			session.close();
			return list;
		}
	}

	@Override
	public int getCount(Map para) throws Exception {
		// TODO Auto-generated method stub
		mapper = session.getMapper(TbQuestionListMapper.class);

		int count = 0;
		try {
			count = mapper.TbQuestionListSelectCount(para);
		} finally {
			session.close();
			return count;
		}
	}
	
	@Override
	public int getMax(Map para) throws Exception {
		// TODO Auto-generated method stub
mapper=session.getMapper(TbQuestionListMapper.class);

int max=0;
try{
  max=mapper.TbQuestionListSelectMax(para);
} finally {
session.close();
return max;
	}
	}

	@Override
	public void insert(TbQuestionListBean bean) {
		mapper = session.getMapper(TbQuestionListMapper.class);
		try {
			mapper.TbQuestionListInsert(bean);
			session.commit();
		} finally {
			session.close();
		}
	}

	@Override
	public void update(TbQuestionListBean bean) {
		mapper = session.getMapper(TbQuestionListMapper.class);
		try {
			mapper.TbQuestionListUpdate(bean);
			session.commit();
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(TbQuestionListBean bean) {
		mapper = session.getMapper(TbQuestionListMapper.class);
		try {
			mapper.TbQuestionListDelete(bean);
			session.commit();
		} finally {
			session.close();
		}
	}
}
