(ns linked-in.core
  (:use [crate.core :only (html)]
        [domina :only (swap-content!)]
        [domina.xpath :only (xpath)])
  (:require [clojure.browser.repl :as repl]
            [cljs.reader :as reader]
            [one.browser.remote :as remote])
  (:require-macros [linked-in.app.view-macros.profile :as profile]))

(def content-node (xpath "//div[@class='content']"))

(defn show-profile
  [profile]
  (->> profile
    profile/template
    html
    (swap-content! content-node)))

(defn ^:export start
  []
  (remote/request
    100
    "profiles/client/100"
    :on-success #(->> % :body reader/read-string show-profile)
    :on-error #(->> % :status (str "*ERROR* ") js/alert)))

(defn ^:export repl
  []
  (repl/connect "http://localhost:9000/repl"))
