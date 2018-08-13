app.service('loginService_shop',function ($http) {
    //读取登录人的名称
    this.showLoginName =  function () {
        return $http.get('../login/name.action');
    }
});