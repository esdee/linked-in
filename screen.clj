lein repl

; start the server
(load "linked_in/dev_server")
(linked-in.dev-server/run-server) start the browser repl
(require '[cljs.repl]
         '[cljs.repl.browser])
(cljs.repl/repl (cljs.repl.browser/repl-env))

(js/alert "Hello World")

; macro
(load "linked_in/app/view_macros/profile")

(linked-in.app.view-macros.profile/template {:lastName "Shashy"})

(require '[clojure.string :as str])

(doc str/replace)

(str/replace "hello*world" "*" "<br>")
