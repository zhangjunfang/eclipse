// goweb01 project main.go
package main

import (
	"fmt"
	_ "github.com/astaxie/beego"
	_ "github.com/beego/bee"
	"log"
	"net/http"
	"strings"
)

func say(w http.ResponseWriter, r *http.Request) {

	r.ParseForm()
	fmt.Println(r.Form)
	fmt.Println(r.URL.Path)
	fmt.Println(r.URL.Scheme)
	fmt.Println(r.FormValue("aa"))
	fmt.Println(r.Form["aa"])
	for key, value := range r.Form {
		fmt.Println(key, " === ", strings.Join(value, "=================="))
	}
	fmt.Fprintf(w, "张伯雨第一个测试")
}

func main() {
	http.HandleFunc("/88888", say)
	err := http.ListenAndServe(":8888", nil)
	if err != nil {
		log.Fatal("ListenAndServe  监听失败 ： ", err)
	}
	fmt.Println("Hello World!")
}
