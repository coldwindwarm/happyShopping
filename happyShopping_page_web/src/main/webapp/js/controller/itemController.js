//商品详细页（控制层）
app.controller('itemController',function($scope){
	//数量操作
	$scope.addNum=function(x){
		$scope.num=$scope.num+x;
		if($scope.num<1){
			$scope.num=1;
		}
	}	

	//记录用户选择的规格
	$scope.specificationItems = {};
	//用户选择规格
	$scope.selectSpecification = function(name,value){
		
		$scope.specificationItems[name] = value;
		
		//读取sku
		searchSku();
	}
	
	//判断某规格选项是否被用户选中
	$scope.isSelected = function(name,value){
		if($scope.specificationItems[name] == value){
			
			return true;
		}else{
			return false;
		}
		
	}
	
	//加载默认的SKU信息  skuList
	$scope.loadSku = function(){
		$scope.sku = skuList[0];
		
		//深克隆  将默认的SKU信息给  $scope.specificationItems  这样就会默认去调用方法选中了
		$scope.specificationItems = JSON.parse(JSON.stringify($scope.sku.spec));
	}
	
	//选中规格更新SKU
	//匹配两个map对象是否一样
	matchObject = function(map1,map2){
		for(var key in map1){
			if(map1[key] != map2[key]){
				//首先第一个map与第二个map相比,然后第二个map与第一个map相比,
				return false;
			}
		}
		for(var key in map2){
			if(map2[key] != map1[key]){
			
				return false;
			}
		}
		return true;
		
	}
	//在SKU列表中查询当前用户选择的SKU
	searchSku = function(){
		for(var i=0;i<skuList.length;i++){
			if(matchObject(skuList[i].spec,$scope.specificationItems)){
				//如果有和当前用户选择一样的SKU,那么就赋值给当前显示的sku
				$scope.sku = skuList[i];
				return;
			}
			
		}
		//如果没有匹配的	
		$scope.sku={id:0,title:'--------',price:0};
		
	}
	//添加商品到购物车
$scope.addToCart = function(){
	
	alert('skuid:'+$scope.sku.id)
	
}
	
});