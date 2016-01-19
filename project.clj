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
                                                     "resources/whitespace"
                                                     "resources/simple"
                                                     "resources/advanced"]
                   :cljsbuild {:builds [{:id "whitespace"
                                         :source-paths ["src" "test"]
                                         :compiler {:output-to "resources/whitespace/build.js"
                                                    :output-dir "resources/whitespace"
                                                    :optimizations :whitespace
                                                    :source-map "resources/whitespace/build.js.map"}
                                         :notify-command ["phantomjs"
                                                          "test/phantomjs_runner.js"
                                                          "resources/whitespace/build.js"]}
                                        {:id "simple"
                                         :source-paths ["src" "test"]
                                         :compiler {:output-to "resources/simple/build.js"
                                                    :output-dir "resources/simple"
                                                    :optimizations :simple
                                                    :source-map "resources/simple/build.js.map"}
                                         :notify-command ["phantomjs"
                                                          "test/phantomjs_runner.js"
                                                          "resources/simple/build.js"]}
                                        {:id "advanced"
                                         :source-paths ["src" "test"]
                                         :compiler {:output-to "resources/advanced/build.js"
                                                    :output-dir "resources/advanced"
                                                    :optimizations :advanced
                                                    :source-map "resources/advanced/advanced.js.map"}
                                         :notify-command ["phantomjs"
                                                          "test/phantomjs_runner.js"
                                                          "resources/advanced/build.js"]}]
                               :test-commands {"whitespace" ["phantomjs"
                                                             "test/phantomjs_runner.js"
                                                             "resources/whitespace/build.js"]
                                               "simple"     ["phantomjs"
                                                             "test/phantomjs_runner.js"
                                                             "resources/simple/build.js"]
                                               "advanced"   ["phantomjs"
                                                             "test/phantomjs_runner.js"
                                                             "resources/advanced/build.js"]}}}})
