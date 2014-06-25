package routers

import (
	"beego1/controllers"
	"github.com/astaxie/beego"
)

func init() {
	beego.Router("/", &controllers.MainController{}, "get:Get")
	beego.Router("/login", &controllers.MainController{}, "post:Login")
	beego.Router("/login2", &controllers.MainController{}, "post:Post")
	beego.Router("/addUser", &controllers.MainController{}, "post:AddUser")
}
