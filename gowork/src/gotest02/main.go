// gotest02 project main.go
package main

import (
	"fmt"
	"time"
	"runtime"
)
func  task(arg ...int ) chan int {
	x:=0;
	c:=make(chan int,10);
	go func(){
		time.Sleep(10*time.Second);
		for _,v:=range arg{
			x+=v;
		}
		c<-x;
		
	}();
	
	return c;
}
func main() {
	fmt.Println("Hello World!");
	runtime.GOMAXPROCS(runtime.NumCPU());
	c:=task(1,2,3,4,5,6,7,8,9);
	
	//for v:=range c {
	//	fmt.Println(v);
	//}
	for{
		if k,v:=<-c; v{
			fmt.Println(k);
		}else{
			break;
		}
	}
	close(c);
}
