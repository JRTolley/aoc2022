(ns core
  (:require [clojure.string :as str]))

(defn sum-list [list]
  (->> list
       (map #(Integer/parseInt %))
       (reduce +)))

(defn init []
  (as-> (slurp "input.txt") a
    (str/split a #"\n\n")
    (map #(str/split % #"\n") a)
    (map sum-list a)
    (sort > a)
    (take 3 a)
    (reduce + a)))

(init)
