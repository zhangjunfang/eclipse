package main

import (
	_ "beego1/routers"
	"github.com/astaxie/beego"
)

func main() {
	beego.SessionOn = true
	beego.SessionName = "jsessionId"
	beego.SetLevel(0)
	beego.EnableXSRF = true
	beego.XSRFKEY = "多个地方个地方广泛的个地方广泛的个地方"
	beego.XSRFExpire = 3600 * 5 //5m
	beego.Run("127.0.0.1:9090")

}
