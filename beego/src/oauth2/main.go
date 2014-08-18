package main

import (
	"code.google.com/p/goauth2/oauth"
	"encoding/json"
	"html/template"
	"io/ioutil"
	"log"
	"net/http"
)

var (
	notAuthenticatedTemplate = template.Must(template.ParseFiles("auth.html"))
	userInfoTemplate         = template.Must(template.ParseFiles("detail.html"))
)

var oauthCfg = &oauth.Config{
	// https://code.google.com/apis/console
	ClientId:     "540736938865.apps.googleusercontent.com",
	ClientSecret: "IqRUVfii1XQ9l8m8MrKXay8r",
	AuthURL:      "https://accounts.google.com/o/oauth2/auth",
	TokenURL:     "https://accounts.google.com/o/oauth2/token",
	RedirectURL:  "http://nanhaizh.cn/oauth2callback",
	Scope:        "https://www.googleapis.com/auth/userinfo.profile",
}

const profileInfoURL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json"

func handleRoot(w http.ResponseWriter, r *http.Request) {
	if err := notAuthenticatedTemplate.Execute(w, nil); err != nil {
		log.Fatal("handleRoot: ", err.Error())
		return
	}
}

func handleAuthorize(w http.ResponseWriter, r *http.Request) {
	lang := r.Header.Get("Accept-Language")
	url := oauthCfg.AuthCodeURL(lang)

	http.Redirect(w, r, url, http.StatusFound)
}

func handleOAuth2Callback(w http.ResponseWriter, r *http.Request) {
	code := r.FormValue("code")

	t := &oauth.Transport{oauthCfg, nil, nil}

	t.Exchange(code)

	resp, err := t.Client().Get(profileInfoURL)
	if err != nil {
		log.Fatal("Get: ", err.Error())
		return
	}

	defer resp.Body.Close()
	bytes, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		log.Fatal("ReadAll: ", err.Error())
		return
	}

	type auth struct {
		ID         string `json:"id"`
		Name       string `json:"name"`
		GivenName  string `json:"given_name"`
		FamilyName string `json:"family_name"`
		Link       string `json:"link"`
		Picture    string `json:"picture"`
		Gender     string `json:"gender"`
		Birthday   string `json:"birthday"`
		Locale     string `json:"locale"`
	}

	var (
		Auth  auth
		Auths []auth
	)

	if err := json.Unmarshal(bytes, &Auth); err != nil {
		log.Fatal("Unmarshal: ", err.Error())
		return
	}

	for i := 0; i < 10; i++ {
		Auths = append(Auths, Auth)
	}

	if err := userInfoTemplate.Execute(w, &Auths); err != nil {
		log.Fatal("userInfoTemplate.Execute: ", err.Error())
		return
	}
}

func main() {
	http.HandleFunc("/", handleRoot)
	http.HandleFunc("/authorize", handleAuthorize)

	http.HandleFunc("/oauth2callback", handleOAuth2Callback)

	http.ListenAndServe(":8080", nil)
}
