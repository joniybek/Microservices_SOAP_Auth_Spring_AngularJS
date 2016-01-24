var app = angular.module('toolsApp');
app.config(function ($stateProvider) {
            $stateProvider
                .state('main', {
                    url: '',
                    templateUrl: HOST+APP+'views/main/main.html '
                })
            .state('soapjobs', {
                    url: 'soapjobs',
                    templateUrl: HOST+APP+'views/soapjobs/soapjobs.html ',
                    controller: 'SoapJobsCtrl'
                })
                .state('test', {
                    url: 'test',
                    templateUrl: HOST+APP+'views/soapjobs/soapjobpopup.html ',
                    controller: 'SoapPopupCtrl'
                })
              ;
    });