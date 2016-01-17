(ns alter-cljs.core
  (:refer-clojure :exclude [alter-var-root]))

(defmacro if-cljs
  "Return then if we are generating cljs code and else for Clojure code.
   http://blog.nberger.com.ar/blog/2015/09/18/more-portable-complex-macro-musing"
  [then else]
  (if (:ns &env) then else))

(def resolve-clj
  (try clojure.core/resolve
    (catch Exception _
      (constantly nil))))

(defmulti sym->var
  (fn [env sym]
    (cond
      (contains? env sym) :clj
      (resolve-clj sym) :clj-resolved
      :else :cljs)))

(defn meta->fq-sym [{:keys [ns name] :as m}]
  (symbol (str (ns-name ns)) (str name)))

(defmethod sym->var :clj [env sym]
  (loop [init (-> env sym .-init)]
    (cond
      (instance? clojure.lang.Compiler$TheVarExpr init)
      (-> init .-var meta meta->fq-sym)

      (instance? clojure.lang.Compiler$LocalBindingExpr init)
      (recur (-> init .-b .-init))

      :default
      nil)))

(defmethod sym->var :clj-resolved [env sym]
  (-> sym resolve meta meta->fq-sym))

(defmethod sym->var :cljs [env sym]
  (let [init (get-in env [:locals sym :init])
        var-name (get-in init [:var :info :name])]
      (cond
        var-name var-name
        (:form init) (recur (:env init) (:form init))
        :else nil)))

(def sentinel nil)

(defmacro alter-var-root [var-ref f]
  (let [var-seq? (and (seq? var-ref) (= 'var (first var-ref)))
        sym? (symbol? var-ref)
        var-sym (or (cond
                      var-seq? (second var-ref)
                      sym? (sym->var &env var-ref)
                      :else nil)
                    'alter-cljs.core/sentinel)
        var-sym? (not= var-sym 'alter-cljs.core/sentinel) #_(boolean (not (nil? var-sym)))]
    `(if-cljs
       (if ~var-sym?
           (set! ~var-sym (~f ~var-sym))
           (throw (ex-info "Expected var" {:got ~var-ref})))
       (if ~var-sym?
           (clojure.core/alter-var-root (var ~var-sym) ~f)
           (clojure.core/alter-var-root ~var-ref ~f)))))
