app.service("uploadService",function ($http) {
    this.uploadFile=function () {
        var formData = new FormData();
        //file.files[0]表示html文件中第一个上传文件的组件
        formData.append("file",file.files[0]);

       return $http({
           method:'POST',
           url:"../upload.action",
           data:formData,
           //anjularjs对于post和get请求默认的Content-Type header 是application/json。
           // 通过设置‘Content-Type’: undefined，
           // 这样浏览器会帮我们把Content-Type 设置为 multipart/form-data.
           headers: {'Content-Type':undefined},
           //通过设置 transformRequest: angular.identity ，
           // anjularjs transformRequest function 将序列化我们的formdata object.
           transformRequest: angular.identity
       })
    }
});