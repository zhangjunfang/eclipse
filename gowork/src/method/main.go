// method project main.go
package main

import (
	"fmt"
	"methods"
)

func main() {
	fmt.Println("Hello World!")
	c:=methods.Circle{12.3};
	fmt.Println(c.Area());
	
	r:=methods.Rectange{25.3,45.6};
	fmt.Println(r.Area());
	fmt.Println("=================");
	aa:=methods.Array{"0","1"};
	bb:=[]string{"20","30","50"};
	dd:=bb[:];
	aa.Add(dd);
	fmt.Println(aa);
	aa.Add("70","80","90");
	fmt.Println(aa);
	
	fmt.Println(aa.M("J"));
}
