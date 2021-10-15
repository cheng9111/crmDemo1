<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
    <base href="<%=basePath%>">
    <title>演示bs_typeahead插件</title>
    <script type="text/javascript">
        $(function () {
            //保存用户名的id
            var name2id={};
           $("#customerName").typeahead({
               //source:['字节跳动','动力节点','国庆节']
               //query:在文本框输入的关键字，内部keyup
               source:function (query,process) {
                   $.ajax({
                       url:'workbench/transaction/typeahead.do',
                       data:{
                           customerName:query
                       },
                       type:'post',
                       dataType:'json',
                       success:function (data) {  //customerList包含3个customer对象，
                            //alert(data.length);
                           var customerNameArr=[];
                           $.each(data,function (index,obj) {
                                //alert(obj.name);
                               //放name
                               customerNameArr.push(obj.name);
                               //放id,ojb.name客户名做为数组的下标，将obj.id，id值做为这个下标对应的值。
                               //字节跳动=001 动力节点=002 国庆节=003
                               name2id[obj.name]=obj.id;

                           });
                           //将customerNameArr交给source
                           process(customerNameArr);
                       }
                   })
               },
               //选择之后
               afterSelect:function(item){
                  // alert(item);
                   alert(name2id[item]);

               }
           });
        });
    </script>
</head>
<body>
<input type="text" id="customerName">
</body>
</html>
