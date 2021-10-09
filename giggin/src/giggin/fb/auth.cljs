(ns giggin.fb.auth
  (:require [giggin.state :as state]
            [giggin.fb.db :refer [db-save!]]
            ["firebase/auth" :refer [getAuth GoogleAuthProvider onAuthStateChanged signInWithPopup signOut]]))

(defn sign-in-with-google
  []
  (let [provider (GoogleAuthProvider.)
        sign-in (signInWithPopup (getAuth) provider)]))

(defn sign-out
  []
  (signOut (getAuth)))

(defn on-auth-state-changed
  []
  (onAuthStateChanged
    (getAuth)
    (fn [user]
      (.log js/console user)
      (if user
        (let [uid (.-uid user)
              profile {:display-name (.-displayName user)
                       :photo-url (.-photoURL user)
                       :email (.-email user)}]
          (reset! state/user profile)
          (db-save! ["user" uid "profile"] (clj->js profile)))
        (reset! state/user nil)))))
