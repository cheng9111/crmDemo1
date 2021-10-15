<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <base href="<%=basePath%>">
    <title>演示文件下载</title>
    <script type="text/javascript">
        $(function () {
            //给"下载"按钮添加单击事件
            $("#fileDownLoad").click(function () {
                //发送下载请求(只能是同步请求)
                window.location.href = "workbench/activity/fileDownLoad.do";
            });
        });
    </script>
</head>
<body>
<input type="button" value="下载" id="fileDownLoad">
</body>
</html>
