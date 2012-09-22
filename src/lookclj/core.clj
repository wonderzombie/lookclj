(ns lookclj.core)

(def rooms 
  {:red-room
    { :desc "You're in a red room."
      :exits { :north :white-room }}
   :white-room
    { :desc "You're in a white room."
      :exits { :south :red-room }}})

(defn nextroom [room dir]
  (if (nil? dir)
    room
    (get-in rooms [room :exits dir])))

(defn lookup-cmd [cmd]
  (condp contains? cmd
    #{"north" "n"}        :north
    #{"south" "s"}        :south
    #{"east"  "e"}        :east
    #{"west"  "w"}        :west
    #{"quit" "exit" "q"}  :quit
    #{"examine" "look"}   :look
    :else nil))

(defn exits [room]
  (map name (keys (get-in rooms [room :exits]))))

(defn prompt [room]
  (println (get-in rooms [room :desc]))
  (println "Exits:" (print-str (exits room)))
  (print "> ")
  (flush))

(defn getcmd []
  (let [in (read-line)]
    (lookup-cmd in)))

(defn mainloop []
  (loop [room :red-room]
    (prompt room)
    (let [cmd (getcmd)]
      (case cmd
        :quit
          (do (println "Bye."))
        :look ; no-op for now.
          (recur room)
        (recur (nextroom room cmd))))))

(defn -main []
  (do 
    (println "Starting lookclj.")
    (mainloop)))
