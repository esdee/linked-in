(defproject linked-in "1.0.0-SNAPSHOT"
  :description "clojurescript solution to the issues posed by linked in
               http://engineering.linkedin.com/frontend/client-side-templating-throwdown-mustache-handlebars-dustjs-and-more"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [ring "1.0.2"]
                 [net.cgrand/moustache "1.1.0"] ; minimal routing
                 [ring-reload-modified "0.1.1"] ; reload modified clj files
                 [hiccup "0.3.8"]               ; server side html generation
                 [cheshire "2.2.0"]             ; json parsing/generating
                 [domina "1.0.0-alpha1"]        ; dom manipulation
                 [crate "0.1.0-SNAPSHOT"]       ; client side html generation
                ]
  :plugins [[lein-cljsbuild "0.0.14"]]          ; build clojure script files
  :cljsbuild {:source-path "src/cljs"
              :compiler
                  {:output-dir "public/javascripts/out"
                   :output-to "public/javascripts/maind.js"
                   :optimizations :simple
                   :pretty-print true}}
  :dev-dependencies [[lein-git-deps "0.0.1-SNAPSHOT"]] ; download source from github
  :git-dependencies [["https://github.com/clojure/clojurescript.git"]]
  :source-path "src/clj"
  :extra-classpath-dirs [".lein-git-deps/clojurescript/src/clj"
                         ".lein-git-deps/clojurescript/src/cljs"
                         "src/cljs"])
