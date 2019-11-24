<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="/resource/css/bootstrap.min.css" />
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resource/js/cms.js"></script>
<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function query(){
	//会跳转到别的页面
	//location.href="users?username="+$("[name='username']").val();
	$("#center").load("/admin/user/users?username="+$("[name='username']").val());
}


</script>
<title>用户列表</title>
</head>
<body>
	<div class="form-group form-inline">
		<label for="username"></label>用戶名：<input id="username" type="text"
			class="form-control" name="username" value="${user.username}">&nbsp;
		<button type="button" class="btn btn-success" onclick="query()">查詢</button>
	</div>

	<table class="table table-hover  table-bordered">
		<thead class="thead-light">
			<tr>
				<th>序号</th>
				<th>用户名</th>
				<th>昵称</th>
				<th>性别</th>
				<th>生日</th>
				<th>注册时间</th>
				<th>更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var="u" varStatus="i">

				<tr>
					<td>${(info.pageNum-1) * info.pageSize+i.index+1 }</td>
					<td>${u.username }</td>
					<td>${u.nickname }</td>
					<td>${u.gender==1?"男":"女"}</td>
					<td><fmt:formatDate value="${u.birthday}" pattern="yyyy-MM-dd" /></td>
					<td><fmt:formatDate value="${u.created }"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><fmt:formatDate value="${u.updated }"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><c:if test="${u.locked==0 }">
								<button type="button" class="btn btn-success" onclick="update(this,${u.id})">正常</button>
							</c:if> <c:if test="${u.locked==1 }">
								<button  type="button" class="btn btn-danger" onclick="update(this,${u.id})">停用</button>

							</c:if></td>
				</tr>
			</c:forEach>
		</tbody>

	</table>
	 <!-- 引入分页信息 -->
		<jsp:include page="/WEB-INF/view/common/pages.jsp"/>

	<script type="text/javascript">
				//用戶信息列表分頁
			 function goPage(page){
				 var url ="/admin/user/users?page="+page+"&username="+$("[name='username']").val();
				 //在中间区域加载分页页面
				 $("#center").load(url);
				 
			 }
			 
			 
 	 function update(obj,id){
	  //0:正常 1:停用
	  //如果当前状态为正常,则改为停用.如果是停用则改为正常
	
	  var locked =$(obj).text()=="正常"?"1":"0";
	 
	  $.post("/admin/user/update",{id:id,locked:locked},function(flag){
        if(flag){
        //	alert("操作成功");
        	$(obj).text(locked==1?"禁用":"正常");//先改变按钮内容
        	$(obj).attr("class",locked=="0"?"btn btn-success":"btn btn-danger")//改变按钮颜色
        }else{
        	alert(flag.msg)
        }		  
	  })
	  
  }
 
 
 </script>
</body>
</html>