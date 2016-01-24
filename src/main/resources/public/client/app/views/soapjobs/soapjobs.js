var app = angular.module('toolsApp');
app.controller('SoapJobsCtrl', function ($scope, $rootScope, $http, $uibModal, soapDAOService) {

    $scope.orderByRating  = function(job){
        console.log("+"+job.rating);
        return job.rating;
    }

    soapDAOService.getAll().then(function(data){
        $scope.jobs = data;
    });

    $scope.open = function (id) {
        var templateUrl;
        var controller;
        var size;
        soapDAOService.setSelected(id);
        var modalInstance = $uibModal.open({
            animation: $scope.animationsEnabled,
            templateUrl: HOST+APP+'views/soapjobs/soapjobpopup.html',
            controller: 'SoapPopupCtrl',
            size: 'lg'
        });

    }


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