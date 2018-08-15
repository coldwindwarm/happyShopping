//控制层
app.controller('goodsController', function ($scope, $controller, goodsService, uploadService, itemCatService, typeTemplateService,$location) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };

    //分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };

    //查询实体
    $scope.findOne = function (id) {
        //获取url上的id数值
        // 注意： ?前要加# ，则是angularJS的地址路由的书写形式
        var id = $location.search()['id'];
        //如果没有带id过来,就让其return,不回显
        if (id == null){
            return;
        }
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;

                //向富文本编辑器添加商品介绍
                editor.html($scope.entity.goodsDesc.introduction);

                //显示图片列表.将json数据转换为json对象
                $scope.entity.goodsDesc.itemImages = JSON.parse($scope.entity.goodsDesc.itemImages);

                //显示扩展属性--和新增的时候冲突,需要根据url的id判断
                $scope.entity.goodsDesc.customAttributeItems=  JSON.parse($scope.entity.goodsDesc.customAttributeItems);

                //显示规格属性
                $scope.entity.goodsDesc.specificationItems = JSON.parse($scope.entity.goodsDesc.specificationItems);

                //显示SKU列表,循环遍历SKU列表--将SPEC:json数据转换为json对象
                for (var i = 0; i < $scope.entity.itemList.length; i++) {
                    $scope.entity.itemList[i].spec = JSON.parse($scope.entity.itemList[i].spec);
                }
            }
        );
    };
    //保存
    $scope.save=function(){
        //得到商品描述的富文本
        $scope.entity.goodsDesc.introduction=editor.html();

        //服务层对象
        var serviceObject;
        //如果有ID
        if($scope.entity.goods.id!=null){
            //修改
            serviceObject=goodsService.update( $scope.entity );
        }else{
            //增加
            serviceObject=goodsService.add( $scope.entity  );
        }
        serviceObject.success(
            function(response){
                if(response.success){
                    alert("保存成功");
                    //跳转到商品列表页面
                    location.href='goods.html';

                }else{
                    alert(response.message);
                }
            }
        );
    };


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        goodsService.dele($scope.selectIds).success(
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
        goodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    };
    //商品控制层
    $scope.uploadFile = function () {
        uploadService.uploadFile().success(
            function (response) {
                console.log(response);
                if (response.success) {
                    //上传成功,取出图片地址并显示
                    $scope.image_entity.url = response.message;
                } else {
                    alert(response.message);
                }
            }
        ).error(function () {
            alert("上传发生错误");
        })
    };

    //定义添加图片页面实体结构--初始化
    $scope.entity = {goods: {}, goodsDesc: {itemImages: [], specificationItems: []}};
    //添加图片列表
    $scope.add_image_entity = function () {
        //商品描述的商品图片为json格式   {颜色:"",url:""}
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    };

    //删除列表的图片
    $scope.remove_image_entity = function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index, 1);
    };

    //读取一级分类
    $scope.selectItemCatListOne = function () {
        itemCatService.findByParentId(0).success(
            function (response) {
                $scope.ItemCatListOne = response;
            }
        )
    };
    //读取二级分类:通过angularJs的监听方法
    //newValue代表改变后的值oldValue代表以前的值
    $scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.ItemCatListTwo = response;
            }
        )
    });
    //读取三级分类:通过angularJs的监听方法
    //newValue代表改变后的值oldValue代表以前的值
    $scope.$watch('entity.goods.category2Id', function (newValue, oldValue) {
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.ItemCatListThree = response;
            }
        )
    });
    //读取对应分类下的模板ID:通过angularJs的监听方法
    //newValue代表改变后的值oldValue代表以前的值
    $scope.$watch('entity.goods.category3Id', function (newValue, oldValue) {
        itemCatService.findOne(newValue).success(
            function (response) {
                $scope.entity.goods.typeTemplateId = response.typeId;
            }
        )
    });
    //读取对应模板ID下的品牌列表:通过angularJs的监听方法
    //newValue代表改变后的值oldValue代表以前的值
    $scope.$watch('entity.goods.typeTemplateId', function (newValue, oldValue) {
        typeTemplateService.findOne(newValue).success(
            function (response) {
                $scope.typeTemplate = response;
                //将类型模板的json数据转为对象,赋值给$scope.typeTemplate.brandIds
                $scope.typeTemplate.brandIds = JSON.parse(response.brandIds);

                //扩展属性,json字符串转为对象显示在页面上
                if($location.search()['id'] == null) {
                    $scope.entity.goodsDesc.customAttributeItems = JSON.parse(response.customAttributeItems);
                }
            }
        );

        //得到规格选项
        typeTemplateService.findSpecList(newValue).success(
            function (response) {
                $scope.specList = response;
            }
        )
    });

    //更新点击规格选项得到的数据
    $scope.updateSpecAttribute = function ($event, name, value) {
        //查询有没有对象的attributeName
        var object = $scope.searchObjectKey(
            $scope.entity.goodsDesc.specificationItems, 'attributeName', name
        );
        if (object != null) {
            //如果规格选项被选中
            if ($event.target.checked) {
                object.attributeValue.push(value);
            } else {
                //如果不选中,就移除这个规格选项
                object.attributeValue.splice(object.attributeValue.indexOf(value), 1);
                //如果规格选项都没了,就移除这个记录
                if (object.attributeValue.length == 0) {
                    $scope.entity.goodsDesc.specificationItems.splice($scope.entity.goodsDesc.specification_items.indexOf(object), 1);
                }
            }

        } else {
            //如果规格选项中没有这个规格,就添加这个规格
            $scope.entity.goodsDesc.specificationItems.push({"attributeName": name, "attributeValue": [value]});
        }
    };

    //创建SKU列表
    $scope.createItemList = function () {
        //初始化itemList
        $scope.entity.itemList = [{spec: {}, price: 0, num: 99999, status: '0', isDefault: '0'}];

        var items = $scope.entity.goodsDesc.specificationItems;

        // alert(items);
        //遍历规格选项
        for (var i = 0; i < items.length; i++) {
            $scope.entity.itemList = addColumn( $scope.entity.itemList,items[i].attributeName,items[i].attributeValue)
        }
    };
    //定义一个深克隆的方法
    addColumn = function (list, columnName, columnValues) {
        //新建一个新的集合
        var newList = [];
        //遍历规格选项的选项--attributeValue
        for (var i = 0; i < list.length; i++) {
            var oldRow = list[i];
            for (var j = 0; j < columnValues.length; j++) {
               //深克隆:现将json对象转为字符串,再转换成一个新的json对象
                var newRow = JSON.parse(JSON.stringify(oldRow));

                //newRow表示一条新的item记录,为了给后端传值--item表
                newRow.spec[columnName] = columnValues[j];
                //将克隆后的item加入到新的list
                newList.push(newRow);
            }
        }
        return newList;
    };

    //创建状态数组,根据索引和状态值对应
    $scope.status=['未审核','已审核','审核未通过','商家关闭'];

    //构建分类数组,通过查询所有分类,以分类id为索引,以分类名称为id对应的值
    $scope.itemCatList = [];
    $scope.findItemCatList = function () {
        itemCatService.findAll().success(
            function (response) {
                //循环遍历分类 response是一个所有分类的数组
                for (var i = 0; i < response.length; i++) {
                    $scope.itemCatList[response[i].id] = response[i].name;
                }
            }
        )
    };

    //修改商品回显规格选项的时候,判断复选框勾选
    $scope.checkAttributeValue = function (specName,optionName) {
        var items = $scope.entity.goodsDesc.specificationItems;
        //查询有没有对应的规格名称
        var object = $scope.searchObjectKey(items,'attributeName',specName);
        //没有肯定不勾选
        if (object == null){
            return false;
        }else {
            //如果有对应的规格名称,且规格选项也有对应的,那么就勾选
            if(object.attributeValue.indexOf(optionName) >= 0){
                return true;
            }else {
                return false;
            }
        }
    }
});
