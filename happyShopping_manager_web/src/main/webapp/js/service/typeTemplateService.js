//服务层
app.service('typeTemplateService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../typeTemplate/findAll.action');
	};
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../typeTemplate/findPage.action?page='+page+'&rows='+rows);
	};
	//查询实体
	this.findOne=function(id){
		return $http.get('../typeTemplate/findOne.action?id='+id);
	};
	//增加 
	this.add=function(entity){
		return  $http.post('../typeTemplate/add.action',entity );
	};
	//修改 
	this.update=function(entity){
		return  $http.post('../typeTemplate/update.action',entity );
	};
	//删除
	this.dele=function(ids){
		return $http.get('../typeTemplate/delete.action?ids='+ids);
	};
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../typeTemplate/search.action?page='+page+"&rows="+rows, searchEntity);
	}    	
});
