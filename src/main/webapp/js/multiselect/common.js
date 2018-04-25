/**
 * 共通JS函数
 */

/**
 * 打开模态窗口
 * 
 * @param url
 *            地址
 */
function openModalWindow(url) {
	return window.showModalDialog(url, window, 'dialogWidth:1024px;dialogHeight:768px;status:no;help:no;resizable:no;scroll:yes;');
}

/**
 * 打开带参数的模态窗口
 * 
 * @param url
 *            地址
 * @param params
 *            参数，可以是对象
 */
function openModalWindow(url, params) {
	return window.showModalDialog(url, params, 'dialogWidth:1024px;dialogHeight:768px;status:no;help:no;resizable:no;scroll:yes;');
}

/**
 * 打开窗口-最大化
 * 
 * @param url
 * @param title
 */
function openFullWindow(url, title) {
	window.open(url, title, 'fullscreen=1,toolbar=1,location=1,directories=1,status=1,menubar=1,scrollbars=1,resizable=1,width='
			+ screen.width +',height='+ screen.height +',left=0,top=0',false);
}

/**
 * 打开小窗口
 * 
 * @param url
 * @param title
 */
function openSubWindow(url, title) {
	window.open(url, title, 'fullscreen=1,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,'+
			'width=1024px,height=768px,left=0,top=0',false);
}

/**
 * 创建树形结构节点
 * 
 * @param id
 *            结点ID
 * @param text
 *            结点名
 * @returns { 节点 }
 */
function getNode(id, text) {
    var node = {
            "id" : id,
            "text" : text,
            "value" : id,
            "showcheck" : true,
            complete : true,
            "isexpand" : false,
            "checkstate" : 0,
            "hasChildren" : false
        };
    return node;
}

/**
 * 设置树形结构节点，将子结点设置到父节点之下
 * 
 * @param pNode
 *            父节点
 * @param node
 *            子节点
 * @param parentId
 *            父节点ID
 */
function setNode(pNode, node, parentId) {
    if (pNode.id == parentId) {
    	if (node.isexpand) {
    		pNode.isexpand = true;
    	}
        // 查找到对应的父结点
        if (pNode.hasChildren) {
        	// 有子结点就直接push
            pNode.ChildNodes.push(node)
        } else {
            // 没有就新建
            pNode.hasChildren = true;
            var arr = [];
            arr.push(node);
            pNode["ChildNodes"] = arr;
        }
        return true;
    } else {
        if (pNode.hasChildren) {
            var c = pNode.ChildNodes[pNode.ChildNodes.length - 1];
            // 有子节点
            if (setNode(c, node, parentId)) {
            	// pNode.isexpand = true;
            	if (node.isexpand) {
            		pNode.isexpand = true;
            	}
            	return true;
            }
        } else {
            // 没有子节点
            // 暂时报个错
            // alert("没有找到父结点：" + node.id + "|" + parentId);
        }
    }
}

function ajaxPost(postUrl, param, callbackFunc) {
	startLoading();
	$.ajax({
        type : "post",
        url : postUrl,
        data: param,
        cache : false,
        dataType : "json",
        success : function(result) {
        	if (result.S_RSP_CD == null || result.S_RSP_CD == ""
        		|| result.S_RSP_DESC_TX == null) {
        		alert("系统忙，请稍后再试或联系技术人员处理！");
                endLoading();
                return;
        	}
        	// 先判断 返回码
        	if (result.S_RSP_CD != "0000") {
        		// 出错了
        		alert("处理错误：" + "(" + result.S_RSP_DESC_TX + ")");
        		endLoading();
        		return;
        	}
        	// 没有错误则进入回调函数
        	try {
        		callbackFunc(result);
        	} catch (e) {
        		//
        	}
        	endLoading();
        },
        error : function(result) {
            alert("系统忙，请稍后再试或联系技术人员处理！");
            endLoading();
        }
	});
}

/**
 * ajaxpost方法，不判断交易码，在callback里判断
 * @param postUrl 请求链接
 * @param param   请求参数
 * @param callbackFunc 回调函数
 */
