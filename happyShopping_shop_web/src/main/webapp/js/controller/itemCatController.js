//控制层
app.controller('itemCatController', function ($scope, $controller, itemCatService,typeTemplateService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        itemCatService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };

    //分页
    $scope.findPage = function (page, rows) {
        itemCatService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };

    //查询实体
    $scope.findOne = function (id) {
        itemCatService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    };

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = itemCatService.update($scope.entity); //修改
        } else {
            //上级分类id赋值给给实体类的上级id
            $scope.entity.parentId = $scope.parent_id;
           $scope.entity.typeId = $scope.entity.typeId.id;
           // console.log($scope.aaa)
            serviceObject = itemCatService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新加载当前分类
                    $scope.findByParentId($scope.parent_id);
                } else {
                    alert(response.message);
                }
            }
        );
    };


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        itemCatService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                }
            }
        );
    };


    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        itemCatService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                //更新总记录数
                $scope.paginationConf.totalItems = response.total;
            }
        );
    };

    //设置一个上级分类的id
    $scope.parent_id =  0;

    // 通过上级分类id得到商品分类列表
    $scope.findByParentId = function (parentId) {
        $scope.parent_id = parentId;
        itemCatService.findByParentId(parentId).success(
            function (response) {
                $scope.list = response;
            }
        )
    };


    //默认为1级目录
    $scope.grade = 1;

    //设置级别的方法
    $scope.setGrade = function (value) {
        $scope.grade = value;
    };

    //读取顶部列表的方法.分析:,
    $scope.selectList = function (p_entity) {

        // 第一级目录,则二三级为空
        if($scope.grade == 1){
            $scope.entity_1 = null;
            $scope.entity_2 = null;
        }
        // 第二级目录的时候,三级目录为空,
        if($scope.grade == 2){
            $scope.entity_1 = p_entity;
            $scope.entity_2 = null;
        }
        // 第三级目录的时候,改变第三级目录就OK
        if($scope.grade == 3){
            $scope.entity_2 = p_entity;
        }
        //调用寻找到子类列表
        $scope.findByParentId(p_entity.id);
    };

    //实现类型模板的选择框
    $scope.typeTemplateList = {data:[{id:1,text:'联想'},{id:2,text:'华为'},{id:3,text:'小米'}]};
    //从类型模板controller获取类型模板列表
    $scope.findTypeTemplateList = function () {
        typeTemplateService.findTypeTemplateList().success(
            function (response) {
                $scope.typeTemplateList = {data:response};
            }
        )
    };
});	
