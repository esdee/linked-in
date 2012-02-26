(require '[cljs.repl]
         '[cljs.repl.browser])
(cljs.repl/repl (cljs.repl.browser/repl-env))

(js/alert "Hello World")

