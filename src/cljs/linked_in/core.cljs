(ns linked-in.core
  (:use [crate.core :only (html)]
        [domina :only (swap-content!)]
        [domina.xpath :only (xpath)])
  (:require [clojure.browser.repl :as repl]
            [cljs.reader :as reader]
            [one.browser.remote :as remote])
  (:require-macros [linked-in.app.view-macros.profile :as profile]))

; This is where the profile will be displayed
(def content-node (xpath "//div[@class='content']"))

(defn show-profile
  [profile]
  (->> profile
    profile/template
    html
    (swap-content! content-node)))

; Retrieve the clojure map from the server and display it
(defn ^:export start
  []
  (remote/request
    100
    "profiles/client/100"
    :on-success #(->> % :body reader/read-string show-profile)
    :on-error #(->> % :status (str "*ERROR* ") js/alert)))

; for Browser based repl
(defn ^:export repl
  []
  (repl/connect "http://localhost:9000/repl"))