function ajaxPost2(postUrl, param, callbackFunc) {
	startLoading();
	$.ajax({
        type : "post",
        url : postUrl,
        data: param,
        cache : false,
        dataType : "json",
        success : function(result) {
        	// 进入回调函数
        	try {
        		callbackFunc(result);
        	} catch (e) {
        		//
        	}
        	endLoading();
        },
        error : function(result) {
            alert("系统忙，请稍后再试或联系技术人员处理！");
            endLoading();
        }
	});
}

function ajaxPostHtml(postUrl, param, callbackFunc) {
	startLoading();
	$.ajax({
        type : "post",
        url : postUrl,
        data: param,
        cache : false,
        dataType : "html",
        success : function(result) {
        	// 没有错误则进入回调函数
        	try {
        		callbackFunc(result);
        	} catch (e) {
        		//
        	}
        	endLoading();
        },
        error : function(result) {
        	alert("系统忙，请稍后再试或联系技术人员处理！");
            endLoading();
        }
	});
}

/**
 * 只提交AJAX交易，不做处理
 * @param postUrl
 * @param param
 * @param callbackFunc
 */
function ajaxPostOnly(postUrl, param) {
	$.ajax({
        type : "post",
        url : postUrl,
        data: param,
        cache : false,
        success : function(result) {
        },
        error : function(result) {
        }
	});
}

/**
 * 只提交AJAX交易，不做处理
 * @param postUrl
 * @param param
 * @param callbackFunc
 */
function ajaxGetOnly(postUrl, param) {
	$.ajax({
        type : "get",
        url : postUrl,
        data: param,
        cache : false,
        success : function(result) {
        },
        error : function(result) {
        }
	});
}

/**
 * 取当前日期
 * 
 * @returns {String}
 */
function GetCurrentDay() {
	var date = new Date();
    var s = date.getUTCFullYear();
    s += ("00"+(date.getUTCMonth()+1)).slice(-2);
    s += ("00"+date.getUTCDate()).slice(-2);
    return s;
}

/**
 * 计算当前日期+N天后的日期
 */
function CurrentDateAddDay(n){
	var s, d, t, t2;
	t = new Date().getTime();
    t2 = n * 1000 * 3600 * 24;
    t+= t2;
    d = new Date(t);
    s = d.getUTCFullYear() + "-";
    s += ("00"+(d.getUTCMonth()+1)).slice(-2) + "-";
    s += ("00"+d.getUTCDate()).slice(-2);
    return s;
} 

/**
 * 计算给定日期后的N天
 * 
 * @param str
 * @param n
 * @returns
 */
function DateAddDay(str,n){   
  var   dd, mm, yy;   
  var   reg = /^(\d{4})-(\d{1,2})-(\d{1,2})$/;
  if (arr = str.match(reg)) {
    yy = Number(arr[1]);
    mm = Number(arr[2])-1;
    dd = Number(arr[3]);
  } else {
    var d = new Date();
    yy = d.getUTCFullYear();
    mm = ("00"+(d.getUTCMonth())).slice(-2);
    dd = ("00"+d.getUTCDate()).slice(-2);
  }
  return date2str(yy, mm, dd,n);
} 

/**
 * 计算给定日期后的N天
 * 
 * @param yy
 * @param mm
 * @param dd
 * @param n
 * @returns {String}
 */
function SpecifiedDateAddDay(yy, mm, dd,n) {
    var s, d, t, t2;
    t = Date.UTC(yy, mm, dd);
    t2 = n * 1000 * 3600 * 24;
    t+= t2;
    d = new Date(t);
    s = d.getUTCFullYear() + "-";
    s += ("00"+(d.getUTCMonth()+1)).slice(-2) + "-";
    s += ("00"+d.getUTCDate()).slice(-2);
    return s;
}

