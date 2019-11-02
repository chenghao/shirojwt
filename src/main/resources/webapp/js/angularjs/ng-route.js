
var app = angular.module('app', ['ui.router', 'ui.bootstrap', 'ngSanitize']);

app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/login");
    $stateProvider.state('index', {
        url: "/index",
        templateUrl: "views/common/index_tlp.html?" + new Date(),
        controller: 'indexCtrl',
        data: {
            loginCheck: true
        }
    }).state('htgl_jsgl', {
        url: "/htgl_jsgl",
        templateUrl: "views/sys/htgl_jsgl_tlp.html?" + new Date(),
        controller: 'htgl_jsglCtrl',
        data: {
            loginCheck: true
        }
    })
    ;
}]);

app.run(function ($rootScope, $window) {
    $rootScope.$on('$stateChangeStart', function (event, toState) {
        $rootScope.logined = true;

        if (!$rootScope.logined && toState.data.loginCheck) {
            event.preventDefault();
            skipToLogin();
        }
    });
});

app.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('httpInterceptor');
}]);