(ns easy-render.geometry-parser
  (:use [clojure.string :only (split)]
        [easy-render.vector :only (build-vector)]
        [easy-render.face :only (build-face)]
        [easy-render.model :only (build-model)]))

(defmulti parse-values
  (fn [line-type _] line-type))

(defmethod parse-values :v [_ values]
  (apply build-vector (mapv read-string values) ))

(defmethod parse-values :f [_ values]
  (->> values
       (mapcat #(split % #"\/"))
       (mapv read-string)
       (partition 3)
       (apply build-face)))

(defmethod parse-values :default [_ values]
  [])

(defn- parse-line [line]
  (let [[line-type & values] (split line #" ")
        line-keyword (keyword line-type)]
    [line-keyword (parse-values line-keyword values)]))

(defn- iter [acc line]
  (let [[line-type value] (parse-line line)]
    (assoc acc
           line-type
           (conj (get acc line-type []) value))))

(defn parse [file-stream]
  (->> file-stream
       line-seq
       (reduce iter {})
       build-model))
