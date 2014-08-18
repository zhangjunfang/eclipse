// controller project controller.go
package controller

import (
	"business"
	"fmt"
	"io"
	"net/http"
	"time"
)

func RequestParamer(w http.ResponseWriter, r *http.Request) {
	method := r.Method
	var user business.User
	if method == "get" || method == "GET" {
		r.ParseForm()
		user = business.User{1, r.Form.Get("userName"), r.Form.Get("loginName"), time.Now()}
	}
	if method == "post" || method == "POST" {
		user = business.User{1, r.PostFormValue("userName"), r.PostFormValue("loginName"), time.Now()}
	}
	go business.AddUser(user)
	io.WriteString(w, "操作成功！！！！！")
}

func UserController() {
	fmt.Println("hello ocean !!!!")
	http.HandleFunc("/user", RequestParamer)
	http.ListenAndServe(":8080", nil)
}
