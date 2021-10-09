(ns giggin.fb.db
  (:require [giggin.state :as state]
            ["firebase/database" :as db]
            [clojure.string :as str]))

(defn db-ref
  [path]
  (db/ref (db/getDatabase) (str/join "/" path)))

(defn db-save!
  [path value]
  (db/set (db-ref path) value))

(defn db-subscribe
  [path]
  (db/onValue (db-ref path)
              (fn [snapshot]
                (reset! state/gigs (js->clj (.val snapshot) :keywordize-keys true)))))
