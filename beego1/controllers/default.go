package controllers

import (
	"beego1/models"
	"fmt"
	"github.com/astaxie/beego"
	//"github.com/astaxie/beego/validation"
	//"log"
	//"mime/multipart"
	"os"
	//"path"
	"strconv"
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
		this.Data["msg"] = "信息不存在"
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
	p.Address = this.GetString("jg")
	p.Contactaddress = this.GetString("lxdz")
	p.Department = this.GetString("szbm")
	p.Education = this.GetString("xl")
	p.Graduation = this.GetString("byyx")
	p.Job = this.GetString("zc")
	p.Nation = this.GetString("mz")
	p.Politicsstatus = this.GetString("zzmm")
	p.Professional = this.GetString("cszy")
	p.Sex = this.GetString("xb")
	p.Specialty = this.GetString("zy")
	p.Telephone = this.GetString("lxdh")
	p.Unit = this.GetString("gzdw")
	p.Workoccupation = this.GetString("zw")
	this.Data["flag"] = p.Add()
	//	fmt.Println("port:::::" + strconv.Itoa(this.Ctx.Input.Port()))
	this.Data["url"] = this.Ctx.Input.Site() + ":" + strconv.Itoa(this.Ctx.Input.Port()) + "/redirect"
	this.TplNames = "aa.tpl"
}
func (this *MainController) Redirect() {
	this.TplNames = "sign.tpl"
}

func (this *MainController) Update() {
	p := &models.Person{Id: 167}
	p.Name = "王五"
	sign := p.UpdatePerson()
	if !sign {
		this.Data["sign"] = "操作失败"
	} else {
		this.Data["sign"] = "操作成功"
	}
	this.TplNames = "sucess.html"
}
func (this *MainController) UpdateBatch() {
	p := &models.Person{}
	p.Name = "王五"
	sign := p.UpdateBatchPerson()
	if !sign {
		this.Data["sign"] = "操作失败"
	} else {
		this.Data["sign"] = "操作成功"
	}
	this.TplNames = "sucess.html"
}
func (this *MainController) DeleteBatchPerson() {
	p := &models.Person{}
	p.Name = "王五"
	sign := p.DeleteBatchPerson()
	if !sign {
		this.Data["sign"] = "操作失败"
	} else {
		this.Data["sign"] = "操作成功"
	}
	this.TplNames = "sucess.html"
}
func (this *MainController) DeletePerson() {
	p := &models.Person{}
	p.Id = 170
	sign := p.DeletePerson()
	if !sign {
		this.Data["sign"] = "操作失败"
	} else {
		this.Data["sign"] = "操作成功"
	}
	this.TplNames = "sucess.html"
}
func (this *MainController) FileUpload() {

	m := strings.ToLower(this.Ctx.Input.Method())
	if m == "get" {
		this.TplNames = "upload.tpl"
	} else {
		//var file multipart.File
		//var header *multipart.FileHeader
		//var err error

		//file, header, err = this.GetFile("imgs")
		//defer file.Close()
		//if err != nil {
		//	fmt.Println("操作失败")
		//} else {
		//	fmt.Println("操作成功")
		//}
		fi, err4 := os.Stat("/aa")
		if err4 != nil {
			os.IsExist(err4)
			os.MkdirAll("/aa", 0777)
		} else {
			fi.IsDir()
		}
		os.NewFile(0777, "/aa/bb.jpg")
		err2 := this.SaveToFile("imgs", "/aa/bb.jpg")
		//fmt.Println(file, "============")
		//fmt.Println(header, "============")
		if err2 != nil {
			fmt.Println("err2:===", err2)
			this.Data["sign"] = "操作失败"
		} else {
			this.Data["sign"] = "操作成功"
		}
		this.TplNames = "sucess.html"
	}

}
