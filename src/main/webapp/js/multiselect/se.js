function show_download(URL)                    
{
        window.open (URL,'newwindow','height=200,width=450,top=200,left=200,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');

}

function list_filter(sValue,objCombo)
{
    var i;
    var nSelectLength;
    i=0;
    nSelectLength=objCombo.length;

    while(i<nSelectLength)
    {
        if(objCombo.options[i].text.indexOf(sValue)<0)
        {
            //没有找到
            objCombo.options.remove(i);
            nSelectLength=objCombo.length;
            i=0;
        }
        else
        {
            i++;
        }
    }
}
function list_filter_country(sValue,objCombo)
{
    var i;
    var nSelectLength;
    i=0;
    nSelectLength=objCombo.length;

    while(i<nSelectLength)
    {
        if(objCombo.options[i].value.length != 6 || objCombo.options[i].text.indexOf(sValue)<0)
        {
            //没有找到
            objCombo.options.remove(i);
            nSelectLength=objCombo.length;
            i=0;
        }
        else
        {
            i++;
        }
    }
}

function CheckAllEx()
{	
	var listCount = 0;
	for (var i=0;i<document.directory.elements.length;i++)
	{	
		var e = document.directory.elements[i];
		if ((e.name != 'allbox') && (e.type=='checkbox') && (e.disabled == false))
		{
			listCount = listCount + 1;
			e.checked = document.directory.allbox.checked;
		}
	}
	//makeChecked(listCount);
}



function focusNext ( form )
{
	if( event.keyCode != 13 )
		return;

	for ( var i = 0; i < form.length - 1 ; i++ )
	{
		if ( event.srcElement != form[i] )
			continue;
		
		if ( form[i].type == 'textarea' )
			return;

		if ( form[i].type == 'button' )
		{
			form[i].blur();
			form.submit();
			return;
		}

		if ( form[i+1].type == 'reset' )
			return;
		

		if ( form[i+1].type == 'hidden' || form[i+1].disabled == true )
		{
			for ( var j = 1; j < form.length - i; j++ )
			{
				if ( form[i+j].type != 'hidden' && form[i+j].disabled == false )
				{
					/*
					form[i+j].focus();
					if ( form[i+j].type == "text" )
						if(form[i+j].value == 0 )
							form[i+j].value = '' ;

					if ( form[i+j].type == "button")
						event.returnValue = false ;
					return;
					*/

					if ( form[i+j].type == "button" || form[i+1].type == "reset" )
					{
						form[i+j].focus();
						event.returnValue = false ;
						return;
					}
					if ( form[i+j].type == "text" )
						if(form[i+j].value == 0 )
							form[i+j].value = '' ;
						
					event.keyCode = 9;
					return;

				}
			}
		}
		else
		{
			/*
			form[i+1].focus();
			if ( form[i+1].type == "text" )
				if(form[i+1].value == 0 )
					form[i+1].value = '' ;

			if ( form[i+1].type == "button")
				event.returnValue = false ;
			return;
			*/
			if ( form[i+1].type == "button" || form[i+1].type == "reset" )
			{
				form[i+1].focus();
				event.returnValue = false ;
				return;
			}
			
			if ( form[i+1].type == "text" )
				if(form[i+1].value == 0 )
					form[i+1].value = '' ;
				
			event.keyCode = 9;
			return;

		}
	}
}


//add by Xiao in neimeng 2003.04.27
function DesPass( name )
{        
	 var ret = "";
	 var pass  = "0D1C3B2A6E5F47899";
 	 for (var i = 0; i < name.length; i++ ) {
 	      var tmp = name.substring( i, i+1 );
 	      ret = ret + pass.substring( parseInt( tmp ) + i, parseInt( tmp ) + i+1 );    
         }
         return ret;
}

function fDesPass( name )
{        
	
	 
	 var ret = "";
	 var pass  = "0D1C3B2A6E5F47899";
 	 for (var i = 0; i < name.length; i++ ) {
 	      var tmp = name.substring( i, i+1 );
 	      for( var j=0 ; j< pass.length; j++ ) {
 	           var tmp1 = pass.substring( j, j+1 );
 	           if ( tmp == tmp1 ) {
 	                var k = j - i;
 	                ret = ret + k;
 	           	break; 
 	           } 	
 	      }	
         }
         return ret;
}
//end of add

function clearfield()
{
	if (search.keyword.value == "输入查询关键字")
	      	search.keyword.value = "";
}
		
