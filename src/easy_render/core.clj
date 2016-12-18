(ns easy-render.core
  (:use [easy-render.geometry-parser :refer :all]
        [easy-render.render :refer :all]
        [easy-render.vector :refer :all]
        [clojure.java.io :only (reader)])

  (:import [easy-render JCanvas]
           [javax.swing JFrame]
           [java.awt Color]))


(defn canvas [drawings]
  (doto (JCanvas. drawings)
    (.setBackground (Color. 0, 0, 0))
    (.setOpaque true)))

(defn window [panel]
  (doto (JFrame. "Easy Render")
    (.setContentPane panel)
    (.setSize 748 625)
    (.setResizable false)
    (.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
    (.show)))

(defn paint [model g]
  (letfn [(drawLine [x0 y0 x1 y1] (.drawLine g x0 y0 x1 y1) )
          (setColor [color] (.setColor g color))]
    (setColor (Color. 250, 235, 215))
    (->> model
      render
      (map #(apply drawLine %))
      doall)))

(defn load-model [file-name]
  (with-open [file-stream (reader file-name)]
    (parse file-stream)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [model (load-model (first args))
        paint-model (partial paint model)]
    (window (canvas paint-model))))
