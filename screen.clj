lein repl

; start the server
(load "linked_in/dev_server")
(linked-in.dev-server/run-server)

; start the browser repl
(require '[cljs.repl]
         '[cljs.repl.browser])
(cljs.repl/repl (cljs.repl.browser/repl-env))

(js/alert "Hello World")

; core.js
(in-ns 'linked-in.core)

(def profile (atom nil))

(defn start
  []
  (remote/request
    100
    "profiles/client/100"
    :on-success #(->> % :body reader/read-string (reset! profile))
    :on-error #(js/alert "Error")))

(start)

(:lastName @profile)

(def content-node (xpath "//div[@class='content']"))

(defn names
  [profile]
  )
(defn profile-template
  [profile]
  [:div.content
    [:div.names (str (:firstName profile) " "
                     (:lastName profile))]
   ])

(swap-content! content-node
               (html (profile-template @profile)))


(load "linked_in/dev_server")

(require '[linked-in.dev-server :as ds])

(ds/profile-client nil nil)

(load "linked_in/app/view_macros/profile")

(require '[linked-in.app.view-macros.profile :as profile])

(profile/template {:lastName "Dass" :firstName "Shashy"})
