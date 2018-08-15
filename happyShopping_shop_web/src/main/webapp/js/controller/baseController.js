app.controller('baseController', function ($scope) {
    //重新加载列表,数据
    $scope.reloadList = function () {
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };


    //分页控件配置
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            $scope.reloadList();//重新加载
        }
    };

    //定义一个装有id的数组
    $scope.selectIds = [];
    //更新复选框id的数组,当选中的时候,加上复选框的id,不选中的时候,则从数组中删除这个id
    $scope.updateSelection = function ($event, id) {
        //$event事件
        if ($event.target.checked) {
            //如果被选中,就加进ids
            $scope.selectIds.push(id);
        } else {
            //否则就移除
            var index = $scope.selectIds.indexOf(id);
            //移除
            $scope.selectIds.splice(index, 1)
        }
    };

    //转换json字符串为有格式的显示在页面上
    //第一个参数为数据库中的值,第二个为json字符串相对应的key
    $scope.jsonToString = function (jsonString, key) {
        //转换为json对象
        var json = JSON.parse(jsonString);
        var value = "";
        for (var i = 0; i < json.length; i++) {
            //拼接字符串
            if (i > 0){
                value += ",";
            }
            //json[i]表示取出第几条json数据,json[i][key]表示第几条数据的key值对应的value
            value += json[i][key];
        }
        return value;
    };
    //从集合中按照key查询对象
    $scope.searchObjectKey = function (list,key,keyValue) {
        for (var i = 0; i < list.length; i++) {
            //如果能够查出对应的keyValue,就得到这个对象,否则返回null
            if (list[i][key] == keyValue){
                return list[i];
            }
        }
        return null;
    }
});