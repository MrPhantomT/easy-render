(ns easy-render.render
  (:use [easy-render.vector :refer :all]
        [easy-render.face :refer :all]
        [easy-render.model :refer :all]))

(defn error-at-x [delta-x delta-y [prev-error prev-y]]
  (let [cur-error (+ (* (Math/abs delta-y) 2) prev-error)
        y-inc (if (> delta-y 0) 1 -1)
        ]
    (if (> cur-error (Math/abs delta-x))
      [(- cur-error (Math/abs (* delta-x 2) )) (+ prev-y y-inc)]
      [cur-error prev-y])))

(defmulti line
  (fn [p0 p1]
    (< (Math/abs (- (first p0) (first p1)))
       (Math/abs (- (second p0) (second p1))))))

(defn inner-line [x0 y0 x1 y1 fun]
  (let [dx (- x1 x0) dy (- y1 y0)
        x-inc (if (> x0 x1) -1 1)]
    (->>
      (iterate (partial error-at-x dx dy) [0 y0])
      (pmap second)
      (pmap fun (range x0 x1 x-inc)))))

(defmethod line true [p0 p1]
  (let [x0 (first p0) x1 (first p1)
        y0 (second p0) y1 (second p1)]
    (inner-line y0 x0 y1 x1 #(vector %2 %1))))

(defmethod line false [p0 p1]
  (let [x0 (first p0) x1 (first p1)
        y0 (second p0) y1 (second p1)]
    (inner-line x0 y0 x1 y1 vector)))


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
  (pmap int [(* intensity 255) (* intensity 255) (* intensity 255) 255.0 ] ))

(defn render-polygon [[pointsV intensity]]
  (let  [[pg0 pg1 pg2] (pmap transfrom-point pointsV )
         points (concat (line pg0 pg1) (line pg1 pg2) (line pg2 pg0))
         ; points (concat  (line [13 20] [80 40]) (line [80 40] [60 60]) (line [60 60] [13 20])  )
         ]
    ; (println "<---")
    ; (println (line [13 20] [80 40]))
    ; (println "")
    ; (println (line [80 40] [60 60]))
    ; (println "")
    ; (println (line [60 60] [13 20]))
    ; (println "--->")
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
       (pmap #(vectors-for-face model %))
       (pmap #(with-intnsity % light))
       (filter positive-intensity)
       (pmap render-polygon)))
