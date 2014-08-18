// gotest project main.go
package main

import (
	"fmt"
	"runtime"
)
func test(c chan bool,b bool, a int8){
	x:=0;
	for i:=0;i<int(a);i++{
		x+=i;
	}
	fmt.Println(x);
	if(b){
		c<-true;
	}
	
}
func main() {
	fmt.Println("Hello World!")
	runtime.GOMAXPROCS(runtime.NumCPU());
	c:=make(chan bool,100);
	for  i:=0;i<100;i++{
		go  test(c,i==i,int8(i));
	}
	
	fmt.Println("====================");
	for v:=range c{
		fmt.Println(v);
	}
	close(c);
}
