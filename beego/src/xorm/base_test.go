package xorm

import (
	"errors"
	"fmt"
	"strings"
	"testing"
	"time"
)

/*
CREATE TABLE `userinfo` (
    `id` INT(10) NULL AUTO_INCREMENT,
    `username` VARCHAR(64) NULL,
    `departname` VARCHAR(64) NULL,
    `created` DATE NULL,
    PRIMARY KEY (`uid`)
);
CREATE TABLE `userdeatail` (
    `id` INT(10) NULL,
    `intro` TEXT NULL,
    `profile` TEXT NULL,
    PRIMARY KEY (`uid`)
);
*/

type Userinfo struct {
	Uid        int64  `xorm:"id pk not null autoincr"`
	Username   string `xorm:"unique"`
	Departname string
	Alias      string `xorm:"-"`
	Created    time.Time
	Detail     Userdetail `xorm:"detail_id int(11)"`
	Height     float64
	Avatar     []byte
	IsMan      bool
}

type Userdetail struct {
	Id      int64
	Intro   string `xorm:"text"`
	Profile string `xorm:"varchar(2000)"`
}

func directCreateTable(engine *Engine, t *testing.T) {
	err := engine.DropTables(&Userinfo{}, &Userdetail{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.Sync(&Userinfo{}, &Userdetail{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.DropTables(&Userinfo{}, &Userdetail{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(&Userinfo{}, &Userdetail{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateIndexes(&Userinfo{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateIndexes(&Userdetail{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateUniques(&Userinfo{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateUniques(&Userdetail{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
}

func insert(engine *Engine, t *testing.T) {
	user := Userinfo{0, "xiaolunwen", "dev", "lunny", time.Now(),
		Userdetail{Id: 1}, 1.78, []byte{1, 2, 3}, true}
	cnt, err := engine.Insert(&user)
	fmt.Println(user.Uid)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert not returned 1")
		t.Error(err)
		panic(err)
		return
	}
	if user.Uid <= 0 {
		err = errors.New("not return id error")
		t.Error(err)
		panic(err)
	}

	user.Uid = 0
	cnt, err = engine.Insert(&user)
	if err == nil {
		err = errors.New("insert failed but no return error")
		t.Error(err)
		panic(err)
	}
	if cnt != 0 {
		err = errors.New("insert not returned 1")
		t.Error(err)
		panic(err)
		return
	}
}

func testQuery(engine *Engine, t *testing.T) {
	sql := "select * from userinfo"
	results, err := engine.Query(sql)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(results)
}

func exec(engine *Engine, t *testing.T) {
	sql := "update userinfo set username=? where id=?"
	res, err := engine.Exec(sql, "xiaolun", 1)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(res)
}

func querySameMapper(engine *Engine, t *testing.T) {
	sql := "select * from `Userinfo`"
	results, err := engine.Query(sql)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(results)
}

func execSameMapper(engine *Engine, t *testing.T) {
	sql := "update `Userinfo` set `Username`=? where (id)=?"
	res, err := engine.Exec(sql, "xiaolun", 1)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(res)
}

func insertAutoIncr(engine *Engine, t *testing.T) {
	// auto increment insert
	user := Userinfo{Username: "xiaolunwen2", Departname: "dev", Alias: "lunny", Created: time.Now(),
		Detail: Userdetail{Id: 1}, Height: 1.78, Avatar: []byte{1, 2, 3}, IsMan: true}
	cnt, err := engine.Insert(&user)
	fmt.Println(user.Uid)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert not returned 1")
		t.Error(err)
		panic(err)
		return
	}
	if user.Uid <= 0 {
		t.Error(errors.New("not return id error"))
	}
}

type BigInsert struct {
}

func insertDefault(engine *Engine, t *testing.T) {

}

func insertMulti(engine *Engine, t *testing.T) {
	//engine.InsertMany = true
	users := []Userinfo{
		{Username: "xlw", Departname: "dev", Alias: "lunny2", Created: time.Now()},
		{Username: "xlw2", Departname: "dev", Alias: "lunny3", Created: time.Now()},
		{Username: "xlw11", Departname: "dev", Alias: "lunny2", Created: time.Now()},
		{Username: "xlw22", Departname: "dev", Alias: "lunny3", Created: time.Now()},
	}
	cnt, err := engine.Insert(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != int64(len(users)) {
		err = errors.New("insert not returned 1")
		t.Error(err)
		panic(err)
		return
	}

	users2 := []*Userinfo{
		&Userinfo{Username: "1xlw", Departname: "dev", Alias: "lunny2", Created: time.Now()},
		&Userinfo{Username: "1xlw2", Departname: "dev", Alias: "lunny3", Created: time.Now()},
		&Userinfo{Username: "1xlw11", Departname: "dev", Alias: "lunny2", Created: time.Now()},
		&Userinfo{Username: "1xlw22", Departname: "dev", Alias: "lunny3", Created: time.Now()},
	}

	cnt, err = engine.Insert(&users2)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if cnt != int64(len(users2)) {
		err = errors.New(fmt.Sprintf("insert not returned %v", len(users2)))
		t.Error(err)
		panic(err)
		return
	}
}

func insertTwoTable(engine *Engine, t *testing.T) {
	userdetail := Userdetail{ /*Id: 1, */ Intro: "I'm a very beautiful women.", Profile: "sfsaf"}
	userinfo := Userinfo{Username: "xlw3", Departname: "dev", Alias: "lunny4", Created: time.Now(), Detail: userdetail}

	cnt, err := engine.Insert(&userinfo, &userdetail)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if userinfo.Uid <= 0 {
		err = errors.New("not return id error")
		t.Error(err)
		panic(err)
	}

	if userdetail.Id <= 0 {
		err = errors.New("not return id error")
		t.Error(err)
		panic(err)
	}

	if cnt != 2 {
		err = errors.New("insert not returned 2")
		t.Error(err)
		panic(err)
		return
	}
}

type Article struct {
	Id      int32  `xorm:"pk INT autoincr"`
	Name    string `xorm:"VARCHAR(45)"`
	Img     string `xorm:"VARCHAR(100)"`
	Aside   string `xorm:"VARCHAR(200)"`
	Desc    string `xorm:"VARCHAR(200)"`
	Content string `xorm:"TEXT"`
	Status  int8   `xorm:"TINYINT(4)"`
}

type Condi map[string]interface{}

func update(engine *Engine, t *testing.T) {
	// update by id
	user := Userinfo{Username: "xxx", Height: 1.2}
	cnt, err := engine.Id(1).Update(&user)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("update not returned 1")
		t.Error(err)
		panic(err)
		return
	}

	condi := Condi{"username": "zzz", "height": 0.0, "departname": ""}
	cnt, err = engine.Table(&user).Id(1).Update(&condi)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("update not returned 1")
		t.Error(err)
		panic(err)
		return
	}

	cnt, err = engine.Update(&Userinfo{Username: "yyy"}, &user)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	total, err := engine.Count(&user)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if cnt != total {
		err = errors.New("insert not returned 1")
		t.Error(err)
		panic(err)
		return
	}

	err = engine.Sync(&Article{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	defer func() {
		err = engine.DropTables(&Article{})
		if err != nil {
			t.Error(err)
			panic(err)
		}
	}()

	a := &Article{0, "1", "2", "3", "4", "5", 2}
	cnt, err = engine.Insert(a)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if cnt != 1 {
		err = errors.New(fmt.Sprintf("insert not returned 1 but %d", cnt))
		t.Error(err)
		panic(err)
	}

	if a.Id == 0 {
		err = errors.New("insert returned id is 0")
		t.Error(err)
		panic(err)
	}

	cnt, err = engine.Id(a.Id).Update(&Article{Name: "6"})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if cnt != 1 {
		err = errors.New(fmt.Sprintf("insert not returned 1 but %d", cnt))
		t.Error(err)
		panic(err)
		return
	}

	type UpdateAllCols struct {
		Id int64
		Bool bool
		String string
	}

	col1 := &UpdateAllCols{}
	err = engine.Sync(col1)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	
	_, err = engine.Insert(col1)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	col2 := &UpdateAllCols{col1.Id, true, ""}
	_, err = engine.Id(col2.Id).AllCols().Update(col2)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	col3 := &UpdateAllCols{}
	has, err := engine.Id(col2.Id).Get(col3)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if !has {
		err = errors.New(fmt.Sprintf("cannot get id %d", col2.Id))
		t.Error(err)
		panic(err)
		return
	}

	if *col2 != *col3 {
		err = errors.New(fmt.Sprintf("col2 should eq col3"))
		t.Error(err)
		panic(err)
		return
	}
}

func updateSameMapper(engine *Engine, t *testing.T) {
	// update by id
	user := Userinfo{Username: "xxx", Height: 1.2}
	cnt, err := engine.Id(1).Update(&user)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("update not returned 1")
		t.Error(err)
		panic(err)
		return
	}

	condi := Condi{"Username": "zzz", "Height": 0.0, "Departname": ""}
	cnt, err = engine.Table(&user).Id(1).Update(&condi)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if cnt != 1 {
		err = errors.New("insert not returned 1")
		t.Error(err)
		panic(err)
		return
	}

	cnt, err = engine.Update(&Userinfo{Username: "yyy"}, &user)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if cnt != 1 {
		err = errors.New("insert not returned 1")
		t.Error(err)
		panic(err)
		return
	}
}

func testDelete(engine *Engine, t *testing.T) {
	user := Userinfo{Uid: 1}
	cnt, err := engine.Delete(&user)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("delete not returned 1")
		t.Error(err)
		panic(err)
		return
	}

	user.Uid = 0
	has, err := engine.Id(3).Get(&user)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if has {
		//var tt time.Time
		//user.Created = tt
		cnt, err := engine.UseBool().Delete(&user)
		if err != nil {
			t.Error(err)
			panic(err)
		}
		if cnt != 1 {
			t.Error(errors.New("delete failed"))
			panic(err)
		}
	}
}

type NoIdUser struct {
	User   string `xorm:"unique"`
	Remain int64
	Total  int64
}

func get(engine *Engine, t *testing.T) {
	user := Userinfo{Uid: 2}

	has, err := engine.Get(&user)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if has {
		fmt.Println(user)
	} else {
		fmt.Println("no record id is 2")
	}

	err = engine.Sync(&NoIdUser{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	_, err = engine.Where("`user` = ?", "xlw").Delete(&NoIdUser{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	cnt, err := engine.Insert(&NoIdUser{"xlw", 20, 100})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if cnt != 1 {
		err = errors.New("insert not returned 1")
		t.Error(err)
		panic(err)
	}

	noIdUser := new(NoIdUser)
	has, err = engine.Where("`user` = ?", "xlw").Get(noIdUser)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if !has {
		err = errors.New("get not returned 1")
		t.Error(err)
		panic(err)
	}
	fmt.Println(noIdUser)
}

func cascadeGet(engine *Engine, t *testing.T) {
	user := Userinfo{Uid: 11}

	has, err := engine.Get(&user)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if has {
		fmt.Println(user)
	} else {
		fmt.Println("no record id is 2")
	}
}

func find(engine *Engine, t *testing.T) {
	users := make([]Userinfo, 0)

	err := engine.Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	for _, user := range users {
		fmt.Println(user)
	}

	users2 := make([]Userinfo, 0)
	err = engine.Sql("select * from userinfo").Find(&users2)
	if err != nil {
		t.Error(err)
		panic(err)
	}
}

func find2(engine *Engine, t *testing.T) {
	users := make([]*Userinfo, 0)

	err := engine.Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	for _, user := range users {
		fmt.Println(user)
	}
}

func findMap(engine *Engine, t *testing.T) {
	users := make(map[int64]Userinfo)

	err := engine.Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	for _, user := range users {
		fmt.Println(user)
	}
}

func findMap2(engine *Engine, t *testing.T) {
	users := make(map[int64]*Userinfo)

	err := engine.Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	for id, user := range users {
		fmt.Println(id, user)
	}
}

func count(engine *Engine, t *testing.T) {
	user := Userinfo{Departname: "dev"}
	total, err := engine.Count(&user)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Printf("Total %d records!!!\n", total)
}

func where(engine *Engine, t *testing.T) {
	users := make([]Userinfo, 0)
	err := engine.Where("(id) > ?", 2).Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(users)

	err = engine.Where("(id) > ?", 2).And("(id) < ?", 10).Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(users)
}

func in(engine *Engine, t *testing.T) {
	users := make([]Userinfo, 0)
	err := engine.In("(id)", 7, 8, 9).Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(users)
	if len(users) != 3 {
		err = errors.New("in uses should be 7,8,9 total 3")
		t.Error(err)
		panic(err)
	}

	for _, user := range users {
		if user.Uid != 7 && user.Uid != 8 && user.Uid != 9 {
			err = errors.New("in uses should be 7,8,9 total 3")
			t.Error(err)
			panic(err)
		}
	}

	users = make([]Userinfo, 0)
	ids := []interface{}{7, 8, 9}
	err = engine.Where("departname = ?", "dev").In("(id)", ids...).Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(users)

	if len(users) != 3 {
		err = errors.New("in uses should be 7,8,9 total 3")
		t.Error(err)
		panic(err)
	}

	for _, user := range users {
		if user.Uid != 7 && user.Uid != 8 && user.Uid != 9 {
			err = errors.New("in uses should be 7,8,9 total 3")
			t.Error(err)
			panic(err)
		}
	}

	err = engine.In("(id)", 1).In("(id)", 2).In("departname", "dev").Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(users)

	cnt, err := engine.In("(id)", 4).Update(&Userinfo{Departname: "dev-"})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("update records not 1")
		t.Error(err)
		panic(err)
	}

	user := new(Userinfo)
	has, err := engine.Id(4).Get(user)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if !has {
		err = errors.New("get record not 1")
		t.Error(err)
		panic(err)
	}
	if user.Departname != "dev-" {
		err = errors.New("update not success")
		t.Error(err)
		panic(err)
	}

	cnt, err = engine.In("(id)", 4).Update(&Userinfo{Departname: "dev"})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("update records not 1")
		t.Error(err)
		panic(err)
	}

	cnt, err = engine.In("(id)", 5).Delete(&Userinfo{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("deleted records not 1")
		t.Error(err)
		panic(err)
	}
}

func limit(engine *Engine, t *testing.T) {
	users := make([]Userinfo, 0)
	err := engine.Limit(2, 1).Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(users)
}

func order(engine *Engine, t *testing.T) {
	users := make([]Userinfo, 0)
	err := engine.OrderBy("id desc").Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(users)

	users2 := make([]Userinfo, 0)
	err = engine.Asc("id", "username").Desc("height").Find(&users2)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(users2)
}

func join(engine *Engine, t *testing.T) {
	users := make([]Userinfo, 0)
	err := engine.Join("LEFT", "userdetail", "userinfo.id=userdetail.id").Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
}

func having(engine *Engine, t *testing.T) {
	users := make([]Userinfo, 0)
	err := engine.GroupBy("username").Having("username='xlw'").Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(users)
}

func orderSameMapper(engine *Engine, t *testing.T) {
	users := make([]Userinfo, 0)
	err := engine.OrderBy("(id) desc").Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(users)

	users2 := make([]Userinfo, 0)
	err = engine.Asc("(id)", "Username").Desc("Height").Find(&users2)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(users2)
}

func joinSameMapper(engine *Engine, t *testing.T) {
	users := make([]Userinfo, 0)
	err := engine.Join("LEFT", `"Userdetail"`, `"Userinfo"."id"="Userdetail"."Id"`).Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
}

func havingSameMapper(engine *Engine, t *testing.T) {
	users := make([]Userinfo, 0)
	err := engine.GroupBy("Username").Having(`"Username"='xlw'`).Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(users)
}

func transaction(engine *Engine, t *testing.T) {
	counter := func() {
		total, err := engine.Count(&Userinfo{})
		if err != nil {
			t.Error(err)
		}
		fmt.Printf("----now total %v records\n", total)
	}

	counter()
	defer counter()
	session := engine.NewSession()
	defer session.Close()

	err := session.Begin()
	if err != nil {
		t.Error(err)
		panic(err)
	}
	//session.IsAutoRollback = false
	user1 := Userinfo{Username: "xiaoxiao", Departname: "dev", Alias: "lunny", Created: time.Now()}
	_, err = session.Insert(&user1)
	if err != nil {
		session.Rollback()
		t.Error(err)
		panic(err)
	}

	user2 := Userinfo{Username: "yyy"}
	_, err = session.Where("(id) = ?", 0).Update(&user2)
	if err != nil {
		session.Rollback()
		fmt.Println(err)
		//t.Error(err)
		return
	}

	_, err = session.Delete(&user2)
	if err != nil {
		session.Rollback()
		t.Error(err)
		panic(err)
	}

	err = session.Commit()
	if err != nil {
		t.Error(err)
		panic(err)
	}
	// panic(err) !nashtsai! should remove this
}

func combineTransaction(engine *Engine, t *testing.T) {
	counter := func() {
		total, err := engine.Count(&Userinfo{})
		if err != nil {
			t.Error(err)
		}
		fmt.Printf("----now total %v records\n", total)
	}

	counter()
	defer counter()
	session := engine.NewSession()
	defer session.Close()

	err := session.Begin()
	if err != nil {
		t.Error(err)
		panic(err)
	}

	user1 := Userinfo{Username: "xiaoxiao2", Departname: "dev", Alias: "lunny", Created: time.Now()}
	_, err = session.Insert(&user1)
	if err != nil {
		session.Rollback()
		t.Error(err)
		panic(err)
	}
	user2 := Userinfo{Username: "zzz"}
	_, err = session.Where("id = ?", 0).Update(&user2)
	if err != nil {
		session.Rollback()
		t.Error(err)
		panic(err)
	}

	_, err = session.Exec("delete from userinfo where username = ?", user2.Username)
	if err != nil {
		session.Rollback()
		t.Error(err)
		panic(err)
	}

	err = session.Commit()
	if err != nil {
		t.Error(err)
		panic(err)
	}
}

func combineTransactionSameMapper(engine *Engine, t *testing.T) {
	counter := func() {
		total, err := engine.Count(&Userinfo{})
		if err != nil {
			t.Error(err)
		}
		fmt.Printf("----now total %v records\n", total)
	}

	counter()
	defer counter()
	session := engine.NewSession()
	defer session.Close()

	err := session.Begin()
	if err != nil {
		t.Error(err)
		panic(err)
	}
	//session.IsAutoRollback = false
	user1 := Userinfo{Username: "xiaoxiao2", Departname: "dev", Alias: "lunny", Created: time.Now()}
	_, err = session.Insert(&user1)
	if err != nil {
		session.Rollback()
		t.Error(err)
		panic(err)
	}
	user2 := Userinfo{Username: "zzz"}
	_, err = session.Where("(id) = ?", 0).Update(&user2)
	if err != nil {
		session.Rollback()
		t.Error(err)
		panic(err)
	}

	_, err = session.Exec("delete from `Userinfo` where `Username` = ?", user2.Username)
	if err != nil {
		session.Rollback()
		t.Error(err)
		panic(err)
	}

	err = session.Commit()
	if err != nil {
		t.Error(err)
		panic(err)
	}
}

func table(engine *Engine, t *testing.T) {
	err := engine.DropTables("user_user")
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.Table("user_user").CreateTable(&Userinfo{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
}

func createMultiTables(engine *Engine, t *testing.T) {
	session := engine.NewSession()
	defer session.Close()

	user := &Userinfo{}
	err := session.Begin()
	if err != nil {
		t.Error(err)
		panic(err)
	}
	for i := 0; i < 10; i++ {
		tableName := fmt.Sprintf("user_%v", i)

		err = session.DropTable(tableName)
		if err != nil {
			session.Rollback()
			t.Error(err)
			panic(err)
		}

		err = session.Table(tableName).CreateTable(user)
		if err != nil {
			session.Rollback()
			t.Error(err)
			panic(err)
		}
	}
	err = session.Commit()
	if err != nil {
		t.Error(err)
		panic(err)
	}
}

func tableOp(engine *Engine, t *testing.T) {
	user := Userinfo{Username: "tablexiao", Departname: "dev", Alias: "lunny", Created: time.Now()}
	tableName := fmt.Sprintf("user_%v", len(user.Username))
	cnt, err := engine.Table(tableName).Insert(&user)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert not returned 1")
		t.Error(err)
		panic(err)
		return
	}

	has, err := engine.Table(tableName).Get(&Userinfo{Username: "tablexiao"})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if !has {
		err = errors.New("Get has return false")
		t.Error(err)
		panic(err)
		return
	}

	users := make([]Userinfo, 0)
	err = engine.Table(tableName).Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	id := user.Uid
	cnt, err = engine.Table(tableName).Id(id).Update(&Userinfo{Username: "tableda"})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	_, err = engine.Table(tableName).Id(id).Delete(&Userinfo{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
}

func testCharst(engine *Engine, t *testing.T) {
	err := engine.DropTables("user_charset")
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.Charset("utf8").Table("user_charset").CreateTable(&Userinfo{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
}

func testStoreEngine(engine *Engine, t *testing.T) {
	err := engine.DropTables("user_store_engine")
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.StoreEngine("InnoDB").Table("user_store_engine").CreateTable(&Userinfo{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
}

type tempUser struct {
	Id       int64
	Username string
}

func testCols(engine *Engine, t *testing.T) {
	users := []Userinfo{}
	err := engine.Cols("id, username").Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	fmt.Println(users)

	tmpUsers := []tempUser{}
	err = engine.NoCache().Table("userinfo").Cols("id, username").Find(&tmpUsers)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(tmpUsers)

	user := &Userinfo{Uid: 1, Alias: "", Height: 0}
	affected, err := engine.Cols("departname, height").Id(1).Update(user)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println("===================", user, affected)
}

func testColsSameMapper(engine *Engine, t *testing.T) {
	users := []Userinfo{}
	err := engine.Cols("(id), Username").Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	fmt.Println(users)

	tmpUsers := []tempUser{}
	err = engine.Table("Userinfo").Cols("(id), Username").Find(&tmpUsers)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(tmpUsers)

	user := &Userinfo{Uid: 1, Alias: "", Height: 0}
	affected, err := engine.Cols("Departname, Height").Update(user)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println("===================", user, affected)
}

type tempUser2 struct {
	tempUser   `xorm:"extends"`
	Departname string
}

func testExtends(engine *Engine, t *testing.T) {
	err := engine.DropTables(&tempUser2{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(&tempUser2{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	tu := &tempUser2{tempUser{0, "extends"}, "dev depart"}
	_, err = engine.Insert(tu)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	tu2 := &tempUser2{}
	_, err = engine.Get(tu2)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	tu3 := &tempUser2{tempUser{0, "extends update"}, ""}
	_, err = engine.Id(tu2.Id).Update(tu3)
	if err != nil {
		t.Error(err)
		panic(err)
	}
}

type allCols struct {
	Bit       int   `xorm:"BIT"`
	TinyInt   int8  `xorm:"TINYINT"`
	SmallInt  int16 `xorm:"SMALLINT"`
	MediumInt int32 `xorm:"MEDIUMINT"`
	Int       int   `xorm:"INT"`
	Integer   int   `xorm:"INTEGER"`
	BigInt    int64 `xorm:"BIGINT"`

	Char       string `xorm:"CHAR(12)"`
	Varchar    string `xorm:"VARCHAR(54)"`
	TinyText   string `xorm:"TINYTEXT"`
	Text       string `xorm:"TEXT"`
	MediumText string `xorm:"MEDIUMTEXT"`
	LongText   string `xorm:"LONGTEXT"`
	Binary     []byte `xorm:"BINARY(23)"`
	VarBinary  []byte `xorm:"VARBINARY(12)"`

	Date      time.Time `xorm:"DATE"`
	DateTime  time.Time `xorm:"DATETIME"`
	Time      time.Time `xorm:"TIME"`
	TimeStamp time.Time `xorm:"TIMESTAMP"`

	Decimal float64 `xorm:"DECIMAL"`
	Numeric float64 `xorm:"NUMERIC"`

	Real   float32 `xorm:"REAL"`
	Float  float32 `xorm:"FLOAT"`
	Double float64 `xorm:"DOUBLE"`

	TinyBlob   []byte `xorm:"TINYBLOB"`
	Blob       []byte `xorm:"BLOB"`
	MediumBlob []byte `xorm:"MEDIUMBLOB"`
	LongBlob   []byte `xorm:"LONGBLOB"`
	Bytea      []byte `xorm:"BYTEA"`

	Bool bool `xorm:"BOOL"`

	Serial int `xorm:"SERIAL"`
	//BigSerial int64 `xorm:"BIGSERIAL"`
}

func testColTypes(engine *Engine, t *testing.T) {
	err := engine.DropTables(&allCols{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(&allCols{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	ac := &allCols{
		1,
		4,
		8,
		16,
		32,
		64,
		128,

		"123",
		"fafdafa",
		"fafafafdsafdsafdaf",
		"fdsafafdsafdsaf",
		"fafdsafdsafdsfadasfsfafd",
		"fadfdsafdsafasfdasfds",
		[]byte("fdafsafdasfdsafsa"),
		[]byte("fdsafsdafs"),

		time.Now(),
		time.Now(),
		time.Now(),
		time.Now(),

		1.34,
		2.44302346,

		1.3344,
		2.59693523,
		3.2342523543,

		[]byte("fafdasf"),
		[]byte("fafdfdsafdsafasf"),
		[]byte("faffadsfdsdasf"),
		[]byte("faffdasfdsadasf"),
		[]byte("fafasdfsadffdasf"),

		true,

		0,
		//21,
	}

	cnt, err := engine.Insert(ac)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert return not 1")
		t.Error(err)
		panic(err)
	}
	newAc := &allCols{}
	has, err := engine.Get(newAc)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if !has {
		err = errors.New("error no ideas")
		t.Error(err)
		panic(err)
	}

	// don't use this type as query condition
	newAc.Real = 0
	newAc.Float = 0
	newAc.Double = 0
	newAc.LongText = ""
	newAc.TinyText = ""
	newAc.MediumText = ""
	newAc.Text = ""
	cnt, err = engine.Delete(newAc)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New(fmt.Sprintf("delete error, deleted counts is %v", cnt))
		t.Error(err)
		panic(err)
	}
}

type MyInt int
type MyUInt uint
type MyFloat float64
type MyString string

/*func (s *MyString) FromDB(data []byte) error {
    reflect.
    s MyString(data)
    return nil
}

func (s *MyString) ToDB() ([]byte, error) {
    return []byte(string(*s)), nil
}*/

type MyStruct struct {
	Type      MyInt
	U         MyUInt
	F         MyFloat
	S         MyString
	IA        []MyInt
	UA        []MyUInt
	FA        []MyFloat
	SA        []MyString
	NameArray []string
	Name      string
	UIA       []uint
	UIA8      []uint8
	UIA16     []uint16
	UIA32     []uint32
	UIA64     []uint64
	UI        uint
	//C64       complex64
	MSS map[string]string
}

func testCustomType(engine *Engine, t *testing.T) {
	err := engine.DropTables(&MyStruct{})
	if err != nil {
		t.Error(err)
		panic(err)
		return
	}

	err = engine.CreateTables(&MyStruct{})
	i := MyStruct{Name: "Test", Type: MyInt(1)}
	i.U = 23
	i.F = 1.34
	i.S = "fafdsafdsaf"
	i.UI = 2
	i.IA = []MyInt{1, 3, 5}
	i.UIA = []uint{1, 3}
	i.UIA16 = []uint16{2}
	i.UIA32 = []uint32{4, 5}
	i.UIA64 = []uint64{6, 7, 9}
	i.UIA8 = []uint8{1, 2, 3, 4}
	i.NameArray = []string{"ssss", "fsdf", "lllll, ss"}
	i.MSS = map[string]string{"s": "sfds,ss", "x": "lfjljsl"}
	cnt, err := engine.Insert(&i)
	if err != nil {
		t.Error(err)
		panic(err)
		return
	}
	if cnt != 1 {
		err = errors.New("insert not returned 1")
		t.Error(err)
		panic(err)
		return
	}

	fmt.Println(i)
	i.NameArray = []string{}
	i.MSS = map[string]string{}
	i.F = 0
	has, err := engine.Get(&i)
	if err != nil {
		t.Error(err)
		panic(err)
	} else if !has {
		t.Error(errors.New("should get one record"))
		panic(err)
	}

	ss := []MyStruct{}
	err = engine.Find(&ss)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(ss)

	sss := MyStruct{}
	has, err = engine.Get(&sss)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(sss)

	if has {
		sss.NameArray = []string{}
		sss.MSS = map[string]string{}
		cnt, err := engine.Delete(&sss)
		if err != nil {
			t.Error(err)
			panic(err)
		}
		if cnt != 1 {
			t.Error(errors.New("delete error"))
			panic(err)
		}
	}
}

type UserCU struct {
	Id      int64
	Name    string
	Created time.Time `xorm:"created"`
	Updated time.Time `xorm:"updated"`
}

func testCreatedAndUpdated(engine *Engine, t *testing.T) {
	u := new(UserCU)
	err := engine.DropTables(u)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(u)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	u.Name = "sss"
	cnt, err := engine.Insert(u)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert not returned 1")
		t.Error(err)
		panic(err)
		return
	}

	u.Name = "xxx"
	cnt, err = engine.Id(u.Id).Update(u)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("update not returned 1")
		t.Error(err)
		panic(err)
		return
	}

	u.Id = 0
	u.Created = time.Now().Add(-time.Hour * 24 * 365)
	u.Updated = u.Created
	fmt.Println(u)
	cnt, err = engine.NoAutoTime().Insert(u)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert not returned 1")
		t.Error(err)
		panic(err)
		return
	}
}

type IndexOrUnique struct {
	Id        int64
	Index     int `xorm:"index"`
	Unique    int `xorm:"unique"`
	Group1    int `xorm:"index(ttt)"`
	Group2    int `xorm:"index(ttt)"`
	UniGroup1 int `xorm:"unique(lll)"`
	UniGroup2 int `xorm:"unique(lll)"`
}

func testIndexAndUnique(engine *Engine, t *testing.T) {
	err := engine.CreateTables(&IndexOrUnique{})
	if err != nil {
		t.Error(err)
		//panic(err)
	}

	err = engine.DropTables(&IndexOrUnique{})
	if err != nil {
		t.Error(err)
		//panic(err)
	}

	err = engine.CreateTables(&IndexOrUnique{})
	if err != nil {
		t.Error(err)
		//panic(err)
	}

	err = engine.CreateIndexes(&IndexOrUnique{})
	if err != nil {
		t.Error(err)
		//panic(err)
	}

	err = engine.CreateUniques(&IndexOrUnique{})
	if err != nil {
		t.Error(err)
		//panic(err)
	}
}

type IntId struct {
	Id   int `xorm:"pk autoincr"`
	Name string
}

type Int32Id struct {
	Id   int32 `xorm:"pk autoincr"`
	Name string
}

type UintId struct {
	Id   uint `xorm:"pk autoincr"`
	Name string
}

type Uint32Id struct {
	Id   uint32 `xorm:"pk autoincr"`
	Name string
}

type Uint64Id struct {
	Id   uint64 `xorm:"pk autoincr"`
	Name string
}

type StringPK struct {
	Id   string `xorm:"pk notnull"`
	Name string
}

func testIntId(engine *Engine, t *testing.T) {
	err := engine.DropTables(&IntId{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(&IntId{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	cnt, err := engine.Insert(&IntId{Name: "test"})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert count should be one")
		t.Error(err)
		panic(err)
	}

	bean := new(IntId)
	has, err := engine.Get(bean)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if !has {
		err = errors.New("get count should be one")
		t.Error(err)
		panic(err)
	}

	beans := make([]IntId, 0)
	err = engine.Find(&beans)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if len(beans) != 1 {
		err = errors.New("get count should be one")
		t.Error(err)
		panic(err)
	}

	cnt, err = engine.Id(bean.Id).Delete(&IntId{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert count should be one")
		t.Error(err)
		panic(err)
	}
}

func testInt32Id(engine *Engine, t *testing.T) {
	err := engine.DropTables(&Int32Id{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(&Int32Id{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	cnt, err := engine.Insert(&Int32Id{Name: "test"})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if cnt != 1 {
		err = errors.New("insert count should be one")
		t.Error(err)
		panic(err)
	}

	bean := new(Int32Id)
	has, err := engine.Get(bean)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if !has {
		err = errors.New("get count should be one")
		t.Error(err)
		panic(err)
	}

	beans := make([]Int32Id, 0)
	err = engine.Find(&beans)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if len(beans) != 1 {
		err = errors.New("get count should be one")
		t.Error(err)
		panic(err)
	}

	cnt, err = engine.Id(bean.Id).Delete(&Int32Id{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert count should be one")
		t.Error(err)
		panic(err)
	}
}

func testUintId(engine *Engine, t *testing.T) {
	err := engine.DropTables(&UintId{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(&UintId{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	cnt, err := engine.Insert(&UintId{Name: "test"})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert count should be one")
		t.Error(err)
		panic(err)
	}

	bean := new(UintId)
	has, err := engine.Get(bean)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if !has {
		err = errors.New("get count should be one")
		t.Error(err)
		panic(err)
	}

	beans := make([]UintId, 0)
	err = engine.Find(&beans)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if len(beans) != 1 {
		err = errors.New("get count should be one")
		t.Error(err)
		panic(err)
	}

	cnt, err = engine.Id(bean.Id).Delete(&UintId{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert count should be one")
		t.Error(err)
		panic(err)
	}
}

func testUint32Id(engine *Engine, t *testing.T) {
	err := engine.DropTables(&Uint32Id{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(&Uint32Id{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	cnt, err := engine.Insert(&Uint32Id{Name: "test"})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if cnt != 1 {
		err = errors.New("insert count should be one")
		t.Error(err)
		panic(err)
	}

	bean := new(Uint32Id)
	has, err := engine.Get(bean)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if !has {
		err = errors.New("get count should be one")
		t.Error(err)
		panic(err)
	}

	beans := make([]Uint32Id, 0)
	err = engine.Find(&beans)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if len(beans) != 1 {
		err = errors.New("get count should be one")
		t.Error(err)
		panic(err)
	}

	cnt, err = engine.Id(bean.Id).Delete(&Uint32Id{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert count should be one")
		t.Error(err)
		panic(err)
	}
}

func testUint64Id(engine *Engine, t *testing.T) {
	err := engine.DropTables(&Uint64Id{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(&Uint64Id{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	cnt, err := engine.Insert(&Uint64Id{Name: "test"})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if cnt != 1 {
		err = errors.New("insert count should be one")
		t.Error(err)
		panic(err)
	}

	bean := new(Uint64Id)
	has, err := engine.Get(bean)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if !has {
		err = errors.New("get count should be one")
		t.Error(err)
		panic(err)
	}

	beans := make([]Uint64Id, 0)
	err = engine.Find(&beans)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if len(beans) != 1 {
		err = errors.New("get count should be one")
		t.Error(err)
		panic(err)
	}

	cnt, err = engine.Id(bean.Id).Delete(&Uint64Id{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert count should be one")
		t.Error(err)
		panic(err)
	}
}

func testStringPK(engine *Engine, t *testing.T) {
	err := engine.DropTables(&StringPK{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(&StringPK{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	cnt, err := engine.Insert(&StringPK{Name: "test"})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if cnt != 1 {
		err = errors.New("insert count should be one")
		t.Error(err)
		panic(err)
	}

	bean := new(StringPK)
	has, err := engine.Get(bean)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if !has {
		err = errors.New("get count should be one")
		t.Error(err)
		panic(err)
	}

	beans := make([]StringPK, 0)
	err = engine.Find(&beans)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if len(beans) != 1 {
		err = errors.New("get count should be one")
		t.Error(err)
		panic(err)
	}

	cnt, err = engine.Id(bean.Id).Delete(&StringPK{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert count should be one")
		t.Error(err)
		panic(err)
	}
}

func testMetaInfo(engine *Engine, t *testing.T) {
	tables, err := engine.DBMetas()
	if err != nil {
		t.Error(err)
		panic(err)
	}

	for _, table := range tables {
		fmt.Println(table.Name)
		for _, col := range table.Columns {
			fmt.Println(col.String(engine.dialect))
		}

		for _, index := range table.Indexes {
			fmt.Println(index.Name, index.Type, strings.Join(index.Cols, ","))
		}
	}
}

func testIterate(engine *Engine, t *testing.T) {
	err := engine.Omit("is_man").Iterate(new(Userinfo), func(idx int, bean interface{}) error {
		user := bean.(*Userinfo)
		fmt.Println(idx, "--", user)
		return nil
	})

	if err != nil {
		t.Error(err)
		panic(err)
	}
}

func testRows(engine *Engine, t *testing.T) {
	rows, err := engine.Omit("is_man").Rows(new(Userinfo))
	if err != nil {
		t.Error(err)
		panic(err)
	}
	defer rows.Close()

	idx := 0
	user := new(Userinfo)
	for rows.Next() {
		err = rows.Scan(user)
		if err != nil {
			t.Error(err)
			panic(err)
		}
		fmt.Println(idx, "--", user)
		idx++
	}
}

type StrangeName struct {
	Id_t int64 `xorm:"pk autoincr"`
	Name string
}

func testStrangeName(engine *Engine, t *testing.T) {
	err := engine.DropTables(new(StrangeName))
	if err != nil {
		t.Error(err)
	}

	err = engine.CreateTables(new(StrangeName))
	if err != nil {
		t.Error(err)
	}

	_, err = engine.Insert(&StrangeName{Name: "sfsfdsfds"})
	if err != nil {
		t.Error(err)
	}

	beans := make([]StrangeName, 0)
	err = engine.Find(&beans)
	if err != nil {
		t.Error(err)
	}
}

type VersionS struct {
	Id   int64
	Name string
	Ver  int `xorm:"version"`
}

func testVersion(engine *Engine, t *testing.T) {
	err := engine.DropTables(new(VersionS))
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(new(VersionS))
	if err != nil {
		t.Error(err)
		panic(err)
	}

	ver := &VersionS{Name: "sfsfdsfds"}
	_, err = engine.Insert(ver)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(ver)
	if ver.Ver != 1 {
		err = errors.New("insert error")
		t.Error(err)
		panic(err)
	}

	newVer := new(VersionS)
	has, err := engine.Id(ver.Id).Get(newVer)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if !has {
		t.Error(errors.New(fmt.Sprintf("no version id is %v", ver.Id)))
		panic(err)
	}
	fmt.Println(newVer)
	if newVer.Ver != 1 {
		err = errors.New("insert error")
		t.Error(err)
		panic(err)
	}

	newVer.Name = "-------"
	_, err = engine.Id(ver.Id).Update(newVer)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	newVer = new(VersionS)
	has, err = engine.Id(ver.Id).Get(newVer)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println(newVer)
	if newVer.Ver != 2 {
		err = errors.New("insert error")
		t.Error(err)
		panic(err)
	}

	/*
	   newVer.Name = "-------"
	   _, err = engine.Id(ver.Id).Update(newVer)
	   if err != nil {
	       t.Error(err)
	       return
	   }*/
}

func testDistinct(engine *Engine, t *testing.T) {
	users := make([]Userinfo, 0)
	err := engine.Distinct("departname").Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if len(users) != 1 {
		t.Error(err)
		panic(errors.New("should be one record"))
	}

	fmt.Println(users)

	type Depart struct {
		Departname string
	}

	users2 := make([]Depart, 0)
	err = engine.Distinct("departname").Table(new(Userinfo)).Find(&users2)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if len(users2) != 1 {
		t.Error(err)
		panic(errors.New("should be one record"))
	}
	fmt.Println(users2)
}

func testUseBool(engine *Engine, t *testing.T) {
	cnt1, err := engine.Count(&Userinfo{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	users := make([]Userinfo, 0)
	err = engine.Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	var fNumber int64
	for _, u := range users {
		if u.IsMan == false {
			fNumber += 1
		}
	}

	cnt2, err := engine.UseBool().Update(&Userinfo{IsMan: true})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if fNumber != cnt2 {
		fmt.Println("cnt1", cnt1, "fNumber", fNumber, "cnt2", cnt2)
		/*err = errors.New("Updated number is not corrected.")
		  t.Error(err)
		  panic(err)*/
	}

	_, err = engine.Update(&Userinfo{IsMan: true})
	if err == nil {
		err = errors.New("error condition")
		t.Error(err)
		panic(err)
	}
}

func testBool(engine *Engine, t *testing.T) {
	_, err := engine.UseBool().Update(&Userinfo{IsMan: true})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	users := make([]Userinfo, 0)
	err = engine.Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	for _, user := range users {
		if !user.IsMan {
			err = errors.New("update bool or find bool error")
			t.Error(err)
			panic(err)
		}
	}

	_, err = engine.UseBool().Update(&Userinfo{IsMan: false})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	users = make([]Userinfo, 0)
	err = engine.Find(&users)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	for _, user := range users {
		if user.IsMan {
			err = errors.New("update bool or find bool error")
			t.Error(err)
			panic(err)
		}
	}
}

type TTime struct {
	Id int64
	T  time.Time
	Tz time.Time `xorm:"timestampz"`
}

func testTime(engine *Engine, t *testing.T) {
	err := engine.Sync(&TTime{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	tt := &TTime{}
	_, err = engine.Insert(tt)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	tt2 := &TTime{Id: tt.Id}
	has, err := engine.Get(tt2)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if !has {
		err = errors.New("no record error")
		t.Error(err)
		panic(err)
	}

	tt3 := &TTime{T: time.Now(), Tz: time.Now()}
	_, err = engine.Insert(tt3)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	tt4s := make([]TTime, 0)
	err = engine.Find(&tt4s)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	fmt.Println("=======\n", tt4s, "=======\n")
}

func testPrefixTableName(engine *Engine, t *testing.T) {
	tempEngine, err := NewEngine(engine.DriverName, engine.DataSourceName)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	tempEngine.ShowSQL = true
	mapper := NewPrefixMapper(SnakeMapper{}, "xlw_")
	//tempEngine.SetMapper(mapper)
	tempEngine.SetTableMapper(mapper)
	exist, err := tempEngine.IsTableExist(&Userinfo{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if exist {
		err = tempEngine.DropTables(&Userinfo{})
		if err != nil {
			t.Error(err)
			panic(err)
		}
	}
	err = tempEngine.CreateTables(&Userinfo{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
}

type CreatedUpdated struct {
	Id       int64
	Name     string
	Value    float64   `xorm:"numeric"`
	Created  time.Time `xorm:"created"`
	Created2 time.Time `xorm:"created"`
	Updated  time.Time `xorm:"updated"`
}

func testCreatedUpdated(engine *Engine, t *testing.T) {
	err := engine.Sync(&CreatedUpdated{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	c := &CreatedUpdated{Name: "test"}
	_, err = engine.Insert(c)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	c2 := new(CreatedUpdated)
	has, err := engine.Id(c.Id).Get(c2)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if !has {
		panic(errors.New("no id"))
	}

	c2.Value -= 1
	_, err = engine.Id(c2.Id).Update(c2)
	if err != nil {
		t.Error(err)
		panic(err)
	}
}

type ProcessorsStruct struct {
	Id int64

	B4InsertFlag      int
	AfterInsertedFlag int
	B4UpdateFlag      int
	AfterUpdatedFlag  int
	B4DeleteFlag      int `xorm:"-"`
	AfterDeletedFlag  int `xorm:"-"`

	B4InsertViaExt      int
	AfterInsertedViaExt int
	B4UpdateViaExt      int
	AfterUpdatedViaExt  int
	B4DeleteViaExt      int `xorm:"-"`
	AfterDeletedViaExt  int `xorm:"-"`
}

func (p *ProcessorsStruct) BeforeInsert() {
	p.B4InsertFlag = 1
}

func (p *ProcessorsStruct) BeforeUpdate() {
	p.B4UpdateFlag = 1
}

func (p *ProcessorsStruct) BeforeDelete() {
	p.B4DeleteFlag = 1
}

func (p *ProcessorsStruct) AfterInsert() {
	p.AfterInsertedFlag = 1
}

func (p *ProcessorsStruct) AfterUpdate() {
	p.AfterUpdatedFlag = 1
}

func (p *ProcessorsStruct) AfterDelete() {
	p.AfterDeletedFlag = 1
}

func testProcessors(engine *Engine, t *testing.T) {
	// tempEngine, err := NewEngine(engine.DriverName, engine.DataSourceName)
	// if err != nil {
	//     t.Error(err)
	//     panic(err)
	// }

	engine.ShowSQL = true
	err := engine.DropTables(&ProcessorsStruct{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	p := &ProcessorsStruct{}

	err = engine.CreateTables(&ProcessorsStruct{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	b4InsertFunc := func(bean interface{}) {
		if v, ok := (bean).(*ProcessorsStruct); ok {
			v.B4InsertViaExt = 1
		} else {
			t.Error(errors.New("cast to ProcessorsStruct failed, how can this be!?"))
		}
	}

	afterInsertFunc := func(bean interface{}) {
		if v, ok := (bean).(*ProcessorsStruct); ok {
			v.AfterInsertedViaExt = 1
		} else {
			t.Error(errors.New("cast to ProcessorsStruct failed, how can this be!?"))
		}
	}

	_, err = engine.Before(b4InsertFunc).After(afterInsertFunc).Insert(p)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4InsertFlag == 0 {
			t.Error(errors.New("B4InsertFlag not set"))
		}
		if p.AfterInsertedFlag == 0 {
			t.Error(errors.New("B4InsertFlag not set"))
		}
		if p.B4InsertViaExt == 0 {
			t.Error(errors.New("B4InsertFlag not set"))
		}
		if p.AfterInsertedViaExt == 0 {
			t.Error(errors.New("AfterInsertedViaExt not set"))
		}
	}

	p2 := &ProcessorsStruct{}
	_, err = engine.Id(p.Id).Get(p2)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p2.B4InsertFlag == 0 {
			t.Error(errors.New("B4InsertFlag not set"))
		}
		if p2.AfterInsertedFlag != 0 {
			t.Error(errors.New("AfterInsertedFlag is set"))
		}
		if p2.B4InsertViaExt == 0 {
			t.Error(errors.New("B4InsertViaExt not set"))
		}
		if p2.AfterInsertedViaExt != 0 {
			t.Error(errors.New("AfterInsertedViaExt is set"))
		}
	}
	// --

	// test update processors
	b4UpdateFunc := func(bean interface{}) {
		if v, ok := (bean).(*ProcessorsStruct); ok {
			v.B4UpdateViaExt = 1
		} else {
			t.Error(errors.New("cast to ProcessorsStruct failed, how can this be!?"))
		}
	}

	afterUpdateFunc := func(bean interface{}) {
		if v, ok := (bean).(*ProcessorsStruct); ok {
			v.AfterUpdatedViaExt = 1
		} else {
			t.Error(errors.New("cast to ProcessorsStruct failed, how can this be!?"))
		}
	}

	p = p2 // reset

	_, err = engine.Before(b4UpdateFunc).After(afterUpdateFunc).Update(p)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4UpdateFlag == 0 {
			t.Error(errors.New("B4UpdateFlag not set"))
		}
		if p.AfterUpdatedFlag == 0 {
			t.Error(errors.New("AfterUpdatedFlag not set"))
		}
		if p.B4UpdateViaExt == 0 {
			t.Error(errors.New("B4UpdateViaExt not set"))
		}
		if p.AfterUpdatedViaExt == 0 {
			t.Error(errors.New("AfterUpdatedViaExt not set"))
		}
	}

	p2 = &ProcessorsStruct{}
	_, err = engine.Id(p.Id).Get(p2)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p2.B4UpdateFlag == 0 {
			t.Error(errors.New("B4UpdateFlag not set"))
		}
		if p2.AfterUpdatedFlag != 0 {
			t.Error(errors.New("AfterUpdatedFlag is set: " + string(p.AfterUpdatedFlag)))
		}
		if p2.B4UpdateViaExt == 0 {
			t.Error(errors.New("B4UpdateViaExt not set"))
		}
		if p2.AfterUpdatedViaExt != 0 {
			t.Error(errors.New("AfterUpdatedViaExt is set: " + string(p.AfterUpdatedViaExt)))
		}
	}
	// --

	// test delete processors
	b4DeleteFunc := func(bean interface{}) {
		if v, ok := (bean).(*ProcessorsStruct); ok {
			v.B4DeleteViaExt = 1
		} else {
			t.Error(errors.New("cast to ProcessorsStruct failed, how can this be!?"))
		}
	}

	afterDeleteFunc := func(bean interface{}) {
		if v, ok := (bean).(*ProcessorsStruct); ok {
			v.AfterDeletedViaExt = 1
		} else {
			t.Error(errors.New("cast to ProcessorsStruct failed, how can this be!?"))
		}
	}

	p = p2 // reset
	_, err = engine.Before(b4DeleteFunc).After(afterDeleteFunc).Delete(p)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4DeleteFlag == 0 {
			t.Error(errors.New("B4DeleteFlag not set"))
		}
		if p.AfterDeletedFlag == 0 {
			t.Error(errors.New("AfterDeletedFlag not set"))
		}
		if p.B4DeleteViaExt == 0 {
			t.Error(errors.New("B4DeleteViaExt not set"))
		}
		if p.AfterDeletedViaExt == 0 {
			t.Error(errors.New("AfterDeletedViaExt not set"))
		}
	}
	// --

	// test insert multi
	pslice := make([]*ProcessorsStruct, 0)
	pslice = append(pslice, &ProcessorsStruct{})
	pslice = append(pslice, &ProcessorsStruct{})
	cnt, err := engine.Before(b4InsertFunc).After(afterInsertFunc).Insert(&pslice)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if cnt != 2 {
			t.Error(errors.New("incorrect insert count"))
		}
		for _, elem := range pslice {
			if elem.B4InsertFlag == 0 {
				t.Error(errors.New("B4InsertFlag not set"))
			}
			if elem.AfterInsertedFlag == 0 {
				t.Error(errors.New("B4InsertFlag not set"))
			}
			if elem.B4InsertViaExt == 0 {
				t.Error(errors.New("B4InsertFlag not set"))
			}
			if elem.AfterInsertedViaExt == 0 {
				t.Error(errors.New("AfterInsertedViaExt not set"))
			}
		}
	}

	for _, elem := range pslice {
		p = &ProcessorsStruct{}
		_, err = engine.Id(elem.Id).Get(p)
		if err != nil {
			t.Error(err)
			panic(err)
		} else {
			if p2.B4InsertFlag == 0 {
				t.Error(errors.New("B4InsertFlag not set"))
			}
			if p2.AfterInsertedFlag != 0 {
				t.Error(errors.New("AfterInsertedFlag is set"))
			}
			if p2.B4InsertViaExt == 0 {
				t.Error(errors.New("B4InsertViaExt not set"))
			}
			if p2.AfterInsertedViaExt != 0 {
				t.Error(errors.New("AfterInsertedViaExt is set"))
			}
		}
	}
	// --
}

func testProcessorsTx(engine *Engine, t *testing.T) {
	// tempEngine, err := NewEngine(engine.DriverName, engine.DataSourceName)
	// if err != nil {
	//     t.Error(err)
	//     panic(err)
	// }

	// tempEngine.ShowSQL = true
	err := engine.DropTables(&ProcessorsStruct{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(&ProcessorsStruct{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	// test insert processors with tx rollback
	session := engine.NewSession()
	err = session.Begin()
	if err != nil {
		t.Error(err)
		panic(err)
	}

	p := &ProcessorsStruct{}
	b4InsertFunc := func(bean interface{}) {
		if v, ok := (bean).(*ProcessorsStruct); ok {
			v.B4InsertViaExt = 1
		} else {
			t.Error(errors.New("cast to ProcessorsStruct failed, how can this be!?"))
		}
	}

	afterInsertFunc := func(bean interface{}) {
		if v, ok := (bean).(*ProcessorsStruct); ok {
			v.AfterInsertedViaExt = 1
		} else {
			t.Error(errors.New("cast to ProcessorsStruct failed, how can this be!?"))
		}
	}
	_, err = session.Before(b4InsertFunc).After(afterInsertFunc).Insert(p)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4InsertFlag == 0 {
			t.Error(errors.New("B4InsertFlag not set"))
		}
		if p.AfterInsertedFlag != 0 {
			t.Error(errors.New("B4InsertFlag is set"))
		}
		if p.B4InsertViaExt == 0 {
			t.Error(errors.New("B4InsertViaExt not set"))
		}
		if p.AfterInsertedViaExt != 0 {
			t.Error(errors.New("AfterInsertedViaExt is set"))
		}
	}

	err = session.Rollback()
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4InsertFlag == 0 {
			t.Error(errors.New("B4InsertFlag not set"))
		}
		if p.AfterInsertedFlag != 0 {
			t.Error(errors.New("B4InsertFlag is set"))
		}
		if p.B4InsertViaExt == 0 {
			t.Error(errors.New("B4InsertViaExt not set"))
		}
		if p.AfterInsertedViaExt != 0 {
			t.Error(errors.New("AfterInsertedViaExt is set"))
		}
	}
	session.Close()
	p2 := &ProcessorsStruct{}
	_, err = engine.Id(p.Id).Get(p2)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p2.Id > 0 {
			err = errors.New("tx got committed upon insert!?")
			t.Error(err)
			panic(err)
		}
	}
	// --

	// test insert processors with tx commit
	session = engine.NewSession()
	err = session.Begin()
	if err != nil {
		t.Error(err)
		panic(err)
	}

	p = &ProcessorsStruct{}
	_, err = session.Before(b4InsertFunc).After(afterInsertFunc).Insert(p)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4InsertFlag == 0 {
			t.Error(errors.New("B4InsertFlag not set"))
		}
		if p.AfterInsertedFlag != 0 {
			t.Error(errors.New("AfterInsertedFlag is set"))
		}
		if p.B4InsertViaExt == 0 {
			t.Error(errors.New("B4InsertViaExt not set"))
		}
		if p.AfterInsertedViaExt != 0 {
			t.Error(errors.New("AfterInsertedViaExt is set"))
		}
	}

	err = session.Commit()
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4InsertFlag == 0 {
			t.Error(errors.New("B4InsertFlag not set"))
		}
		if p.AfterInsertedFlag == 0 {
			t.Error(errors.New("AfterInsertedFlag not set"))
		}
		if p.B4InsertViaExt == 0 {
			t.Error(errors.New("B4InsertViaExt not set"))
		}
		if p.AfterInsertedViaExt == 0 {
			t.Error(errors.New("AfterInsertedViaExt not set"))
		}
	}
	session.Close()
	p2 = &ProcessorsStruct{}
	_, err = engine.Id(p.Id).Get(p2)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p2.B4InsertFlag == 0 {
			t.Error(errors.New("B4InsertFlag not set"))
		}
		if p2.AfterInsertedFlag != 0 {
			t.Error(errors.New("AfterInsertedFlag is set"))
		}
		if p2.B4InsertViaExt == 0 {
			t.Error(errors.New("B4InsertViaExt not set"))
		}
		if p2.AfterInsertedViaExt != 0 {
			t.Error(errors.New("AfterInsertedViaExt is set"))
		}
	}
	insertedId := p2.Id
	// --

	// test update processors with tx rollback
	session = engine.NewSession()
	err = session.Begin()
	if err != nil {
		t.Error(err)
		panic(err)
	}

	b4UpdateFunc := func(bean interface{}) {
		if v, ok := (bean).(*ProcessorsStruct); ok {
			v.B4UpdateViaExt = 1
		} else {
			t.Error(errors.New("cast to ProcessorsStruct failed, how can this be!?"))
		}
	}

	afterUpdateFunc := func(bean interface{}) {
		if v, ok := (bean).(*ProcessorsStruct); ok {
			v.AfterUpdatedViaExt = 1
		} else {
			t.Error(errors.New("cast to ProcessorsStruct failed, how can this be!?"))
		}
	}

	p = p2 // reset

	_, err = session.Id(insertedId).Before(b4UpdateFunc).After(afterUpdateFunc).Update(p)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4UpdateFlag == 0 {
			t.Error(errors.New("B4UpdateFlag not set"))
		}
		if p.AfterUpdatedFlag != 0 {
			t.Error(errors.New("AfterUpdatedFlag is set"))
		}
		if p.B4UpdateViaExt == 0 {
			t.Error(errors.New("B4UpdateViaExt not set"))
		}
		if p.AfterUpdatedViaExt != 0 {
			t.Error(errors.New("AfterUpdatedViaExt is set"))
		}
	}
	err = session.Rollback()
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4UpdateFlag == 0 {
			t.Error(errors.New("B4UpdateFlag not set"))
		}
		if p.AfterUpdatedFlag != 0 {
			t.Error(errors.New("AfterUpdatedFlag is set"))
		}
		if p.B4UpdateViaExt == 0 {
			t.Error(errors.New("B4UpdateViaExt not set"))
		}
		if p.AfterUpdatedViaExt != 0 {
			t.Error(errors.New("AfterUpdatedViaExt is set"))
		}
	}

	session.Close()
	p2 = &ProcessorsStruct{}
	_, err = engine.Id(insertedId).Get(p2)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p2.B4UpdateFlag != 0 {
			t.Error(errors.New("B4UpdateFlag is set"))
		}
		if p2.AfterUpdatedFlag != 0 {
			t.Error(errors.New("AfterUpdatedFlag is set"))
		}
		if p2.B4UpdateViaExt != 0 {
			t.Error(errors.New("B4UpdateViaExt not set"))
		}
		if p2.AfterUpdatedViaExt != 0 {
			t.Error(errors.New("AfterUpdatedViaExt is set"))
		}
	}
	// --

	// test update processors with tx commit
	session = engine.NewSession()
	err = session.Begin()
	if err != nil {
		t.Error(err)
		panic(err)
	}

	p = &ProcessorsStruct{}

	_, err = session.Id(insertedId).Before(b4UpdateFunc).After(afterUpdateFunc).Update(p)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4UpdateFlag == 0 {
			t.Error(errors.New("B4UpdateFlag not set"))
		}
		if p.AfterUpdatedFlag != 0 {
			t.Error(errors.New("AfterUpdatedFlag is set"))
		}
		if p.B4UpdateViaExt == 0 {
			t.Error(errors.New("B4UpdateViaExt not set"))
		}
		if p.AfterUpdatedViaExt != 0 {
			t.Error(errors.New("AfterUpdatedViaExt is set"))
		}
	}
	err = session.Commit()
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4UpdateFlag == 0 {
			t.Error(errors.New("B4UpdateFlag not set"))
		}
		if p.AfterUpdatedFlag == 0 {
			t.Error(errors.New("AfterUpdatedFlag not set"))
		}
		if p.B4UpdateViaExt == 0 {
			t.Error(errors.New("B4UpdateViaExt not set"))
		}
		if p.AfterUpdatedViaExt == 0 {
			t.Error(errors.New("AfterUpdatedViaExt not set"))
		}
	}
	session.Close()
	p2 = &ProcessorsStruct{}
	_, err = engine.Id(insertedId).Get(p2)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4UpdateFlag == 0 {
			t.Error(errors.New("B4UpdateFlag not set"))
		}
		if p.AfterUpdatedFlag == 0 {
			t.Error(errors.New("AfterUpdatedFlag not set"))
		}
		if p.B4UpdateViaExt == 0 {
			t.Error(errors.New("B4UpdateViaExt not set"))
		}
		if p.AfterUpdatedViaExt == 0 {
			t.Error(errors.New("AfterUpdatedViaExt not set"))
		}
	}
	// --

	// test delete processors with tx rollback
	session = engine.NewSession()
	err = session.Begin()
	if err != nil {
		t.Error(err)
		panic(err)
	}

	b4DeleteFunc := func(bean interface{}) {
		if v, ok := (bean).(*ProcessorsStruct); ok {
			v.B4DeleteViaExt = 1
		} else {
			t.Error(errors.New("cast to ProcessorsStruct failed, how can this be!?"))
		}
	}

	afterDeleteFunc := func(bean interface{}) {
		if v, ok := (bean).(*ProcessorsStruct); ok {
			v.AfterDeletedViaExt = 1
		} else {
			t.Error(errors.New("cast to ProcessorsStruct failed, how can this be!?"))
		}
	}

	p = &ProcessorsStruct{} // reset

	_, err = session.Id(insertedId).Before(b4DeleteFunc).After(afterDeleteFunc).Delete(p)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4DeleteFlag == 0 {
			t.Error(errors.New("B4DeleteFlag not set"))
		}
		if p.AfterDeletedFlag != 0 {
			t.Error(errors.New("AfterDeletedFlag is set"))
		}
		if p.B4DeleteViaExt == 0 {
			t.Error(errors.New("B4DeleteViaExt not set"))
		}
		if p.AfterDeletedViaExt != 0 {
			t.Error(errors.New("AfterDeletedViaExt is set"))
		}
	}
	err = session.Rollback()
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4DeleteFlag == 0 {
			t.Error(errors.New("B4DeleteFlag not set"))
		}
		if p.AfterDeletedFlag != 0 {
			t.Error(errors.New("AfterDeletedFlag is set"))
		}
		if p.B4DeleteViaExt == 0 {
			t.Error(errors.New("B4DeleteViaExt not set"))
		}
		if p.AfterDeletedViaExt != 0 {
			t.Error(errors.New("AfterDeletedViaExt is set"))
		}
	}
	session.Close()

	p2 = &ProcessorsStruct{}
	_, err = engine.Id(insertedId).Get(p2)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p2.B4DeleteFlag != 0 {
			t.Error(errors.New("B4DeleteFlag is set"))
		}
		if p2.AfterDeletedFlag != 0 {
			t.Error(errors.New("AfterDeletedFlag is set"))
		}
		if p2.B4DeleteViaExt != 0 {
			t.Error(errors.New("B4DeleteViaExt is set"))
		}
		if p2.AfterDeletedViaExt != 0 {
			t.Error(errors.New("AfterDeletedViaExt is set"))
		}
	}
	// --

	// test delete processors with tx commit
	session = engine.NewSession()
	err = session.Begin()
	if err != nil {
		t.Error(err)
		panic(err)
	}

	p = &ProcessorsStruct{}

	_, err = session.Id(insertedId).Before(b4DeleteFunc).After(afterDeleteFunc).Delete(p)
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4DeleteFlag == 0 {
			t.Error(errors.New("B4DeleteFlag not set"))
		}
		if p.AfterDeletedFlag != 0 {
			t.Error(errors.New("AfterDeletedFlag is set"))
		}
		if p.B4DeleteViaExt == 0 {
			t.Error(errors.New("B4DeleteViaExt not set"))
		}
		if p.AfterDeletedViaExt != 0 {
			t.Error(errors.New("AfterDeletedViaExt is set"))
		}
	}
	err = session.Commit()
	if err != nil {
		t.Error(err)
		panic(err)
	} else {
		if p.B4DeleteFlag == 0 {
			t.Error(errors.New("B4DeleteFlag not set"))
		}
		if p.AfterDeletedFlag == 0 {
			t.Error(errors.New("AfterDeletedFlag not set"))
		}
		if p.B4DeleteViaExt == 0 {
			t.Error(errors.New("B4DeleteViaExt not set"))
		}
		if p.AfterDeletedViaExt == 0 {
			t.Error(errors.New("AfterDeletedViaExt not set"))
		}
	}
	session.Close()
	// --
}

type NullData struct {
	Id         int64
	StringPtr  *string
	StringPtr2 *string `xorm:"text"`
	BoolPtr    *bool
	BytePtr    *byte
	UintPtr    *uint
	Uint8Ptr   *uint8
	Uint16Ptr  *uint16
	Uint32Ptr  *uint32
	Uint64Ptr  *uint64
	IntPtr     *int
	Int8Ptr    *int8
	Int16Ptr   *int16
	Int32Ptr   *int32
	Int64Ptr   *int64
	RunePtr    *rune
	Float32Ptr *float32
	Float64Ptr *float64
	// Complex64Ptr *complex64 // !nashtsai! XORM yet support complex128:  'json: unsupported type: complex128'
	// Complex128Ptr *complex128 // !nashtsai! XORM yet support complex128:  'json: unsupported type: complex128'
	TimePtr *time.Time
}

type NullData2 struct {
	Id         int64
	StringPtr  string
	StringPtr2 string `xorm:"text"`
	BoolPtr    bool
	BytePtr    byte
	UintPtr    uint
	Uint8Ptr   uint8
	Uint16Ptr  uint16
	Uint32Ptr  uint32
	Uint64Ptr  uint64
	IntPtr     int
	Int8Ptr    int8
	Int16Ptr   int16
	Int32Ptr   int32
	Int64Ptr   int64
	RunePtr    rune
	Float32Ptr float32
	Float64Ptr float64
	// Complex64Ptr complex64 // !nashtsai! XORM yet support complex128:  'json: unsupported type: complex128'
	// Complex128Ptr complex128 // !nashtsai! XORM yet support complex128:  'json: unsupported type: complex128'
	TimePtr time.Time
}

type NullData3 struct {
	Id        int64
	StringPtr *string
}

func testPointerData(engine *Engine, t *testing.T) {

	err := engine.DropTables(&NullData{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(&NullData{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	nullData := NullData{
		StringPtr:  new(string),
		StringPtr2: new(string),
		BoolPtr:    new(bool),
		BytePtr:    new(byte),
		UintPtr:    new(uint),
		Uint8Ptr:   new(uint8),
		Uint16Ptr:  new(uint16),
		Uint32Ptr:  new(uint32),
		Uint64Ptr:  new(uint64),
		IntPtr:     new(int),
		Int8Ptr:    new(int8),
		Int16Ptr:   new(int16),
		Int32Ptr:   new(int32),
		Int64Ptr:   new(int64),
		RunePtr:    new(rune),
		Float32Ptr: new(float32),
		Float64Ptr: new(float64),
		// Complex64Ptr: new(complex64),
		// Complex128Ptr: new(complex128),
		TimePtr: new(time.Time),
	}

	*nullData.StringPtr = "abc"
	*nullData.StringPtr2 = "123"
	*nullData.BoolPtr = true
	*nullData.BytePtr = 1
	*nullData.UintPtr = 1
	*nullData.Uint8Ptr = 1
	*nullData.Uint16Ptr = 1
	*nullData.Uint32Ptr = 1
	*nullData.Uint64Ptr = 1
	*nullData.IntPtr = -1
	*nullData.Int8Ptr = -1
	*nullData.Int16Ptr = -1
	*nullData.Int32Ptr = -1
	*nullData.Int64Ptr = -1
	*nullData.RunePtr = 1
	*nullData.Float32Ptr = -1.2
	*nullData.Float64Ptr = -1.1
	// *nullData.Complex64Ptr = 123456789012345678901234567890
	// *nullData.Complex128Ptr = 123456789012345678901234567890123456789012345678901234567890
	*nullData.TimePtr = time.Now()

	cnt, err := engine.Insert(&nullData)
	fmt.Println(nullData.Id)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert not returned 1")
		t.Error(err)
		panic(err)
		return
	}
	if nullData.Id <= 0 {
		err = errors.New("not return id error")
		t.Error(err)
		panic(err)
	}

	// verify get values
	nullDataGet := NullData{}
	has, err := engine.Id(nullData.Id).Get(&nullDataGet)
	if err != nil {
		t.Error(err)
		panic(err)
	} else if !has {
		t.Error(errors.New("ID not found"))
	}

	if *nullDataGet.StringPtr != *nullData.StringPtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.StringPtr)))
	}

	if *nullDataGet.StringPtr2 != *nullData.StringPtr2 {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.StringPtr2)))
	}

	if *nullDataGet.BoolPtr != *nullData.BoolPtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%t]", *nullDataGet.BoolPtr)))
	}

	if *nullDataGet.UintPtr != *nullData.UintPtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.UintPtr)))
	}

	if *nullDataGet.Uint8Ptr != *nullData.Uint8Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Uint8Ptr)))
	}

	if *nullDataGet.Uint16Ptr != *nullData.Uint16Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Uint16Ptr)))
	}

	if *nullDataGet.Uint32Ptr != *nullData.Uint32Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Uint32Ptr)))
	}

	if *nullDataGet.Uint64Ptr != *nullData.Uint64Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Uint64Ptr)))
	}

	if *nullDataGet.IntPtr != *nullData.IntPtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.IntPtr)))
	}

	if *nullDataGet.Int8Ptr != *nullData.Int8Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Int8Ptr)))
	}

	if *nullDataGet.Int16Ptr != *nullData.Int16Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Int16Ptr)))
	}

	if *nullDataGet.Int32Ptr != *nullData.Int32Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Int32Ptr)))
	}

	if *nullDataGet.Int64Ptr != *nullData.Int64Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Int64Ptr)))
	}

	if *nullDataGet.RunePtr != *nullData.RunePtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.RunePtr)))
	}

	if *nullDataGet.Float32Ptr != *nullData.Float32Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Float32Ptr)))
	}

	if *nullDataGet.Float64Ptr != *nullData.Float64Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Float64Ptr)))
	}

	// if *nullDataGet.Complex64Ptr != *nullData.Complex64Ptr {
	//  t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Complex64Ptr)))
	// }

	// if *nullDataGet.Complex128Ptr != *nullData.Complex128Ptr {
	//  t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Complex128Ptr)))
	// }

	/*if (*nullDataGet.TimePtr).Unix() != (*nullData.TimePtr).Unix() {
	      t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]:[%v]", *nullDataGet.TimePtr, *nullData.TimePtr)))
	  } else {
	      // !nashtsai! mymysql driver will failed this test case, due the time is roundup to nearest second, I would considered this is a bug in mymysql driver
	      fmt.Printf("time value: [%v]:[%v]", *nullDataGet.TimePtr, *nullData.TimePtr)
	      fmt.Println()
	  }*/
	// --

	// using instance type should just work too
	nullData2Get := NullData2{}

	tableName := engine.tableMapper.Obj2Table("NullData")

	has, err = engine.Table(tableName).Id(nullData.Id).Get(&nullData2Get)
	if err != nil {
		t.Error(err)
		panic(err)
	} else if !has {
		t.Error(errors.New("ID not found"))
	}

	if nullData2Get.StringPtr != *nullData.StringPtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.StringPtr)))
	}

	if nullData2Get.StringPtr2 != *nullData.StringPtr2 {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.StringPtr2)))
	}

	if nullData2Get.BoolPtr != *nullData.BoolPtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%t]", nullData2Get.BoolPtr)))
	}

	if nullData2Get.UintPtr != *nullData.UintPtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.UintPtr)))
	}

	if nullData2Get.Uint8Ptr != *nullData.Uint8Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.Uint8Ptr)))
	}

	if nullData2Get.Uint16Ptr != *nullData.Uint16Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.Uint16Ptr)))
	}

	if nullData2Get.Uint32Ptr != *nullData.Uint32Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.Uint32Ptr)))
	}

	if nullData2Get.Uint64Ptr != *nullData.Uint64Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.Uint64Ptr)))
	}

	if nullData2Get.IntPtr != *nullData.IntPtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.IntPtr)))
	}

	if nullData2Get.Int8Ptr != *nullData.Int8Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.Int8Ptr)))
	}

	if nullData2Get.Int16Ptr != *nullData.Int16Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.Int16Ptr)))
	}

	if nullData2Get.Int32Ptr != *nullData.Int32Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.Int32Ptr)))
	}

	if nullData2Get.Int64Ptr != *nullData.Int64Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.Int64Ptr)))
	}

	if nullData2Get.RunePtr != *nullData.RunePtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.RunePtr)))
	}

	if nullData2Get.Float32Ptr != *nullData.Float32Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.Float32Ptr)))
	}

	if nullData2Get.Float64Ptr != *nullData.Float64Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.Float64Ptr)))
	}

	// if nullData2Get.Complex64Ptr != *nullData.Complex64Ptr {
	//  t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.Complex64Ptr)))
	// }

	// if nullData2Get.Complex128Ptr != *nullData.Complex128Ptr {
	//  t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", nullData2Get.Complex128Ptr)))
	// }

	/*if nullData2Get.TimePtr.Unix() != (*nullData.TimePtr).Unix() {
	      t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]:[%v]", nullData2Get.TimePtr, *nullData.TimePtr)))
	  } else {
	      // !nashtsai! mymysql driver will failed this test case, due the time is roundup to nearest second, I would considered this is a bug in mymysql driver
	      fmt.Printf("time value: [%v]:[%v]", nullData2Get.TimePtr, *nullData.TimePtr)
	      fmt.Println()
	  }*/
	// --
}

