<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String activity_class_m = (String) session
            .getAttribute("activity_class_m");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>奖状</title>


<script type="text/javascript" language="javascript"
    src="/gdmgr/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
    src="/gdmgr/scripts/jquery-1.12.2.min.js"></script>
<script language="JavaScript" type="text/javascript">
    //第一种方法  
    function method1(tableid) {
        var excelIdp = $("#excelId").val();

        var parametermap = $("#parameter").val();
        var data = JSON.parse(parametermap);
        var temp = "";
        for ( var key in data) {
            temp += key + "=" + data[key] + "&";
        }
        var para = temp.substring(0, temp.lastIndexOf('&'));
        /*----------------*/
        var excelQueryColumn = $("#excelQueryColumn").val();
        var data = JSON.parse(excelQueryColumn);
        var temp = "";
        for (var i = 0; i < data.length; i++) {
            var id = data[i].id;
            var cnname = data[i].cnname;
            var column = data[i].column;
            var type = data[i].type;
            var way = data[i].way;

            if (id == null || id == "") {
                id = "";
            }
            var value = $("#" + column.replace('.', '') + id + "").val();
            if (value != null && value != "" &&value != "null")
            {
                if (way != null && way != "") {
                    temp += " and " + column + way + "#" + value + "#";
                } else {
                    temp += " and " + column + " lik #^" + value + "^#";
                }
            }
        }
        $("#query").val(temp);
        var queryp = $("#query").val();
        var url = "/gdmgr/ToExcelAction/activityReportExportExcel.do?"
                + para+"&query="+encodeURIComponent(queryp);
        window.open(url, '_self');

    }
</script>
<script>
    $(document).on('ready', function() {
        var excelIdp = "${excelId}";
        $("#excelId").val(excelIdp);

        var parameterp = '${parameter}';
        //var s=JSON.stringify(parameterp) ;
        $("#parameter").val(parameterp);
        getAll('/gdmgr/ToExcelAction/activityReportList.do');
    });

    function search() {

        var excelQueryColumn = $("#excelQueryColumn").val();

        var data = JSON.parse(excelQueryColumn);
        var temp = "";
        for (var i = 0; i < data.length; i++) {
            var id = data[i].id;
            var cnname = data[i].cnname;
            var column = data[i].column;
            var type = data[i].type;
            var way = data[i].way;

            if (id == null || id == "") {
                id = "";
            }
            var value = $("#" + column.replace('.', '') + id + "").val();
            if (value != null && value != "" &&value != "null")
            {
                if (way != null && way != "") {
                    temp += " and " + column + way + "#" + value + "#";
                } else {
                    temp += " and " + column + " lik #^" + value + "^#";
                }
            }
        }

        $("#query").val(temp);

        getAll('/gdmgr/ToExcelAction/activityReportList.do');
    }

    function getAll(tzurl) {
        var excelIdp = $("#excelId").val();
        var parametermap = $("#parameter").val();
        var queryp = $("#query").val();
        $
                .ajax({
                    type : 'POST',
                    dataType : 'json',
                    url : tzurl,
                    data : {
                        parameter : parametermap,
                        query : queryp
                    },
                    success : function(result) {

                        var divtext = '';
                        var headdivtext = '';
                        var querytext = '';
                        var listcolumn = result.listcolumn;
                        var listresult = result.listresult;
                        var pagenational = result.pageString;
                        var excelQueryColumn = JSON
                                .stringify(result.excelQueryColumn);

                        $("#excelQueryColumn").val(excelQueryColumn);

                        headdivtext += '<tr style=" text-align:center">';
                        for (var i = 0; i < listcolumn.length; i++) {
                            headdivtext += '<th   style="text-align:center;">'
                                    + listcolumn[i] + '</th>';
                        }
                        headdivtext += '</tr>';

                        for (var i = 0; i < listresult.length; i++) {
                            divtext += '<tr style=" text-align:center">';

                            if (listresult[i] instanceof Array) {
                                for (var j = 0; j < listresult[i].length; j++) {
                                    if (listresult[i][j] == null) {
                                        divtext += '<td   >' + '' + '</td>';
                                    } else {
                                        divtext += '<td >' + listresult[i][j]
                                                + '</td>';
                                    }

                                }
                            } else {
                                divtext += '<td >' + listresult[i] + '</td>';
                            }

                            divtext += '</tr>';
                        }
                        if($("#aaa").val()=="0"){
                        var data = JSON.parse(excelQueryColumn);
                        var temp = "";
                        for (var i = 0; i < data.length; i++) {
                            var id = data[i].id;
                            var cnname = data[i].cnname;
                            var column = data[i].column;
                            var type = data[i].type;
                            var keyvalue = data[i].keyvalue;
                            if (id == null || id == "") {
                                id = "";
                            }
                            if (type == "text") {
                                querytext += ' '
                                        + cnname
                                        + '：<input type="text" id="'
                                        + column.replace('.', '')
                                        + id
                                        + '" style="margin-right:10px;width:100px;height:20px;"/>';

                            } else if (type == "date") {

                                querytext += cnname
                                        + '<input type="text" id="'
                                        + column.replace('.', '')
                                        + id
                                        + '" style="margin-right:0px;width:100px;height:20px; " class="Wdate" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd\',readOnly:true})"/>';
                            } else if (type == "datetime") {

                                querytext += cnname
                                        + '<input type="text" id="'
                                        + column.replace('.', '')
                                        + id
                                        + '" style="margin-right:0px;width:135px;height:20px; " class="Wdate" onclick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',readOnly:true})"/>';
                            } else if (type == "list") {

                                querytext += cnname + '：<select id="'
                                        + column.replace('.', '') + id + '" >';
                                var groups = new Array(); //定义一数组 
                                groups = keyvalue.split(";"); //字符分割 
                                for (k = 0; k < groups.length; k++) {
                                    var group = groups[k];
                                    var key = group.split(":")[0];
                                    var value = group.split(":")[1];

                                    querytext += '<option value="'+key+'">'
                                            + value + '</option>';
                                }

                                querytext += '</select>';
                            }

                        }

                        //divtext += pagenational;
                        
                            $("#querydiv").html(querytext);
                            $("#aaa").val("0")
                        }
                        $("#tblHeader").html(headdivtext);
                        $("#newtable tbody").html(divtext);
                        $("#pageContent").html(pagenational);
                    }
                });
    }
