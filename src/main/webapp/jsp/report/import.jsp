<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>导入表数据</title>
<link href="${pageContext.request.contextPath}/css/bootstrap-combined.min.css" type="text/css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/index-style.css" type="text/css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/ladda-themeless.min.css" type="text/css"
	rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.12.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/menu.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/spin.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ladda.min.js"></script>
</head>
<body>
	<!--  ul class="nav nav-pills topnav">
	<li class="nettips"></li>
	<li class="pull-right"><a href="/Excel2Db/import" style="color:#5C6;">退出</a></li>
	<li class="pull-right disabled"><a href="#" style="color:#DE9;">${sessionScope.zntype}您好，<b>${sessionScope.username}</b></a></li>
</ul-->
	<div class="container-fluid" style="padding:0 5px;">
		<div class="row-fluid">

			<div>
				<h3>导入表数据</h3>
				
				<form class="self-form" style="width:500px;overflow:hidden;"
					enctype="multipart/form-data" method="post"
					action="${pageContext.request.contextPath}/import" id="import-form">
					<div class="form-group">
						<label class="form-label">选择对应表</label>
						<div class="col-sm-10">
							<select class="form-control" name="table">
								<option value="">-请选择-</option>
								<option value="city">保险先锋地市</option>
								<option value="county">保险先锋县域</option>
								<option value="postal">千万标杆网点</option>
								<option value="person">百万大单英雄榜</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="form-label">选择文件</label>
						<div class="col-sm-10">
							<a type="button" class="btn btn-info btn-lg"
								style="position:relative;">选择文件 <input type="file"
								name="importxls"
								style="position:absolute;
							opacity:0;width:100%;height:100%;top:0;left:0;z-index:999;">
							</a> <span id="file_name"></span>
						</div>
					</div>
					<!--  div class="form-group">
			    <label class="form-label">从第几行开始是数据</label>
			    <div class="col-sm-10">
			    	<input type="text" value="2" name="startrow">
			    </div>
			  </div-->
					<!--  span class="help-block"><span class="label label-info">要求</span> 起始行默认为2，最小值为1；Excel中字段顺序要和数据库中的一致；列数要和数据中的字段数相同；数字类型请转化为数字格式，设置成常规即可</span-->
					<span class="help-block"><span class="label label-warning">注意</span>
						目前支持Microsoft Excel 2007及以上版本文件(.xlsx);</span>
					<div class="form-group">
						<label class="form-label" style="height:auto;"></label>
						<button id="import-btn" type="submit"
							class="btn btn-primary ladda-button" data-style="slide-down">
							<span class="ladda-label">导入</span>
						</button>
					</div>
				</form>
				<div class="alert" role="alert" style="margin:5px 5px;display:none;"
					id="tip-info">
					<button type="button" class="close"
						onclick="$('#tip-info').css('display','none')">
						<span aria-hidden="true">&times;</span>
					</button>
					<span></span>
				</div>
				<table class="table table-hover" id="table-list">
					<thead>

					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$("[name=importxls]").change(function() {
				$("#file_name").text(this.value);
			})
			$("[item=import]").addClass("active");

			var form = $("#import-form");
			var btn;
			form.ajaxForm({
				beforeSubmit : function() {
					var table = $("[name=table]").val();
					var file = $("[name=importxls]").val();
						var tableString = $("#table-list tbody");
						tableString.html('');
						$('#tip-info').css('display','none')

					if (table == '') {
						$("#tip-info").addClass("alert-danger").css("display",
								"block");
						$("#tip-info > span").text("请选择一张表");
						return false;
					} else if (file == "") {
						$("#tip-info").addClass("alert-danger").css("display",
								"block");
						$("#tip-info > span").text("请选择要上传的文件");
						return false;
					} else if (!checkFile(file)) {
						$("#tip-info").addClass("alert-danger").css("display",
								"block");
						$("#tip-info > span").text("文件格式错误");
						return false;
					} else {
						btn = Ladda.create($("#import-btn").get(0));
						btn.start();
					}
				},
				dataType : "json",
				success : function(data) {
					if (data.status == "true") {
						$("#tip-info").removeClass("alert-danger").addClass(
								"alert-success").css("display", "block");
						$("#tip-info > span").text(data.info);

						var table = $("#table-list tbody");
						var htmltt = '';

						var rows = data.rows;
						for (var i = 0; i < rows.length; i++) {
							var columns = rows[i];
							var html = "<tr>";
							for (var j = 0; j < columns.length; j++) {

if(i==0)
{
		//html += "<td align=\"center\" valign=\"middle\" colspan=\""+columns.length+"\">" + columns[j] + "</td>";
		break;

}else
{
		html += "<td>" + columns[j] + "</td>";
}
						
							}
							html += "</tr>";
							htmltt += html;
						}

						table.html(htmltt);

					} else {
						$("#tip-info").removeClass("alert-danger").addClass(
								"alert-danger").css("display", "block");
						$("#tip-info > span").text(data.info);
					}
					btn.stop();
				}
			});
		});

		function checkFile(file) {
			return /\.(xls)$/.test(file) || /\.(xlsx)$/.test(file);
		}
	</script>
</body>
</html>