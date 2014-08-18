package models

import (
	"fmt"
	"github.com/astaxie/beego/orm"
	_ "github.com/go-sql-driver/mysql"
)

type User struct {
	Id      int      `orm:"size(60);pk"`
	Name    string   `orm:"size(60)"`
	Email   string   `orm:"size(512)"`
	Profile *Profile `orm:"rel(one)"` // OneToOne relation
}

type Profile struct {
	Id   int
	Age  int16
	Name string `orm:"size(512)"`
	User *User  `orm:"reverse(one)"` // 设置反向关系(可选)
}

func (u *User) TableName() string {
	return "auth_user"
}

// 多字段索引
func (u *User) TableIndex() [][]string {
	return [][]string{
		[]string{"Id", "Name"},
	}
}

// 多字段唯一键
func (u *User) TableUnique() [][]string {
	//[]string{"Name", "Email"},
	return [][]string{
		[]string{"Name"},
	}
}

//仅支持 MySQL
// 设置引擎为 INNODB
func (u *User) TableEngine() string {
	return "INNODB"
}
func init() {
	// 需要在init中注册定义的model
	orm.RegisterModel(new(User), new(Profile))
	orm.Debug = true
	orm.RegisterDriver("mysql", orm.DR_MySQL)
	orm.RegisterDataBase("default", "mysql", "root:@/orm?charset=utf8", 30, 30)
}
func (this *User) Add_OneToOne() {
	o := orm.NewOrm()
	o.Using("default") // 默认使用 default，你可以指定为其他数据库

	profile := new(Profile)
	profile.Age = 30

	user := new(User)
	user.Profile = profile
	user.Name = "slene"

	fmt.Println(o.Insert(profile))
	fmt.Println(o.Insert(user))
}

//手动创建表
func RawTable() {
	// 数据库别名
	name := "default"
	// drop table 后再建表
	force := true
	// 打印执行过程
	verbose := true

	// 遇到错误立即返回
	err := orm.RunSyncdb(name, force, verbose)
	if err != nil {
		fmt.Println(err)
	}
}
