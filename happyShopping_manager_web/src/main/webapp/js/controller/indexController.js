app.controller('loginController',function($scope,$controller,loginService){
    //读取当前登录人
    $scope.showLoginName = function () {
        loginService.showLoginName().success(
            function (response) {
                $scope.loginName = response.loginName;
            }
        )
    }

});