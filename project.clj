(defproject alter-cljs "0.1.1-SNAPSHOT"
  :description "A ClojureScript implementation of alter-var-root"
  :url "https://github.com/eyelidlessness/alter-cljs"
  :license {:name "WTFPL v2"
            :url "http://www.wtfpl.net/"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.228" :scope "provided"]]
  :aliases {"spec" ["with-profile" "dev" "spec"]
            "cljsbuild" ["with-profile" "dev" "cljsbuild"]}
  :profiles {:dev {:dependencies [[speclj "3.3.1"]]
                   :plugins [[lein-cljsbuild "1.1.2"]
                             [speclj "3.3.1"]]
                   :clean-targets ^{:protect false} [:target-path
                                                     "resources/js"]
                   :cljsbuild {:builds [{:source-paths ["src" "test"]
                                         :compiler {:output-to "resources/js/alter-cljs.js"
                                                    :output-dir "resources/js"
                                                    :optimizations :advanced
                                                    ; :optimizations :whitespace
                                                    :source-map "resources/js/alter-cljs.js.map"}
                                         :notify-command ["phantomjs"
                                                          "test/phantomjs_runner.js"
                                                          "resources/js/alter-cljs.js"]}]
                               :test-commands {"test" ["phantomjs"
                                                       "test/phantomjs_runner.js"
                                                       "resources/js/alter-cljs.js"]}}}})
