// licenseStatusQueryREQ project licenseStatusQueryREQ.go
package licenseStatusQueryREQ
import (
	"fmt"
	"database/sql"
	"mode"
	"time"
)

var res2 mode.LicenseStatusQueryRES;
func  LicenseStatusQueryREQ(req mode.LicenseStatusQueryREQ,db *sql.DB)( res mode.LicenseStatusQueryRES){
    tx,err:= db.Begin();
	check(err);
	//注册码状态查询
	fmt.Println("注册码状态查询 卡号",req.AccountID);
	rows,err4:=tx.Query( " SELECT  overdue_date FROM user where code_num=?   LIMIT 1 ",req.AccountID);
	defer rows.Close();
	res2.TID=req.TID;
	res2.Status="1";
	res2.MsgResult="1";
	if(err4!=nil){
		res2.MsgResult="13";
		fmt.Println("====注册码状态查询失败========");	
	}
	for rows.Next(){
		rows.Scan(&res2.Expirationdate );
		fmt.Println("=====数据库中最新到期日期=======",res2.Expirationdate);
	}
	bl:=time.Now().Before(res2.Expirationdate);
	if(bl){
		res.Status="0";
	}else{
		res.Status="1";
	}
	res2.MsgResult="0";
	tx.Commit();
	
	return res2;	
}


func check(err error){
	if(err!=nil){
		panic(err);
	}
	
}