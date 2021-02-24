(defproject postman-to-md "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [merkki "0.2.1"]
                 [table "0.5.0"]
                 [doric "0.9.0"]
                 [org.clojure/data.json "0.2.6"]]
  :jvm-opts ["-Xmx1G" "-Dfile.encoding=UTF-8"]
  :profiles {:uberjar {:aot :all}}
  :main postman-to-md.core)
