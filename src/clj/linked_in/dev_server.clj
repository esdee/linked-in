(ns linked-in.dev-server
  "The server used in development mode"
  (:use [ring.adapter.jetty :only (run-jetty)]
        [ring.middleware.file :only (wrap-file)]
        [ring.middleware.file-info :only (wrap-file-info)]
        [ring.middleware.reload-modified :only (wrap-reload-modified)]
        [ring.middleware.stacktrace :only (wrap-stacktrace)]
        [ring.util.response :only (response)]
        [net.cgrand.moustache :only (app delegate)]
        [hiccup.core :only (html)]
        [hiccup.page-helpers :only (include-css include-js javascript-tag link-to)]
        [cheshire.core :only (parse-string)])
  ;(:require [single-page.views.profiles :as profiles])
  )

(defn- page
  [_]
  (let [js-files [ "javascripts/maind.js"]
        js-scripts   ["linked_in.core.repl();"];  (get-in config [:js @*mode*])
        page-data (html
         [:head
          [:title "Single Page"]
          ;(include-css "css/single-page.css")
          [:link {:rel "shortcut icon" :href "img/favicon.ico"}]]
         [:body
          [:div.container [:div.content "Profile Goes Here"]]
          (map include-js js-files)
          (map javascript-tag js-scripts)])]
    (response page-data)))

(comment
(defn- profile
  [_ id]
  (let [profile (parse-string (slurp "data/profile.json") true)]
    (response (profiles/single-profile profile))))

(defn- profile-client
  [_ id]
  (let [profile (parse-string (slurp "data/profile.json") true)]
    (response (pr-str profile))))
)

(def app-routes
  (app
    :get [
      [""] (delegate page)
     ;["profiles" "client" id] (delegate profile-client id)
     ;["profiles" id] (delegate profile id)
    ]))

(def my-app (-> app-routes
              (wrap-file "public")
              wrap-file-info
              wrap-stacktrace
              (wrap-reload-modified ["src/clj/"])))

(defn run-server
  "Start the development server on port 8080"
  []
  (run-jetty (var my-app)
             {:join? false :port 8080}))