function CheckAll()
{	
	var listCount = 0;
	for (var i=0;i<document.directory.elements.length;i++)
	{	
		var e = document.directory.elements[i];
		if ((e.name != 'allbox') && (e.type=='checkbox'))
		{
			listCount = listCount + 1;
			e.checked = document.directory.allbox.checked;
		}
	}
	makeChecked(listCount);
}


function makeChecked(listCount)
{
	if (listCount > 1)
	{
		for (var i=0; i<document.directory.checkbox.length; i++)
		{	
			var CB = document.directory.checkbox[i];
			if (CB.checked)
			{
				hL(CB);
				document.directory.txtCheck[i].value = "1";
			}
			else
			{
				dL(CB);
				document.directory.txtCheck[i].value = "0";
			}
		}
	}

	if (listCount == 1)
	{
		var CB = document.directory.checkbox;
		if (CB.checked)
		{
			hL(CB);
			document.directory.txtCheck.value = "1";
		}
		else
		{
			dL(CB);
			document.directory.txtCheck.value = "0";
		}			
	}
}



function hL(E)
{
	while (E.tagName!="TR")
		E=E.parentElement;
	E.className = "H";
}

function dL(E)
{
	while (E.tagName!="TR")
		E=E.parentElement;
	E.className = "";
}
	
function checkDel()
{
	check=false;
	for (var i=0;i<document.directory.elements.length;i++)
	{
		var e = document.directory.elements[i];		
		if ((e.name != 'allbox') && (e.type=='checkbox'))
		{
			if (e.checked==true)
			{
				check=true;
				break;
			}
		}
	}
	if (check == false)
		window.alert("请选择要删除的记录！");
	else
	{
		if (window.confirm("是否删除记录？") == true)
		{	//document.directory.Type.value="2";
			check = true;
		}
		else
			check = false;
	}
	return check;
}

function checkSelect()
{
	check=false;
	for (var i=0;i<document.directory.elements.length;i++)
	{
		var e = document.directory.elements[i];		
		if ((e.name != 'allbox') && (e.type=='checkbox'))
		{
			if (e.checked==true)
			{
				check=true;
				break;
			}
		}
	}
	if (check == false)
		window.alert("请选择要提交的记录！");
	else
	{
		if (window.confirm("是否提交记录？") == true)
		{	//document.directory.Type.value="2";
			check = true;
		}
		else
			check = false;
	}
	return check;
}


function checkLogout()
{
	check=false;
	for (var i=0;i<document.directory.elements.length;i++)
	{
		var e = document.directory.elements[i];		
		if ((e.name != 'allbox') && (e.type=='checkbox'))
		{
			if (e.checked==true)
			{
				check=true;
				break;
			}
		}
	}
	if (check == false)
		window.alert("请选择要注销的记录！");
	else
	{
		if (window.confirm("是否注销记录？") == true)
		{	//document.directory.Type.value="2";
			check = true;
		}
		else
			check = false;
	}
	return check;
}

function checkForce()
{
	check=false;
	for (var i=0;i<document.directory.elements.length;i++)
	{
		var e = document.directory.elements[i];		
		if ((e.name != 'allbox') && (e.type=='checkbox'))
		{
			if (e.checked==true)
			{
				check=true;
				break;
			}
		}
	}
	if (check == false)
		window.alert("请选择要强制下班的记录！");
	else
	{
		if (window.confirm("是否强制下班？") == true)
		{	//document.directory.Type.value="2";
			check = true;
		}
		else
			check = false;
	}
	return check;
}

