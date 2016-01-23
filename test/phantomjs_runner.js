#! /usr/bin/env phantomjs

var fs = require('fs');
var p = require('webpage').create();
var sys = require('system');

p.onConsoleMessage = function (x) {
    fs.write('/dev/stdout', x, 'w');
};

p.injectJs(phantom.args[0]);

var result = p.evaluate(function () {
    if (typeof alter_cljs !== 'undefined') {
        alter_cljs.testing.run(true);
    }
});

p.close();

phantom.exit(result);
