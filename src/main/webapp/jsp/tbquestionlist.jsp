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
<script type="text/javascript" language="javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
	<script>
		function toAdd(){
			var question_id="${question_id}";
			$("#question_id").val(question_id);
			window.location.href="TbQuestionListServlet?method=toAdd";
		}
		
		
		$(document).on('ready', function() {
			var question_id="${question_id}";
			$("#question_id").val(question_id);
			getAll('TbQuestionListServlet?method=list');
		});
		
		
		function search(){
			getAll('TbQuestionListServlet?method=list');
		}
		
		function dodel(mquestion_id){
			var r=confirm("确定要删除吗？");
			if(r){
				$.ajax({
					type:'POST',
					url:'TbQuestionListServlet?method=doDelete',
					data:{
question_id:mquestion_id					
},
					success:function(k){
							alert("删除成功！")
							window.location.href = "TbQuestionListServlet?method=index&question_id=${question_id}";
					},
					error : function() {
						alert("对不起，系统错误，请稍候重试！")
					}
				});
			}
			
			
		}
		function getAll(tzurl){
		var question_msg=$('#question_msg').val();
		var question_img=$('#question_img').val();
		var question_answers=$('#question_answers').val();
		var question_true_answer=$('#question_true_answer').val();
		var question_score=$('#question_score').val();
		if(question_score!=""){  
		if( !/^\d+$/.test(question_score)){  
	        alert("题目分数(选择)必须是正整数!"); 
	        return ;
	    }  
	    }  
		
		var last_modify_tlr_id=$('#last_modify_tlr_id').val();
		var last_modify_prg_id=$('#last_modify_prg_id').val();
		var last_modify_tm=$('#last_modify_tm').val();
	

			$.ajax({
					type:'POST',
					dataType:'json',
					url:tzurl,
					data:{
question_msg:$("#question_msg").val(),
question_img:$("#question_img").val(),
question_answers:$("#question_answers").val(),
question_true_answer:$("#question_true_answer").val(),
question_score:$("#question_score").val(),
last_modify_tlr_id:$("#last_modify_tlr_id").val(),
last_modify_prg_id:$("#last_modify_prg_id").val(),
last_modify_tm:$("#last_modify_tm").val()					},
					success:function(result){
	
							var divtext = '';
							var data =result.list;
							var pagenational = result.pageString;
						
							for(var i=0;i<data.length;i++){
								divtext += '<tr class="even" style="white-space:nowrap; overflow:hidden; text-align:center">';
	 divtext += '<td>' + data[i].questionMsg + '</td>';
	 divtext += '<td>' + data[i].questionImg + '</td>';
	 divtext += '<td>' + data[i].questionAnswers + '</td>';
	 divtext += '<td>' + data[i].questionTrueAnswer + '</td>';
	 divtext += '<td>' + data[i].questionScore + '</td>';
	 divtext += '<td>' + data[i].lastModifyTlrId + '</td>';
	 divtext += '<td>' + data[i].lastModifyPrgId + '</td>';
	 divtext += '<td>' + data[i].lastModifyTm + '</td>';
								divtext += '<td ><a href="TbQuestionListServlet?method=toUpdate&question_id='+data[i].questionId+'"> [修改] </a>'
								divtext +='|<a href="javascript:void(0);" onclick="dodel('+data[i].questionId+')"> [删除] </a></td>';
								divtext += '</tr>';
							}
							//divtext += pagenational;
							$("#newtable tbody").html(divtext);
							$("#pageContent").html(pagenational);
					}
				});
		}
	</script>
</head>
<body style="overflow:auto">
	<div style="padding-left:20px;margin-bottom:10px;" >
	<input type="hidden" id="question_id" name="question_id" value="" />
	题目信息表:
<div>

<font size="2" color="">题目内容(编辑):</font><input type="text" class="input-text wid400 bg" id="question_msg" name="question_msg" value="${ question_msg}"/>
	
<font size="2" color="">题目图片:</font>	<input type="file" id="question_img" name="question_img"  />

<font size="2" color="">题目可选答案(编辑):</font><input type="text" class="input-text wid400 bg" id="question_answers" name="question_answers" value="${ question_answers}"/>

<font size="2" color="">题目标准答案:</font><input type="text" class="input-text wid400 bg" id="question_true_answer" name="question_true_answer" value="${ question_true_answer}"/>
	
<font size="2" color="">题目分数(选择):</font><select id="question_score" name="question_score" class="form-control" style="width: 187px;height:23px;margin-bottom:0px;">
				<option value="">请选择</option>
					<c:forEach var="item" items="${question_scoreSelectList}">	
							<option value='${fn:substringBefore(item,"-")}'>${fn:substringAfter(item,"-")}</option>
					</c:forEach>
					
</select>

<font size="2" color="">最后更新操作员:</font><input type="text" class="input-text wid400 bg" id="last_modify_tlr_id" name="last_modify_tlr_id" value="${ last_modify_tlr_id}"/>

<font size="2" color="">最后更新程序:</font><input type="text" class="input-text wid400 bg" id="last_modify_prg_id" name="last_modify_prg_id" value="${ last_modify_prg_id}"/>
	
<font size="2" color="">最后更新时间:</font>	<input id="last_modify_tm" name="last_modify_tm" value="${ last_modify_tm}" style="margin-right:10px;width: 150px" class="Wdate" 
					 onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>

最后更新时间结束:	<input id="last_modify_tmEnd" name="last_modify_tmEnd" value="${ last_modify_tmEnd}" style="margin-right:10px;width: 150px" class="Wdate" 
					 onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})"/>
	
<input type="button" value="查询" name = "btn_search" onmouseover="this.style.cursor='hand'" style="width:50px;height:20px;font-size:12px;" class="subBtn" onclick="search()">
	
<input type="button" value="新增" name = "btn_search" onmouseover="this.style.cursor='hand'" style="width:50px;height:20px;font-size:12px;" class="subBtn" onclick="toAdd()">
</div>
	
	</div>
	<div id="signContent">
	  <div class="table-list lr10">
	      <table width="100%">
	      <tr>
	        <td style="vertical-align: top;">
	        <table id="newtable" width="100%">
	          <thead class=trhead id="tblHeader">
	            
				<tr> 				<th  style="text-align:center;">题目内容(编辑)</th>
				<th  style="text-align:center;">题目图片</th>
				<th  style="text-align:center;">题目可选答案(编辑)</th>
				<th  style="text-align:center;">题目标准答案</th>
				<th  style="text-align:center;">题目分数(选择)</th>
				<th  style="text-align:center;">最后更新操作员</th>
				<th  style="text-align:center;">最后更新程序</th>
				<th  style="text-align:center;">最后更新时间</th>
				<th  style="text-align:center;">操作</th></tr>
				</thead>
				<tbody id="records">
			    </tbody>
		</table>
	       </td>
	      </tr>
	      </table>
	</div>
	<div id="pageContent"></div>
  </div>
	
</body>
</html>

