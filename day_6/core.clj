(ns core
  (:require [clojure.string :as str]
            [clojure.pprint :refer [pprint]]
            [clojure.set :as set]))


(defn determine-start
  [in]
  (loop [count 14
         fc (take 14 in)
         rest (drop 14 in)]
    (prn fc)
    (if (= (distinct fc) fc)
      count
      (recur (inc count)
             (->> fc (drop 1) reverse (concat (take 1 rest)) reverse)
             (drop 1 rest)))))

(->> (slurp "input.txt") 
     determine-start)