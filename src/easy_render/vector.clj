(ns easy-render.vector)

(defprotocol VectorProtocol
  (x [this] "x docs")
  (y [this] "y docs")
  (z [this] "z docs"))

(defrecord Vector [x y z]
  VectorProtocol
  (x [this] (:x this))
  (y [this] (:y this))
  (z [this] (:z this)))

(defn build-vector [x y z]
  (Vector. x y z))
