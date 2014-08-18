// business project business.go
package business

import (
	"database/sql"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"time"
)

func OpenConnection() (*sql.DB, error) {
	return sql.Open("mysql", "root:@/greennet?charset=utf8")
}
func check(err error) {
	if err != nil {
		panic(err)
	}
}

type User struct {
	Id          int32
	User_name   string
	Login_name  string
	Effect_date time.Time
}

func (user *User) String() string {

	return string(user.Id) + " ==  " + user.Effect_date.String() + "  " + user.Login_name + "  " + user.User_name
}
func AddUser(user User) {
	mysdb, _ := OpenConnection()
	//defer mysdb.Close()
	//为什么不需要关闭，理由如下
	//理由 1：// Close closes the database, releasing any open resources.
	//func (db *DB) Close() error {
	//	db.mu.Lock()
	//	defer db.mu.Unlock()
	//	var err error
	//	for _, dc := range db.freeConn {
	//		err1 := dc.closeDBLocked()
	//		if err1 != nil {
	//			err = err1
	//		}
	//	}
	//	db.freeConn = nil
	//	db.closed = true
	//	return err
	//}
	//理由 2： Conn is a connection to a database. It is not used concurrently
	// by multiple goroutines.
	//
	// Conn is assumed to be stateful.
	//type Conn interface {
	//	// Prepare returns a prepared statement, bound to this connection.
	//	Prepare(query string) (Stmt, error)

	//	// Close invalidates and potentially stops any current
	//	// prepared statements and transactions, marking this
	//	// connection as no longer in use.
	//	//
	//	// Because the sql package maintains a free pool of
	//	// connections and only calls Close when there's a surplus of
	//	// idle connections, it shouldn't be necessary for drivers to
	//	// do their own connection caching.
	//	Close() error

	//	// Begin starts and returns a new transaction.
	//	Begin() (Tx, error)
	//}
	tx, _ := mysdb.Begin()
	stm2, _ := mysdb.Prepare("INSERT INTO user set user_name=?,login_name=? , effect_date=? ")
	defer stm2.Close()
	stm2.Exec(user.Login_name, user.User_name, user.Effect_date)
	tx.Commit()
}
func UpdateUser(user User) {
	mysdb, _ := OpenConnection()
	//defer mysdb.Close()
	tx, _ := mysdb.Begin()
	stm2, _ := mysdb.Prepare(" update  user set user_name=?,login_name=? where id=?  ")
	defer stm2.Close()
	stm2.Exec(user.Login_name, user.User_name, user.Effect_date)
	tx.Commit()
}
func DeleteUser(user User) {
	mysdb, _ := OpenConnection()
	//defer mysdb.Close()
	tx, _ := mysdb.Begin()
	stm2, _ := mysdb.Prepare(" DELETE FROM  user WHERE id =?  ")
	defer stm2.Close()
	stm2.Exec(user.Id)
	tx.Commit()
}

func FindUser(user User) {
	mysdb, _ := OpenConnection()
	//defer mysdb.Close()
	tx, _ := mysdb.Begin()
	stm2, _ := mysdb.Prepare(" DELETE FROM  user WHERE id =?  ")
	defer stm2.Close()
	stm2.Exec(user.Id)
	tx.Commit()
}

func FindAllUser() {
	mysdb, _ := OpenConnection()
	//defer mysdb.Close()
	//tx, _ := mysdb.Begin()
	row, _ := mysdb.Query(" select id,user_name,login_name ,effect_date from user ")
	defer row.Close()
	var user User
	users := []User{}
	for row.Next() {
		row.Scan(&user.Id, &user.User_name, &user.Login_name, &user.Effect_date)
		users = append(users, user)

		fmt.Println("user:=", user.String())

	}
	row.Close()
	for _, user := range users {
		fmt.Printf("%d, %s, %s\n", user.Id, user.User_name, user.Login_name)
	}
	//tx.Commit()
}
