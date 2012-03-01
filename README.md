### Linked In throwdown
A clojure + clojurescript consideration of Linked In's client-side javascript [throwdown](http://engineering.linkedin.com/frontend/client-side-templating-throwdown-mustache-handlebars-dustjs-and-more). This project uses the same template file to generate html with clojurescript on the client side or with clojure on the server side.

#### Usage

lein deps

lein cljsbuild clean

lein cljsbuild once

lein repl


(load "linked-in/dev_server")

(linked-in.dev-server/run-server)

browse http://localhost:8080

#### License
Do whatever you want with this code. However it includes a file from  Brenton Ashworth's [One](https://github.com/brentonashworth/one) project for [remote calls](https://github.com/brentonashworth/one/blob/master/src/lib/cljs/one/browser/remote.cljs).
