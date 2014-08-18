// a0001 project main.go
package main

import (
	"fmt"
	"strconv"
)

func test(arg ...int8) {

	for k, v := range arg {
		fmt.Println(k, v)
	}

}
func main() {
	fmt.Println("Hello World!")

	var aa string = "fsdfsdfsdfsd"
	fmt.Println(aa[2:len(aa)])
	x := []int8{1, 2, 3, 4, 5, 6, 7, 8}
	test(x[2:len(x)]...)
	fmt.Println(strconv.Itoa(444))

}
