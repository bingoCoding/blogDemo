$(document).ready(function() {
	$('.leftpanel .nav .parent > a').click(function() {
	      
	      var coll = $(this).parents('.collapsed').length;
	      
	      if (!coll) {
	         $('.leftpanel .nav .parent-focus').each(function() {
	            $(this).find('.children').slideUp('fast');
	            $(this).removeClass('parent-focus');
	         });
	         
	         var child = $(this).parent().find('.children');
	         if(!child.is(':visible')) {
	            child.slideDown('fast');
	            if(!child.parent().hasClass('active'))
	               child.parent().addClass('parent-focus');
	         } else {
	            child.slideUp('fast');
	            child.parent().removeClass('parent-focus');
	         }
	      }
	      return false;
	});
	
	var start = new Date();
	start.setDate(start.getDate() -6);
	var end = new Date();
	$('#date-range').daterangepicker({
		startDate: $.format.date(start,'yyyy-MM-dd'),
		endDate: $.format.date(end,'yyyy-MM-dd'),
		ranges:{
			'最近一个月': [moment().subtract(1, 'month'), moment()],
			'最近一年': [moment().subtract(1, 'year').add(1,'day'), moment()]
		},
		locale : {
			format : 'YYYY-MM-DD',
			separator : ' 至 ',
			applyLabel : '确定',
			cancelLabel : '取消',
			fromLabel : '起始时间',
			toLabel : '结束时间',
			customRangeLabel : '自定义',
			daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
			monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
				'七月', '八月', '九月', '十月', '十一月', '十二月' ],
			firstDay : 1
		}
	});
	
	$('#date-single').daterangepicker({
		date: $.format.date(end,'yyyy-MM-dd'),
		endDate: $.format.date(end,'yyyy-MM-dd'),
		singleDatePicker: true,
		//format: 'yyyy-mm-dd',
		locale : {
			format : 'YYYY-MM-DD',
			separator : ' 至 ',
			applyLabel : '确定',
			cancelLabel : '取消',
			fromLabel : '起始时间',
			toLabel : '结束时间',
			customRangeLabel : '自定义',
			daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
			monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
				'七月', '八月', '九月', '十月', '十一月', '十二月' ],
			firstDay : 1
		}
	});
});

function initPop() {
	$("[data-toggle='popover']").popover({
		trigger: 'manual',
		html: 'true',
		placement: 'left',
		content: popContent,
		container: 'body',
		animation: false
	}).on("mouseenter", function () {
        var self = this;
        $(this).popover("show");
        $(this).siblings(".popover").on("mouseleave", function () {
            $(self).popover('hide');
        });
    }).on("mouseleave", function () {
        var self = this;
        setTimeout(function () {
            if (!$(".popover:hover").length) {
                $(self).popover("hide")
            }
        }, 100);
    });
}

function initPopCloseEvent() {
	document.onclick = function (event) {
		var target = $(event.target);
        var targetParent = target.parent();
        
        // 点击popup自定义部分不关闭
        if (target.hasClass('popover-own')) {
        	return;
        }
        
        if (!targetParent.hasClass('popover')
                && !targetParent.hasClass('pop')
                && !targetParent.hasClass('popover-content')
                && !targetParent.hasClass('popover-title')
                && !targetParent.hasClass('arrow')) {
        	$("[data-toggle='popover']").popover('hide');
        } else if (!target.hasClass('popover')
                && !target.hasClass('pop')
                && !target.hasClass('popover-content')
                && !target.hasClass('popover-title')
                && !target.hasClass('arrow')) {
        	$("[data-toggle='popover']").popover('hide');
        }
	};
}

//popup内容
function popContent() {
	var data = $(this).attr('data-param').split('&');
	var sPopDiv = '<div class="popover-own">';
	
	for (var i = 0; i < data.length; i++) {
		sPopDiv += '<div class="popover-own">' + data[i] + '</div>'
	}
	
	sPopDiv += '</div>';
	return sPopDiv;
}