pproxy 0.4
======
http抓包代理程序,http协议调试工具。  
采用golang编写，采用bs模式(s-代理程序，b-会话查看、配置管理等功能)  

下载编译好的可执行文件: <http://pan.baidu.com/s/1i3pAe7V>  


<pre>
支持：
1.url重定向
   如把 http://www.baidu.com/s?wd=pproxy 修改为 http://m.baidu.com/s?wd=pproxy
   
2.form表单动态  
   get、post可以动态修改（增删改）  
   
3.hosts文件支持
  相当于 修改host或者dns 如  
  将www.baidu.com 请求全部发往127.0.0.1  
  将www.baidu.com:81 请求全部发往192.168.1.2:8080  
  
4.可查看request 和response详情
   form表单参数，header等都可以很方便的看到
   
5.登录认证支持
   支持httpBasic认证
   
6.redo功能
   可以修改request的参数（get、post、header）
</pre>

使用javascript来配置重定向功能，如
```
if(req.host=="www.baidu.com"){
   req.host="www.163.com"
   req.host_addr="127.0.0.0:81" // send req to 127.0.0.1:81
}
```
当然也可以这样：
```
if(req.host.indexOf("baidu.com")>-1){
  req.host_addr="127.0.0.0:81"
}
```

req变量示例：
```
#url : http://www.example.com/album/list?cid=126
#req对象有如下一下属性：
schema : http
host : www.example.com
port : 80
path : /album/list
get: {cid:[123]}
post: {}
username : 
password : 
method: GET
form_get  : {add:function(k,v){},set:function(k,v){},get:function(k){},len:function(){}} 
form_post : {add:function(k,v){},set:function(k,v){},get:function(k){},len:function(){}}

host_addr: #修改该请求的host是使用，如 127.0.0.1:3218

#注意 get 和post的值是数组，如上cid参数
#form_get 用于更方便的操作  get参数对象
#form_post 用于更方便的操作 post参数对象
```
除了url变量外，其他的都是可以修改来对request进行重写的

增强的hosts文件使用:
```
www.baidu.com 127.0.0.1
www.baidu.com:81 10.0.2.2:8080
```



配置文件：
```
conf/
├── pproxy.conf          #server的配置
├── hosts_8080           #8080端口server的hosts规则
├── req_rewrite_8080.js  #8080端口server的url重写规则
├── hosts_8081
├── req_rewrite_8081.js
└── users                #全局帐号配置文件
```

users配置:
```
#name psw isAdmin
admin e10adc3949ba59abbe56e057f20f883e:md5 admin   #帐号 admin，密码 是 md5(123456),是管理员帐号
```
可以在线修改配置时必须使用管理员帐号登录

配置文件示例:
```
port : 8080

title : demo
notice :notice notice

#数据存放目录，相对于当前配置的路径
dataDir : ../data/

#代理服务认证方式
authType : none
#options:{none : 无认证     basic : http basic  try_basic : 尝试httpBasic认证 }

#那些request和response数据进行存储
responseSave : all
#options:{ all : 所有   only_broadcast : 发送到session list的才存储}

#session列表查看数据
sessionView : all
# options :{ all:所有人可见 ip_or_user : 输入正确的ip或者user后可见}

```
