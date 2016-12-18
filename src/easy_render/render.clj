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

(defn render-polygon [vectors]
  (map #(render-line (nth vectors %1)
                     (nth vectors %2))
       [0 1 2]
       [1 2 0]))

(defn render [model]
  (->> model
       faces
       (map #(vectors-for-face model %))
       (mapcat render-polygon)))
