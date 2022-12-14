(ns core
  (:require [clojure.string :as str]
            [clojure.pprint :refer [pprint]]
            [clojure.set :as set]))

(defn add-crates-to-result-map
  [result-map crates]
  (loop [rm result-map
         remaining (rest crates)
         to-process (first crates)] 
    (let [c (map-indexed (fn [idx item]
                           (if (= (nth item 1) \space)
                             nil
                             [idx (nth item 1)])) to-process)]
      (if (first remaining) 
        (recur (merge-with conj rm (into {} c)) (rest remaining) (first remaining))
        (merge-with conj rm (into {} c))))))

(defn process-instructions
  [instructions]
  (->> (map #(str/split % #"\D+") instructions)
       (map #(drop 1 %))
       (map #(map (fn [i] (Integer/parseInt i)) %))))

(defn create-crate-map
  [[m x]]
  (let [crates (->> (map #(partition 4 (str % " ")) (drop-last 2 m))
                    reverse)
        result-map (into {} (map #(do [% []])(range 9)))
        ] 
    [(add-crates-to-result-map result-map crates) (process-instructions x)]))

(defn execute-instructions
  [[result-map instructions]]
  (loop [rm result-map
         next (first instructions)
         remaining (rest instructions)]
    (if-not next
      rm
      (recur
       (let [amount (first next)
             from (dec (second next))
             to (dec (last next))
             to-move (take-last amount (get rm from))] 
         (-> rm 
             (update to concat to-move) 
             (update from #(drop-last amount %))
             (update to vec)
             (update from vec)))
       (first remaining)
       (rest remaining)))))

(->> (str/split (slurp "input.txt") #"\n")
     (split-at 10)
     create-crate-map
     execute-instructions
     sort
     )