func testNullValue(engine *Engine, t *testing.T) {

	err := engine.DropTables(&NullData{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(&NullData{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	nullData := NullData{}

	cnt, err := engine.Insert(&nullData)
	fmt.Println(nullData.Id)
	if err != nil {
		t.Error(err)
		panic(err)
	}
	if cnt != 1 {
		err = errors.New("insert not returned 1")
		t.Error(err)
		panic(err)
		return
	}
	if nullData.Id <= 0 {
		err = errors.New("not return id error")
		t.Error(err)
		panic(err)
	}

	nullDataGet := NullData{}

	has, err := engine.Id(nullData.Id).Get(&nullDataGet)
	if err != nil {
		t.Error(err)
		panic(err)
	} else if !has {
		t.Error(errors.New("ID not found"))
	}

	if nullDataGet.StringPtr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.StringPtr)))
	}

	if nullDataGet.StringPtr2 != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.StringPtr2)))
	}

	if nullDataGet.BoolPtr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%t]", *nullDataGet.BoolPtr)))
	}

	if nullDataGet.UintPtr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.UintPtr)))
	}

	if nullDataGet.Uint8Ptr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Uint8Ptr)))
	}

	if nullDataGet.Uint16Ptr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Uint16Ptr)))
	}

	if nullDataGet.Uint32Ptr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Uint32Ptr)))
	}

	if nullDataGet.Uint64Ptr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Uint64Ptr)))
	}

	if nullDataGet.IntPtr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.IntPtr)))
	}

	if nullDataGet.Int8Ptr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Int8Ptr)))
	}

	if nullDataGet.Int16Ptr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Int16Ptr)))
	}

	if nullDataGet.Int32Ptr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Int32Ptr)))
	}

	if nullDataGet.Int64Ptr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Int64Ptr)))
	}

	if nullDataGet.RunePtr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.RunePtr)))
	}

	if nullDataGet.Float32Ptr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Float32Ptr)))
	}

	if nullDataGet.Float64Ptr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Float64Ptr)))
	}

	// if nullDataGet.Complex64Ptr != nil {
	//  t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Complex64Ptr)))
	// }

	// if nullDataGet.Complex128Ptr != nil {
	//  t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Complex128Ptr)))
	// }

	if nullDataGet.TimePtr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.TimePtr)))
	}

	nullDataUpdate := NullData{
		StringPtr:  new(string),
		StringPtr2: new(string),
		BoolPtr:    new(bool),
		BytePtr:    new(byte),
		UintPtr:    new(uint),
		Uint8Ptr:   new(uint8),
		Uint16Ptr:  new(uint16),
		Uint32Ptr:  new(uint32),
		Uint64Ptr:  new(uint64),
		IntPtr:     new(int),
		Int8Ptr:    new(int8),
		Int16Ptr:   new(int16),
		Int32Ptr:   new(int32),
		Int64Ptr:   new(int64),
		RunePtr:    new(rune),
		Float32Ptr: new(float32),
		Float64Ptr: new(float64),
		// Complex64Ptr: new(complex64),
		// Complex128Ptr: new(complex128),
		TimePtr: new(time.Time),
	}

	*nullDataUpdate.StringPtr = "abc"
	*nullDataUpdate.StringPtr2 = "123"
	*nullDataUpdate.BoolPtr = true
	*nullDataUpdate.BytePtr = 1
	*nullDataUpdate.UintPtr = 1
	*nullDataUpdate.Uint8Ptr = 1
	*nullDataUpdate.Uint16Ptr = 1
	*nullDataUpdate.Uint32Ptr = 1
	*nullDataUpdate.Uint64Ptr = 1
	*nullDataUpdate.IntPtr = -1
	*nullDataUpdate.Int8Ptr = -1
	*nullDataUpdate.Int16Ptr = -1
	*nullDataUpdate.Int32Ptr = -1
	*nullDataUpdate.Int64Ptr = -1
	*nullDataUpdate.RunePtr = 1
	*nullDataUpdate.Float32Ptr = -1.2
	*nullDataUpdate.Float64Ptr = -1.1
	// *nullDataUpdate.Complex64Ptr = 123456789012345678901234567890
	// *nullDataUpdate.Complex128Ptr = 123456789012345678901234567890123456789012345678901234567890
	*nullDataUpdate.TimePtr = time.Now()

	cnt, err = engine.Id(nullData.Id).Update(&nullDataUpdate)
	if err != nil {
		t.Error(err)
		panic(err)
	} else if cnt != 1 {
		t.Error(errors.New("update count == 0, how can this happen!?"))
		return
	}

	// verify get values
	nullDataGet = NullData{}
	has, err = engine.Id(nullData.Id).Get(&nullDataGet)
	if err != nil {
		t.Error(err)
		return
	} else if !has {
		t.Error(errors.New("ID not found"))
		return
	}

	if *nullDataGet.StringPtr != *nullDataUpdate.StringPtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.StringPtr)))
	}

	if *nullDataGet.StringPtr2 != *nullDataUpdate.StringPtr2 {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.StringPtr2)))
	}

	if *nullDataGet.BoolPtr != *nullDataUpdate.BoolPtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%t]", *nullDataGet.BoolPtr)))
	}

	if *nullDataGet.UintPtr != *nullDataUpdate.UintPtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.UintPtr)))
	}

	if *nullDataGet.Uint8Ptr != *nullDataUpdate.Uint8Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Uint8Ptr)))
	}

	if *nullDataGet.Uint16Ptr != *nullDataUpdate.Uint16Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Uint16Ptr)))
	}

	if *nullDataGet.Uint32Ptr != *nullDataUpdate.Uint32Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Uint32Ptr)))
	}

	if *nullDataGet.Uint64Ptr != *nullDataUpdate.Uint64Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Uint64Ptr)))
	}

	if *nullDataGet.IntPtr != *nullDataUpdate.IntPtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.IntPtr)))
	}

	if *nullDataGet.Int8Ptr != *nullDataUpdate.Int8Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Int8Ptr)))
	}

	if *nullDataGet.Int16Ptr != *nullDataUpdate.Int16Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Int16Ptr)))
	}

	if *nullDataGet.Int32Ptr != *nullDataUpdate.Int32Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Int32Ptr)))
	}

	if *nullDataGet.Int64Ptr != *nullDataUpdate.Int64Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Int64Ptr)))
	}

	if *nullDataGet.RunePtr != *nullDataUpdate.RunePtr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.RunePtr)))
	}

	if *nullDataGet.Float32Ptr != *nullDataUpdate.Float32Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Float32Ptr)))
	}

	if *nullDataGet.Float64Ptr != *nullDataUpdate.Float64Ptr {
		t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Float64Ptr)))
	}

	// if *nullDataGet.Complex64Ptr != *nullDataUpdate.Complex64Ptr {
	//  t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Complex64Ptr)))
	// }

	// if *nullDataGet.Complex128Ptr != *nullDataUpdate.Complex128Ptr {
	//  t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]", *nullDataGet.Complex128Ptr)))
	// }

	// !nashtsai! skipped mymysql test due to driver will round up time caused inaccuracy comparison
	// skipped postgres test due to postgres driver doesn't read time.Time's timzezone info when stored in the db
	// mysql and sqlite3 seem have done this correctly by storing datatime in UTC timezone, I think postgres driver
	// prefer using timestamp with timezone to sovle the issue
	if engine.DriverName != POSTGRES && engine.DriverName != MYMYSQL &&
		engine.DriverName != MYSQL {
		if (*nullDataGet.TimePtr).Unix() != (*nullDataUpdate.TimePtr).Unix() {
			t.Error(errors.New(fmt.Sprintf("inserted value unmatch: [%v]:[%v]", *nullDataGet.TimePtr, *nullDataUpdate.TimePtr)))
		} else {
			// !nashtsai! mymysql driver will failed this test case, due the time is roundup to nearest second, I would considered this is a bug in mymysql driver
			//  inserted value unmatch: [2013-12-25 12:12:45 +0800 CST]:[2013-12-25 12:12:44.878903653 +0800 CST]
			fmt.Printf("time value: [%v]:[%v]", *nullDataGet.TimePtr, *nullDataUpdate.TimePtr)
			fmt.Println()
		}
	}

	// update to null values
	nullDataUpdate = NullData{}

	string_ptr := engine.columnMapper.Obj2Table("StringPtr")

	cnt, err = engine.Id(nullData.Id).Cols(string_ptr).Update(&nullDataUpdate)
	if err != nil {
		t.Error(err)
		panic(err)
	} else if cnt != 1 {
		t.Error(errors.New("update count == 0, how can this happen!?"))
		return
	}

	// verify get values
	nullDataGet = NullData{}
	has, err = engine.Id(nullData.Id).Get(&nullDataGet)
	if err != nil {
		t.Error(err)
		return
	} else if !has {
		t.Error(errors.New("ID not found"))
		return
	}

	fmt.Printf("%+v", nullDataGet)
	fmt.Println()

	if nullDataGet.StringPtr != nil {
		t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.StringPtr)))
	}
	/*
	  if nullDataGet.StringPtr2 != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.StringPtr2)))
	  }

	  if nullDataGet.BoolPtr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%t]", *nullDataGet.BoolPtr)))
	  }

	  if nullDataGet.UintPtr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.UintPtr)))
	  }

	  if nullDataGet.Uint8Ptr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Uint8Ptr)))
	  }

	  if nullDataGet.Uint16Ptr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Uint16Ptr)))
	  }

	  if nullDataGet.Uint32Ptr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Uint32Ptr)))
	  }

	  if nullDataGet.Uint64Ptr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Uint64Ptr)))
	  }

	  if nullDataGet.IntPtr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.IntPtr)))
	  }

	  if nullDataGet.Int8Ptr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Int8Ptr)))
	  }

	  if nullDataGet.Int16Ptr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Int16Ptr)))
	  }

	  if nullDataGet.Int32Ptr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Int32Ptr)))
	  }

	  if nullDataGet.Int64Ptr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Int64Ptr)))
	  }

	  if nullDataGet.RunePtr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.RunePtr)))
	  }

	  if nullDataGet.Float32Ptr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Float32Ptr)))
	  }

	  if nullDataGet.Float64Ptr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Float64Ptr)))
	  }

	  // if nullDataGet.Complex64Ptr != nil {
	  //  t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Float64Ptr)))
	  // }

	  // if nullDataGet.Complex128Ptr != nil {
	  //  t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.Float64Ptr)))
	  // }

	  if nullDataGet.TimePtr != nil {
	      t.Error(errors.New(fmt.Sprintf("not null value: [%v]", *nullDataGet.TimePtr)))
	  }*/
	// --

}

