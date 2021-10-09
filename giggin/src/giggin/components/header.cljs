(ns giggin.components.header
  (:require [giggin.state :as state]
            [giggin.fb.auth :as auth]))

(defn header
  []
  [:header
   [:img.logo {:src "img/giggin-logo.svg" :alt "Giggin logo"}]
   (if @state/user
     [:button.btn.btn--link.float--right.tooltip
      {:data-tooltip "Sign out"
       :on-click auth/sign-out}
      [:figure.avatar
       [:img {:src (:photo-url @state/user)}]]]
     [:button.btn.btn--link.float--right
      {:on-click auth/sign-in-with-google}
      "Login"])])
