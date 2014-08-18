package main

import (
	"fmt"
	"io/ioutil"
	"net/http"
	"os"
	// "log"
	"io"
	"path/filepath"
	"regexp"
	"strings"
)

var destDir = "E:/kuaipan/templateCopyer/template"
var img_ext = []string{".jpg", ".gif", ".png", ".jpeg", ".JPG", ".GIF", ".PNG", ".JPEG", ".ico", ".ICO"}
var css_ext = []string{".css", ".less", ".CSS", ".LESS"}
var js_ext = []string{".js", ".JS"}
var extArray = []string{".jpg", ".gif", ".png", ".jpeg", ".css", ".less", ".js", ".ico"}

// append(img_ext,css_ext,js_ext)
var desturl = "http://www.36kr.com"
var destrooturl = "http://www.36kr.com"

func main() {
	html := get_resource(desturl)
	resource_link := ""
	src_resource_list, linkhref_resource_list := html_get_resource_list(html)

	for k := range src_resource_list {
		resource_link = down_resource(src_resource_list[k])
		html = strings.Replace(html, src_resource_list[k], resource_link, -1)
	}

	for k := range linkhref_resource_list {
		resource_link = down_resource(linkhref_resource_list[k])
		html = strings.Replace(html, linkhref_resource_list[k], resource_link, -1)
	}

	ioutil.WriteFile(destDir+"/index.html", []byte(html), 777)

	// /fmt.Println(src_resource_list,linkhref_resource_list)
}

func down_resource(url string) string {

	dir := destDir
	filetype := ""
	if in_array(filepath.Ext(url), img_ext) {
		filetype = "images"
	} else if in_array(filepath.Ext(url), css_ext) {
		filetype = "css"
	} else if in_array(filepath.Ext(url), js_ext) {
		filetype = "js"
	}
	dir = dir + "/" + filetype + "/"

	filename := get_true_filename(url)
	fullfilename := dir + filename
	fmt.Println(fullfilename)
	fmt.Println(url)

	out, _ := os.Create(fullfilename)
	// out, _ := os.Create("F:\\kuaipan\\templateCopyer\\template\\images\\9b6493956db5cc890654a6b21bb2d77e.gif")
	defer out.Close()

	if strings.HasPrefix(url, "/") {
		url = destrooturl + url
	}

	if !strings.HasPrefix(url, "/") && !strings.HasPrefix(url, "http") && !strings.HasPrefix(url, "https") {
		url = desturl + "/" + url
	}

	resp, err := http.Get(url)
	// resp, err := http.Get("http://a.36krcnd.com/photo/9b6493956db5cc890654a6b21bb2d77e.gif")
	if err != nil {
		fmt.Println(err)
		os.Exit(0)
	}
	defer resp.Body.Close()
	io.Copy(out, resp.Body)

	return filetype + "/" + filename

}

func get_true_filename(url string) string {
	filename := filepath.Base(url)
	extname := filepath.Ext(url)
	for k := range extArray {
		if strings.Contains(extname, extArray[k]) {
			true_filename := strings.Replace(filename, extname, extArray[k], -1)
			return true_filename
		}
	}
	return "nil"
}

func html_get_resource_list(content string) ([]string, []string) {
	re, _ := regexp.Compile("\\<[\\S\\s]+?\\>")
	content = re.ReplaceAllStringFunc(content, strings.ToLower)

	src_resource_list := html_src_resource_list(content)
	href_resource_list := html_linkhref_resource_list(content)
	return src_resource_list, href_resource_list
}

func css_resource_list(content string) []string {
	re, _ := regexp.Compile("src\\s*=\\s*\"([\\S\\s]+?)\"") //src\s*=\s*(["|'])([\S\s]+?)\1
	resource_list_raw := re.FindAllStringSubmatch(content, -1)
	resource_list := []string{}
	for _, v := range resource_list_raw {
		if !in_array(strings.ToLower(filepath.Ext(v[1])), extArray) {
			continue
		}
		resource_list = append(resource_list, v[1])
	}

	re, _ = regexp.Compile("src\\s*=\\s*'([\\S\\s]+?)'") //src\s*=\s*(["|'])([\S\s]+?)\1
	resource_list_raw = re.FindAllStringSubmatch(content, -1)
	for _, v := range resource_list_raw {
		if !in_array(strings.ToLower(filepath.Ext(v[1])), extArray) {
			continue
		}
		resource_list = append(resource_list, v[1])
	}

	return resource_list
}

func html_src_resource_list(content string) []string {

	re, _ := regexp.Compile("src\\s*=\\s*\"([\\S\\s]+?)\"") //src\s*=\s*(["|'])([\S\s]+?)\1
	resource_list_raw := re.FindAllStringSubmatch(content, -1)
	resource_list := []string{}
	for _, v := range resource_list_raw {
		if !in_array(strings.ToLower(filepath.Ext(v[1])), extArray) {
			continue
		}
		resource_list = append(resource_list, v[1])
	}

	re, _ = regexp.Compile("src\\s*=\\s*'([\\S\\s]+?)'") //src\s*=\s*(["|'])([\S\s]+?)\1
	resource_list_raw = re.FindAllStringSubmatch(content, -1)
	for _, v := range resource_list_raw {
		if !in_array(strings.ToLower(filepath.Ext(v[1])), extArray) {
			continue
		}
		resource_list = append(resource_list, v[1])
	}

	return resource_list

}

func html_linkhref_resource_list(content string) []string {

	extArray := []string{".jpg", ".gif", ".png", ".jpeg", ".css", ".less"}

	re, _ := regexp.Compile("<\\s*link([\\S\\s]+?)href\\s*=\\s*\"([\\S\\s]+?)\"") //src\s*=\s*(["|'])([\S\s]+?)\1
	resource_list_raw := re.FindAllStringSubmatch(content, -1)
	resource_list := []string{}
	for _, v := range resource_list_raw {
		if !in_array(strings.ToLower(filepath.Ext(v[2])), extArray) {
			continue
		}
		resource_list = append(resource_list, v[2])
	}

	re, _ = regexp.Compile("<\\s*link([\\S\\s]+?)href\\s*=\\s*'([\\S\\s]+?)'") //src\s*=\s*(["|'])([\S\s]+?)\1
	resource_list_raw = re.FindAllStringSubmatch(content, -1)
	for _, v := range resource_list_raw {
		if !in_array(strings.ToLower(filepath.Ext(v[2])), extArray) {
			continue
		}
		resource_list = append(resource_list, v[2])
	}

	return resource_list

}

/*func css_get_resource_list(content string,ext string) []string{

}*/

func get_resource(url string) string {
	res, err := http.Get(url)
	if err != nil {
		// log.Println(err)
		os.Exit(0)
	}
	defer res.Body.Close()

	body, _ := ioutil.ReadAll(res.Body)
	return string(body)
	// log.Println(string(body))
}

func in_array(v string, array []string) bool {
	for k := range array {
		if strings.Contains(v, array[k]) {
			return true
		}
	}
	return false
}
