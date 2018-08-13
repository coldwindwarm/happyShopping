//自定义服务层
app.service('brandService', function ($http) {
    //查询品牌列表
    this.findAll = function () {
        return $http.get('../brand/list.action');
    };

    //分页查看品牌列表
    this.listByPage = function (pageNum, pageSize) {
        return $http.get('../brand/listByPage.action?pageNum=' + pageNum + '&pageSize=' + pageSize);
    };

    //增加方法
    this.add = function (entity) {
        return $http.post('../brand/add.action', entity);
    };

    //修改方法
    this.update = function (entity) {
        return $http.post('../brand/update.action', entity);
    };

    //通过id找到brand的信息
    this.findBrandById = function (id) {
        return $http.get('../brand/findBrandById.action?id=' + id);
    };

    //批量删除
    this.dele = function (selectIds) {
        return $http.get('../brand/delete.action?ids=' + selectIds)
    };
    //条件查询
    this.search = function (pageNum, pageSize, searchEntity) {
        return $http.post('../brand/search.action?pageNum=' + pageNum + '&pageSize=' + pageSize, searchEntity);
    };
    //下拉列表品牌查询
    this.findBrandList = function () {
        return $http.get('../brand/selectOptionList.action');
    }
});