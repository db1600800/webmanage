window.onload = function() {

	// 日期格式化
	function formatDate(date, format) {
		if(!date) return;
		if(!format) format = "yyyy-MM-dd";
		switch(typeof date) {
			case "string":
				date = new Date(date.replace(/-/, "/"));
				break;
			case "number":
				date = new Date(date);
				break;
		}
		if(!date instanceof Date) return;
		var dict = {
			"yyyy": date.getFullYear(),
			"M": date.getMonth() + 1,
			"d": date.getDate(),
			"H": date.getHours(),
			"m": date.getMinutes(),
			"s": date.getSeconds(),
			"MM": ("" + (date.getMonth() + 101)).substr(1),
			"dd": ("" + (date.getDate() + 100)).substr(1),
			"HH": ("" + (date.getHours() + 100)).substr(1),
			"mm": ("" + (date.getMinutes() + 100)).substr(1),
			"ss": ("" + (date.getSeconds() + 100)).substr(1)
		};
		return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {
			return dict[arguments[0]];
		});
	}

	// 日期加一天
	function addDate(date, amount) {
		var a = date.valueOf();
		a = a + amount * 24 * 60 * 60 * 1000;
		a = new Date(a);
		return a;
	}

	//	时间控件

	var year = new Date().getFullYear();
	var month = new Date().getMonth();
	var today = new Date().getDate();
	var opt = {};
	opt.date = {
		preset: 'date'
	};
	opt.datetime = {
		preset: 'datetime'
	};
	opt.time = {
		preset: 'time'
	};
	opt.default = {
		theme: 'android-ics light', //皮肤样式
		display: 'modal', //显示方式 
		mode: 'scroller', //日期选择模式
		dateFormat: 'yy-mm-dd',
		lang: 'zh',
		showNow: true,
		nowText: "今天",
		minDate: new Date(year-100),
		maxDate: new Date(year + 30, month, today - 1)

	};

	$(".mobile_date").mobiscroll($.extend(opt['date'], opt['default']));

	if($("#s_start").val() == "") {
		var now = formatDate(addDate(new Date(), 0));
		$("#s_start").each(function() {
			$(this).val(now);
		});
	}

	if($("#s_end").val() == "") {
		var now = formatDate(addDate(new Date(), 5));
		$("#s_end").each(function() {
			$(this).val(now);
		});
	}

}