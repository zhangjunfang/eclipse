// mysqldatabase project main.go
package main

import (
	"controller"
	"fmt"
	"io/ioutil"
	"net/http"
	"os"
	"runtime"
	"time"
)

func init() {
	//fmt.Println("--------------------------------------------------")
	////时间戳
	//t := time.Now().Unix()
	//fmt.Println(t)

	////时间戳到具体显示的转化
	//fmt.Println(time.Unix(t, 0).String())

	////带纳秒的时间戳
	//t = time.Now().UnixNano()
	//fmt.Println(t)
	//fmt.Println("------------------")

	////基本格式化的时间表示
	//fmt.Println(time.Now().String())

	//fmt.Println(time.Now().Format("2006年 01月 02日 00小时 00分 00秒  "))
	resp, _ := http.Get("http://www.baidu.com")
	defer resp.Body.Close()
	body, _ := ioutil.ReadAll(resp.Body)
	fmt.Println(string(body))

}
func main() {
	runtime.GOMAXPROCS(10)
	fmt.Printf(" \r\n NumCPU %d  \r\n  ", runtime.NumCPU())
	fmt.Println(runtime.NumGoroutine())
	runtime.Gosched()
	fmt.Println(time.Now().String())
	controller.UserController()
}