</script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<link rel="stylesheet"
    href="<%=basePath%>/css/commonexcel/global.css" type="text/css"></link>
<link rel="stylesheet"
    href="<%=basePath%>/css/commonexcel/admin_style.css" type="text/css"></link>
<style type="text/css">
.mytable {
    table-layout: fixed;
    width: 98% border:0px;
    margin: 0px;
}

.mytable tr td {
    text-overflow: ellipsis; /* for IE */
    -moz-text-overflow: ellipsis; /* for Firefox,mozilla */
    overflow: hidden;
    white-space: nowrap;
    border: 1px solid;
    text-align: left
}

a {
    font-size: 12px
}

a:link {
    text-decoration: underline;
}

//
未访问：蓝色、无下划线
a:active: {
    color: red;
}

//
激活：红色
a:visited {
    text-decoration: none;
}

//
已访问：purple、无下划线
a:hover {
    text-decoration: underline;
}
//
鼠标移近：红色、下划线
</style>
</head>
<body style="overflow:auto">
    <div style="padding-left:20px;margin-bottom:10px;">



        <table width="100%">
            <tr style="display:none, text-align:center">

                <td><input type="hidden" id="excelId" name="excelId"
                    style="width:700px;height:20px;"></input> <input type="hidden"
                    id="parameter" name="parameter" style="width:700px;height:20px;"></input>
                    <input type="hidden" id="query" name="query"
                    style="width:700px;height:20px;"></input> <input type="hidden"
                    id="excelQueryColumn" name="excelQueryColumn"
                    style="width:700px;height:20px;"></input></td>
            </tr>
            <tr style=" text-align:left;height:30px;">
                <td>${excelTitle}</td>
                
            </tr>
            <tr style=" height:30px;">
                <td align="left">
                    <div id="querydiv"></div>
                </td>
                <td style=" margin-left:10px" align=right><input type="button" value="查询"
                    name="btn_search" onmouseover="this.style.cursor='hand'"
                    style="width:80px;height:20px;font-size:12px;margin-left:0px"
                    class="subBtn" onclick="search()" /> <input type="button"
                    value="导出Excel" name="btn_search"
                    onmouseover="this.style.cursor='hand'"
                    style="width:100px;height:20px;font-size:12px;" class="subBtn"
                    onclick="method1('newtable')" /> <input type="button" value="返回"
                    name="btn2" style="width:80px;height:20px;font-size:12px;"
                    onmouseover="this.style.cursor='hand'" class="subBtn"
                    onclick="history.go(-1)"></td>
                
</tr>
<tr>
</tr>
<tr >

</tr>

<tr>
</tr>

        </table>

    </div>
    <div id="signContent">
        <div class="table-list lr10">

            <table id="newtable" width="100%">
                <thead id="tblHeader">


                </thead>
                <tbody id="records">
                </tbody>
            </table>

        </div>
        <div id="pageContent"></div>
    </div>
<input type="text" id="aaa" value="0" style="display:none;"/>
</body>
</html>