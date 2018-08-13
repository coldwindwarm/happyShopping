app.controller('loginController_shop',function ($scope, $controller, loginService_shop) {
    $scope.showLoginName= function () {
        loginService_shop.showLoginName().success(
            function (response) {
                $scope.loginName = response.loginName;
            }
        )
    }
});