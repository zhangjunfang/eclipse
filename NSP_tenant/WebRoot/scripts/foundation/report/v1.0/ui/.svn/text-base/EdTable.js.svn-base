var _widget_type = "<select onblur='EdTable.saveEdit(this,this.value)' onchange='appendWidgetSelectOption(this.value)' style='width:100%;padding:0px; margin:0px;'>" +
		"<option value='文本框'>文本框</option><option value='下拉框'>下拉框</option><option value='日期框'>日期框</option>" +
		"<option value='下拉树'>下拉树</option></select>";

	var _form_widget_validator = "<select onblur='EdTable.saveEdit(this,this.value)' style='width:100%;padding:0px; margin:0px;' >"+
		"<option value='允许为空'>允许为空</option><option value='不能为空'>不能为空</option><option value='数字'>数字</option>"+
		+"<option value='日期格式'>日期格式</option></select>";
	var _field_type = "<select onblur='EdTable.saveEdit(this,this.value)' style='width:100%;padding:0px; margin:0px;'>" +
		"<option value='string'>string</option><option value='number'>number</option><option value='date'>date</option></select>";
	
	var EdTable = function(){
	// 给单元格绑定事件
	function initBindGridEvent(){
		$("td.editable").unbind();
		// 添加单元格点击事件
		addGridClickEvent();
		// 添加单元格双击事件
		addGridDbClickEvent();
		// 添加键盘事件
		addGridKeyPressEvent();
	}
	
	// 给单元格添加单击事件
	function addGridClickEvent(){
		$("td.simpleInput").bind("click",function(){
			$('.simpleInput').each(function(){
				$(this).removeClass("selectCell");
			});
			// 给选中的元素添加选中样式
			$(this).addClass("selectCell");
		});
		$("td.simpleSelect").bind("click",function(){
			$('.simpleSelect').each(function(){
				$(this).removeClass("selectCell");
			});
			// 给选中的元素添加选中样式
			$(this).addClass("selectCell");
		});
	}
	
	//给单元格添加双击事件
	function addGridDbClickEvent(){
		
		$("td.simpleInput").bind("dblclick",function(){
			$('.simpleInput').each(function(){
				$(this).removeClass("selectCell");
			});
			if($(this).children("input").length > 0){
				return;
			}
			var val=$(this).html();
			var width = $(this).css("width");
			var height = $(this).css("height");
			$(this).html("<input type='text' onblur='EdTable.saveEdit(this,this.value)' style='width:"+ width +";height:"+ height +"; padding:0px; margin:0px;' value='"+val+"' >");
			$(this).children("input").select();
		});	
		$("td.simpleSelect").bind("dblclick",function(){
			
			$('.simpleSelect').each(function(){
				$(this).removeClass("selectCell");
			});
			if($(this).children("select").length > 0){
				return;
			}
			var val=$(this).html();
			var id = $(this).attr("id");
			if(id == '_field_type'){
				id = _field_type;
			} else if(id == '_widget_type'){
				id = _widget_type;
			}else if(id == '_form_widget_validator'){
				id = _form_widget_validator;
			}else if(id == '_widget_dc'){
				id = _widget_dc;
			}
//			var width = $(this).css("width");
//			var height = $(this).css("height");
			$(this).html(id);
			$($(this).children("select")).val(val);
			$(this).children("input").select();
		});	
	}
	
	// 给单元格添加键盘事件
	function addGridKeyPressEvent(){
		$(document).keyup(function(event){
			if(event.keyCode == 37){
				// 左箭头
				var selectCell = $(".selectCell").prev()[0];
				if(selectCell != undefined){
					$(".selectCell").removeClass("selectCell").prev().addClass("selectCell");
				}
			} else if(event.keyCode == 38){
				// 上箭头
				var col = $(".selectCell").prevAll().length;
				var topCell = $(".selectCell").parent("tr").prev().children()[col];
				if(topCell != undefined){
					$(".selectCell").removeClass("selectCell");
					$(topCell).addClass("selectCell");
				}
			} else if(event.keyCode == 39){
				// 右箭头
				var selectCell = $(".selectCell").next()[0];
				if(selectCell != undefined){
					$(".selectCell").removeClass("selectCell").next().addClass("selectCell");
				}
			} else if(event.keyCode == 40){
				// 下箭头
				var col = $(".selectCell").prevAll().length;
				var topCell = $(".selectCell").parent("tr").next().children()[col];
				if(topCell != undefined){
					$(".selectCell").removeClass("selectCell");
					$(topCell).addClass("selectCell");
				}
			} else if(event.keyCode == 13){
				// 回车键
				var selectCell = $(".selectCell")[0];
				if(selectCell != undefined){
					$(selectCell).dblclick();
				}
			}
		});
	}
	
	// 单元格失去焦点后保存表格信息
	function saveEdit(gridCell,value){
		var pnt=$(gridCell).parent();
		$(pnt).html(value);
		$(gridCell).remove();
	}
	return{
		initBindGridEvent : initBindGridEvent,
		saveEdit : saveEdit
	};
}();