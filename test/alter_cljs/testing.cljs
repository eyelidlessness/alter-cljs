(ns alter-cljs.testing
  (:require [speclj.core]
            [speclj.platform :refer [dynamically-invoke]]
            [speclj.report.progress :refer [new-progress-reporter]]
            [speclj.run.standard :refer [armed run-specs]]))

(enable-console-print!)

(defn ^:export progress-reporter []
  (new-progress-reporter))

(defn ^:export run []
  (let [original #'dynamically-invoke]
    (set! dynamically-invoke
      (fn dynamically-invoke [ns-name fn-name]
        (if (and (= ns-name "speclj.report.progress") (= fn-name "new-progress-reporter"))
            (js* "~{}();" js/alter-cljs.testing.progress-reporter)
            (original ns-name fn-name)))))

  (set! armed true)

  (run-specs
    :color true
    :reporter "progress"))
