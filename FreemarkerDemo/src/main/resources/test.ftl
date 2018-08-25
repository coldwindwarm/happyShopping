<html>
<head>
    <meta charset="utf-8">
    <title>Freemarker入门小DEMO </title>
</head>
<body>
<#include "head.ftl">
<#--我只是一个注释，我不会有任何输出  -->
${name},你好。${message}<br>
<#--assign指令:定义简单类型-->
<#assign linkman="大声点">
联系人:${linkman}<br>
<#--assign指令:定义对象类型：-->
<#assign info={"mobile":"13301231212",'address':'北京市昌平区王府街'} >
电话:${info.mobile} 地址:${info.address}


<#--在freemarker的判断中，可以使用= 也可以使用== -->
<#if success=true>
您已通过实名认证<br>
<#else >
您未通过实名认证<br>
</#if>


----商品价格表----<br>
<#list goodsList as goods>
${goods_index+1}商品名称: ${goods.name} 价格 :${goods.price}<br>
</#list>

<#--1.5 内建函数
内建函数语法格式： 变量+?+函数名称  -->
共${goodsList?size}条记录

<#--1.5.2转换JSON字符串为对象-->
<#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
<#assign data = text?eval/>
开户行:${data.bank}
账户:${data.account}<br>

<#--1.5.3日期格式化-->
当前日期:${today?date}<br>
当前时间:${today?time}<br>
当前日期加时间:${today?datetime}<br>
日期格式化:${today?string("yyyy年MM月dd日")}<br>

<#--1.5.4数字转换为字符串-->
累计积分:${point}<br>
<#--我们会发现数字会以每三位一个分隔符显示，
有些时候我们不需要这个分隔符，就需要将数字转换为字符串,使用内建函数c-->
累计积分:${point?c}<br>
<#--用法为:variable??,如果该变量存在,返回true,否则返回false -->
<#if aaa??>
aaa变量存在
<#else >
aaa变量不存在<br>
</#if>
<#--1.6空值处理运算符-->
<#--1.6.2缺失变量默认值:“!”
我们除了可以判断是否为空值，也可以使用!对null值做转换处理-->
${aaa!'-'}
</html>