var app = angular.module('toolsApp');
app.controller('SoapPopupCtrl', function ($scope, soapDAOService, $cookies, $uibModalInstance) {
    var job;
    soapDAOService.getSelected().then(function (data) {
        $scope.job = data;
        job = data;
        prettify();
    });
    $scope.inputVals = {};
    $scope.req = "";
    $scope.envs = ENV;
    $scope.selectedEnv = $cookies.get('env');
    $scope.$watchCollection('inputVals', function () {
        prettify();
    });

    var prettify = function () {
        var xml = job.xml;
        for (var k in $scope.inputVals) {
            xml = xml.replace("{{" + k + "}}", $scope.inputVals[k]);
        }
        $scope.req = vkbeautify.xml(xml, "   ");
    }

    $scope.ok = function () {
        job.xml = $scope.req;
        $cookies.put('env', $scope.selectedEnv);
        var prRes = soapDAOService.sendRequest(job, $scope.selectedEnv);

        prRes.then(function (result) {
                if (result !== null) {
                    $scope.res = vkbeautify.xml(result, "   ");
                }
            },
            function (result) {
                $scope.res = "Error: " + result;
            }
        );

    }

    $scope.cancel = function () {
        $uibModalInstance.close();
    };

})
;