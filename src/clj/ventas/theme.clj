(ns ventas.theme
  (:require
   [ventas.entities.configuration :as entities.configuration]
   [ventas.plugin :as plugin]))

(defn register! [kw attrs]
  (plugin/register! kw (merge attrs
                              {:type :theme})))

(defn all []
  (plugin/by-type :theme))

(defn current []
  (or (entities.configuration/get :current-theme)
      (some-> (all) (first) (key))))