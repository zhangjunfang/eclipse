// goweb02 project main.go
package main

import (
	"fmt"
	"net/http"
	"strings"
	"html/template"
	"log"
	"database"
)

func  Say(w http.ResponseWriter,r *http.Request){
	r.Header.Add("zhangsan","lisi")
	r.ParseForm();
	fmt.Println(r.Form);
	fmt.Println(r.Form["username"]);
	fmt.Println(r.Form["password"]);
	fmt.Println(r.URL.Path);
	fmt.Println(r.Cookies());
	for cookiekey,cookieValue:=range r.Cookies(){
		fmt.Println(cookiekey,":",cookieValue);	
	}
	fmt.Println(r.Header.Get("zhangsan"));
	fmt.Println(r.URL.Query());
	fmt.Println(r.URL.Host);
	fmt.Println(r.URL.Scheme);
	fmt.Println(r.URL.RequestURI());
	fmt.Println(r.URL.Scheme);
	fmt.Println(r.URL.IsAbs());
	var s  string;
	for key ,value:=range r.Form{
		
		s=s+key+":"+strings.Join(value,",")+"\r\n";
		
	}
	database.
	fmt.Fprintln(w,s);
}

func Login(w http.ResponseWriter,r *http.Request){
	fmt.Println(r.Method);
	if r.Method=="GET"{
		t,_:=template.ParseFiles("login.gtpl");
		t.Execute(w,nil);
	}else{
		r.ParseForm();
		fmt.Println(r.Form["username"]);
	    fmt.Println(r.Form["password"]);
		fmt.Println(r.Form.Get("username"));
		fmt.Fprintln(w,r.FormValue("username"),r.FormValue("password"));
	}
}
func main() {
	http.HandleFunc("/",Say);
	http.HandleFunc("/login",Login);
	err:=http.ListenAndServe(":9090",nil);
	if(err!=nil){
		log.Fatal("监听服务出错了：", err);
	}
	
}
