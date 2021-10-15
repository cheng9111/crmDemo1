<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
//http://127.0.0.1:8080/crm/
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
<script type="text/javascript">
$(function () {
	//给"阶段"下拉框添加change事件
	$("#create-transactionStage").change(function () {
		//alert("ok");
		//收集参数
		//var stageValue=$("#create-transactionStage option:selected").text();
		var stageValue=$("#create-transactionStage").find("option:selected").text();
		//alert(stageValue);
		//表单验证
		if (stageValue==""){
			//清空可能性
			$("#create-possibility").val("");
			return;
		}
		//发送请求
		$.ajax({
			url:'workbench/transaction/getPossibilityByStageValue.do',
			data:{
				stageValue:stageValue
			},
			type:'post',
			dataType:'json',
			success:function (data) {
				$("#create-possibility").val(data);
			}
		});
	});

	//当页面加载完成之后，对容器调用工具函数
	var name2id={};
	$("#create-accountName").typeahead({
		//source:['阿里巴巴','京东商城','腾讯','动力节点','字节跳动']
		source:function (query,process) {//每次键盘弹起，都会执行本函数；可以在函数中生成一个数据源(字符串数组)，交给source参数使用；source每次拿到数据源(字符串数组)，都会根据关键字比对,比对成功的字符串都会列容器下方，从而实现自动补全。
			//query：是用户在容器中输入的关键字
			//process：是一个bs_typeahead插件提供的函数，能够把一个json的字符串数组交给source使用。
			//向后台发送异步请求，查询客户名称组成字符串数组，以json字符串的形式返回，交给source使用
			$.ajax({
				url:'workbench/transaction/queryCustomerByName.do',
				data:{
					customerName:query
				},
				type:'post',
				dataType:'json',
				success:function (data) {//data就是json的字符串数组
					var customerNameArr=[];
					//遍历data复杂类型数组
					$.each(data,function (index,obj) {
						//生成简单类型数组
						customerNameArr.push(obj.name);
						//把obj的name和id赋值给name2id，把name作为name2id属性名，id作为name2id的属性值
						name2id[obj.name]=obj.id;
					});
					process(customerNameArr);
				}
			});
		},
		afterSelect:function (item) {//用户选中一项之后，自动触发本函数；
			//item：选中项，补全之后的名字
			//alert(name2id[item]);
			$("#create-customerId").val(name2id[item]);
		}
	});

	//给"保存"按钮添加单击事件
	$("#saveCreateTranBtn").click(function () {
		//收集参数
		var owner=$("#create-transactionOwner").val();
		var money=$.trim($("#create-amountOfMoney").val());
		var name=$.trim($("#create-transactionName").val());
		var expectedDate=$("#create-expectedClosingDate").val();
		var customerName=$.trim($("#create-accountName").val());
		var customerId=$("#create-customerId").val();
		var stage=$("#create-transactionStage").val();
		var type=$("#create-transactionType").val();
		var source=$("#create-clueSource").val();
		var activityId=$("#create-activityId").val();
		var contactsId=$("#create-contactsId").val();
		var description=$.trim($("#create-describe").val());
		var contactSummary=$.trim($("#create-contactSummary").val());
		var nextContactTime=$("#create-nextContactTime").val();
		//表单验证(作业)

		//发送请求
		$.ajax({
			url:'workbench/transaction/saveCreateTran.do',
			data:{
				owner:owner,
				money:money,
				name:name,
				expectedDate:expectedDate,
				customerName:customerName,
				customerId:customerId,
				stage:stage,
				type:type,
				source:source,
				activityId:activityId,
				contactsId:contactsId,
				description:description,
				contactSummary:contactSummary,
				nextContactTime:nextContactTime
			},
			type:'post',
			dataType:'json',
			success:function (data) {
				if (data.code=="1"){
					//跳转到交易的主页面
					window.location.href = "workbench/transaction/index.do";
				}else{
					//提示信息
					alert(data.message);
				}
			}
		});
	});
});
</script>
</head>
<body>

	<!-- 查找市场活动 -->	
	<div class="modal fade" id="findMarketActivity" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable3" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- 查找联系人 -->	
	<div class="modal fade" id="findContacts" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找联系人</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>邮箱</td>
								<td>手机</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>李四</td>
								<td>lisi@bjpowernode.com</td>
								<td>12345678901</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>李四</td>
								<td>lisi@bjpowernode.com</td>
								<td>12345678901</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	
	<div style="position:  relative; left: 30px;">
		<h3>创建交易</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button id="saveCreateTranBtn" type="button" class="btn btn-primary">保存</button>
			<button type="button" class="btn btn-default">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form" style="position: relative; top: -30px;">
		<div class="form-group">
			<label for="create-transactionOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionOwner">
					<c:forEach items="${userList}" var="u">
						<option value="${u.id}">${u.name}</option>
					</c:forEach>
				</select>
			</div>
			<label for="create-amountOfMoney" class="col-sm-2 control-label">金额</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-amountOfMoney">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-transactionName">
			</div>
			<label for="create-expectedClosingDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-expectedClosingDate">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-accountName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="hidden" id="create-customerId">
				<input type="text" class="form-control" id="create-accountName" placeholder="支持自动补全，输入客户不存在则新建">
			</div>
			<label for="create-transactionStage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
			  <select class="form-control" id="create-transactionStage">
			  	<option></option>
			  	<c:forEach items="${stageList}" var="s">
					<option value="${s.id}">${s.value}</option>
				</c:forEach>
			  </select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionType" class="col-sm-2 control-label">类型</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionType">
				  <option></option>
				  <c:forEach items="${transactionTypeList}" var="tt">
						<option value="${tt.id}">${tt.value}</option>
				  </c:forEach>
				</select>
			</div>
			<label for="create-possibility" class="col-sm-2 control-label">可能性</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-possibility" readonly>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-clueSource" class="col-sm-2 control-label">来源</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-clueSource">
				  <option></option>
				  <c:forEach items="${sourceList}" var="so">
					 <option value="${so.id}">${so.value}</option>
				  </c:forEach>
				</select>
			</div>
			<label for="create-activitySrc" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findMarketActivity"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="hidden" id="create-activityId" value="31d63a3514564d51b6758f974c9fafcf">
				<input type="text" class="form-control" id="create-activitySrc" value="测试市场活动01">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findContacts"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="hidden" id="create-contactsId" value="34046b48f12346e9ba43768914c99640">
				<input type="text" class="form-control" id="create-contactsName" value="张三先生">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-describe" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-describe"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-nextContactTime">
			</div>
		</div>
		
	</form>
</body>
</html>