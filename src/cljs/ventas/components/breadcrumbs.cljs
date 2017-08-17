(ns ventas.components.breadcrumbs
  (:require [fqcss.core :refer [wrap-reagent]]
            [soda-ash.core :as sa]
            [re-frame.core :as rf]
            [reagent.session :as session]
            [ventas.util :as util]
            [ventas.routes :as routes]))

(defn- breadcrumb-data [current-route route-params]
  (map (fn [route] {:url (apply routes/path-for route (first (seq route-params)))
                    :name (:name (routes/route->data route))
                    :route route})
       (routes/route-parents current-route)))

(defn breadcrumb-view [current-page route-params]
  (wrap-reagent
   [sa/Breadcrumb {:fqcss [::breadcrumbs]}
    (util/interpose-fn
     (fn [] [sa/BreadcrumbDivider {:key (gensym)}])
     (for [breadcrumb (breadcrumb-data current-page route-params)]
       [sa/BreadcrumbSection {:key (:route breadcrumb)
                              :fqcss [::breadcrumb]
                              :href (:url breadcrumb)}
        (:name breadcrumb)]))]))

(defn breadcrumbs []
  (let [current-page (:current-page (session/get :route))
        route-params (:route-params (session/get :route))]
    (breadcrumb-view current-page route-params)))