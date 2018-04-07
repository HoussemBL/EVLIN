<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<html>
<head></head>
<body>
	<h1>Struts 2 Hello World Example</h1>

	<h2>
		Hello
		
		<h1><bean:write name="userForm" property="username" />
		
	</h2>

</body>
</html>