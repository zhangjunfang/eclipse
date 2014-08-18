    package main

import (
 "fmt"
 "os"
 "log"
 "net/http"
 "io/ioutil"
 "bytes"
 "encoding/base64"
 "path"
 "strings"
 "regexp"
)

const (
   BASEURL = "http://blog.csdn.net/xushiweizh";
   PATTERN = "href=\"([^<\"]+)\"";
   INDEX = len(BASEURL);
   ROOT = "d:/temp";
)

func getContent(url string) string{
     resp,err := http.Get(url);
     if err != nil{
        //handle error
        log.Fatal(err);
     }
     defer resp.Body.Close();
     body,err := ioutil.ReadAll(resp.Body);
     return string(body);
}

func base64Encoding(s string)string{
     var buf bytes.Buffer;
     encoder := base64.NewEncoder(base64.StdEncoding, &buf);
     defer encoder.Close();
     encoder.Write([]byte(s));
     return buf.String();
}

func saveFile(content,filename string)error{
     base64Name  := base64Encoding(filename);
     if base64Name == "" {
        return nil;
     }
     fout,err := os.Create(path.Join(ROOT,base64Name))
     defer fout.Close();
     if err != nil{
        //handler error
        log.Fatal(err);
        return err;
     }
     for _,line := range strings.Split(content,"\r\n"){
        fout.WriteString(line + "\r\n");
     }
     return nil;
}

func save(url string,links map[string]bool){
    content := getContent(url);
    err := saveFile(content,url[INDEX:]);
    if err != nil{
       log.Fatal(err);
    } 
    links[url]=true
}

func traverse(regxp *regexp.Regexp, url string, links map[string]bool){
   LAST := strings.LastIndex(BASEURL,"/");
     for _, scan := range regxp.FindAllStringSubmatch(getContent(BASEURL),-1){
        var url string;
        if strings.Contains(scan[1],"http://"){
           if strings.Contains(scan[1],BASEURL){
               url = scan[1]  ;
           }else{
             continue;
           } 
        }else{
           if strings.Contains(scan[1],BASEURL[LAST:]){
               url = BASEURL[:LAST]+scan[1];
           }else{
             continue;
           }
        }
        if v,ok := links[url];ok{
           if v == true {
              fmt.Println("Has download");
           }else{
              save(url,links) ;
           }
        }else{
           save(url,links);
        }  
     }
}

func main(){
   links := make(map[string] bool);
   regxp,err := regexp.Compile(PATTERN);
   if err != nil{
      log.Fatal(err);
   }
   traverse(regxp,BASEURL,links);
   for key,val := range links{
       if !val{
          traverse(regxp,key,links);
       }
   }
}


    