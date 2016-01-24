var app = angular.module('toolsApp');
app.service('soapDAOService', function ($http) {

    var soapMessagesList = {};
    var selected = {};

    var getAll = function () {
        return $http({method: "GET", url: "/api/soap/all"}).then(function (result) {
            return result.data;
        });
    };
    var addSoapDAO = function (newObj) {
        soapMessagesList.push(newObj);
    };

    var getSoapDAO = function () {
        return soapMessagesList;
    };

    var setSelected = function (id) {
        selected = $http({method: "GET", url: "/api/soap?id=" + id}).then(function (result) {
            return result.data;
        });
    };

    var getSelected = function () {
        return selected;
    };

    var sendRequest = function (obj, env) {
        console.log("Sending soap: " +obj.name+"/"+env);
        return $http({
            method: "POST",
            url: "/api/soap/send",
            data: {
                environment: env,
                soapxml: obj
            }
        }).then(function (result) {
            return result.data;
        });
    };


    return {
        getAll: getAll,
        addSoapDAO: addSoapDAO,
        getSoapDAO: getSoapDAO,
        setSelected: setSelected,
        getSelected: getSelected,
        sendRequest: sendRequest


    };

});