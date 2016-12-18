(ns easy-render.JCanvas
  (:import [java.awt Color])
  (:gen-class
    :name "easy-render.JCanvas"
    :extends javax.swing.JPanel
    :constructors { [clojure.lang.IFn] [] }
    :init init
    :state state
    :methods [[drawer [java.awt.Graphics] void]]
    :exposes-methods { paintComponent parentPaintComponent }))

(defn -init [func]
  [[] { :drawer func }])

(defn -drawer [this g]
  ((.state this) g))

(defn -paintComponent [this g]
  (.parentPaintComponent this g)
  ((:drawer (.state this)) g))
