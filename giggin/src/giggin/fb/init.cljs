(ns giggin.fb.init
  (:require ["firebase/app" :as firebase]
            [giggin.fb.auth :refer [on-auth-state-changed]]))

(defn firebase-init
  []
  (if (zero? (alength (firebase/getApps)))
    (firebase/initializeApp
      #js {:apiKey "AIzaSyCFHohhhe0MTqahxTH3LtsYs0DD5ypfOh4"
           :authDomain "giggin-d0eb9.firebaseapp.com"
           :projectId "giggin-d0eb9"
           :databaseURL "https://giggin-d0eb9-default-rtdb.europe-west1.firebasedatabase.app"
           :appId "1:615570682202:web:15ea4fe7321bc158374363"})
    (firebase/getApp))
  (on-auth-state-changed))
