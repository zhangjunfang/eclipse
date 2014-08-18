// gogo project main.go
package main

import (
	"fmt"
	"runtime"
)

func Say(s string){
	
	for i:=0;i<10;i++{
		runtime.Gosched();
		fmt.Println(i);
		
	}
	
}

func main() {
	//runtime.GOMAXPROCS(runtime.NumCPU());
	
	runtime.GOMAXPROCS(99999);
	go Say("zhangboyu");
	go Say("zhangjunfang");
}
