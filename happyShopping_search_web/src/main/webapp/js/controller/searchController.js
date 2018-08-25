app.controller('searchController', function ($scope,$location, searchService) {

    //搜索
    $scope.search = function () {
        //将文本框的String类型的pageNum转换为Integer类型,不然后端会报错--string不能转化成Integer
        $scope.searchMap.pageNum = parseInt($scope.searchMap.pageNum);
        searchService.search($scope.searchMap).success(
            function (response) {
                //搜索返回的结果
                $scope.resultMap = response;
                buildPageLabel();
            }
        )
    };

    //定义搜索对象
    $scope.searchMap = {
        'keywords': '',
        'category': '',
        'brand': '',
        'spec': {},
        'price': '',
        'pageNum': 1,
        'pageSize': 20,
        'sort':'',
        'sortField':''
    };
    //添加搜索项
    $scope.addSearchItem = function (key, value) {
        if (key == 'category' || key == 'brand' || key == 'price') {
            $scope.searchMap[key] = value;
        } else {
            $scope.searchMap.spec[key] = value;
        }
        //执行搜索,因为searchMap已经对应了
        $scope.search();
    };
    //移除搜索项
    $scope.removeSearchItem = function (key) {
        if (key == 'category' || key == 'brand' || key == 'price') {
            $scope.searchMap[key] = "";
        } else {
            //移除此属性
            delete $scope.searchMap.spec[key]
        }
        //执行搜索,因为searchMap已经对应了
        $scope.search();
    };

    //构建分页标签(totalPage为总页数)
    buildPageLabel = function () {
        //新增分页栏属性
        $scope.pageLabel = [];
        //前面的省略号
        $scope.firstDot = true;
        //后面的省略号
        $scope.endDot = true;
        //起始页码
        var firstPage = 1;
        //最终页码
        var lastPage = $scope.resultMap.totalPage;

        if ($scope.resultMap.totalPage > 5){
            //如果当前页面<=3
            if ($scope.searchMap.pageNum <= 3){
                lastPage = 5;
                //前面省略号隐藏
                $scope.firstDot = false;

                //如果当前页面>=总页数 -2
            }else if ($scope.searchMap.pageNum >= lastPage -2){
                firstPage = lastPage - 4 ;

                //后面省略号隐藏
                $scope.endDot = false;
            }else {
                //正常情况
                firstPage = $scope.searchMap.pageNum - 2 ;
                lastPage = $scope.searchMap.pageNum + 2 ;
            }
        }else {
            //当总页数小于5,前后面省略号隐藏
            $scope.firstDot = false;
            $scope.endDot = false;
        }
        //循环产生页码标签
        for (var i = firstPage; i <= lastPage; i++) {
            $scope.pageLabel.push(i);
        }
        // alert(pageLabel);
    };

    //根据页码查询
    $scope.queryByPage = function (pageNum) {
        //如果当前页小于1或者大于最大页数,就不搜索
        if (pageNum < 1 || pageNum > $scope.resultMap.totalPage){
            return ;
        }
        $scope.searchMap.pageNum = pageNum;
        $scope.search();
    };

    //排序搜索
    $scope.sortSearch = function (sortField,sort) {
        //调用方法赋值给searchMap
        $scope.searchMap.sort = sort;
        $scope.searchMap.sortField = sortField;
        $scope.search();
    };

    //判断关键字是不是含有品牌
    $scope.keywordsIsBrand = function () {
        for (var i = 0; i < $scope.resultMap.brandList.length; i++) {
           if ($scope.searchMap.keywords.indexOf($scope.resultMap.brandList[i].text) > 0){
               return true;
           }
        }
        return false;
    };
    //接收首页跳转的关键字的方法
    $scope.loadKeywords = function () {
        $scope.searchMap.keywords = $location.search()['keywords'];
        $scope.search();
    }
});