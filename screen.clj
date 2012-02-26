lein repl

; start the server
(load "linked_in/dev_server")
(linked-in.dev-server/run-server)

;start the browser repl
(require '[cljs.repl]
         '[cljs.repl.browser])
(cljs.repl/repl (cljs.repl.browser/repl-env))

(js/alert "Hello World")

; macro
(load "linked_in/app/view_macros/profile")

(linked-in.app.view-macros.profile/template {:lastName "Shashy"})

(require '[clojure.string :as str])

(doc str/replace)

(str/replace "hello\n\n*world" "\\n" "<b>")

(use '[cheshire.core :only (parse-string)])

(def profile (parse-string (slurp "data/profile.json") true))

(def summary (-> profile :positions :values second :summary))

(def lst (str/split summary #"\n"))
(count lst)

(use '[hiccup.core :only (html)])

(def para [:p.desc (map #(list %1 %2)
                 (str/split summary #"\n")
                 (repeat [:br]))])

(take 1 para)

para

(html
  )