function culEndDate(){
	var inputDate=document.all("rep_month_plan.start_date").value;
	var datePartArray=inputDate.split('-');
	// 本月开始的第一天
	var curFirstDay=new Date(datePartArray[0],datePartArray[1]-1);
	// 下月开始的第一天
	var nextFirstDay=addDate('5','1',curFirstDay);
	// 下下月开始的第一天
	var nnextFirstDay=addDate('5','2',curFirstDay);
	// 本月最后一天
	var curEndDay= new Date(nextFirstDay.getTime()-1);
	// 下月最后一天
	var nextEndDay= new Date(nnextFirstDay.getTime()-1);

	alert(nextEndDay);
	document.all("rep_month_plan.curEnddate").value=curEndDay.getYear() +'-' + (curEndDay.getMonth()+1) + '-' +curEndDay.getDate();
	document.all("rep_month_plan.nextEnddate").value=nextEndDay.getYear() +'-' + (nextEndDay.getMonth()+1) + '-' +nextEndDay.getDate();
}

/**
 * 
 */
function addDate(vdate, type, NumDay){
	var date=new Date(vdate.substr(0, 4), vdate.substr(4, 2) - 1, vdate.substr(6, 2));
	type = parseInt(type); // 类型
	var lIntval = parseInt(NumDay);// 间隔
	switch(type){
		case 1 :// 秒
			date.setSeconds(date.getSeconds() + lIntval);
			break;
		case 2 :// 分
			date.setMinutes(date.getMinutes() + lIntval);
			break;
		case 3 :// 时
			date.setHours(date.getHours() + lIntval);
			break;
		case 4 :// 天
			date.setDate(date.getDate() + lIntval);
			break;
		case 5 :// 月
			date.setMonth(date.getMonth() + lIntval);
			break;
		case 6 :// 年
			date.setYear(date.getYear() + lIntval);
			break;
		case 7 :// 季度
			date.setMonth(date.getMonth() + (lIntval * 3) );
			break;
		default:
			break;
	}
    var s = date.getUTCFullYear();
    s += ("00"+(date.getUTCMonth()+1)).slice(-2);
    s += ("00"+date.getUTCDate()).slice(-2);
    return s;
}

(function ($) {
	$.fn.Datetimepicker = function() {
		$(this).datetimepicker({
            lang:'ch',
            timepicker:false,
            format:'Ymd',
            formatDate:'Ymd'
        });
	}
	
    // 屏蔽，适合单个元素. 参数opticy: 不透明度 0 ~ 1.0
    $.fn.mask = function (opaticy) {
        opaticy = opaticy || 0;
        var spec = opaticy * 100;
        var opaticyCSS = 'filter:alpha(opacity=' + spec + ');opacity: ' + opaticy + ';';
        var divHtml = '<div class="divMask" style="position: absolute; width: 100%; height: 100%; left: 0px; top: 0px; background: #fff; ' + opaticyCSS + '"> </div>';
        $(this).wrap('<span style="position: relative"></span>');
        $(this).parent().append(divHtml);
        $(this).data("mask","true");
    }
    // 取消屏蔽
    $.fn.unmask = function () {
        $(this).parent().find(".divMask").remove();
        $(this).unwrap();
        $(this).data("mask", "false");
    }
    

    $.fn.smartFloat = function() {
		var position = function(element) {
			var top = element.position().top, pos = element.css("position");
			$(window).scroll(function() {
				var scrolls = $(this).scrollTop();
				if (scrolls > top) {
					if (window.XMLHttpRequest) {
						element.css({
							position : "fixed",
							top : 0
						});
					} else {
						element.css({
							top : scrolls
						});
					}
				} else {
					element.css({
						position : "absolute",
						top : top
					});
				}
			});
		};
		return $(this).each(function() {
			position($(this));
		});
	};
})(jQuery);


