// resLoadSuccessNotifyREQ project resLoadSuccessNotifyREQ.go
package resLoadSuccessNotifyREQ

import (
	"fmt"
	"database/sql"
	"mode"
	"time"
)

var res2 mode.ResLoadSuccessNotifyRES;
func  ResLoadSuccessNotifyREQ(req mode.ResLoadSuccessNotifyREQ,db *sql.DB)( res mode.ResLoadSuccessNotifyRES){
    tx,err:= db.Begin();
	check(err);
	//资源应用成功通知插入
	smt,err4:=tx.Prepare( "   INSERT INTO syn_sys_his( syn_type, code_num,version_num, syn_status, syn_date, success_date) VALUE (?, ?, ?, ?, ?, ?)   ");
	defer smt.Close();
	res2.MsgResult="0";
	res2.TID=req.TID;
	if(err4!=nil){
		res2.MsgResult="12";
		fmt.Println("====资源应用成功通知插入数据失败========");	
	}
	_,err3:=smt.Exec(0,req.AccessToken,req.ResVersion,2,req.TimeStamp,time.Now());
	if(err3!=nil){
		res2.MsgResult="12";
		fmt.Println("====资源应用成功通知插入数据失败========");	
		tx.Rollback();
	}
	tx.Commit();
	
	return res2;	
}


func check(err error){
	if(err!=nil){
		panic(err);
	}
	
}
