(defproject easy-render "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.swinglabs.swingx/swingx-core "1.6.4"] ]
  :aot [easy-render.JCanvas]
  :main easy-render.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