// 兼容火狐、IE8
// 显示遮罩层
// 固定在页面写入代码
function startLoading(){
    var mask = $("<div>").attr("class", "mask").attr("id", "mask");
    var divObj = $("<div>").attr("class", "loadingDiv");
    divObj.html("<span class='loadingImgSpn'><IMG src='/gdmgr/images/waiting.gif'></span><span class='loadingHintSpn'>正在处理，请稍候……</span></div>");
    var left = (($(document).width() - 200) / 2);
    var top = (($(document).height() - 200) / 2);
    divObj.css("left", left);
    divObj.css("top", top);
    mask.append(divObj);
    mask.css("height", $(document).height());     
    mask.css("width", $(document).width());
    $("body").append(mask);
    $("#mask").show();     
}  
// 隐藏遮罩层
function endLoading(){
	$("#mask").hide(); 
	$("#mask").remove();
}

String.prototype.replaceAll = function(s1, s2)
{
	return this.replace(new RegExp(s1, "gm"), s2);
};

function AutoResizeImage(maxWidth,maxHeight,objImg){
	var img = new Image();
	img.src = objImg.src;
	var hRatio;
	var wRatio;
	var Ratio = 1;
	var w = img.width;
	var h = img.height;
	wRatio = maxWidth / w;
	hRatio = maxHeight / h;
	if (maxWidth ==0 && maxHeight==0){
	Ratio = 1;
	}else if (maxWidth==0){//
	if (hRatio<1) Ratio = hRatio;
	}else if (maxHeight==0){
	if (wRatio<1) Ratio = wRatio;
	}else if (wRatio<1 || hRatio<1){
	Ratio = (wRatio<=hRatio?wRatio:hRatio);
	}
	if (Ratio<1){
	w = w * Ratio;
	h = h * Ratio;
	}
	objImg.height = h;
	objImg.width = w;
	}

String.prototype.getBytesLength = function() { 
	return this.replace(/[^\x00-\xff]/gi, "--").length; 
}

/**
 * 
 * @param obj
 *            文件上传控件-入口，判断是IE还是H5，使用不同方法
 * @param type
 *            文件上传类型 2-图片 3-文件 4-视频 5-语音 与媒体类型保持一致
 * @param callback
 *            回调函数
 * @returns {Boolean}
 */
function ajaxFilePost(obj, type, seccFunc, errFunc) {
	// 判断是使用H5方法还是IE方法
	if (obj.get(0).files) {
		ajaxFilePostH5(obj, type, seccFunc, errFunc)
	} else {
		ajaxFilePostIE(obj, type, seccFunc, errFunc)
	}
}
/**
 * 
 * @param obj
 *            文件上传控件-HTML5
 * @param type
 *            文件上传类型 2-图片 3-文件 4-视频 5-语音 与媒体类型保持一致
 * @param callback
 *            回调函数
 * @returns {Boolean}
 */
function ajaxFilePostH5(obj, type, seccFunc, errFunc) {
	startLoading();
	// ajaxfileupload是假的ajax，实际是用iframe实现的，无法实现进度条。
	// 使用另外方法
    var itm = obj.get(0).files[0];
    var formData = new FormData();
    formData.append("file" , itm);
    beginUpLoad();
    
    /**   
     * 必须false才会避开jQuery对 formdata 的默认处理   
     * XMLHttpRequest会对 formdata 进行正确的处理   
     */ 
    $.ajax({
       type: "POST",
       url: '/gdmgr/upload_ajax?type=' + type,
       data: formData ,
       processData : false,  
       //必须false才会自动加上正确的Content-Type   
       contentType : false ,
       dataType: 'json',
       xhr: function(){
            var xhr = $.ajaxSettings.xhr();
            if(onprogress && xhr.upload) {
                xhr.upload.addEventListener("progress" , onprogress, false);
                return xhr;
            }
       },
       success : function(data, status) // 服务器成功响应处理函数
       {
    	   // 调用回调函数
    	   endUpLoad();
    	   seccFunc(data);
    	   endLoading();
       },
       error : function(data, status, e)// 服务器响应失败处理函数
       {
    	   alert("系统忙，请稍后再试或联系技术人员处理！");
    	   endUpLoad();
    	   errFunc();
    	   endLoading();
       }
    });
}

