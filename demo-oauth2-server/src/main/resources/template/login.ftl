<html>
	<body>
		<h1>Login</h1>
		<form action="/login" method="POST">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div>name:</div>
			<div><input type="text" name="username"/></div>
			<div>passwd:</div>
			<div><input type="password" name="password"/></div>
			<div><input type="submit" value="提交"/></div>
		</form>
	</body>
</html>