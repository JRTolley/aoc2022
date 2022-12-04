(ns core
  (:require [clojure.string :as str]))


(defn score-round
  [round]
  (let [m  (subs round 0 1)
        o  (subs round 2 3)
        rp (case o
             "X" 0
             "Y" 3
             "Z" 6)
        mp (case m
             "A" (case o
                   "X" 3
                   "Y" 1
                   "Z" 2)
             "B" (case o
                   "X" 1
                   "Y" 2
                   "Z" 3)
             "C" (case o
                   "X" 2
                   "Y" 3
                   "Z" 1))]
    (+ mp rp)))

(->> (str/split (slurp "input.txt") #"\n")
    ;'("A Y" "B X" "C Z")
    (map score-round) 
    (reduce +))