/**
 * 
 * @param obj
 *            文件上传控件-IE
 * @param type
 *            文件上传类型 2-图片 3-文件 4-视频 5-语音 与媒体类型保持一致
 * @param callback
 *            回调函数
 * @returns {Boolean}
 */
function ajaxFilePostIE(obj, type, seccFunc, errFunc) {
	startLoading();
	
	$.ajaxFileUpload({
		url : '/gdmgr/upload_ajax?type=' + type, // 用于文件上传的服务器端请求地址
		secureuri : false, // 一般设置为false
		fileElementId : obj.prop("id"), // 文件上传空间的id属性 <input type="file"
										// id="file"
		// name="file" />
		dataType : 'json', // 返回值类型 一般设置为json
		success : function(data, status) // 服务器成功响应处理函数
		{
			// 调用回调函数
			seccFunc(data);
			endLoading();
		},
		error : function(data, status, e)// 服务器响应失败处理函数
		{
			alert("系统忙，请稍后再试或联系技术人员处理！");
			errFunc();
			endLoading();
		}
	})
}

/**
 * 侦查附件上传情况 ,这个方法大概0.05-0.1秒执行一次
 */
function onprogress(evt){
	var loaded = evt.loaded;     // 已经上传大小情况
	var tot = evt.total;      // 附件总大小
	var per = Math.floor(100*loaded/tot);  // 已经上传的百分比
	$("span.mod-send-message_process-size").text( per +"%" );
	$("span.mod-send-message_process").css("width" , per +"%");
}

/**
 * 显示/隐藏上传进度条
 */
function beginUpLoad() {
	$("span.mod-send-message_process-size").text("");
	$("span.mod-send-message_process").css("width" , "0%");
	$("div.mod-send-message_process-wrap").show();
}

function endUpLoad() {
	$("span.mod-send-message_process-size").text("");
	$("span.mod-send-message_process").css("width" , "0%");
	$("div.mod-send-message_process-wrap").hide();
}

/*
 * 保留两位小数
 */
function changeNum(a){
	if (a==null || a=="") {
		return a;
	}
    var re = /([0-9]+\.[0-9]{2})[0-9]*/;
    return a.replace(re,"$1");
}


/**
 * trim方法
 */
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
};
String.prototype.LTrim = function() 
{ 
	return this.replace(/(^\s*)/g, ""); 
};
String.prototype.RTrim = function() 
{ 
	return this.replace(/(\s*$)/g, ""); 
};
/**
 * replacAll方法
 */
String.prototype.replaceAll = function(s1, s2)
{
	return this.replace(new RegExp(s1, "gm"), s2);
};
/**
 * endWith方法
 */
String.prototype.endWith = function(str)
{
	if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
	if (this.substring(this.length - str.length) == str)
		return true;
	else
		return false;
	return true;
};
/**
 * startWith方法
 */
String.prototype.startWith = function(str)
{
	if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
	if (this.substr(0, str.length) == str)
		return true;
	else
		return false;
	return true;
};

/**
 * validate phone number
 * 1.the length is 11；
 * 2.phone number is made of up of Numbers.
 */
function validatePhoneNum(phoneNum){
	var str=new String(phoneNum);
	if(!(/^\d{11}$/.test(str))){
		return false;
	}else{
		return true;
	}
}

/**
 * 
 * @param obj
 *            文件上传控件-入口，判断是IE还是H5，使用不同方法
 * @param type
 *            文件上传类型 2-图片 3-文件 4-视频 5-语音 与媒体类型保持一致
 * @param trancd
 *            上传的交易码
 * @param callback
 *            回调函数
 * @returns {Boolean}
 */
function ajaxFilePostNew(obj, type, trancd, seccFunc, errFunc) {
	// 判断是使用H5方法还是IE方法
	if (obj.get(0).files) {
		ajaxFilePostH5New(obj, type, trancd, seccFunc, errFunc)
	} else {
		ajaxFilePostIENew(obj, type, trancd, seccFunc, errFunc)
	}
}
/**
 * 
 * @param obj
 *            文件上传控件-HTML5
 * @param type
 *            文件上传类型 2-图片 3-文件 4-视频 5-语音 与媒体类型保持一致
 * @param trancd
 *            上传的交易码
 * @param callback
 *            回调函数
 * @returns {Boolean}
 */
