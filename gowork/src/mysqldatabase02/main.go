// mysqldatabase02 project main.go
package main

import (
	"fmt"
	_"github.com/go-sql-driver/mysql"
	"database/sql"
)

func main() {
	fmt.Println("Hello World!");
	db,err:=sql.Open("mysql","greennet:greennet@tcp(192.168.0.213:3306)/greennet?charset=utf8");
    check(err);
	db.SetMaxIdleConns(1000);
    tx,err4:= db.Begin();
    check(err4);
	//删除操作
	stm,err3:=db.Prepare("DELETE FROM  user WHERE id =?");
	defer stm.Close();
	check(err3);
	stm.Exec(8);
	//插入【添加】操作
	//stm2,err2:=db.Prepare("INSERT INTO user set user_name=?,login_name=?, password=?, create_date=?, effect_date=?, overdue_date=?, phone_num=?, mail=?, client_num=?,user_status=?, user_type=?, code_num=?, code_pwd=?,code_time=?, login_status=? ");
	stm2,err2:=db.Prepare("INSERT INTO user(  user_name,  login_name,  password,  create_date,  effect_date,  overdue_date,  phone_num,  mail,  client_num, user_status, user_type,code_num,code_pwd,code_time,login_status) VALUE( user_name=?,login_name=?, password=?, create_date=?, effect_date=?, overdue_date=?, phone_num=?, mail=?, client_num=?,user_status=?, user_type=?, code_num=?, code_pwd=?,code_time=?, login_status=? )");
	check(err2);
	defer stm2.Close();
	rs,_:=stm2.Exec("", "", "59dauq", "2013-06-06 15:27:22", "2013-06-06 15:27:22", "2013-06-21 15:27:22", "", "", "bkLxezHI1hBS7iMTPLucZdKCCokS1WHby/DnLmkxWtE=", 0, 0, "136990425401", "59dauq", 0, 0);
	id,_:=rs.LastInsertId();
	//更新操作	
	stm3,err4:=db.Prepare("update  user set user_name=?,login_name=? where id=? ")
	check(err4);
	stm3.Exec("username02","loginname02",id);
	defer stm3.Close();
	 //查询操作
	row,err5:=db.Query(" select u.id,user_name,u.login_name ,u.effect_date from user as  u join code_list as c on u.code_num=c.code_num ");
	check(err5);
	// users := []User{};
	for row.Next(){
		var id int;
		var user_name string;
		var login_name string;
		var effect_date string;
		row.Scan(&id,&user_name,&login_name,&effect_date);
		fmt.Println("id:=",id);
		fmt.Println("user_name:==",user_name);
		fmt.Println("login_name:=",login_name);
		fmt.Println("effect_date:=",effect_date);
	}
	tx.Commit();
  
}

func check(err error){
	if(err!=nil){
		panic(err);
	}
	
}