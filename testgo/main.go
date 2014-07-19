package main

import (
	"fmt"
	"html/template"
	"io"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"path"
	"path/filepath"
	"runtime/debug"
	//"strconv"
	"strings"
	"testgo/models"
)

const (
	ListDir      = 0x0001
	UPLOAD_DIR   = "./uploads"
	TEMPLATE_DIR = "./views"
)

var templates = make(map[string]*(template.Template))

func init() {
	fileInfoArr, err := ioutil.ReadDir(TEMPLATE_DIR)
	Check(err)
	var templateName, templatePath string
	for _, fileInfo := range fileInfoArr {
		templateName = fileInfo.Name()
		if ext := path.Ext(templateName); ext != ".html" {
			continue
		}
		templatePath = TEMPLATE_DIR + "/" + templateName
		t := template.Must(template.ParseFiles(templatePath))
		templates[templateName] = t
	}
}

func Check(err error) {
	if err != nil {
		panic(err)
	}
}

func RenderHtml(w http.ResponseWriter, tmpl string, data map[string]interface{}) {
	fmt.Println("qqqqqq：", tmpl)
	if _, ok := templates[tmpl+".html"]; !ok {
		fmt.Println("在模板库中找不到：", tmpl)
	}
	err := templates[tmpl+".html"].Execute(w, data)
	Check(err)
}
func IsExits(path string) bool {
	_, err := os.Stat(path)
	if err == nil {
		return true
	}
	return os.IsExist(err)
}
func UploadHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method == "GET" {
		RenderHtml(w, "upload", nil)
	}
	if r.Method == "POST" {
		f, h, err := r.FormFile("image")
		Check(err)
		fileName := h.Filename
		defer f.Close()
		name := filepath.Join(UPLOAD_DIR, fileName)
		t, err := os.OpenFile(name, os.O_RDWR|os.O_CREATE|os.O_EXCL, 0600)
		defer t.Close()
		_, errs := io.Copy(t, f)
		Check(errs)
		http.Redirect(w, r, "/view?id="+fileName, http.StatusFound)
	}
}
func ViewHandler(w http.ResponseWriter, r *http.Request) {
	imageId := r.FormValue("id")
	imagPath := UPLOAD_DIR + "/" + imageId
	if exists := IsExits(imagPath); !exists {
		http.NotFound(w, r)
	}
	w.Header().Set("Content-Type", "image")
	http.ServeFile(w, r, imagPath)
}
func ListHandler(w http.ResponseWriter, r *http.Request) {
	if r.URL.Path == "/favicon.ico" {
		return
	}
	fileInfoArr, err := ioutil.ReadDir("./uploads")
	Check(err)
	data := make(map[string]interface{})
	images := []string{}
	fmt.Println("r.URL.Path::::", r.URL.Path)
	for _, fileinfo := range fileInfoArr {
		if len(strings.Trim(fileinfo.Name(), " ")) > 0 {
			fmt.Println("fileinfo.Name()::::", fileinfo.Name())
			images = append(images, fileinfo.Name())
		}
	}
	data["images"] = images
	RenderHtml(w, "list", data)
}
func SafeHandler(fn http.HandlerFunc) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		defer func() {
			if e, ok := recover().(error); ok {
				http.Error(w, e.Error(), http.StatusInternalServerError)
				log.Println("Warn: panic in %v. -%v", fn, e)
				log.Println(string(debug.Stack()))
			}
		}()
		fn(w, r)
	}
}
func DownloadHandler(w http.ResponseWriter, r *http.Request, file string, filename ...string) {
	output := w.Header()
	output.Set("Content-Description", "File Transfer")
	output.Set("Content-Type", "application/octet-stream")
	if len(filename) > 0 && filename[0] != "" {
		output.Set("Content-Disposition", "attachment; filename="+filename[0])
	} else {
		output.Set("Content-Disposition", "attachment; filename="+filepath.Base(file))
	}
	output.Set("Content-Transfer-Encoding", "binary")
	output.Set("Expires", "0")
	output.Set("Cache-Control", "must-revalidate")
	output.Set("Pragma", "public")
	http.ServeFile(w, r, file)
}
func StaticDirHandler(mux *http.ServeMux, perfix string, staticDir string, flags int) {
	mux.HandleFunc(perfix, func(w http.ResponseWriter, r *http.Request) {
		file := staticDir + r.URL.Path[len(perfix)-1:]
		if (flags & ListDir) == 0 {
			if exists := IsExits(file); !exists {
				http.NotFound(w, r)
				return
			}
		}
		http.ServeFile(w, r, file)
	})
}
func Downloadfile(w http.ResponseWriter, r *http.Request) {
	//获取请求信息，决定下载那个图片
	//r.URL. 或者r.PostForm
	DownloadHandler(w, r, UPLOAD_DIR+"/"+"tulips.jpg", "bb.jpg")
}
func Forward(w http.ResponseWriter, r *http.Request) {
	http.Redirect(w, r, "/upload", http.StatusFound)
}
func Redirect(w http.ResponseWriter, r *http.Request) {
	output := w.Header()
	output.Set("Location", localurl)
	w.WriteHeader(status)
}
func main() {
	models.RawTable()
	mux := http.NewServeMux()
	StaticDirHandler(mux, "/assets", "./public", 0)
	mux.HandleFunc("/", SafeHandler(ListHandler))
	mux.HandleFunc("/view", SafeHandler(ViewHandler))
	mux.HandleFunc("/upload", SafeHandler(UploadHandler))
	mux.HandleFunc("/download", SafeHandler(Downloadfile))
	err := http.ListenAndServe(":8080", mux)
	if err != nil {
		log.Fatal("ListenAndServe: ", err.Error())
	}
}
