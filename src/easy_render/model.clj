(ns easy-render.model
  (:use [easy-render.face :refer :all]
        [easy-render.vector :refer :all]))

(defprotocol ModelProtocol
  (vectors [this] "vectors docs")
  (get-vector [this index] "get-vector docs")
  (faces [this] "faces docs")
  (vectors-for-face [this face] "vectors-for-face docs")
  )

(defrecord Model [v f]
  ModelProtocol
  (vectors [this] (:v this))
  (get-vector [this index] (nth (vectors this) (dec index)))
  (faces [this] (:f this))
  (vectors-for-face [this face] (map #(get-vector this %) (vertices face))))

(defn build-model [{faces :f  vectors :v }]
  (Model. vectors faces))
