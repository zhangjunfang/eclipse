package main

import (
	_ "beego1/routers"
	"github.com/astaxie/beego"
)

func main() {
	//beego.RunMode = ""
	beego.Run("127.0.0.1:9090")
}
