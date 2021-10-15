<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <base href="<%=basePath%>">
    <title>演示文件上传</title>
</head>
<body>
<!--
    关于文件上传的表单：
       1、上传文件只能用file组件(<input type="file">)；比如：<input type="text|password|hidden|radio|checkbox|....">、<select>、<textarea>等
       2、文件上传的请求只能用post，不能用get；
          get和post最根本的区别在于向后台提交数据的方式不一样：
          get:参数在url后边，通过请求头提交到后台;长度有限制；只能提交文本数据；可以使用浏览器缓存，get效率高。
          post:参数通过请求体提交到后台;理论上，对长度没有限制；既能提交文本数据，又能提交二进制数据；不能使用浏览器缓存，post效率低。
       3、文件上传的表单编码格式必须是：multipart/form-data
          浏览器每次向后台发送请求，都会对所有的参数进行统一编码，默认采用的编码格式是application/x-www-form-urlencoded；urlencoded这种编码格式只能对文本进行编码，所以，浏览器会首先把所有参数统一转换成文本数据，然后再进行urlencoded编码。
          文件上传的表单，只能采用multipart/form-data编码格式。
-->
 <form action="workbench/activity/fileUpload.do" method="post" enctype="multipart/form-data">
     <input type="file" name="myFile"><br>
     <input type="text" name="username"><br>
     <input type="submit" value="提交">
 </form>
</body>
</html>
