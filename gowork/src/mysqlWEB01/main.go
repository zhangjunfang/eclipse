// mysqlWEB01 project main.go
package main

import (
	"database/sql"
	"fmt"
	_ "github.com/Go-SQL-Driver/MySQL"
	//"time"
)

func main() {
	db, err := sql.Open("mysql", "root:@/fensydb?charset=utf8")
	checkErr(err)
	tx, _ := db.Begin()
	//插入数据
	stmt, err := db.Prepare("INSERT userinfo SET username=?,departname=?,created=?")
	checkErr(err)

	res, err := stmt.Exec("astaxie", "研发部门", "2012-12-09")
	checkErr(err)
	stmt1, err1 := db.Prepare("INSERT userinfo SET username=?,departname=?,created=?")
	checkErr(err1)
	stmt1.Exec("astaxie", "研发部门", "2012-12-09")
	id2, err2 := res.LastInsertId()
	checkErr(err2)
	fmt.Println(id2)
	stmt, err = db.Prepare("update userinfo set username=? where uid=?")
	checkErr(err)

	stmt.Exec("astaxieupdate", id2)

	tx.Commit()
	db.Close()

}

func checkErr(err error) {
	if err != nil {
		panic(err)
	}
}
