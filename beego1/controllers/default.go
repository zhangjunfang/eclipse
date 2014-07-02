package controllers

import (
	"beego1/models"
	"fmt"
	"github.com/astaxie/beego"
	"github.com/astaxie/beego/session"
	_ "github.com/astaxie/beego/session/redis"
	"html/template"
	"net/http"
	"os"
	"path"
	"path/filepath"
	"runtime"
	"strconv"
	"strings"
	"time"
)

type MainController struct {
	beego.Controller
}

func (this *MainController) Get() {
	this.Data["xsrfdata"] = template.HTML(this.XsrfFormHtml())
	this.TplNames = "index.tpl"
}
func (this *MainController) Login() {
	no := this.StartSession().Get("no")
	fmt.Println("session 中的数据no 的值是：%v", no)
	this.Data["xsrfdata"] = template.HTML(this.XsrfFormHtml())
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
		this.Data["xsrfdata"] = template.HTML(this.XsrfFormHtml())
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
	fmt.Println("port:::::" + strconv.Itoa(this.Ctx.Input.Port()))
	this.Data["url"] = this.Ctx.Input.Site() + ":" + strconv.Itoa(this.Ctx.Input.Port()) + "/redirect"
	this.TplNames = "aa.tpl"
	//这样可以实现客户端的跳转
	//this.Ctx.Redirect(http.StatusFound, "upload")
}

//使用客户端js 实现的页面跳转
//缺陷： 只能实现本应用内的页面跳转【服务器端的转发】

func (this *MainController) Forward() {
	this.Data["xsrfdata"] = template.HTML(this.XsrfFormHtml())
	this.TplNames = "sign.tpl"
	//this.TplNames = "http://www.baidu.com/"
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
	this.TplNames = "sucess.tpl"
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
	this.TplNames = "sucess.tpl"
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
	this.TplNames = "sucess.tpl"
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
	this.TplNames = "sucess.tpl"
}
func (this *MainController) FileUpload() {

	fmt.Println("性别：", this.GetString("sex"))
	m := strings.ToLower(this.Ctx.Input.Method())
	if m == "get" {
		this.Data["xsrfdata"] = template.HTML(this.XsrfFormHtml())
		this.TplNames = "upload.tpl"
	} else {
		path := strings.Replace(GetCurrentPath(), "\\", "/", -1) + "/static/img/"
		//sign := IsDirExists(path)
		//if !sign {
		//	os.MkdirAll("/aa", 0777)
		//}
		_, fileHeader, _ := this.Ctx.Request.FormFile("imgs")
		fn := fileHeader.Filename
		//filepath.Match()
		duplicateName := filepath.Walk(path, func(path string, f os.FileInfo, err error) error {
			if f == nil {
				return err
			}
			if f.IsDir() {
				return nil
			}
			if f.Name() == fn {
				fmt.Println("Duplicate file name", f.Name())
				return err
			}
			return nil
		})
		if duplicateName != nil {
			this.Data["sign"] = "文件已经存在 ！！！"
			this.TplNames = "sucess.html"
			return
		}
		var temp = path + "/" + fn
		os.NewFile(0777, temp)
		err2 := this.SaveToFile("imgs", temp)
		if err2 != nil {
			fmt.Println("操作失败：", err2)
			this.Data["sign"] = "操作失败"
		} else {
			this.Data["sign"] = "操作成功"
		}
		this.TplNames = "sucess.tpl"
	}

}
func (this MainController) FileDown() {
	fn := "bb.jpg"
	path := strings.Replace(GetCurrentPath(), "\\", "/", -1) + "/static/img/"
	temp := path + fn
	ext := GetExt(temp)
	//fmt.Println("ext::::::::", ext)
	dn := "aa" + ext
	this.Ctx.Output.Download(temp, dn)
}

func (this *MainController) SessionTest() {
	var sess session.SessionStore = this.StartSession()
	fmt.Println("session::::::", sess.SessionID())
	no := sess.Get("no")
	if no == nil {
		fmt.Println("no:::::::: %v", no)
		sess.Set("no", 1)
	} else {
		fmt.Println("no::::2222:::: %v", no)
		sess.Set("no", (no.(int))+1)
		this.Data["no"] = (no.(int)) + 1
	}
	fmt.Println("no::::33333:::: %v", no)
	this.Data["sign"] = "操作成功"
	this.TplNames = "sucess.tpl"
}

//使用beego 内部的方法实现跳转【可以实现重定向】
func (this *MainController) HttpRedirect() {
	this.Data["sign"] = "操作成功"
	//this.
	fmt.Println("StatusFound", http.StatusFound)
	this.Ctx.Redirect(http.StatusFound, "http://www.baidu.com/")
	//this.Ctx.Output.Download()
	//http.Redirect(w, r, "/edit/"+"sucess.tpl", http.StatusFound)
}

//判断目录是否存在
func IsDirExists(path string) bool {
	fi, err := os.Stat(path)
	if err != nil {
		return os.IsExist(err)
	} else {
		return fi.IsDir()
	}
	return false
}

//判断文件是否存在
func IsFileEXists(path string) bool {
	if _, err := os.Stat(path); err == nil {
		return true
	} else {
		return false
	}
}

//获取当前所在的文件路径
func GetCurrentPath() string {
	path, err := os.Getwd()
	if err == nil {
		return path
	}
	return ""
}

//获取文件的扩展名称
func GetExt(filename string) string {
	runtime.Caller(0) //和下面代码实现同样的功能，但是符合操作系统的文件路径符
	return path.Ext(filename)
}
