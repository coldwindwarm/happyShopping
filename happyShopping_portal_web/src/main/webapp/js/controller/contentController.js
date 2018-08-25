app.controller('contentController', function ($scope, contentService) {

    //广告列表,以分类id为数组索引,值为对应的content对象集合
    $scope.contentList = [];

    $scope.findByCategoryId = function (categoryId) {
        contentService.findByCategoryId(categoryId).success(
            function (response) {
                $scope.contentList[categoryId] = response;
            }
        );
    };
    //搜索跳转方法
    $scope.search = function () {
        location.href = "http://localhost:9104/search.html#?keywords="+$scope.keywords;
    }
});