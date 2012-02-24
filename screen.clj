lein repl

; start the server
(load "linked_in/dev_server")
(linked-in.dev-server/run-server)

; start the browser repl
(require '[cljs.repl]
         '[cljs.repl.browser])
(cljs.repl/repl (cljs.repl.browser/repl-env))

(js/alert "Hello World")



