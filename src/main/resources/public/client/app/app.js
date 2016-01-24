'use strict';
var app = angular.module('toolsApp', [
    'ui.router',
    'ngCookies',
    'ngAnimate',
    'angular-loading-bar',
    'ui.bootstrap'

]);
app.controller('MainCtrl', function ($rootScope, $scope, $uibModal) {
});

app.filter('searchFor', function () {
    return function (arr, searchString) {
        if (!searchString) {
            return arr;
        }
        var result = [];
        searchString = searchString.toLowerCase();
        angular.forEach(arr, function (item) {
            if ((item.name.toLowerCase().indexOf(searchString) !== -1) ||
                (item.description.toLowerCase().indexOf(searchString) !== -1)) {
                result.push(item);
            }
        });
        return result;
    };
});