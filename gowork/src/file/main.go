// file project main.go
package main

import (
    "fmt"
    "os"
)
func main() {
    os.Mkdir("astaxie", 0777);
    os.MkdirAll("astaxie/test1/test2", 0777);
    err := os.Remove("astaxie");
    if err != nil {
        fmt.Println(err);
    }
    os.RemoveAll("astaxie");
	
	userFile := "astaxie.txt"
    fout, err := os.Create(userFile)
    defer fout.Close()
    if err != nil {
        fmt.Println(userFile, err)
        return
    }
    for i := 0; i < 10; i++ {
        fout.WriteString("Just a test!\r\n")
        fout.Write([]byte("Just a test!\r\n"))
    }
	fl, err := os.Open(userFile)
    defer fl.Close()
    if err != nil {
        fmt.Println(userFile, err)
        return
    }
	buf := make([]byte, 1024)
    for {
        n, _ := fl.Read(buf)
        if 0 == n {
            break
        }
        os.Stdout.Write(buf[:n])
    }
}
