// jsonGOtoxuzhou project main.go
package main

import (
	"fmt"
	_"github.com/go-sql-driver/mysql"
	"database/sql"
	"mode"
	"heartbeat"
	"resSyncREQ"
	"resLoadSuccessNotifyREQ"
	"licenseStatusQueryREQ"
	"configSyncREQ"
	"time"
)
var db *sql.DB;
var err error;
func DBMysql()(*sql.DB){
	//db,err:=sql.Open("mysql","greennet:greennet@tcp(192.168.0.213:3306)/greennet?charset=utf8");
    //check(err);
	//db.SetMaxIdleConns(1000);
	return db;
}

func main() {
	fmt.Println("Hello World!");
	
	//res:=heartbeat.HeartbeatDAO(mode.HeartbeatREQ{"123456","","",""},db);
	//返回的Conn只能使用在一次排程中
	//go 
	var res mode.HeartbeatRES;
	res=heartbeat.HeartbeatDAO(mode.HeartbeatREQ{"123456","","",""},DBMysql());
	fmt.Println("====main= res=======",res.String());
	var res2 mode.ResSyncRES=resSyncREQ.ResSyncREQ(mode.ResSyncREQ{"KMKBAAJT-PIEIYGAW","KMKBAAJT-PIEIYGAW0001",time.Now(),"V000000001"},DBMysql());
	fmt.Println("====main= res2=======",res2.String());
	var res3 mode.ResLoadSuccessNotifyRES=resLoadSuccessNotifyREQ.ResLoadSuccessNotifyREQ(mode.ResLoadSuccessNotifyREQ{"KMKBAAJT-PIEIYGAW","KMKBAAJT-PIEIYGAW",time.Now(),"v_config_20130618153832"},DBMysql());
    fmt.Println("====main= res3=======",res3.String());
	var res4 mode.LicenseStatusQueryRES=licenseStatusQueryREQ.LicenseStatusQueryREQ(mode.LicenseStatusQueryREQ{"KMKBAAJT-PIEIYGAW","KMKBAAJT-PIEIYGAW",time.Now(),"KMKBAAJT-PIEIYGAW"},DBMysql());
    fmt.Println("====main= res4=======",res4.String());
	var res5 mode.ConfigSyncRES=configSyncREQ.ConfigSyncREQ(mode.ConfigSyncREQ{"KMKBAAJT-PIEIYGAW","KMKBAAJT-PIEIYGAW",time.Now(),"KMKBAAJT-PIEIYGAW"},DBMysql());
    fmt.Println("====main= res5=======",res5.String());
}

func init(){
	db,err=sql.Open("mysql","greennet:greennet@tcp(192.168.0.213:3306)/greennet?charset=utf8");
    check(err);
	db.SetMaxIdleConns(1000);
	fmt.Println(db);
}
func check(err error){
	if(err!=nil){
		panic(err);
	}
	
}