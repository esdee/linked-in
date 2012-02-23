(ns linked-in.core
  (:require [clojure.browser.repl :as repl]
            [cljs.reader :as reader]
            ;[one.browser.remote :as remote]
            ;[single-page.view :as view]
))

(comment
 (defn ^:export start
  []
  (remote/request
    100
    "profiles/client/100"
    :on-success #(-> % :body reader/read-string view/single-profile)))
)


(defn ^:export repl
  "Connects to a ClojureScript REPL running on localhost port 9000.
  browser for evaluation. This function should be called from a script
  in the development host HTML page."
  []
  (repl/connect "http://localhost:9000/repl"))
