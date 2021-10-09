(ns giggin.components.orders
  (:require [giggin.state :as state]
            [giggin.helpers :refer [format-price]]
            [giggin.components.checkout-modal :refer [checkout-modal]]))

(defn total
  [orders gigs]
  (->> orders
       (filter (fn [[id]] (not (get-in gigs [id :sold-out]))))
       (map (fn [[id quantity]] (* quantity (get-in gigs [id :price]))))
       (reduce +)))

(defn orders
  []
  (let [orders @state/orders
        gigs @state/gigs

        remove-from-order #(swap! state/orders dissoc %)
        remove-all-orders #(reset! state/orders {})]
    [:aside
     (if (empty? orders)
       [:div.empty
        [:div.title "You don't have any orders"]
        [:div.subtitle "Click on a + to add an order"]]
       [:div.order
        [:div.body
         (for [[id quantity] orders]
           [:div.item {:key id}
            [:div.img
             [:img {:src (get-in gigs [id :img]) :alt (get-in gigs [id :title])}]]
            [:div.content
             (if (get-in gigs [id :sold-out])
               [:p.sold-out "Sold out"]
               [:p.title (str (get-in gigs [id :title]) " \u00D7 " quantity)])]
            [:div.action
             (if (get-in gigs [id :sold-out])
               [:div.price (format-price 0)]
               [:div.price (format-price (* (get-in gigs [id :price]) quantity))])
             [:button.btn.btn--link.tooltip
              {:data-tooltip "Remove"
               :on-click #(remove-from-order id)}
              [:i.icon.icon--cross]]]])]
        [:div.total
         [:hr]
         [:div.item
          [:div.content "Total"]
          [:div.action
           [:div.price (format-price (total orders gigs))]]
          [:button.btn.btn--link.tooltip
           {:data-tooltip "Remove all"
            :on-click remove-all-orders}
           [:i.icon.icon--delete]]]
         [checkout-modal]]])]))
