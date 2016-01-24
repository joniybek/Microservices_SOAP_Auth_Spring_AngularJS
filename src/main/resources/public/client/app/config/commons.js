var app = angular.module('toolsApp');
app.directive('prettyprint', function () {
    return {
        restrict: 'A',
        link: function postLink(scope, element, attrs) {
            element.text(vkbeautify.xml(element.text, 4));
        }
    };
});

/*app.config(function ($httpProvider) {
    //Enable cross domain calls
    $httpProvider.defaults.useXDomain = true;
    //Remove the header used to identify ajax call  that would prevent CORS from working
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
});*/
/*
app.factory("HttpRequest", ['$q', '$http', function(q, http) {
    var deferredData = q.defer();

    http.get('http://your-server.local/data.json').success(function(data) {
        //success, resolve your promise here
        deferredData.resolve(data);
    }).error(function(err) {
        //error, use reject here
        deferredData.reject(err);
    });

    return {
        get: function() {
            return deferredData.promise;
        }
    };
}]);*/
