(ns postman-to-md.core
  (:require [merkki.core :refer [markdown]]
            [clojure.java.io :as io]
            [doric.core :refer [table]]
            ;; [table.core :refer [table]] 
            [clojure.data.json :as json])
  (:gen-class))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn makedoc [item names]
  (let [request (:request item)]
    [[:uh2 (:name item)]
     [:h4 "설명"]
     (:description request)
     [:h4 "URL"]
     [:blockquote
      (str "\n"
           (clojure.string/join
            "\n"
            (map #(str "> " %)
                 (clojure.string/split-lines
                  (table [{:URL (-> request :url :raw)
                           :METHOD (-> request :method)}])))))]
     "\n"
     "\n"
     [:h4 "Headers"]
     [:blockquote
      (str "\n"
           (clojure.string/join
            "\n"
          (map #(str "> " %)
                 (clojure.string/split-lines
                  (table {:name :header :title "헤더" :align :center}
                         (-> item :request :header))))))]
     "\n"
     "\n"
     [:h4 "Request Body"]
     [:code-block (-> item :request :body :raw) "json"]]))



(defn iter [item names]
  (let [sub-item (:item item)]
    (if (empty? sub-item)
      (makedoc item names)
      (mapcat (fn [item]
                (iter item (conj names (:name item))))
              sub-item))))


(defn app [path output]
  (with-open [in (io/reader path)
              out (io/writer output :replace true)]
    (let [document (json/read in :key-fn keyword)]
      (.write out
              (markdown
               (concat [[:uh1 (:name (:info document))]
                        [:blockquote (:description (:info document))]]
                       (iter document [])))))))

(defn -main
  [& args]
  (let [[path output] args]
    (app path output)))
