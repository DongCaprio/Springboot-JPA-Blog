<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form action="/user/join" method="post">
	<input type="hidden" id="id" value="${principal.user.id }">
		<div class="form-group">
			<label for="username">Username</label>
			<input type="text" value="${principal.user.username }" class="form-control" placeholder="Enter username" id="username" readonly="readonly">
		</div>

		<div class="form-group">
			<label for="password">Password</label>
			<input type="password" class="form-control" placeholder="Enter password" id="password">
		</div>

		<div class="form-group">
			<label for="email">Email</label>
			<input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email">
		</div>


		<button id="btn-update" class="btn btn-primary">정보수정 완료</button>
	</form>

</div>

<!-- /하면 static폴더를 바로찾아감(스프링부트?)  -->
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>

