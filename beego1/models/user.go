package models

import (
	"fmt"
	"github.com/astaxie/beego/orm"
	_ "github.com/go-sql-driver/mysql"
	"strconv"
	"time"
)

func init() {
	orm.Debug = true
	orm.RegisterModel(new(Person))
	orm.RegisterDriver("mysql", orm.DR_MySQL)

	orm.RegisterDataBase("default", "mysql", "root:@/orm?charset=utf8", 30, 30)

}

type Person struct {
	Id             int32
	Name           string
	Sex            string
	Ids            string
	Politicsstatus string
	Nation         string
	Address        string
	Graduation     string
	Date           time.Time
	Education      string
	Specialty      string
	Unit           string
	Department     string
	Workoccupation string
	Job            string
	Professional   string
	Telephone      string
	Contactaddress string
}

func (this *Person) Add() bool {
	p := orm.NewOrm()
	p.Using("default") // 默认使用 default，你可以指定为其他数据库
	p.Begin()
	id, err := p.Insert(this)
	fmt.Println(id)
	if err == nil {
		p.Commit()
		return true
	} else {
		p.Rollback()
		return false
	}
	return true
}
func (this *Person) Query() bool {
	p := orm.NewOrm()
	p.Using("default") // 默认使用 default，你可以指定为其他数据库
	err := p.Read(this, "Name", "Ids")
	if err == orm.ErrNoRows {
		fmt.Println("查询不到")
		return false
	} else if err == orm.ErrMissPK {
		fmt.Println("找不到主键")
		return false
	} else {
		return true
	}
}
func (user *Person) String() string {
	return "id:" + strconv.FormatInt(int64(user.Id), 10) + " name:" + user.Name + " ids:" + user.Ids

}
