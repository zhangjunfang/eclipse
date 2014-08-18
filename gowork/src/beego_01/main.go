// beego_01 project main.go
package main

import (
	"fmt"
	"github.com/astaxie/beego"
	//"github.com/astaxie/beego/validation"
	//"net/http"
	//"strconv"
	//"path"
	"runtime"
)

func init() {
	runtime.GOMAXPROCS(runtime.NumCPU())
	fmt.Println(runtime.NumCPU())
	fmt.Println(runtime.NumGoroutine())
	//beego.SessionProvider = "file"
	//beego.SessionSavePath = "./tmp"
	//fmt.Println(path.Dir(path.Base("")))
}

type OceanController struct {
	beego.Controller
}

func (this *OceanController) Prepare() {
	this.SetSession("aa", "aaa")
	//fmt.Println(this.Ctx.Request.FormValue("name"))
	this.Ctx.WriteString(this.Ctx.Request.FormValue("name"))
	//this.Ctx.WriteString(this.GetSession("aa").(string))
	//fmt.Println(this.GetString("name"))
}
func (this *OceanController) Get() {
	beego.SessionOn = true
	beego.SessionProvider = "mysql"
	beego.SessionSavePath = "root:@tcp(127.0.0.1)/greennet?charset=utf8"
}

//type User struct {
//	Name string
//	Age  int
//}

func main() {
	//u := User{"man", 40}
	//valid := validation.Validation{}
	//valid.Required(u.Name, "name")
	//valid.MaxSize(u.Name, 15, "nameMax")
	//valid.Range(u.Age, 0, 18, "age")
	fmt.Println("Hello World!")
	beego.SessionOn = true
	beego.Router("/test", &OceanController{})
	beego.Run()
}
