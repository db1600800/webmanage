package com.tt;
import java.util.List;
import java.util.Map;
//
public class TbQuestionListBean {
	public Integer question_id;/**题目id*/
	public String question_msg;/**题目内容*/
	public String question_img;/**题目图片*/
	public String question_answers;/**题目可选答案*/
	public String question_true_answer;/**题目标准答案*/
	public Integer question_score;/**题目分数*/
	public String last_modify_tlr_id;/**最后更新操作员*/
	public String last_modify_prg_id;/**最后更新程序*/
	public String last_modify_tm;/**最后更新时间*/
	public Integer getQuestionId() {
		return question_id;
	}
	public void setQuestionId(Integer question_id) {
		this.question_id = question_id;
	}
	public String getQuestionMsg() {
		return question_msg;
	}
	public void setQuestionMsg(String question_msg) {
		this.question_msg = question_msg;
	}
	public String getQuestionImg() {
		return question_img;
	}
	public void setQuestionImg(String question_img) {
		this.question_img = question_img;
	}
	public String getQuestionAnswers() {
		return question_answers;
	}
	public void setQuestionAnswers(String question_answers) {
		this.question_answers = question_answers;
	}
	public String getQuestionTrueAnswer() {
		return question_true_answer;
	}
	public void setQuestionTrueAnswer(String question_true_answer) {
		this.question_true_answer = question_true_answer;
	}
	public Integer getQuestionScore() {
		return question_score;
	}
	public void setQuestionScore(Integer question_score) {
		this.question_score = question_score;
	}
	public String getLastModifyTlrId() {
		return last_modify_tlr_id;
	}
	public void setLastModifyTlrId(String last_modify_tlr_id) {
		this.last_modify_tlr_id = last_modify_tlr_id;
	}
	public String getLastModifyPrgId() {
		return last_modify_prg_id;
	}
	public void setLastModifyPrgId(String last_modify_prg_id) {
		this.last_modify_prg_id = last_modify_prg_id;
	}
	public String getLastModifyTm() {
		return last_modify_tm;
	}
	public void setLastModifyTm(String last_modify_tm) {
		this.last_modify_tm = last_modify_tm;
	}
}

