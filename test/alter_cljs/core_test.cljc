(ns alter-cljs.core-test
  #?(:clj (:refer-clojure :exclude [alter-var-root]))
  (#?(:clj :require :cljs :require-macros)
   [speclj.core :refer [describe it should= should-throw with]]
   [alter-cljs.core :refer [alter-var-root]])
  (:require [speclj.core]
            [speclj.run.standard]))

(def some-var :original)

(def ex-type #?(:clj java.lang.Exception :cljs ExceptionInfo))

(defn base-fn [] 0)

(defn var->sym [v]
  (let [{:keys [ns name]} (meta v)]
    (symbol (str (ns-name ns)) (str name))))

(defn wrap-fn! [v wrapper]
  (let [pair (map var->sym [v wrapper])]
    (when-not (= pair (-> v meta ::wrapped))
      (alter-var-root v
        (fn [original]
          (fn [& args]
            (apply @wrapper original args))))
      (alter-meta! v #(assoc % ::wrapped pair)))
      nil))

(defn inc-wrapper [original]
  (inc (original)))

(describe "alter-var-root compatibility"
  (it "alters the var"
    (alter-var-root #'alter-cljs.core-test/some-var
      (fn [original]
        [original :modified]))
    (should= some-var [:original :modified]))

  (it "alters a var without specifying the namespace"
    (alter-var-root #'some-var
      (fn [original]
        [original :modified-again]))
    (should= some-var [[:original :modified] :modified-again]))

  (it "alters a var named by symbol"
    (alter-var-root alter-cljs.core-test/some-var
      (fn [original]
        [(first original) :modified-by-fq-sym]))
    (should= some-var [[:original :modified] :modified-by-fq-sym]))

  (it "alters a var named by symbol without specifying the namespace"
    (alter-var-root some-var
      (fn [original]
        [(first original) :modified-by-sym]))
    (should= some-var [[:original :modified] :modified-by-sym]))

  (it "alters a var bound to a symbol"
    (let [some-var-ref #'some-var]
      (alter-var-root some-var-ref
        (fn [original]
          (first original)))
      (should= some-var [:original :modified])))

  (it "alters a var bound through several levels of indirection"
    (let [some-var-ref #'some-var
          some-mid-sym some-var-ref
          some-sym some-mid-sym]
      (let [nested some-sym]
        (alter-var-root nested
          (fn [original]
            (first original)))
          (should= some-var :original))))

  (it "throws when trying to alter a non-var"
    (let [some-sym :nope]
      (should-throw ex-type
        (alter-var-root some-sym identity))
      (should-throw ex-type
        (alter-var-root :some-kw identity))
      (should-throw ex-type
        (alter-var-root 0 identity))
      (should-throw ex-type
        (alter-var-root "a" identity))))

  (it "alters a var passed to a function as an argument"
    (do
      (wrap-fn! #'base-fn #'inc-wrapper)
      (should= (base-fn) 1))))
