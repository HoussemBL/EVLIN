<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<html>
<head>hhhh</head>
<body>
	<h1>Struts 2 Hello World Example</h1>

	<html:form action="/affiche"  method="POST">
		User Name: 
		<html:text name="userForm"  property="username" />
		<html:submit value="inscrire">inscrire</html:submit>

	</html:form>




</body>
</html>