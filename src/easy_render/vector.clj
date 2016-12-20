(ns easy-render.vector)

(defprotocol VectorProtocol
  (x [this] "x docs")
  (y [this] "y docs")
  (z [this] "z docs")
  (length [this] "length docs")
  (normalize [this] "normalize docs"))

(defrecord Vector [x y z]
  VectorProtocol
  (x [this] (:x this))
  (y [this] (:y this))
  (z [this] (:z this))
  (length [this] (Math/sqrt (+ (Math/pow (:x this) 2)
                               (Math/pow (:y this) 2)
                               (Math/pow (:z this) 2))))
  (normalize [this] (let [l (length this)]
                      (Vector. (/ (:x this) l)
                               (/ (:y this) l)
                               (/ (:z this) l)))))

(defn build-vector [x y z]
  (Vector. x y z))

(defn vector-minus [v1 v2]
  (let [v1x (x v1) v1y (y v1) v1z (z v1)
        v2x (x v2) v2y (y v2) v2z (z v2)]
    (Vector. (- v1x v2x)
             (- v1y v2y)
             (- v1z v2z))))

(defn cross-product [v1 v2]
  (let [v1x (x v1) v1y (y v1) v1z (z v1)
        v2x (x v2) v2y (y v2) v2z (z v2)]
    (Vector. (- (* v1y v2z) (* v1z v2y))
             (- (* v1z v2x) (* v1x v2z))
             (- (* v1x v2y) (* v1y v2x)))))

(defn scalar-product [v1 v2]
  (let [v1x (x v1) v1y (y v1) v1z (z v1)
        v2x (x v2) v2y (y v2) v2z (z v2)]
    (+ (* v1x v2x) (* v1y v2y) (* v1z v2z))))
