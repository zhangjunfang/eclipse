// methods project methods.go
package methods

import "math"
type Rectange struct{
	
	Width float32;
	Heigth float32;
}

type Circle struct{
	
	Radius float32;
	
}

func (r Rectange) Area() (s float32){
	
	return r.Heigth*r.Width;
}
func (c Circle) Area() (s float32){
	return c.Radius*c.Radius* math.Pi;
	
}

type Array []interface{};

func (a *Array) Add(b ...interface{}){
	
	copy(*a,b);
}
type Months map[string]int ;



func (a *Array) M(b string)(c int) {
	m:=Months{"J":31,"F":28};
	return m[b];
}