function checkPrimaryDel()//listCount
{
	check=false;
	for (var i=0;i<document.directory.elements.length;i++)
	{
		var e = document.directory.elements[i];		
		if ((e.name != 'allbox') && (e.type=='checkbox'))
		{
			if (e.checked==true)
			{
				check=true;
				break;
			}
		}
	}
	if (check == false)
		window.alert("请选择要删除的记录！");
	else
	{
		//if (listCount > 1)
		//{
			for (var i=0; i<document.directory.checkbox.length; i++)
			{	
				var CB = document.directory.checkbox[i];
				if (CB.checked && (document.directory.S_STRING23[i].value == "1"))
				{
					window.alert(document.directory.S_SUB_INST_ID[i].value + "为主机构，不能删除！");
					return false;
				}
			}
		//}

		//if (listCount == 1)
		//{
			//var CB = document.directory.checkbox;
			//if (CB.checked && (document.directory.S_STRING23.value == "1"))
			//{
				//window.alert(document.directory.S_SUB_INST_ID.value + "为主机构，不能删除！");
				//return false;
			//}
		//}
	}
		if (window.confirm("是否删除记录？") == true)
		{	//document.directory.Type.value="2";
			check = true;
		}
		else
			check = false;
	
	return check;
}
//add by sea 2004.5.11
function checkSecDel()
{
	check=false;
	for (var i=0;i<document.directory.elements.length;i++)
	{
		var e = document.directory.elements[i];		
		if ((e.name != 'allbox') && (e.type=='checkbox'))
		{
			if (e.checked==true)
			{
				check=true;
				if(document.directory.elements[i+2].value.length==8)
				{    //营销员维护主键有3个  
					var kword=document.directory.elements[i+2].value;
					var secid=document.directory.elements[i+3].value;
					var stat=document.directory.elements[i+4].value;
				}
				if(document.directory.elements[i+2].value.length==12)
				{   //科室维护主键有2个     
					var kword=document.directory.elements[i+2].value;
					var secid=document.directory.elements[i+2].value;
					var stat=document.directory.elements[i+3].value;
				}
				var unitcode=document.directory.unit_code.value;						
				if(unitcode!=secid.substring(0,9))
				 {  
				    alert(kword+"  不属于本机构的直接管辖范围，请重新选择！");
				    return false;				 	
				 }												
				if(stat=="0")
				 {  
				    alert(kword+"  为注册状态，不能删除，请重新选择！");
				    return false;				 	
				 }
			}
		}
	}
	if (check == false)
		window.alert("请选择要删除的记录！");
	else
	{
		if (window.confirm("是否删除记录？并删除相应的任务量记录？") == true)
		{	//document.directory.Type.value="2";
			check = true;
		}
		else
			check = false;
	}
	return check;
}

//add by sea 2004.3.30
function checkAuth()
{
	var sFeatures="dialogHeight: 200px; dialogWidth: 250px; edge: Sunken; center: Yes; help: No; resizable: No; status: No;";
	var ret = window.showModalDialog("/cpai.ims/general/Dialog.htm","主管授权:", sFeatures);
	if ( ret == null || ret == "" )
	{
		return false;
	}
	else
	{
		var acc = ret.substring(0,ret.indexOf(":"));
		var pwd = ret.substring(ret.indexOf(":")+1);
		if ( acc == null || acc == "" || pwd == null || pwd == "" )
		{
			return false;
		}
		else
		{
			//var master_id=acc;
			//var master_passwd=pwd;
			document.directory.master_id.value = acc;
			document.directory.master_passwd.value = pwd;
			return true;
		}
	}
}

//add by sea 2004.3.15
function checkMove()
{ 
	check=false;
	for (var i=0;i<document.directory.elements.length;i++)
	{
		var e = document.directory.elements[i];
		
		if ((e.name != 'allbox') && (e.type=='checkbox'))
		{
			if (e.checked==true)
			{
				check=true;				
				if(document.directory.elements[i+6].value!="0")
				 {  
				    alert(document.directory.elements[i+2].value+"  已归档，请重新选择！");
				    return false;				 	
				 }				
			}
		}
	}
	if (check == false)
		window.alert("请选择要归档的文件！");
	else
	{
		if (window.confirm("是否归档？") == true)
		{	
			check = true;
		}
		else
			check = false;
	}
	tmp_action = document.directory.action;	
	with( document.directory )
	{
	    action = "/cpai.ims/moveto_" + tmp_action.substring( 17 );	   	    
	}	
	return check;
}

function checkLogicDel()
{
	check=false;
	for (var i=0;i<document.directory.elements.length;i++)
	{
		var e = document.directory.elements[i];		
		if ((e.name != 'allbox') && (e.type=='checkbox'))
		{
			if (e.checked==true)
			{
				check=true;
				if(document.directory.elements[i+6].value=="3")
				 {  
				    alert(document.directory.elements[i+2].value+"  已删除，请重新选择！");
				    return false;				 	
				 }
			}
		}
	}
	if (check == false)
		window.alert("请选择要删除的文件！");
	else
	{
		if (window.confirm("是否删除文件？") == true)
		{	//document.directory.Type.value="2";
			check = true;
		}
		else
			check = false;
	}
	return check;
}

