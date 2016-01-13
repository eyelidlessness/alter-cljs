# alter-cljs

A ClojureScript implementation of `alter-var-root`.

```clojure
[alter-cljs "0.1.0"]
```

## Usage

```clojure
(ns my-ns
  #?(:clj (:refer-clojure :exclude [alter-var-root]))
  (#?(:clj :require :cljs :require-macros) [alter-cljs.core :refer [alter-var-root]]))

(alter-var-root whatever
  (fn [x]
    (do-something-to x)))
```

## Tests

### Clojure

```bash
lein spec
```

### ClojureScript

ClojureScript tests require PhantomJS. There is a [known issue](https://github.com/slagyr/speclj/issues/133) where compilation warnings will be thrown, but the tests still run as expected.

```bash
lein cljsbuild spec
```

## License

DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
Version 2, December 2004

Copyright (C) 2015 Trevor Schmidt <trevor@democratizr.com>

Everyone is permitted to copy and distribute verbatim or modified
copies of this license document, and changing it is allowed as long
as the name is changed.

DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

0. You just DO WHAT THE FUCK YOU WANT TO.
