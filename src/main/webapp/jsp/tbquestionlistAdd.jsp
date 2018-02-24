<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>题目信息表</title>
<script src="${ctx}/kindeditor/kindeditor.js" type="text/javascript"></script>
<script src="${ctx}/kindeditor/lang/zh_CN.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
<script type="text/javascript">
$(document).on('ready', function() {
	
	var question_id='${question_id}';
	$("#question_id").val(question_id);
	
});
	function doSave() {
	  
		if ($("#question_id").val() == "") {
			alert("请输入题目id！");
			return false;
		}
		var question_id=$('#question_id').val();
		if(question_id=="" || !/^\d+$/.test(question_id)){  
	        alert("必须是正整数!"); 
	        return false;
	    }  
		
		if ($("#question_msg").val() == "") {
			alert("请输入题目内容！");
			return false;
		}
		if ($("#question_img").val() == "") {
			alert("请输入题目图片！");
			return false;
		}
		if ($("#question_answers").val() == "") {
			alert("请输入题目可选答案！");
			return false;
		}
		if ($("#question_true_answer").val() == "") {
			alert("请输入题目标准答案！");
			return false;
		}
		if ($("#question_score").val() == "") {
			alert("请输入题目分数！");
			return false;
		}
		var question_score=$('#question_score').val();
		if(question_score=="" || !/^\d+$/.test(question_score)){  
	        alert("必须是正整数!"); 
	        return false;
	    }  
		
		if ($("#last_modify_tlr_id").val() == "") {
			alert("请输入最后更新操作员！");
			return false;
		}
		if ($("#last_modify_prg_id").val() == "") {
			alert("请输入最后更新程序！");
			return false;
		}
		if ($("#last_modify_tm").val() == "") {
			alert("请输入最后更新时间！");
			return false;
		}
		 myForm.submit();
	}
	 
</script>
</head>
<body>
 <form action="TbQuestionListServlet?method=doAdd" method="post" enctype="multipart/form-data" name="myForm">
	<div style="margin-left: 20px;">题目信息表</div>
	<div class="table_form lr10">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tbody>
<input type="hidden" id="question_id" name="entity.question_id" value="${question_id}" />
				<tr>
					<td align="right" style="width: 120px"><font color="red">*</font>题目内容：</td>
					<td><input type="text" class="input-text wid400 bg"
						id="question_msg" name="entity.question_msg" value="${ entity.question_msg}"/></td>
					
				</tr>
				<tr>
					<td align="right" style="width: 120px"><font color="red">*</font>题目图片：</td>
					<td><input type="text" class="input-text wid400 bg"
						id="question_img" name="entity.question_img" value="${ entity.question_img}"/></td>
					
				</tr>
				<tr>
					<td align="right" style="width: 120px"><font color="red">*</font>题目可选答案：</td>
					<td><input type="text" class="input-text wid400 bg"
						id="question_answers" name="entity.question_answers" value="${ entity.question_answers}"/></td>
					
				</tr>
				<tr>
					<td align="right" style="width: 120px"><font color="red">*</font>题目标准答案：</td>
					<td><input type="text" class="input-text wid400 bg"
						id="question_true_answer" name="entity.question_true_answer" value="${ entity.question_true_answer}"/></td>
					
				</tr>
				<tr>
					<td align="right" style="width: 120px"><font color="red">*</font>题目分数：</td>
					<td><input type="text" class="input-text wid400 bg"
						id="question_score" name="entity.question_score" value="${ entity.question_score}"/></td>
					
				</tr>
				<tr>
					<td align="right" style="width: 120px"><font color="red">*</font>最后更新操作员：</td>
					<td><input type="text" class="input-text wid400 bg"
						id="last_modify_tlr_id" name="entity.last_modify_tlr_id" value="${ entity.last_modify_tlr_id}"/></td>
					
				</tr>
				<tr>
					<td align="right" style="width: 120px"><font color="red">*</font>最后更新程序：</td>
					<td><input type="text" class="input-text wid400 bg"
						id="last_modify_prg_id" name="entity.last_modify_prg_id" value="${ entity.last_modify_prg_id}"/></td>
					
				</tr>
				<tr>
					<td align="right" style="width: 120px"><font color="red">*</font>最后更新时间：</td>
					<td><input id="last_modify_tm" name="entity.last_modify_tm" value="${ entity.last_modify_tm}" style="margin-right:10px;width: 150px" class="Wdate" 
					 onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/></td>
				</tr>
				<tr height="60px">
					<td align="right" style="width: 120px"></td>
					<td><input type="button" value="保存" name="btn"
						onmouseover="this.style.cursor='hand'" class="subBtn"
						onclick="doSave()"> <input type="button" value="返回"
						name="btn2" onmouseover="this.style.cursor='hand'" class="subBtn"
						onclick="history.go(-1)">
				</tr>
			</tbody>
		</table>
	</div>
	</form>
</body>
</html>

