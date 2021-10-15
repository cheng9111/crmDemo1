<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.bjpowernode.crm.settings.domain.User,java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>测试jstl标签</title>
</head>
<body>
    <%
        int age=20;
    %>

    <%
        if(age>30){
    %>
        <h1>Hello world!</h1>
    <%
        }else{
    %>
            <h1>Hello beijing</h1>
    <%
        }
    %>

    <hr>

    <%
        request.setAttribute("age",20);
    %>
    <c:if test="${age>30}">
        <h1>Hello world!</h1>
    </c:if>
    <c:if test="${age<=30}">
        <h1>Hello beijing!</h1>
    </c:if>

    <hr>
    <%
        for(int i=0;i<10;i++){
    %>
        <h1><%=i %>Hello world</h1>
    <%
        }
    %>

    <hr>

    <!--
        begin：循环变量开始值，
        end:循环变量结束值
        varStatus：循环状态
            -index：循环变量
            -count:循环次数
    -->
    <c:forEach begin="10" end="20" varStatus="vs">
        <h1>${vs.index}Hello world${vs.count}</h1>
    </c:forEach>

    <hr>

    <%
        List<User> userList=new ArrayList<>();
        User user=new User();
        user.setId("1001");
        user.setName("zhangsan");
        user.setEmail("zs@163.com");
        userList.add(user);
        user=new User();
        user.setId("1002");
        user.setName("lisi");
        user.setEmail("ls@163.com");
        userList.add(user);
        user=new User();
        user.setId("1003");
        user.setName("wangwu");
        user.setEmail("ww@163.com");
        userList.add(user);

    %>

    <table border="1" width="30%" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>EMAIL</th>
        </tr>
        <%
            for(User u:userList){
        %>
                <tr>
                    <td><%=u.getId()%></td>
                    <td><%=u.getName()%></td>
                    <td><%=u.getEmail()%></td>
                </tr>
        <%
            }
        %>
    </table>

    <hr>

    <%
        request.setAttribute("userList",userList);
    %>
    <table border="1" width="30%" cellspacing="0">
        <tr>
            <th>NO</th>
            <th>ID</th>
            <th>NAME</th>
            <th>EMAIL</th>
        </tr>
        <!--
            items：要遍历的数组或者集合，使用EL表达式获取；所以，在使用jstl标签之前，必须把所需数据保存到作用域中。
            var：相当于循环变量，从集合或者数组中每次获取到的对象，保存到var指定的变量中。
        -->
        <c:forEach items="${userList}" var="u" varStatus="vs">
            <tr>
                <td>${vs.count}</td>
                <td>${u.id}</td>
                <td>${u.name}</td>
                <td>${u.email}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
