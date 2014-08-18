// stundent project stundent.go
package stundent


type Human struct{
	
	Name string;
	Age int;
	Wigth float32;
	
	
}

type Student struct{
	
	Human ;
	
	Id string;
	
}

type Address struct{
	
	Man  Student  ;
	
	Addr  [] string;
	
}
