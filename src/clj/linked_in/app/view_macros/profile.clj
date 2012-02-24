(ns linked-in.app.view-macros.profile
  (:require [clojure.string :as str :only (replace)]))

(defmacro block-section
  [id]
  {:id `(name ~id)
   :class "section" :style "display:block"})

(defmacro full-name
  [profile]
  `(str (:firstName ~profile) " " (:lastName ~profile)))

(defmacro overview
  [profile]
  `(let [positions# (-> ~profile :positions :values)
         pos-details# (fn [p#]
                        [:li (:title p#) [:span.at " at "] (-> p# :company :name)])
         site-details# (fn [w#]
                         [:li [:a {:href (:url w#) :target "_blank"} (:name w#)]])]
    [:dl#overview
     [:dt.summary-current {:style "display:block"} "Current"]
     [:dd.summary-current {:style "display:block"}
      [:ul.current (pos-details# (first positions#))]]
     [:dt.summary-past {:style "display:block"} "Past"]
     [:dd.summary-past {:style "display:block"}
      [:ul.past (map pos-details# (take 3 (rest positions#)))]]
     [:dt.summary-education {:style "display:block"} "Education"]
     [:dd.summary-education {:style "display:block"}
      [:ul (map (fn [e#] [:li (:schoolName e#)])
                (-> ~profile :educations :values))]]
     [:dt "Connections"]
     [:dd.overview-connections
      [:p [:strong (-> ~profile :connections :_total)] " connections"]]
     [:dt.websites "Websites"]
     [:dd.websites
      [:ul (map site-details# (-> ~profile :memberUrlResources :values))]]]))

(defmacro profile-header
  [profile]
  `[:div.profile-header
     [:div#profile-picture.image {:style "display:block"}
      [:img {:src (:pictureUrl ~profile)
             :width 80 :height 80
             :alt (full-name ~profile)}]]
     [:h1
      [:span#name {:class "n fn"}
       [:span.given-name (:firstName ~profile)]
       [:span.family-name (:lastName ~profile)]]]
     [:p.title {:style "display:block"}
      (:headline ~profile)]
     [:dl#headline {:class "demographic-info adr"}
      [:dt "Location"] [:dd.locality (-> ~profile :location :name)]
      [:dt "Industry"] [:dd.industry (:industry ~profile)]]
     [:h2.section-title (str (full-name ~profile) "'s Overview")]
      (overview ~profile)])

(defmacro profile-summary
  [profile]
  `[:div (block-section :profile-summary)
    [:div.header [:h2 (str (full-name ~profile) "'s Summary")]]
    [:div.content
     [:p (:summary ~profile)]
     [:div#profile-specialities {:style "display:block"}
     [:h3 "Specialties"]
     [:p.null (:specialties ~profile)]]]])

(defmacro proficiency
  [skill]
  `(let [proficiency# (:proficiency ~skill)]
    (str "(" (:proficiency proficiency#)
         ", " (:years proficiency#) " years experience)")))

(defmacro profile-skills
  [profile]
  `[:div (block-section :profile-skills)
    [:div.header [:h2 (str (full-name ~profile) "'s Skills")]]
    [:div.content
     [:ul {:class "skills competencies"}
       (map (fn [sk#]
              [:li {:class "skills competencies"}
               [:h3 [:a {:href "#"} (-> sk# :skill :name)]]
               [:span.proficiency (proficiency sk#)]])
            (-> ~profile :skills :values))]]])

(defmacro organization-details
  [position]
  `(let [company# (:company ~position)]
     [:p {:class "orgstats organization-details"}
      (str (:type company#) "; " (:industry company#) " Industry")]))

(defmacro period
  [position]
  `(let [m# ["January" "February" "March" "April" "May" "June"
             "July" "August" "September" "October" "November" "December"]
        fd# #(str (->> % :month dec m#) " " (:year %))]
    [:p.period
     [:abbr.dtstart (fd# (:startDate ~position))] " - "
     (if (:isCurrent ~position) " Present" (fd# (:endDate ~position)))]))

(defmacro position-detail
  [position]
  `[:div {:class "position first experience vevent vcard current-position"
          :style "display:block"}
    [:a.include {:href "#name"}]
    [:div.postitle
     [:h3 {:class "postitle anet"} [:span.title (:title ~position)]]
     [:h4 [:strong [:a.company-profile-public {:href "#"}
                     [:span {:class "org summary"}
                       (-> ~position :company :name)]]]]]
    (organization-details ~position)
    (period ~position)
    [:p.desc (-> ~position :summary (str/replace "*" "<br>"))]])

(defmacro profile-experience
  [profile]
  `[:div (block-section :profile-experience)
    [:div.header [:h2 (str (full-name ~profile) "'s Experience")]]
    [:div {:class "content vcalendar"}
     (map #(position-detail %) (-> ~profile :positions :values))]])

(defmacro template
  [profile]
  `[:div.content
    (profile-header ~profile)
    (profile-summary ~profile)
    (profile-skills ~profile)
    (profile-experience ~profile)])
