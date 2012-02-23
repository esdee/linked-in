(ns linked-in.app.view-macros.profile)

(defmacro names
  [profile]
  `[:span#name {:class "n fn"}
     [:span.given-name (:firstName ~profile)]
     [:span " ! "]
     [:span.last-name (:lastName ~profile)]])

(defmacro template
  [profile]
  `[:div.content
    (names ~profile)])
