(ns easy-render.render
  (:use [easy-render.vector :refer :all]
        [easy-render.face :refer :all]
        [easy-render.model :refer :all]))

;NOTE temporary hardcode size
(defn render-line [vector1 vector2]
  (let [x0 (+ (* (x vector1) 320) 374)
        y0 (+ (* (y vector1) -240) 312.5)
        x1 (+ (* (x vector2) 320) 374)
        y1 (+ (* (y vector2) -240) 312.5)]
    [x0 y0 x1 y1]))

(defn transfrom-x [x]
  (+ (* x 320) 374))

(defn transfrom-y [y]
  (+ (* y -240) 312.5))

(defn transfrom-point [p]
  [(transfrom-x (x p)) (transfrom-y (y p))])

(defn gray-color [intensity]
  (map int [(* intensity 255) (* intensity 255) (* intensity 255) 255.0 ] ))

(defn render-polygon [[pointsV intensity]]
  (let  [points (doall (map transfrom-point pointsV) )]
    [points (gray-color intensity)]))

(defn with-intnsity [[p0 p1 p2] light]
  (let [normal (normalize (cross-product (vector-minus p1 p0)
                                         (vector-minus p2 p0)))
        intensity (scalar-product normal light)]
    [[p0 p1 p2] intensity]))

(defn positive-intensity [[_ intensity]]
  (> intensity 0))

(defn render [model light]
  (->> model
       faces
       (map #(vectors-for-face model %))
       (map #(with-intnsity % light))
       (filter positive-intensity)
       (map render-polygon)))
