// aa project main.go
package main

import (
	"fmt"
	"github.com/astaxie/beego"
	"github.com/beego/admin"
	"github.com/codegangsta/martini"
	"github.com/fatih/pool"
	_ "github.com/go-web-framework/handy"
	"io/ioutil"
	"net"
	"net/http"
)

type OceanController struct {
	beego.Controller
}

func main() {
	admin.Run()
	beego.Router("/", &OceanController{})
	beego.Run()

	p, _ := pool.New(5, 30, factory)
	p.Get()
	martini.Classic()
	response, _ := http.Get("http://www.baidu.com")
	defer response.Body.Close()
	body, _ := ioutil.ReadAll(response.Body)
	fmt.Println(string(body))
	fmt.Println("-------------------------------------------------------")

	client := &http.Client{}
	reqest, _ := http.NewRequest("GET", "http://www.baidu.com", nil)

	reqest.Header.Set("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
	reqest.Header.Set("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3")
	reqest.Header.Set("Accept-Encoding", "gzip,deflate,sdch")
	reqest.Header.Set("Accept-Language", "zh-CN,zh;q=0.8")
	reqest.Header.Set("Cache-Control", "max-age=0")
	reqest.Header.Set("Connection", "keep-alive")

	response2, _ := client.Do(reqest)
	if response2.StatusCode == 200 {
		body2, _ := ioutil.ReadAll(response2.Body)
		bodystr := string(body2)
		fmt.Println(bodystr)
	}
}
func factory() (net.Conn, error) {
	return net.Dial("tcp", "127.0.0.1:4000")
}
