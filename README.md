### Linked In throwdown
[Blog post](http://esdee.github.com/project/2012/03/01/the-linkedin-throwdown/>)

A clojure + clojurescript consideration of Linked In's client-side javascript
[throwdown](http://engineering.linkedin.com/frontend/client-side-templating-throwdown-mustache-handlebars-dustjs-and-more).

#### Usage

    git clone git@github.com:esdee/linked-in.git

    cd linked-in

    lein deps

    lein cljsbuild clean

    lein cljsbuild once

    lein repl

    (run-server)

http://localhost:8080               (client side templating)

http://localhost:8080/profiles/100  (server side templating)

#### License
Do whatever you want with this code.
However it includes a file from  Brenton Ashworth's
[One](https://github.com/brentonashworth/one) project for
[remote calls](https://github.com/brentonashworth/one/blob/master/src/lib/cljs/one/browser/remote.cljs).