//add by wanglin 2004.2.5
function checkUnregis()
{ 
	check=false;
	for (var i=0;i<document.directory.elements.length;i++)
	{
		var e = document.directory.elements[i];
		
		if ((e.name != 'allbox') && (e.type=='checkbox'))
		{
			if (e.checked==true)
			{
				check=true;				
				if(document.directory.elements[i+2].value.length==8)
				{    //营销员维护主键有3个  
					var kword=document.directory.elements[i+2].value;
					var secid=document.directory.elements[i+3].value;
					var stat=document.directory.elements[i+4].value;
				}
				if(document.directory.elements[i+2].value.length==12)
				{   //科室维护主键有2个     
					var kword=document.directory.elements[i+2].value;
					var secid=document.directory.elements[i+2].value;
					var stat=document.directory.elements[i+3].value;
				}
				var unitcode=document.directory.unit_code.value;						
				if(unitcode!=secid.substring(0,9))
				 {  
				    alert(kword+"  不属于本机构的直接管辖范围，请重新选择！");
				    return false;				 	
				 }												
				if(stat=="1")
				 {  
				    alert(kword+"  已经被注销，请重新选择！");
				    return false;				 	
				 }				
			}
		}
	}
	if (check == false)
		window.alert("请选择要注销的记录！");
	else
	{
		if (window.confirm("是否注销记录？") == true)
		{	
			check = true;
		}
		else
			check = false;
	}
	tmp_action = document.directory.action;	
	with( document.directory )
	{
	    action = "/cpai.ims/unregi_" + tmp_action.substring( 17 );	   	    
	}	
	return check;
}

function checkRegis()
{ //参数为操作员登录时的机构号
	check=false;
	for (var i=0;i<document.directory.elements.length;i++)
	{
		var e = document.directory.elements[i];
		
		if ((e.name != 'allbox') && (e.type=='checkbox'))
		{
			if (e.checked==true)
			{
				check=true;				
				if(document.directory.elements[i+2].value.length==8)
				{    //营销员维护主键有3个  
					var kword=document.directory.elements[i+2].value;
					var secid=document.directory.elements[i+3].value;
					var stat=document.directory.elements[i+4].value;
				}
				if(document.directory.elements[i+2].value.length==12)
				{   //科室维护主键有2个     
					var kword=document.directory.elements[i+2].value;
					var secid=document.directory.elements[i+2].value;
					var stat=document.directory.elements[i+3].value;
				}
				
				var unitcode=document.directory.unit_code.value;				
				if(unitcode!=secid.substring(0,9))
				 {  
				    alert(kword+"  不属于本机构的直接管辖范围，请重新选择！");
				    return false;				 	
				 }												
				if(stat=="0")
				 {  
				    alert(kword+"  已经被注册，请重新选择！");
				    return false;				 	
				 }	
			}
		}
	}
	if (check == false)
		window.alert("请选择要取消注销的记录！");
	else
	{
		if (window.confirm("是否取消注销记录？") == true)
		{	
			check = true;
		}
		else
			check = false;
	}
	
	tmp_action = document.directory.action;	
		
	with( document.directory )
	{
	    action = "/cpai.ims/regist_" + tmp_action.substring( 17 );		      	    
	}	
	return check;	
}

function checkUpd()
{	
	check=false;
	
	if (window.confirm("是否确定提交更新？") == true)
		check = true;
	else
		check = false;
	return check;
}

function chkKey()
{
	if (event.keyCode < 48) { event.returnValue=false; }
	if (event.keyCode > 57) { event.returnValue=false; }
}

function chkDecKey()
{
	if (event.keyCode < 46) { event.returnValue=false; }
	if (event.keyCode > 57) { event.returnValue=false; }
	if (event.keyCode == 47) { event.returnValue=false; }
}

function pageTo()
{
	    if (event.keyCode == 13)
 	    {
			var s = window.parent.frames[0].location.href;
			var l = s.lastIndexOf('/');
			s = s.substring(0,l);
			 l = s.lastIndexOf('/');
			s = s.substring(0,l+1);
	  		window.navigate( s + "general/" + document.directory.page_name.value + "?pageNum=" + window.document.forms[0].pageNum.value );
			event.returnValue=false;
	    } 	
	    if (event.keyCode < 48) { event.returnValue=false; }
	    if (event.keyCode > 57) { event.returnValue=false; }
	
	    window.document.forms[0].pageNum.focus();
}