function ajaxFilePostH5New(obj, type, trancd, seccFunc, errFunc) {
	startLoading();
	// ajaxfileupload是假的ajax，实际是用iframe实现的，无法实现进度条。
	// 使用另外方法
    var itm = obj.get(0).files[0];
    var formData = new FormData();
    formData.append("file" , itm);
    beginUpLoad();
    
    /**   
     * 必须false才会避开jQuery对 formdata 的默认处理   
     * XMLHttpRequest会对 formdata 进行正确的处理   
     */ 
    $.ajax({
       type: "POST",
       url: '/gdmgr/newupload_ajax?type=' + type + '&trancd=' + trancd + "&fileName=" + encodeURIComponent(obj.val()),
       data: formData ,
       processData : false,  
       //必须false才会自动加上正确的Content-Type   
       contentType : false ,
       dataType: 'json',
       xhr: function(){
            var xhr = $.ajaxSettings.xhr();
            if(onprogress && xhr.upload) {
                xhr.upload.addEventListener("progress" , onprogress, false);
                return xhr;
            }
       },
       success : function(data, status) // 服务器成功响应处理函数
       {
    	   // 调用回调函数
    	   endUpLoad();
    	   seccFunc(data);
    	   endLoading();
       },
       error : function(data, status, e)// 服务器响应失败处理函数
       {
    	   alert("系统忙，请稍后再试或联系技术人员处理！");
    	   endUpLoad();
    	   errFunc();
    	   endLoading();
       }
    });
}

/**
 * 
 * @param obj
 *            文件上传控件-IE
 * @param type
 *            文件上传类型 2-图片 3-文件 4-视频 5-语音 与媒体类型保持一致
 * @param trancd
 *            上传的交易码
 * @param callback
 *            回调函数
 * @returns {Boolean}
 */
function ajaxFilePostIENew(obj, type, trancd,seccFunc, errFunc) {
	startLoading();
	
	$.ajaxFileUpload({
		url : '/gdmgr/newupload_ajax?type=' + type + '&trancd=' + trancd + "&fileName=" + encodeURIComponent(obj.val()), // 用于文件上传的服务器端请求地址
		secureuri : false, // 一般设置为false
		fileElementId : obj.prop("id"), // 文件上传空间的id属性 <input type="file"
										// id="file"
		// name="file" />
		dataType : 'json', // 返回值类型 一般设置为json
		success : function(data, status) // 服务器成功响应处理函数
		{
			// 调用回调函数
			seccFunc(data);
			endLoading();
		},
		error : function(data, status, e)// 服务器响应失败处理函数
		{
			alert("系统忙，请稍后再试或联系技术人员处理！");
			errFunc();
			endLoading();
		}
	})
}

/**
 * 判断后台交易返回成功还是失败
 * @param json 后台返回结果参数
 * @returns {Boolean} true:交易失败 false:交易成功
 */
function hasError(json) {
    // 判断是否有返回码
    if (json.S_RSP_CD == null || json.S_RSP_CD == ""
        || json.S_RSP_DESC_TX == null) {
        endLoading();
        alert("系统忙，请稍后再试或联系技术人员处理！");
        return true;
    }
    // 返回码是否正常
    if (json.S_RSP_CD != "0000") {
        endLoading();
        // 出错了，截取错误信息
        var msg = json.S_RSP_DESC_TX.split(":");
        if (msg && msg.length > 1) {
            alert(msg[1]);
        } else {
            alert(json.S_RSP_DESC_TX);
        }
        return true;
    }
    
    return false;
}

/**
 * 判断是否有中文
 * @param str
 * @returns {Boolean}
 */
function isChn(str) {
	// 通过正则表达式判断是否有中文
	var reg = /[\u4e00-\u9fa5]/g;
	if (!reg.test(str)) {
		return false;
	}
	return true;
}