(ns lookclj.core)

(defn applymove [[x y] d]
  (cond
    (= d :north) [x (inc y)]
    (= d :south) [x (dec y)]
    (= d :east)  [(inc x) y]
    (= d :west)  [(dec x) y]
    :else        [x y]))

(defn command [in]
  (condp contains? in
    #{"north" "n"}    :north
    #{"south" "s"}    :south
    #{"east"  "e"}    :east
    #{"west"  "w"}    :west
    #{"quit" "exit"}  :quit
    :else nil))

(defn mainloop []
  (loop [pos [0 0]]
    (println "You're at:" pos)
    (let [in  (read-line)
          cmd (command in)]
      (if (= cmd :quit)
        (do
          (println "Quitting."))
        (recur (applymove pos cmd))))))

(defn -main []
  (do 
    (println "Starting lookclj.")
    (mainloop)))
