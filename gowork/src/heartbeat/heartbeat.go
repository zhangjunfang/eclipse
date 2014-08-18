// heartbeat project heartbeat.go
package heartbeat

import (
	"fmt"
	"database/sql"
	"time"
	"mode"
)
//var res mode.HeartbeatRES;
var res2 mode.HeartbeatRES;
func  HeartbeatDAO(req mode.HeartbeatREQ,db *sql.DB)( res mode.HeartbeatRES){
    tx,err:= db.Begin();
	check(err);
	//添加数据操作代码
	rows,err4:=tx.Query( " SELECT  s.sftp_ip,s.sftp_port,s.sftp_user,s.sftp_pwd FROM sftp_dates  as s  LIMIT 1 ");
	defer rows.Close();
	
	if(err4!=nil){
		res2.Result="7";
		fmt.Println("====err4========");
		
	}
	for rows.Next(){
		//var sftp_ip string;
		//var sftp_port string;
		//var sftp_user string;
		//var sftp_pwd  string;
		
	   // res.Result="0";
	
		//rows.Scan(&sftp_ip,&sftp_port,&sftp_user,&sftp_pwd );
		//res.Desc1=sftp_ip;
		//res.Desc2=sftp_port;
		//res.Desc3=sftp_user;
		//res.Desc4=sftp_pwd;
		//fmt.Println("=====res.Desc1=======",res.Desc1);
		
		rows.Scan(&res2.Desc1,&res2.Desc2,&res2.Desc3,&res2.Desc4 );
		res2.Result="0";
		fmt.Println("=====res2.Desc1=======",res2.Desc1);
		
	}
	
	
	smt,err5:=tx.Prepare(" INSERT INTO heart_beat_record(create_date,code_num) VALUE (?,?) ");
	defer smt.Close();
	if(err5!=nil){
		res2.Result="7";
		fmt.Println("======err5======");
	}
	_,err3:=smt.Exec(time.Now(),req.AccountID);
	if(err3!=nil){
		res2.Result="7";
		fmt.Println("======err3======");
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