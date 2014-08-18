// des project main.go
package main

import (
	"fmt"
	"encoding/base64"
	"os"
	"crypto/des"
)

func main() {
	fmt.Println("Hello World!");
	//加密
	data := []byte("any + old & data");
	str := base64.StdEncoding.EncodeToString(data);
	fmt.Println(str);
	// Output:
	//go:   YW55ICsgb2xkICYgZGF0YQ==
	//java :YW55ICsgb2xkICYgZGF0YQ==
  //解密
	str2 := "YW55ICsgb2xkICYgZGF0YQ==";
	data2, err := base64.StdEncoding.DecodeString(str2);
	if err != nil {
		fmt.Println("error:", err);
		return;
	}
	fmt.Println(string(data2),"\r\n");
	fmt.Println("=================11==========================");
	input := []byte("foo\x00bar");
	encoder := base64.NewEncoder(base64.StdEncoding, os.Stdout);
	encoder.Write(input);
	encoder.Close();
	//fmt.Println("\r\n","=================22==========================");
	input2 := []byte("rgKGIX2V41ziTohudiJhmvApV+XOb4rEy3OtJVqFIB/rnxcqs9PbBGEekPVmsBGHbsw4fvZgai8HuQDcr2NSBkyJvD0Fs286tSatLTTxvVfmxmVDBxkS4Q==");
	encoder2 := base64.NewEncoder(base64.StdEncoding, os.Stdout);
	encoder2.Write(input2);
	encoder2.Close();
	fmt.Println("\r\n","=================33==========================");
	encoder3 := base64.NewDecoder(base64.StdEncoding, os.Stdout);
	
	encoder3.Read(input2);
	fmt.Println("以上是base64加密测试代码");
	
	
		
	key := []byte("12345678");
	dataEN:=[]byte("87654321");
	fmt.Println("加密的元数据dataEN",dataEN);
	c, _ := des.NewCipher(key);//加密的密钥
	out1:=make([]byte,len(dataEN));//创建加密后的数组切片，用于存储加密后的数据
	c.Encrypt(out1, dataEN);//执行数据加密
	fmt.Println("加密后",out1);//输出加密后的数据
	out2:=make([]byte,len(out1));//创建解密后的数组切片，用于存储解密后的数据
	c.Decrypt(out2, out1);//执行解密数据
	fmt.Println("解密后:",out2);//输出解密后的数据
	
	
}
