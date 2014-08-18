Goop
====

![Goopie](https://raw.githubusercontent.com/nitrous-io/goop/master/goopie.png)

A dependency manager for Go (golang), inspired by Bundler. It is different from other dependency managers in that it does not force you to mess with your `GOPATH`.

### Getting Started

1. Install Goop: `go get github.com/nitrous-io/goop`

2. Create `Goopfile`. Revision reference (e.g. Git SHA hash) is optional, but recommended. Prefix hash with `#`. (This is to futureproof the file format.)

   Example:
   ```
   github.com/mattn/go-sqlite3
   github.com/gorilla/context #14f550f51af52180c2eefed15e5fd18d63c0a64a
   github.com/gorilla/mux #854d482e26505d59549690719cbc009f04042c2e
   ```

3. Run `goop install`. This will install packages inside a subdirectory called `.vendor` and create `Goopfile.lock`, recording exact versions used for each package. Subsequent `goop install` runs will ignore `Goopfile` and install the versions specified in `Goopfile.lock`. You should check this file in to your source version control. It's a good idea to add `.vendor` to your version control system's ignore settings (e.g. `.gitignore`).

4. Run commands using `goop exec` (e.g. `goop exec go run main.go`). This will execute your command in an environment that has correct `GOPATH` and `PATH` set.

5. Go commands can be run without the `exec` keyword (e.g. `goop go test`).

### Other commands

* Run `goop update` to ignore an existing `Goopfile.lock`, and update to latest versions of packages (as specified in `Goopfile`).

* Running `eval $(goop env)` will modify `GOPATH` and `PATH` in current shell session, allowing you to run commands without `goop exec`.

### Caveat

Goop currently only supports Git and Mercurial. This should be fine for 99% of the cases, but you are more than welcome to make a pull request that adds support for Subversion and Bazaar.

- - -
Copyright (c) 2014 Irrational Industries, Inc. d.b.a. Nitrous.IO.<br>
This software is licensed under the [MIT License](http://github.com/nitrous-io/goop/raw/master/LICENSE).
