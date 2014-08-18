// xml project main.go
package main

import (
	"fmt" 
	"encoding/xml"
	"io/ioutil"
    "os"

)
type Recurlyservers struct {
    XMLName     xml.Name `xml:"servers"`
    Version     string   `xml:"version,attr"`
    Svs         []server `xml:"server"`
    Description string   `xml:",innerxml"`
}
type server struct {
    XMLName    xml.Name `xml:"server"`
    ServerName string   `xml:"serverName"`
    ServerIP   string   `xml:"serverIP"`
}
func main() {
    file, err := os.Open("test.xml") // For read access.     
    if err != nil {
        fmt.Printf("error: %v", err)
        return
    }
    defer file.Close()
    data, err := ioutil.ReadAll(file)
    if err != nil {
        fmt.Printf("error: %v", err)
        return
    }
    v := Recurlyservers{}
    err = xml.Unmarshal(data, &v)
    if err != nil {
        fmt.Printf("error: %v", err)
        return
    }
   // fmt.Println(v);
	fmt.Println(v.Description);
	fmt.Println(v.Version);
	fmt.Println(v.XMLName);
	for k,v:=range v.Svs{
		fmt.Println(k,v.ServerName,v.ServerIP);
	}
	
}
