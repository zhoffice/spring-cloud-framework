<html>
	<body>
		<form action="/oauth/token" method="post">
			<input type="hidden" name="grant_type" value="authorization_code"/>
			<input type="hidden" name="code" value="${code}"/>
			<input type="hidden" name="client_id" value="client-with-registered-redirect"/>
			<input type="hidden" name="client_secret" value="secret123"/>
			<input type="submit" value="获取token"/>
		</form>
	</body>
</html>