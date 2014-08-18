// configSyncREQ project configSyncREQ.go
package configSyncREQ
import (
	"fmt"
	"database/sql"
	"mode"
	"strconv"
)

var res2 mode.ConfigSyncRES;
func  ConfigSyncREQ(req mode.ConfigSyncREQ,db *sql.DB)( res mode.ConfigSyncRES){
    tx,err:= db.Begin();
	check(err);
	//配置同步请求
	res2.MsgResult="0";
	res2.Isupdate="0";
	//获取数据库中卡号对应最新版本
	rows,_:=tx.Query( "  SELECT value FROM  type_record  where  code_num=? and TYPE=1 LIMIT 1  ",req.AccessToken);
	defer rows.Close();
	res2.TID=req.TID;
	for rows.Next(){
		rows.Scan(&res2.ResVersion );
		fmt.Println("=====数据库中卡号对应最新版本=======",res2.ResVersion);
	}
	if(res2.ResVersion!=req.ConfigVersion||len(res2.ResVersion)!=len(req.ConfigVersion)){
		res2.Isupdate="1";
		//tx.Prepare("  INSERT INTO syn_sys_his( syn_type, code_num,version_num, syn_status, syn_date, success_date) VALUE (:syn_type, :code_num, :version_num, :syn_status, :syn_date, :success_date)  ");
	}else{
		res2.Isupdate="0";
		res2.ResVersion=req.ConfigVersion;
	}
	//获取数据库中卡号对应上网过滤条件
	rows1,_:=tx.Query( " SELECT  url_filter, pic_filter,text_filter,games_filter,filter FROM net_filter where code_num=?  LIMIT 1 ",req.AccessToken);
	defer rows1.Close();
	for rows1.Next(){
		var  url_filter uint8;
		var  pic_filter uint8;
		var  text_filter uint8;
		var  games_filter uint8;
		var  filter uint8;
		rows1.Scan(&url_filter,&pic_filter,&text_filter,&games_filter,&filter );
		res2.Filtercontrol="url_filter:"+strconv.Itoa(int(url_filter))+";"+"pic_filter:"+strconv.Itoa(int(pic_filter))+";"+"text_filter:"+strconv.Itoa(int(text_filter))+";"+"games_filter:"+strconv.Itoa(int(games_filter))+";"+"filter:"+strconv.Itoa(int(filter))+";";
	}
	//获取数据库中卡号对应每周上网时间段的过滤条件
	rows2,_:=tx.Query( " SELECT  control, monday_time,tuesday_time,wednesday_time,thursday_time,firday_time,saturday_time,sunday_time FROM  net_week_time    where code_num=?  LIMIT 1 ",req.AccessToken);
	defer rows2.Close();
	for rows2.Next(){
		var  control uint8;
		var  monday_time string;
		var  tuesday_time string;
		var  wednesday_time string;
		var  thursday_time string;
		var  firday_time string;
		var  saturday_time string;
		var  sunday_time string;
		rows2.Scan(&control,&monday_time,&tuesday_time,&wednesday_time,&thursday_time,&firday_time,&saturday_time,&sunday_time );
		if(control==0){
			res2.Timecontrol="control:"+strconv.Itoa(int(control));
		}else{
			res2.Timecontrol="control:"+strconv.Itoa(int(control))+"/"+"monday_time:"+monday_time+"/"+"tuesday_time:"+tuesday_time+"/"+"wednesday_time:"+wednesday_time+"/"+"thursday_time:"+thursday_time+"/"+"firday_time:"+firday_time+"/"+"saturday_time:"+saturday_time+"/"+"sunday_time:"+sunday_time+"/";
		}
		
		
	}
	tx.Commit();
	
	return res2;	
}


func check(err error){
	if(err!=nil){
		panic(err);
	}
	
}