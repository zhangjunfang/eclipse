// go01 project main.go
package main

import (
	"fmt"
	"errors"
	"strconv"
	"reflect"
	"aa"
)

func main() {
	
    mm:="中国人";
	c:=[]byte(mm);
	c[0]=[]byte("国")[0];
	c[1]=[]byte("国")[1];
	c[2]=[]byte("国")[2];
	mm2:=string(c);
	fmt.Println("%s",mm2);
	var err=errors.New("类型错误");
	if(err!=nil){
		fmt.Printf("dgdfgdfgdfg");
	}
	for i:=1;i<10;i++ {
		fmt.Printf("%d \n",i);
	}
	 array :=[...] int {1,2,3,4,7,5,10,15,78,9,6};
	for aa,value:=range  array{
		fmt.Print("\r\n %d,%d ",value,aa);
		
	}
	fmt.Print("\r\n++++++++++++++++++++++++++");
	d:=array[1:8];
	d[2]=160;
	
	d=append(d,888,999,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4);
	//d=append(d,);
	for aa,value:=range  d{
		fmt.Print("\r\n %d,%d ",value,aa);
		
	}
	fmt.Print("\r\n======================%d ,%d ",len(d),cap(d));
	for aa,value:=range  array{
		fmt.Print("\r\n %d,%d ",value,aa);
		
	}
	dd:=copy(d,array[:]);
	fmt.Print("\r\n======================%d ,%d ",len(d),cap(d));
	fmt.Print("\r\n======================%d  ",dd);
	for aa,value:=range  d{
		fmt.Print("\r\n %d,%d ===",value,aa);
		
	}
	fmt.Println("========================map=====================");
	
	maps:=make(map[string]int);
	for i,v:=range d{
		fmt.Print("\r\n %s, %d ===",i,v);
		
		maps[strconv.Itoa(i)]=v;
	}
	fmt.Print("\r\n %d ===",len(maps));
	fmt.Print("\r\n %d ===",maps["10"]);
	fmt.Println("========================map22=====================");
	vs,ok:=maps["10"];
	if(ok){
		fmt.Println("\r\n %d ===",maps["10"]);
		fmt.Println(vs);
		maps["10"]=100;
		fmt.Println("\r\n %d ===",maps["10"]);
	}
	fmt.Println("========================map2=====================");
	for i,v:=range maps{
		fmt.Println(reflect.TypeOf(i));
		fmt.Println("%t  ====",i);
		fmt.Println(reflect.TypeOf(v));
		fmt.Println(strconv.Itoa(v));
		delete(maps,"4");
	}
	for i,v:=range maps{
		fmt.Println(reflect.TypeOf(i));
		fmt.Println("%t  ====",i);
		fmt.Println(reflect.TypeOf(v));
		fmt.Println(strconv.Itoa(v));
	}
	fmt.Println(aa.Aa);
}
