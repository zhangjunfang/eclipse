// functiontest01 project main.go
package main

import (
	"fmt"
	"test"
	"stundent"
)

func main() {
	fmt.Println("Hello World!")
	sum,div:=test.Test(2,3);
	fmt.Printf("%T , %d , %d ",2,sum,div,);
	fmt.Println();
	test.Args(1,2,78,89,94,45,56,61,12,23,30);
	fmt.Println(test.Args02(12));
	fmt.Println(test.Args02("12"));
	sss:=int64(12);
	fmt.Printf("%T " ,sss);
	fmt.Println(test.Args02(sss));
	fmt.Println(test.Osuser());
	fmt.Println(test.GetOsPassword());
	s:=stundent.Student{stundent.Human{"zhangsan",28,28.6},"12345"};
	fmt.Println(s.Age,s.Human.Age);
	array:=[]string{"11","222","555"};
	array2:=[]string{"aa","bb0","ddd","dfdfdf"};
	copy(array,array2);
	append(array,"aa","bb0","ddd","dfdfdf");
	ss:=stundent.Address{Man:s,Addr:array};
	fmt.Println(ss.Man.Age,ss.Man.Human.Age,ss.Man,ss.Addr);
	
	
	
	
}

func init(){
	p:=test.Person{Name:"aaa",Age:24};
	fmt.Println(p.Name);
	var pp test.Person ;
	pp=test.Person{Name:"bbb",Age:50};
	fmt.Println(pp.Name);
	fmt.Println(pp.Age);
	dd:=test.Person{"ddd",100};
	fmt.Println(dd.Name);
	fmt.Println(dd.Age);
	
	ee,mac:=test.ComparePerson(p,pp);
	fmt.Println(ee.Age);
	fmt.Println(mac);
	fmt.Println("永远第一个执行");
	
}


