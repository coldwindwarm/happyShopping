/**
 * 定义一个controller 发送http请求从后台获取数据
 */
app.controller('brandController', function ($scope,$controller, brandService) {

    //继承baseController
    $controller('baseController',{$scope:$scope});

    //查询品牌列表
    $scope.findAll = function () {
        brandService.success(
            function (response) {
                $scope.list = response;
            }
        );
    };


    //分页方法
    $scope.listByPage = function (pageNum, pageSize) {
        brandService.listByPage(pageNum, pageSize).success(
            function (response) {
                //显示当前页数据
                $scope.list = response.rows;
                //更新总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        )
    };

    //增加或修改方法
    $scope.save = function () {
        //服务层对象
        var serviceObject;
        if ($scope.entity.id != null) {
            serviceObject = brandService.update($scope.entity);
        } else {
            serviceObject = brandService.add($scope.entity);
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //如果增加成功,就刷新列表
                    $scope.reloadList();
                } else {
                    alert(response.message);
                }
            }
        )
    };

    //通过id找到brand的信息
    $scope.findBrandById = function (id) {
        brandService.findBrandById(id).success(
            function (response) {
                $scope.entity = response;
            }
        )
    };


    //批量删除
    $scope.dele = function () {
        brandService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    //如果增加成功,就刷新列表
                    $scope.reloadList();
                } else {
                    alert(response.message);
                }
            }
        )
    };
    $scope.searchEntity = {};
    //条件查询
    $scope.search = function (pageNum, pageSize) {
        console.log($scope.searchEntity);
        brandService.search(pageNum, pageSize, $scope.searchEntity).success(
            function (response) {
                //显示当前页数据
                $scope.list = response.rows;
                //更新总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        )
    }
});