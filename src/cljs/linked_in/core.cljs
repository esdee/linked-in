(ns linked-in.core
  (:use [crate.core :only (html)]
        [domina :only (swap-content!)]
        [domina.xpath :only (xpath)])
  (:require [clojure.browser.repl :as repl]
            [cljs.reader :as reader]
            [one.browser.remote :as remote])
  (:require-macros [linked-in.app.view-macros.profile :as profile]))


(def content-node (xpath "//div[@class='content']"))

(defn block-section
  [id]
  {:id (name id) :class "section" :style "display:block"})

(defn full-name
  [profile]
  (str (:firstName profile) " " (:lastName profile)))

(defn overview
  [profile]
  (let [positions (-> profile :positions :values)
        educations (-> profile :educations :values)
        pos-details (fn [p] [:li (:title p) [:span.at " at "]
                             (-> p :company :name)])]
    [:dl#overview
     [:dt.summary-current {:style "display:block"} "Current"]
     [:dd.summary-current {:style "display:block"}
      [:ul.current (pos-details (first positions))]]
     [:dt.summary-past {:style "display:block"} "Past"]
     [:dd.summary-past {:style "display:block"}
     [:ul.past (map pos-details (take 3 (rest positions)))]]
     [:dt.summary-education {:style "display:block"} "Education"]
     [:dd.summary-education {:style "display:block"}
      [:ul (map (fn [e] [:li (:schoolName e)]) educations)]]]))

(defn profile-header
  [profile]
  [:div.profile-header
   [:div#profile-picture.image {:style "display:block"}
    [:img {:src (:pictureUrl profile)
           :width 80 :height 80
           :alt (full-name profile)}]]
   [:h1
    [:span#name {:class "n fn"}
     [:span.given-name (:firstName profile)]
     [:span.family-name (:lastName profile)]]]
   [:p.title {:style "display:block"}
    (:headline profile)]
   [:dl#headline {:class "demographic-info adr"}
    [:dt "Location"] [:dd.locality (get-in profile [:location :name])]
    [:dt "Industry"] [:dd.industry (:industry profile)]]
   [:h2.section-title (str (full-name profile) "'s Overview")]
    (overview profile)])

(defn profile-summary
  [profile]
  [:div (block-section :profile-summary)
   "Profile Summary"])

(defn profile-skills
  [profile]
  [:div (block-section :profile-skills)
   "Profile Skills"])

(defn profile-experience
  [profile]
  [:div (block-section :profile-experience)
   "Profile Experience"])

(defn template
  [profile]
  [:div.content
   (profile-header profile)
   (profile-summary profile)
   (profile-skills profile)
   (profile-experience profile)])

(defn show-profile
  [profile]
  (->> profile
    template
    html
    (swap-content! content-node)))

(defn start
  []
  (remote/request
    100
    "profiles/client/100"
    :on-success #(->> % :body reader/read-string show-profile)
    :on-error #(->> % :body (str "*ERROR* ") js/alert)))

(defn ^:export repl
  []
  (repl/connect "http://localhost:9000/repl"))
