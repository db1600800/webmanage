<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>题目信息表</title>
<link rel="stylesheet" href="<%=basePath%>css/admin_style.css" type="text/css" />
<script src="<%=basePath%>kindeditor/kindeditor.js" type="text/javascript"></script>
<script src="<%=basePath%>kindeditor/lang/zh_CN.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
<script type="text/javascript">
var question_msgEditor;
KindEditor.ready(function(K) {
question_msgEditor = K.create('#question_msg', {
resizeType : 1,
allowPreviewEmoticons : false,
allowImageUpload : true,
afterBlur:function(){this.sync();},   //关键  同步KindEditor的值到textarea文本框   解决了多个editor的取值问题
uploadJson : '/kindeditor/jsp/upload_json.jsp',
fileManagerJson : '/kindeditor/jsp/file_manager_json.jsp',
items : [
'source','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
'insertunorderedlist', '|', ,'media' ,'link']
});
});
var question_answersEditor;
KindEditor.ready(function(K) {
question_answersEditor = K.create('#question_answers', {
resizeType : 1,
allowPreviewEmoticons : false,
allowImageUpload : true,
afterBlur:function(){this.sync();},   //关键  同步KindEditor的值到textarea文本框   解决了多个editor的取值问题
uploadJson : '/kindeditor/jsp/upload_json.jsp',
fileManagerJson : '/kindeditor/jsp/file_manager_json.jsp',
items : [
'source','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
'insertunorderedlist', '|', ,'media' ,'link']
});
});
$(document).on('ready', function() {
	
	var question_id='${question_id}';
	$("#question_id").val(question_id);
	
});
	function doSave() {
	  
	/* 	if ($("#question_id").val() == "") {
			alert("请输入题目id！");
			return false;
		}
		var question_id=$('#question_id').val();
		if(question_id=="" || !/^\d+$/.test(question_id)){  
	        alert("题目id必须是正整数!"); 
	        return false;
	    }   */
		
		if ($("#question_msg").val() == "") {
			alert("请输入题目内容(编辑)！");
			return false;
		}
		if ($("#question_img").val() == "") {
			alert("请输入题目图片！");
			return false;
		}
		if ($("#question_answers").val() == "") {
			alert("请输入题目可选答案(编辑)！");
			return false;
		}
		if ($("#question_true_answer").val() == "") {
			alert("请输入题目标准答案！");
			return false;
		}
		if ($("#question_score").val() == "") {
			alert("请输入题目分数(选择)！");
			return false;
		}
		var question_score=$('#question_score').val();
		if(question_score=="" || !/^\d+$/.test(question_score)){  
	        alert("题目分数(选择)必须是正整数!"); 
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
			alert("请输入最后更新时间！");
			return false;
		}
		 if(question_msgEditor.count('text')>2000){
            alert('题目内容(编辑)字数超过限制');
             return;
         }
		 if(question_answersEditor.count('text')>2000){
            alert('题目可选答案(编辑)字数超过限制');
             return;
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
<input type="hidden" id="question_id" name="question_id" value="${question_id}" />
	<tr>
					<td align="right" style="width: 120px">题目内容(编辑)：</td>
						<td><textarea type="text" style="width:400px;height:50px;"
							id="question_msg" name="question_msg" >${question_msg}</textarea></td>
				</tr>
					<tr >
						<td align="right" style="width: 120px">题目图片：
						</td>
						<td>
							<input type="file" id="question_img" name="question_img"  />
								
						</td>
						
					</tr>
	<tr>
					<td align="right" style="width: 120px">题目可选答案(编辑)：</td>
						<td><textarea type="text" style="width:400px;height:50px;"
							id="question_answers" name="question_answers" >${question_answers}</textarea></td>
				</tr>
				<tr>
					<td align="right" style="width: 120px"><font color="red">*</font>题目标准答案：</td>
					<td><input type="text" class="input-text wid400 bg"
						id="question_true_answer" name="question_true_answer" value="${ question_true_answer}"/></td>
					
				</tr>
					<tr >
						<td align="right" style="width: 120px">题目分数(选择)：
						</td>
						<td>
<select id="question_score" name="question_score" class="form-control" style="width: 187px;height:28px;margin-bottom:10px;">
				<option value="">请选择</option>
					<c:forEach var="item" items="${question_scoreSelectList}">	
							<option value='${fn:substringBefore(item,"-")}'>${fn:substringAfter(item,"-")}</option>
					</c:forEach>
					
</select>
						</td>
						
					</tr>
				<tr>
					<td align="right" style="width: 120px"><font color="red">*</font>最后更新操作员：</td>
					<td><input type="text" class="input-text wid400 bg"
						id="last_modify_tlr_id" name="last_modify_tlr_id" value="${ last_modify_tlr_id}"/></td>
					
				</tr>
				<tr>
					<td align="right" style="width: 120px"><font color="red">*</font>最后更新程序：</td>
					<td><input type="text" class="input-text wid400 bg"
						id="last_modify_prg_id" name="last_modify_prg_id" value="${ last_modify_prg_id}"/></td>
					
				</tr>
				<tr>
					<td align="right" style="width: 120px"><font color="red">*</font>最后更新时间：</td>
					<td><input id="last_modify_tm" name="last_modify_tm" value="${ last_modify_tm}" style="margin-right:10px;width: 150px" class="Wdate" 
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

