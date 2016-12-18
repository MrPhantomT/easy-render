(ns easy-render.JGraphic
  (:import [java.awt Color])
  (:gen-class
    :name "easy-render.JGraphic"
    :extends java.awt.Graphics
    :constructors { [clojure.lang.IFn] [] }))

