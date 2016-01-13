(defproject alter-cljs "0.1.0"
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
                   :cljsbuild {:builds [{:source-paths ["src" "test"]
                                         :compiler {:output-to "target/cljsbuild/alter-cljs.js"
                                                    :optimizations :whitespace}
                                         :notify-command ["phantomjs"
                                                          "test/phantomjs_runner.js"
                                                          "target/cljsbuild/alter-cljs.js"]}]
                               :test-commands {"test" ["phantomjs"
                                                       "test/phantomjs_runner.js"
                                                       "target/cljsbuild/alter-cljs.js"]}}}})
