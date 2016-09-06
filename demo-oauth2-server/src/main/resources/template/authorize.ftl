<html>
<body>
	<h1>授权</h1>
	<p>您确认授权${authorizationRequest.clientId}访问您的信息吗?</p>
	
	<#if scopes??>
		<form id='confirmationForm' name='confirmationForm' action='/oauth/authorize' method='post'>
			<#list scopes?keys as key>
			 key：${key} 
			</#list>
			<input name='scope.read' value='true' type='hidden' />
			<input name='user_oauth_approval' value='true' type='hidden' />
			<label><input name='authorize' value='Authorize' type='submit' /></label>
	<#else>
		<form id='denialForm' name='denialForm' action='/oauth/authorize' method='post'>
			<input name='user_oauth_approval' value='false' type='hidden'/>
			<label><input name='deny' value='Deny' type='submit'/></label>
	</#if>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
</body>
</html>