//grid所需要的全局变量
var G = {proxy:{},reader:{totalProperty:"",root:""},pageSize:10,bodyDOm:null,pagingDom:null,
	currentPageIndex:1,
	columns:[],
	color:{
		defaultColor		:"#fff",//默认颜色
		selected 			:	"#DFE8F6",//行选中的颜色
		hot					:	"#EFEFEF",//鼠标移上去的颜色
		checkboxBackground	:	"#EBECEE",
		checkboxBorder		:	"#D0D0D0"
	},
	ids:{
		headerId	:	"grid_header",
		bodyId		:	"grid_body",
		pagingId	:	"grid_paging"
	},
	selectedRowIndex:-1,
	useCheckbox		:false,
	pagination : true,
	checkValueKey	:"",
	listeners		: null,
	imgBasePath		: "",
	imgs:{}};//定义全局变量
var ___gridData = {};
function Grid(gridId,opts){
	var grid_dom = document.getElementById(gridId);
	if(!grid_dom) return;
	this.gridDom = grid_dom;
	//设置grid的宽度与高度
	this.gridDom.style.width = typeof(opts.width)=="string"?opts.width:opts.width+"px";
	this.height = opts.height;
	if(!opts) opts = {};
	this.pageSize = opts.pageSize || 10;
	this.width = opts.width || 500;
	this.height = opts.height || 200;
	this.columns = this._toColumns(opts);
	G.imgBasePath = opts.imgBasePath || "/js/grid/imgs/";
	G.imgs = _loadImgs_(G.imgBasePath);
	G.columns = this.columns;
	this.proxy = opts.proxy;
	this.reader = opts.reader;
	G.proxy = this.proxy;
	G.reader.totalProperty = opts.reader ? opts.reader.totalProperty : "totalProperty";
	G.reader.root = opts.reader ? opts.reader.root : "root";
	G.pageSize = this.pageSize; 
	G.useCheckbox = typeof(opts.useCheckbox)=="undefined"?false:opts.useCheckbox;
	G.checkValueKey = opts.checkValueKey || null;
	G.listeners = opts.listeners || {};
	this.draw();
};
//grid的图片按钮的定义
function _loadImgs_(basePath){
	return {
		first		:	basePath + "page-first.gif",
		first_disabled:	basePath + "page-first-disabled.gif",
		prev		:	basePath +"page-prev.gif",
		prev_disabled		:	basePath +	"page-prev-disabled.gif",
		next		:	basePath +"page-next.gif",
		next_disabled	:	basePath +"page-next-disabled.gif",
		last		:	basePath +"page-last.gif",
		last_disabled	:	basePath +"page-last-disabled.gif",
		refresh			:	basePath + "refresh.gif",
		loading			:	basePath + "wait.gif",
		split			:	basePath + "grid-split.gif"
	};
};
//定义一个列对象
function Column(dataIndex){
	this.dataIndex = dataIndex;
	this.header = dataIndex;
	this.width = 100;
	this.align = "";
}
//将用户定义的列转换为grid内部支持的列
Grid.prototype._toColumn = function(c){
	if(!c) c = {dataIndex:""};
	var col = new Column(c.dataIndex);
	col.header = c.header || c.dataIndex;
	col.width = c.width || col.width;
	col.align = c.align || "";
	col.render = c.render || null;
	return col;
};
//设置当前列表的起始页码，一般不用设置，由grid自身来维护
Grid.prototype.setPageIndex = function(pageIndex){
	if(pageIndex == null || pageIndex=="") return;
	if(typeof(pageIndex)=="string") pageIndex = parseInt(pageIndex);
	G.currentPageIndex = pageIndex;
};
//将用户设置的选项中的列定义转换为该类内部所需要的列定义，是个数组
Grid.prototype._toColumns = function(opts){
	if(opts && opts.columns){
		var o_cols = opts.columns;
		var columns = new Array();
		var c;
		for(var i=0;i<o_cols.length;i++){
			c = this._toColumn(o_cols[i]);
			columns[i] = c;
		}
		return columns;
	}
	return [];
};
Grid.prototype.getServerData = function(){
	return this.data;
};
//当grid支持多选时，调用该方法则获取选中的行的值的累加，以逗号隔开
Grid.prototype.getSelectedValue = function(){
	var dom = G.bodyDOm;
	//var inputs = document.getElementsByTagName("input");
	var inputs = $('#grid_body input');
	var input;
	var s = new Array();
	var j = 0;
	for(var i=0;i<inputs.length;i++){
		input = inputs[i];
		if(input.type=="checkbox" && input.checked && input.value!="" && input.value!="on"){
			s.push(___gridData[input.value]);
		}
	}
	return s;
};
Grid.prototype.setStore = function(data){
	___createPagination(data);
};
//重新加载，由外部调用,params格式：{json:json,limit:10}
Grid.prototype.reload = function(params){
	_reload_(params);
};
//构造列表控件
Grid.prototype.draw = function(){
	var _tbC = document.createDocumentFragment();
	//先创建头
	var _div = document.createElement("div");	
	_div.className = "grid_header";
	_div.id = G.ids.headerId;
	var _tb = document.createElement("table");
	var _tr = _tb.insertRow(0);
	var _td;
	var _c;//列的定义
	var _st;//样式的定义，共用
	_tb.cellSpacing = 0;
	var txt;//文本
	if(G.useCheckbox.checked){
		_td = _tr.insertCell(-1);
		_td.innerHTML = "<input type=\"checkbox\" onclick=\"_selectAll_(this,'"+G.ids.bodyId+"');\" />";
		_st =_td.style;
		_st.width = G.useCheckbox.width;
		_st.padding = "0";
		//_st.backgroundColor = G.color.checkboxBackground;
		//_st.borderRight = "thin solid "+G.color.checkboxBorder;
	}
	for(var i=0;i<this.columns.length;i++){
		_c = this.columns[i];
		_td = _tr.insertCell(-1);
		_td.innerHTML = _c.header;
		_st =_td.style;
		_st.width = _c.width;
	}
	_div.appendChild(_tb);
	_tbC.appendChild(_div);
	//创建头结束
	//开始创建体
	_div = document.createElement("div");
	_div.id = G.ids.bodyId;
	_div.className = "scrollingContent grid_body";
	_div.style.height = (this.height-50)+ 'px';
	_tb = document.createElement("table");
	_tb.cellSpacing = 0;
	var _instance = this;
	G.bodyDOm = _tb;	
	_div.appendChild(_tb);
	_tbC.appendChild(_div);
	//创建体结束，以下开始创建分页器
	if(G.pagination){
		_div = document.createElement("div");
		_div.id = G.ids.pagingId;
		_div.className = "paging";
		_div.innerHTML =  _createImgPaging_(G.currentPageIndex,G.pageSize,0);
		G.pagingDom = _div;
		_tbC.appendChild(_div);
	}
	this.gridDom.className = "grid";
	this.gridDom.appendChild(_tbC);
	this.reload();
};
//grid支持checkbox时，全选事件行为，由grid内部调用
function _selectAll_(sender,gridId){
	console.log(gridId);
	var dom = document.getElementById(gridId);
	//var inputs = document.getElementsByTagName("input");
	var inputs = $('#'+gridId+' input');
	var input;
	for(var i=0;i<inputs.length;i++){
		input = inputs[i];
		if(input.type=="checkbox")
			input.checked = sender.checked;
	}
};
//从json对象中获取key所对应的值，类似于java的map
function _getDataFromJSON_(obj,key){
	if(obj && key && obj[key]){
		return obj[key];
	}
	return "";
};
//生成分页器,这个方法是生成文字版的
function _createImgPaging_(pageIndex,pageSize,total){
	
//	if (pageIndex < 1) pageIndex = 1;
//    if (pageSize < 1) pageSize = 10;//如果页大小<1的话,则设为10,即每页显示10条
    //计算共有多少页
    var totalPage = Math.ceil(total / pageSize);
//    if(totalPage == 1) return "";
    if(pageIndex>totalPage) pageIndex = totalPage;
    //开始构造分页器
    var s = "";
    var paddingTop = 4;
    if(window.event){
    	paddingTop = 6;
    }
    s += "<div style='float:right;padding-top:"+paddingTop+"px;'>共" + total +
    		"条数据</div>";
    var inputnum = "";
   	inputnum = "<div style='float:left;'>第<input type='text' style='width:40px;' value='"+pageIndex+"' maxlength='5' OnKeyUp='_go1_(event,this.value);' />页共" +
    		totalPage + "页</div>";
    if(total == 0){
    	s += _createimg_(G.imgs.first_disabled,"nocursor");
   		s += _createimg_(G.imgs.prev_disabled,"nocursor");
   		s += _createimg_(G.imgs.split,"split_right");
   		s += inputnum;
   		s += _createimg_(G.imgs.split,"split_left");
   		s += _createimg_(G.imgs.next_disabled,"nocursor");
    	s += _createimg_(G.imgs.last_disabled,"nocursor");
    	s += _createimg_(G.imgs.split,"split_right split_left");
    	s += _createimg_(G.imgs.refresh,null,null,true);
        return s;
    }
   
   	if(pageIndex == 1){
   		s += _createimg_(G.imgs.first_disabled,"nocursor");
   		s += _createimg_(G.imgs.prev_disabled,"nocursor");
   		s += _createimg_(G.imgs.split,"split_right");
   	}else{
   		s += _createimg_(G.imgs.first,null,1);
   		s += _createimg_(G.imgs.prev,null,pageIndex-1);
   		s += _createimg_(G.imgs.split,"split_right");
   	}
    s += inputnum;
    if(pageIndex == totalPage){
    	s += _createimg_(G.imgs.next_disabled,"nocursor");
    	s += _createimg_(G.imgs.last_disabled,"nocursor");
    	s += _createimg_(G.imgs.split,"split_right split_left");
    }else{
    	s += _createimg_(G.imgs.next,null,pageIndex+1);
    	s += _createimg_(G.imgs.last,null,totalPage);
    	s += _createimg_(G.imgs.split,"split_right split_left");
    }
    s += _createimg_(G.imgs.refresh,null,null,true);
    return s;
};
//创建一图片按钮
function _createimg_(src,_class,index,isreload){
	var paddingTop = 3;
	if(window.event) paddingTop = 5;
	var s = "<div style='float:left;padding-top:"+paddingTop+"px;'><img src='";
	s += src +"' ";
	if(_class){
		s += "class='"+_class+"' ";
	}
	if(index){
		s += "onclick='_go_("+index+");'";
	}
	if(isreload){
		s += "id='grid_reload_img' onclick='_reload_();'";
	}
	s += "/></div>";
	return s;
};
//翻页
function _go_(index){
	if(typeof(index)=="string")
		index = parseInt(index);
	G.currentPageIndex =index;
	_reload_();
};
//分页栏中的输入框中输入数字后的翻页事件
function _go1_(evt,index){
	if (index == "") return;
	if(evt == null) evt = window.event; 
	if(evt.keyCode != 13) return;
    var reg = /\d+/;
    if(!reg.test(index)){alert("输入的字符无效!"); return;}
    _go_(index);
};
//根据proxy从服务器上获取数据
function _getData_(pageIndex){
	_load_(true);
	//暂时先同步获取数据，以后可改为异步
	var proxy = G.proxy;
	if(proxy.params == undefined){
		proxy.params = {};
	}
	proxy.params.start = pageIndex;
	proxy.params.limit = G.pageSize;
	if(proxy.url == null || proxy.url == ""){
		___createPagination({"root":[],"total":0});
	}else{
		$.post(proxy.url,proxy.params,function(data){
			//var j = eval("("+data+")");
//			var value =  {total:j[G.reader.totalProperty],root:j[G.reader.root]};
			___gridData = data.root;
			___createPagination(data);
		});
	}
	
};
//重新加载数据，根据参数
function _reload_(params){
	if(params)
		G.proxy.params = params;
	var pagenum = 0;
	if(params != undefined && params.start != undefined){
		pagenum = params.start;
	}else{
		pagenum = G.currentPageIndex-1;
	}
	//从服务器上获取数据
	this._getData_(pagenum);
};
function ___createPagination(_dataObj){
	var _rowData,_tb,_tr,_td,_st;
	_tb = G.bodyDOm;
	if(_tb.firstChild){
		var tbody = document.createElement("tbody");
		_tb.replaceChild(tbody,_tb.firstChild);
	}
	//成功从服务器上获取了数据
	var _rowData;//行数据，也是以json格式定义
	for(var i=0;i<_dataObj.root.length;i++){
		_rowData = _dataObj.root[i];
		_tr = _tb.insertRow(-1);
		//鼠标的移上去的效果
//		_tr.onmouseover = function(){
//			if(this.rowIndex != G.selectedRowIndex){
//				this.style.backgroundColor = G.color.hot;
//			}
//		};
//		//鼠标移出行时的效果
//		_tr.onmouseout = function(){
//			if(this.rowIndex != G.selectedRowIndex){
//				this.style.backgroundColor = G.color.defaultColor;
//			}
//		};
		//点击行时的事件，如果用户自定义了点击事件，则在该方法里将会得到调用
		_tr.ondblclick = function(){
			var index = this.rowIndex;
			if(index != G.selectedRowIndex){
//				if(G.selectedRowIndex != -1){
//					if(this.parentNode.rows.length>G.selectedRowIndex)
//						this.parentNode.rows[G.selectedRowIndex].style.backgroundColor = G.color.defaultColor;
//				}
				G.selectedRowIndex = index;
			}				
//			this.style.backgroundColor = G.color.selected;
			if(G.listeners.rowclick){
				//调用用户自定义的行事件
				G.listeners.rowclick(index,_dataObj.root[index]);
			}
		};
		if(G.useCheckbox.checked){
			_td = _tr.insertCell(-1);
			_td.innerHTML = "<input type='checkbox' value='"+i+"' />";
			_st =_td.style;
			_st.width = G.useCheckbox.width;
			_st.padding = "0";
			//_st.backgroundColor = G.color.checkboxBackground;
			//_st.borderRight = "thin solid "+G.color.checkboxBorder;
		}
		for(var j=0;j<G.columns.length;j++){
			_c = G.columns[j];
			_td = _tr.insertCell(-1);				
			if(_c.render){
				_td.innerHTML = _c.render(_rowData);
			}else{
				txt = document.createTextNode(_rowData[_c.dataIndex==undefined ? _c.header:_c.dataIndex]);
				_td.appendChild(txt);
			}
			_st =_td.style;
			_st.width = _c.width;
			if(_c.align!="") _st.textAlign = _c.align;
		}
		_tb.firstChild.appendChild(_tr);
	}
	if(G.pagination)
		G.pagingDom.innerHTML = _createImgPaging_(G.currentPageIndex,G.pageSize,_dataObj.total);
};
/*
	设置正在加载的图片为旋转状态，仅支持在ff浏览器
*/
function _load_(mark){
	var r = document.getElementById("grid_reload_img");
	if(mark){
		r.src = G.imgs.loading;
		r.alt = "loading";
	}else{
		r.src = G.imgs.refresh;
	}
	
};