(ns easy-render.face)

(defprotocol FaceProtocol
  (vertices [this] "vertices docs")
  (texture-coordinates [this] "texture-coordinates docs")
  (normals [this] "normals docs"))

(defrecord Face [p1 p2 p3]
  FaceProtocol
  (vertices [this] (map first (vector (:p1 this) (:p2 this) (:p3 this)))))

(defn build-face [p1 p2 p3]
  (Face. p1 p2 p3))
