(ns alter-cljs.core-test
  (#?(:clj :require :cljs :require-macros)
   [speclj.core :refer [describe it should=]])
  (:require [speclj.core]))

(describe "testing"
  (it "works"
    (should= 0 0)))
