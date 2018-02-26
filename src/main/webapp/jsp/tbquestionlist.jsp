<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>题目信息表</title>
<link rel="stylesheet" href="<%=basePath%>css/admin_style.css" type="text/css" />
	<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
	<script>
		function toAdd(){
			window.location.href="TbQuestionListServlet?method=toAdd&question_id=${question_id}";
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
var searchInput = $("#searchInput").val();
				$.ajax({
					type:'POST',
					dataType:'json',
					url:tzurl,
					data:{
question_id:searchInput					},
					success:function(result){
					
							var divtext = '';
							var data =result.list;
							var pagenational = result.pageString;
						
							for(var i=0;i<data.length;i++){
								var d=data[i];
								var dd=d.question_img;
								var obj = eval('(' + d + ')');
								divtext += '<tr class="even" style="white-space:nowrap; overflow:hidden; text-align:center">';
	 divtext += '<td>' + data[i]['question_msg'] + '</td>';
	 divtext += '<td>' + data.question_img + '</td>';
	 divtext += '<td>' + data.question_answers + '</td>';
	 divtext += '<td>' + data.question_true_answer + '</td>';
	 divtext += '<td>' + data.question_score + '</td>';
	 divtext += '<td>' + data.last_modify_tlr_id + '</td>';
	 divtext += '<td>' + data.last_modify_prg_id + '</td>';
	 divtext += '<td>' + data.last_modify_tm + '</td>';
								divtext += '<td ><a href="TbQuestionListServlet?method=toUpdate&question_id='+data[i].question_id+'"> [修改] </a>'
								divtext +='|<a href="javascript:void(0);" onclick="dodel('+data.question_id+')"> [删除] </a></td>';
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
	题目信息表：<input type="text" id="searchInput" style="margin-left:10px;width:100px;height:20px; "/>
	<input type="button" value="查询" name = "btn_search" onmouseover="this.style.cursor='hand'" style="width:50px;height:20px;font-size:12px;" class="subBtn" onclick="search()">
	<input type="button" value="新增" name = "btn_search" onmouseover="this.style.cursor='hand'" style="width:50px;height:20px;font-size:12px;" class="subBtn" onclick="toAdd()">
	
	</div>
	<div id="signContent">
	  <div class="table-list lr10">
	      <table width="100%">
	      <tr>
	        <td style="vertical-align: top;">
	        <table id="newtable" width="100%">
	          <thead class=trhead id="tblHeader">
	            
				<tr> 				<th  style="text-align:center;">题目内容</th>
				<th  style="text-align:center;">题目图片</th>
				<th  style="text-align:center;">题目可选答案</th>
				<th  style="text-align:center;">题目标准答案</th>
				<th  style="text-align:center;">题目分数</th>
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

