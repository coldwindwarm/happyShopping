//服务层
app.service('sellerService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../seller/findAll.action');
	};
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../seller/findPage.action?page='+page+'&rows='+rows);
	};
	//查询实体
	this.findOne=function(id){
		return $http.get('../seller/findOne.action?id='+id);
	};
	//增加 
	this.add=function(entity){
		return  $http.post('../seller/add.action',entity );
	};
	//修改 
	this.update=function(entity){
		return  $http.post('../seller/update.action',entity );
	};
	//删除
	this.dele=function(ids){
		return $http.get('../seller/delete.action?ids='+ids);
	};
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../seller/search.action?page='+page+"&rows="+rows, searchEntity);
	};
	//更新状态
	this.updateStatus = function (sellerId, status) {
        return $http.post('../seller/updateStatus.action?sellerId='+sellerId+"&status="+status);
    }
});
