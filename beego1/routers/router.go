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
	beego.Router("/redirect", &controllers.MainController{}, "get:Forward")
	beego.Router("/update", &controllers.MainController{}, "get,post:Update")
	beego.Router("/updateBatch", &controllers.MainController{}, "get,post:UpdateBatch")
	beego.Router("/deleteBatch", &controllers.MainController{}, "get,post:DeleteBatchPerson")
	beego.Router("/delete", &controllers.MainController{}, "get,post:DeletePerson")
	beego.Router("/upload", &controllers.MainController{}, "get,post:FileUpload")
	beego.Router("/httpRedirect", &controllers.MainController{}, "get,post:HttpRedirect")
	beego.Router("/httpDown", &controllers.MainController{}, "get,post:FileDown")

}
