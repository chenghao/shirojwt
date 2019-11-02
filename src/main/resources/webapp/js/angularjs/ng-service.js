
app.factory('httpInterceptor', ['$q', '$injector', function ($q, $injector) {
    var errorSign = 0;
    var httpInterceptor = {
        'responseError': function (response) {
            if (response.status == 401) {
                alert("未授权，拒绝访问！");
            } else if (response.status === 404) {
                alert(404);
            } else if (response.status === 400) {
                alert(400);
            } else if (response.status === 408) {
                alert(408);
            }
        },
        'response': function (response) {
            var mes = response.data;

            return response;
        }
    };
    return httpInterceptor;
}]);

//请求json数据
app.factory("reqJsonData", ["$http", function ($http) {
    
}]);