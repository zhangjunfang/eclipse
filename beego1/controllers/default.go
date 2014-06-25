package controllers

import (
	"beego1/models"
	"fmt"
	"github.com/astaxie/beego"
	//"github.com/astaxie/beego/validation"
	//"log"
	"strings"
	"time"
)

type MainController struct {
	beego.Controller
}

func (this *MainController) Get() {
	//this.Data["Website"] = "beego.me"
	//this.Data["Email"] = "astaxie@gmail.com"
	this.TplNames = "index.tpl"
}
func (this *MainController) Login() {
	fmt.Println("-----------------------------" + this.Ctx.Input.Url())
	fmt.Println("-----------------------------" + this.GetString("xm"))

	//this.Data["Website"] = "beego.me"
	//this.Data["Email"] = "astaxie@gmail.com"
	this.TplNames = "index.tpl"
}
func (this *MainController) Post() {
	fmt.Println("-----------------post------------" + this.Ctx.Input.Url())
	fmt.Println("-----------------------------" + this.GetString("xm"))
	p := &models.Person{Name: this.GetString("xm"), Ids: this.GetString("sfzh")}
	fmt.Println(p)
	sign := p.Query()
	if !sign {
		this.Data["name"] = p.Name
		this.Data["idCard"] = p.Ids
		this.TplNames = "index.tpl"
	} else {
		this.TplNames = "sign.tpl"
	}

}
func (this *MainController) AddUser() {
	date := this.GetString("bysj")
	fmt.Println("date:" + date)
	p := &models.Person{Name: this.GetString("xm"), Ids: this.GetString("sfzh")}
	var dates string
	dates = strings.Trim(date, " ")
	fmt.Println("dates:" + dates)
	var flag bool
	flag = len(dates) > 0
	if flag {
		t, err := time.Parse("2006-01-02", date)
		if nil == err {
			p.Date = t
		} else {
			p.Date = time.Now()
			fmt.Println("日期格式不正确,但是 使用的默认时间")
		}
	} else {
		p.Date = time.Now()
		fmt.Println("日期值为空,但是 使用的默认时间")
	}
	this.Data["flag"] = p.Add()
	this.TplNames = "aa.tpl"
}
