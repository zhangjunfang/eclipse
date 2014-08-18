// resSyncREQ project resSyncREQ.go
package resSyncREQ
import (
	"fmt"
	"database/sql"
	"mode"
)

var res2 mode.ResSyncRES;
func  ResSyncREQ(req mode.ResSyncREQ,db *sql.DB)( res mode.ResSyncRES){
    tx,err:= db.Begin();
	check(err);
	//查询资源同步版本号
	rows,err4:=tx.Query( "   SELECT t.value FROM  type_record as t  where code_num is null and TYPE=0 LIMIT 1   ");
	defer rows.Close();
	
	if(err4!=nil){
		res2.MsgResult="13";
		fmt.Println("====资源同步查询失败========");	
	}
	res2.ResVersion=req.ResVersion;
	for rows.Next(){
		rows.Scan(&res2.ResVersion );
		fmt.Println("=====数据库中最新资源版本号=======",res2.ResVersion);
	}
	res2.Isupdate="0";
	res2.TID=req.TID;
	res2.MsgResult="0";
	//
	bs:=[]byte(res2.ResVersion);
	bs2:=[]byte(req.ResVersion);
	fmt.Println("====资源同步查询===1111====="+res2.ResVersion);	
	if((len(bs)!=len(bs2))&&(res2.ResVersion!=req.ResVersion)){
			res2.Isupdate="1";
			fmt.Println("====资源同步查询===222====="+res2.ResVersion);	
	}
	tx.Commit();
	
	return res2;	
}


func check(err error){
	if(err!=nil){
		panic(err);
	}
	
}