function batchTo(action)
{
			
		if (event.keyCode == 13)
 	    {
			
	  		window.navigate(action+"?batchNum=" + window.document.forms[0].batchNum.value );
			return;
	    } 	
	    if (event.keyCode < 48) { event.returnValue=false; }
	    if (event.keyCode > 57) { event.returnValue=false; }
	
	    window.document.forms[0].batchNum.focus();
}

function pageActionTo( action )
{
	    if (event.keyCode == 13)
 	    {
	  	window.navigate( action+"pageNum=" + window.document.all.pageNum.value );
		return;
	    } 	
	    if (event.keyCode < 48) { event.returnValue=false; }
	    if (event.keyCode > 57) { event.returnValue=false; }
	
	    window.document.all.pageNum.focus();
}

function chkBox2txtBox(chkBox, txtBox)
{
	if (chkBox.checked == true)
		txtBox.value = "1";
	else
		txtBox.value = "0";
}
function open_print_win(){
	window.open ('../general/print.jsp','newwindow','height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
}
function checkvalue( control,maxlength,description)
{
	var value = control.value;
	
	var length = value.length;

	var n1=0;

	var n2=0;

	for ( var idx=0;idx<length;idx++ )
	{
		var ch = value.charCodeAt(idx);
		
		if ( ch > 128 )
		{
			n1=n1+1;
		}else
		{
			n2=n2+1;
		}
	}
	if(n1*2+n2>maxlength)
	{
		alert(description);
		control.focus();
	}
	
}
function locate( path )
{
	document.location.href=path;
}

//计算字符长度
function strlen(str)
{
var len;
var i;
len = 0;
for (i=0;i<str.length;i++)
{
if (str.charCodeAt(i)>255) len+=2; else len++;
}
return len;
}
 //字符长度判断
function cnfm_word(text)
{
var i=strlen(text.value);
var h = (i - text.maxLength)/2;

	if (i>text.maxLength)
	{
		alert("您的输入超过长度限制！");
		text.value = text.value.substring(0,text.value.length-h);
	}
}

function checkDel1(action)
{
	check = false;
	var seq ="";
	var cur = 1;
	// seq=1^2
	for (var i=0;i<document.all.length;i++)
	{
		var e = document.all[i];
		
		if ((e.name == 'checkbox') && (e.type=='checkbox'))
		{
			if (e.checked==true)
			{
				check = true;
				seq = seq + cur + "^"; 
			}
			cur = cur +1 ;
		}
	}
	if (check == false)
		window.alert("请选择要删除的记录！");
	else
		check = (window.confirm("是否删除记录？") == true);

	if( check ==true )
	{
		seq = seq.substring(0,seq.length-1);
		window.navigate(action+"?seqNo=" + seq );
	}

}

function CheckAll1()
{	
	var listCount = 0;
	for (var i=0;i<document.all.length;i++)
	{	
		var e = document.all[i];
		if ((e.name == 'checkbox') && (e.type=='checkbox'))
		{
			listCount = listCount + 1;
			e.checked = document.all.allbox.checked;
		}
	}
}
function appletPrint(val)
{
	var applet = document.applets[0];
	applet.printStream();
}
function changeMenu(sn,condition)
{	 

	mlist = new menulist();
	for (x in mlist) {
		var form = document.all[mlist[x][0]];
		var menu = form[sn];
		if ( menu.type == 'select-one' ){
			var xmlid = mlist[x][1];
			var valname = mlist[x][2];
			var descname = mlist[x][3];

			var nodes;
			var xml = document.all(xmlid);
			xml.XMLDocument.setProperty("SelectionLanguage", "XPath");
			 
			if ( condition == "" ) 
				nodes = xml.XMLDocument.selectNodes("Records/Record");
			else
				nodes = xml.XMLDocument.selectNodes("Records/Record["+condition+"]");
			var oOption = document.createElement("OPTION");
		
			oOption.text = "[请选择]";
			oOption.value = "";
	 
			var aselect = form[sn];			
            for (var xx = aselect.options.length ;xx>=0;xx-- )
            {
				aselect.options.remove(xx);
            }
			
			aselect.options.add(oOption);
			
			for (var node = nodes.nextNode(); node  != null; node = nodes.nextNode())
			{

				if (node.firstChild != null)
				{
					oOption = document.createElement("OPTION");
					oOption.value=node.getElementsByTagName(valname).item(0).firstChild.nodeValue;
					var desc = node.getElementsByTagName(descname).item(0).firstChild.nodeValue;
					oOption.text = oOption.value +" - "+desc;			
					aselect.options.add(oOption);
				}
			}
		}
	}
}
function onkeyboard(obj,info,money,lvl)
{
	var filename="";
	var style="dialogWidth:380px;dialogHeight:310px;dialogLeft:200px;dialogTop:150px;center:yes;help:no;resizable:no;status:no";
	if(event.keyCode==0x76)
	{
		obj.S_RMK_TX.value=info;
		obj.S_TRAN_AT.value=money;
		obj.S_AUTH_LVL_FRONT.value=lvl;
		filename="/cpagd.bms/jsp/ccw/daily/author.jsp";
		window.showModalDialog(filename, obj, style);
	}
	if(event.keyCode==0x77)
	{
		filename="/cpagd.bms/jsp/ccw/daily/author2.jsp";
		window.showModalDialog(filename, obj, style);
	}
}

function check_acc(val1,val2)
{
    var value;
	if(val1 != null & val2  !=  null)
	{
		value = val2 - val1;
	}

	if(value < 0)
	{
		return false;
	}
	else
	{
		return true;
	}
}
function searchs(formname)
{
    if ( event.keyCode == 13 )
        formname.submit();
}
function checkEnterKey()
{
	if (event.keyCode > 47 && event.keyCode < 58||event.keyCode==13)
	{
		event.returnValue=true;
	}else
		event.returnValue=false;
}

function authcls()
{	
	var aa ;
	var bb ;
	
	aa = document.all.S_AUTH_TLR_ID.value;	
	bb = document.all.S_AUTH_PWD_CD.value;
	//alert("0000000000");
	if ( aa != "" || bb != "" )
	{
		//alert("111111111");
		document.all.S_AUTH_TLR_ID.value="";
		document.all.S_AUTH_PWD_CD.value="";
	}
	//alert("222222222222222");
}

/*20151208 huangjianhong 解决用户重复提交 add start*/
function waiting(prompt){
    if((typeof(prompt) == "undefined") || prompt == ""){
        prompt = "正在处理,请稍候……";
    }
    
    document.getElementById("divModal").style.display = "";
    document.getElementById("divModal").style.width = document.body.scrollWidth;
    document.getElementById("divModal").style.height = document.body.scrollHeight;

    divProgressDialog.style.display = "";
    divProgressDialog.style.width = prompt.length * 20;
    divProgressDialog.style.left = ((document.body.offsetWidth - divProgressDialog.offsetWidth) / 2);
    divProgressDialog.style.top = ((document.body.offsetHeight - divProgressDialog.offsetHeight) / 2);
    
    prompts.innerHTML = "<span style='float:left;margin-top:8px;padding-left:25px;'><IMG src='/cpagd.bms/images/waiting.gif'></span><span style='float:left;margin-top:8px;margin-left:5px;'>"+prompt+"</span>";
}

document.write('<DIV STYLE="height:40;border:#58a3cb solid 1px;text-align:center;BACKGROUND-COLOR:#FFE4C4;FONT-SIZE:9pt;Z-INDEX:9999;FONT-FAMILY:Tahoma;POSITION:absolute;DISPLAY:none;CURSOR:default" ID="divProgressDialog" dir="ltr">');
document.write('<DIV STYLE="PADDING:3px;FONT-WEIGHT:bolder;COLOR:#003366" id="prompts"></DIV>');
document.write('</DIV>');
document.write('<iframe ID="divModal" frameborder=no scrolling=no STYLE="display:none;PADDING-BOTTOM:5px;PADDING-TOP:5px;BACKGROUND-COLOR:buttonface;TEXT-ALIGN:center;BACKGROUND-COLOR:white;FILTER:alpha(opacity=60);LEFT:1px; POSITION:absolute;TOP:0px;Z-INDEX:9998"></iframe>');
/*20151208 huangjianhong 解决用户重复提交 add end*/





//|------------------------------------------------------------------------------------  
//|  
//| 说明：JS弹出全屏遮盖的对话框(弹出层后面有遮盖效果，兼容主流浏览器)  
//|       实现了在最大化、拖动改变窗口大小和拖动滚动条时都可居中显示。  
//|  
//| 注意：主要使用EV_modeAlert和EV_closeAlert这两个函数行了；  
//|       (EV_modeAlert-弹出对话框，EV_closeAlert-关闭对话框)；  
//|       注意：使用时，请在body标签内(不要在其它元素内)写一div，  
//|       再给这div赋一id属性，如：id="myMsgBox"，  
//|       然后就可以调用EV_modeAlert('myMsgBox')来显示了。  
//|       还有，请给你这div设置css：display:none让它在开始时不显示。  
//|  
//| 作者：E旺，QQ：407542585，Blog：http://blog.csdn.net/envon123，(新手)  
//|  
//|------------------------------------------------------------------------------------  
//|  
//用来记录要显示的DIV的ID值  
var EV_MsgBox_ID=""; //重要  

//弹出对话窗口(msgID-要显示的div的id)  
function EV_modeAlert(msgID){  
//创建大大的背景框  
var bgObj=document.createElement("div");  
bgObj.setAttribute('id','EV_bgModeAlertDiv');  
document.body.appendChild(bgObj);  
//背景框满窗口显示  
EV_Show_bgDiv();  
//把要显示的div居中显示  
EV_MsgBox_ID=msgID;  
EV_Show_msgDiv();  
}  

//关闭对话窗口  
function EV_closeAlert(){  
var msgObj=document.getElementById(EV_MsgBox_ID);  
var bgObj=document.getElementById("EV_bgModeAlertDiv");  
msgObj.style.display="none";  
document.body.removeChild(bgObj);  
EV_MsgBox_ID="";  
}  

//窗口大小改变时更正显示大小和位置  
window.onresize=function(){  
if (EV_MsgBox_ID.length>0){  
    EV_Show_bgDiv();  
    EV_Show_msgDiv();  
}  
}  

//窗口滚动条拖动时更正显示大小和位置  
window.onscroll=function(){  
if (EV_MsgBox_ID.length>0){  
    EV_Show_bgDiv();  
    EV_Show_msgDiv();  
}  
}  

//把要显示的div居中显示  
function EV_Show_msgDiv(){  
var msgObj   = document.getElementById(EV_MsgBox_ID);  
msgObj.style.display  = "block";  
var msgWidth = msgObj.scrollWidth;  
var msgHeight= msgObj.scrollHeight;  
var bgTop=EV_myScrollTop();  
var bgLeft=EV_myScrollLeft();  
var bgWidth=EV_myClientWidth();  
var bgHeight=EV_myClientHeight();  
var msgTop=bgTop+Math.round((bgHeight-msgHeight)/4);  
var msgLeft=bgLeft+Math.round((bgWidth-msgWidth)/2);  
msgObj.style.position = "absolute";  
msgObj.style.top      = msgTop+"px";  
msgObj.style.left     = msgLeft+"px";  
msgObj.style.zIndex   = "10001";  
  
}  
//背景框满窗口显示  
function EV_Show_bgDiv(){  
var bgObj=document.getElementById("EV_bgModeAlertDiv");  
var bgWidth=EV_myClientWidth();  
var bgHeight=EV_myClientHeight();  
var bgTop=EV_myScrollTop();  
var bgLeft=EV_myScrollLeft();  
bgObj.style.position   = "absolute";  
bgObj.style.top        = bgTop+"px";  
bgObj.style.left       = bgLeft+"px";  
bgObj.style.width      = bgWidth + "px";  
bgObj.style.height     = bgHeight + "px";  
bgObj.style.zIndex     = "10000";  
bgObj.style.background = "#777";  
bgObj.style.filter     = "progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=60,finishOpacity=60);";  
bgObj.style.opacity    = "0.6";  
}  
//网页被卷去的上高度  
function EV_myScrollTop(){  
var n=window.pageYOffset   
|| document.documentElement.scrollTop   
|| document.body.scrollTop || 0;  
return n;  
}  
//网页被卷去的左宽度  
function EV_myScrollLeft(){  
var n=window.pageXOffset   
|| document.documentElement.scrollLeft   
|| document.body.scrollLeft || 0;  
return n;  
}  
//网页可见区域宽  
function EV_myClientWidth(){  
var n=document.documentElement.clientWidth   
|| document.body.clientWidth || 0;  
return n;  
}  
//网页可见区域高  
function EV_myClientHeight(){  
var n=document.documentElement.clientHeight   
|| document.body.clientHeight || 0;  
return n;  
}  