type CompositeKey struct {
	Id1       int64 `xorm:"id1 pk"`
	Id2       int64 `xorm:"id2 pk"`
	UpdateStr string
}

func testCompositeKey(engine *Engine, t *testing.T) {

	err := engine.DropTables(&CompositeKey{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(&CompositeKey{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	cnt, err := engine.Insert(&CompositeKey{11, 22, ""})
	if err != nil {
		t.Error(err)
	} else if cnt != 1 {
		t.Error(errors.New("failed to insert CompositeKey{11, 22}"))
	}

	cnt, err = engine.Insert(&CompositeKey{11, 22, ""})
	if err == nil || cnt == 1 {
		t.Error(errors.New("inserted CompositeKey{11, 22}"))
	}

	var compositeKeyVal CompositeKey
	has, err := engine.Id(PK{11, 22}).Get(&compositeKeyVal)
	if err != nil {
		t.Error(err)
	} else if !has {
		t.Error(errors.New("can't get CompositeKey{11, 22}"))
	}

	// test passing PK ptr, this test seem failed withCache
	has, err = engine.Id(&PK{11, 22}).Get(&compositeKeyVal)
	if err != nil {
		t.Error(err)
	} else if !has {
		t.Error(errors.New("can't get CompositeKey{11, 22}"))
	}

	compositeKeyVal = CompositeKey{UpdateStr: "test1"}
	cnt, err = engine.Id(PK{11, 22}).Update(&compositeKeyVal)
	if err != nil {
		t.Error(err)
	} else if cnt != 1 {
		t.Error(errors.New("can't update CompositeKey{11, 22}"))
	}

	cnt, err = engine.Id(PK{11, 22}).Delete(&CompositeKey{})
	if err != nil {
		t.Error(err)
	} else if cnt != 1 {
		t.Error(errors.New("can't delete CompositeKey{11, 22}"))
	}
}

type Lowercase struct {
	Id    int64
	Name  string
	ended int64 `xorm:"-"`
}

func testLowerCase(engine *Engine, t *testing.T) {
	err := engine.Sync(&Lowercase{})
	_, err = engine.Where("id > 0").Delete(&Lowercase{})
	if err != nil {
		t.Error(err)
		panic(err)
	}
	_, err = engine.Insert(&Lowercase{ended: 1})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	ls := make([]Lowercase, 0)
	err = engine.Find(&ls)
	if err != nil {
		t.Error(err)
		panic(err)
	}

	if len(ls) != 1 {
		err = errors.New("should be 1")
		t.Error(err)
		panic(err)
	}
}

type User struct {
	UserId   string `xorm:"varchar(19) not null pk"`
	NickName string `xorm:"varchar(19) not null"`
	GameId   uint32 `xorm:"integer pk"`
	Score    int32  `xorm:"integer"`
}

func testCompositeKey2(engine *Engine, t *testing.T) {
	err := engine.DropTables(&User{})

	if err != nil {
		t.Error(err)
		panic(err)
	}

	err = engine.CreateTables(&User{})
	if err != nil {
		t.Error(err)
		panic(err)
	}

	cnt, err := engine.Insert(&User{"11", "nick", 22, 5})
	if err != nil {
		t.Error(err)
	} else if cnt != 1 {
		t.Error(errors.New("failed to insert User{11, 22}"))
	}

	cnt, err = engine.Insert(&User{"11", "nick", 22, 6})
	if err == nil || cnt == 1 {
		t.Error(errors.New("inserted User{11, 22}"))
	}

	var user User
	has, err := engine.Id(PK{"11", 22}).Get(&user)
	if err != nil {
		t.Error(err)
	} else if !has {
		t.Error(errors.New("can't get User{11, 22}"))
	}

	// test passing PK ptr, this test seem failed withCache
	has, err = engine.Id(&PK{"11", 22}).Get(&user)
	if err != nil {
		t.Error(err)
	} else if !has {
		t.Error(errors.New("can't get User{11, 22}"))
	}

	user = User{NickName: "test1"}
	cnt, err = engine.Id(PK{"11", 22}).Update(&user)
	if err != nil {
		t.Error(err)
	} else if cnt != 1 {
		t.Error(errors.New("can't update User{11, 22}"))
	}

	cnt, err = engine.Id(PK{"11", 22}).Delete(&User{})
	if err != nil {
		t.Error(err)
	} else if cnt != 1 {
		t.Error(errors.New("can't delete CompositeKey{11, 22}"))
	}
}

func testAll(engine *Engine, t *testing.T) {
	fmt.Println("-------------- directCreateTable --------------")
	directCreateTable(engine, t)
	fmt.Println("-------------- insert --------------")
	insert(engine, t)
	fmt.Println("-------------- query --------------")
	testQuery(engine, t)
	fmt.Println("-------------- exec --------------")
	exec(engine, t)
	fmt.Println("-------------- insertAutoIncr --------------")
	insertAutoIncr(engine, t)
	fmt.Println("-------------- insertMulti --------------")
	insertMulti(engine, t)
	fmt.Println("-------------- insertTwoTable --------------")
	insertTwoTable(engine, t)
	fmt.Println("-------------- update --------------")
	update(engine, t)
	fmt.Println("-------------- testDelete --------------")
	testDelete(engine, t)
	fmt.Println("-------------- get --------------")
	get(engine, t)
	fmt.Println("-------------- cascadeGet --------------")
	cascadeGet(engine, t)
	fmt.Println("-------------- find --------------")
	find(engine, t)
	fmt.Println("-------------- find2 --------------")
	find2(engine, t)
	fmt.Println("-------------- findMap --------------")
	findMap(engine, t)
	fmt.Println("-------------- findMap2 --------------")
	findMap2(engine, t)
	fmt.Println("-------------- count --------------")
	count(engine, t)
	fmt.Println("-------------- where --------------")
	where(engine, t)
	fmt.Println("-------------- in --------------")
	in(engine, t)
	fmt.Println("-------------- limit --------------")
	limit(engine, t)
	fmt.Println("-------------- order --------------")
	order(engine, t)
	fmt.Println("-------------- join --------------")
	join(engine, t)
	fmt.Println("-------------- having --------------")
	having(engine, t)
}

func testAll2(engine *Engine, t *testing.T) {
	fmt.Println("-------------- combineTransaction --------------")
	combineTransaction(engine, t)
	fmt.Println("-------------- table --------------")
	table(engine, t)
	fmt.Println("-------------- createMultiTables --------------")
	createMultiTables(engine, t)
	fmt.Println("-------------- tableOp --------------")
	tableOp(engine, t)
	fmt.Println("-------------- testCols --------------")
	testCols(engine, t)
	fmt.Println("-------------- testCharst --------------")
	testCharst(engine, t)
	fmt.Println("-------------- testStoreEngine --------------")
	testStoreEngine(engine, t)
	fmt.Println("-------------- testExtends --------------")
	testExtends(engine, t)
	fmt.Println("-------------- testColTypes --------------")
	testColTypes(engine, t)
	fmt.Println("-------------- testCustomType --------------")
	testCustomType(engine, t)
	fmt.Println("-------------- testCreatedAndUpdated --------------")
	testCreatedAndUpdated(engine, t)
	fmt.Println("-------------- testIndexAndUnique --------------")
	testIndexAndUnique(engine, t)
	fmt.Println("-------------- testIntId --------------")
	testIntId(engine, t)
	fmt.Println("-------------- testInt32Id --------------")
	testInt32Id(engine, t)
	fmt.Println("-------------- testUintId --------------")
	testUintId(engine, t)
	fmt.Println("-------------- testUint32Id --------------")
	testUint32Id(engine, t)
	fmt.Println("-------------- testUint64Id --------------")
	testUint64Id(engine, t)
	fmt.Println("-------------- testMetaInfo --------------")
	testMetaInfo(engine, t)
	fmt.Println("-------------- testIterate --------------")
	testIterate(engine, t)
	fmt.Println("-------------- testRows --------------")
	testRows(engine, t)
	fmt.Println("-------------- testStrangeName --------------")
	testStrangeName(engine, t)
	fmt.Println("-------------- testVersion --------------")
	testVersion(engine, t)
	fmt.Println("-------------- testDistinct --------------")
	testDistinct(engine, t)
	fmt.Println("-------------- testUseBool --------------")
	testUseBool(engine, t)
	fmt.Println("-------------- testBool --------------")
	testBool(engine, t)
	fmt.Println("-------------- testTime --------------")
	testTime(engine, t)
	fmt.Println("-------------- testPrefixTableName --------------")
	testPrefixTableName(engine, t)
	fmt.Println("-------------- testCreatedUpdated --------------")
	testCreatedUpdated(engine, t)
	fmt.Println("-------------- testLowercase ---------------")
	testLowerCase(engine, t)
	fmt.Println("-------------- processors --------------")
	testProcessors(engine, t)
	fmt.Println("-------------- transaction --------------")
	transaction(engine, t)
}

// !nash! the 3rd set of the test is intended for non-cache enabled engine
func testAll3(engine *Engine, t *testing.T) {
	fmt.Println("-------------- processors TX --------------")
	testProcessorsTx(engine, t)
	fmt.Println("-------------- insert pointer data --------------")
	testPointerData(engine, t)
	fmt.Println("-------------- insert null data --------------")
	testNullValue(engine, t)
	fmt.Println("-------------- testCompositeKey --------------")
	testCompositeKey(engine, t)
	fmt.Println("-------------- testCompositeKey2 --------------")
	testCompositeKey2(engine, t)
	fmt.Println("-------------- testStringPK --------------")
	testStringPK(engine, t)
}

func testAllSnakeMapper(engine *Engine, t *testing.T) {

}

func testAllSameMapper(engine *Engine, t *testing.T) {

}
