<!DOCTYPE html>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Visual Analytics</title>

<!-- Bootstrap Core CSS -->
<link
	href="template/bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="template/bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Timeline CSS -->
<link href="template/dist/css/timeline.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="template/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Morris Charts CSS -->
<link href="template/bower_components/morrisjs/morris.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="template/bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

 <script src="http://vega.github.io/vega-editor/vendor/d3.min.js"
	charset="utf-8"></script>
<!-- script src="http://vega.github.io/vega-editor/vendor/vega.js"
	charset="utf-8"></script> -->
<!-- <script src="http://vega.github.io/vega-editor/vendor/vega-embed.js"
	charset="utf-8"></script>  -->
	<!--  <script src="vega-embed.min.js"></script>-->
	  <script src="vega-embed.js"></script> 
	 <script src="vega.js"></script>
	 <script src="vega.min.js"></script> 

<script type="text/javascript">
	function submitForm() {
		alert(document.getElementById('beana').value);

	}
</script>
</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="pointRelais.jsp">EVLIN</a>
			</div>
			<!-- /.navbar-header -->

			<ul class="nav navbar-top-links navbar-right">
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i
						class="fa fa-envelope fa-fw"></i> <i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-messages">
						<li><a href="#">
								<div>
									<strong>John Smith</strong> <span class="pull-right text-muted">
										<em>Yesterday</em>
									</span>
								</div>
								<div>Lorem ipsum dolor sit amet, consectetur adipiscing
									elit. Pellentesque eleifend...</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<strong>John Smith</strong> <span class="pull-right text-muted">
										<em>Yesterday</em>
									</span>
								</div>
								<div>Lorem ipsum dolor sit amet, consectetur adipiscing
									elit. Pellentesque eleifend...</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<strong>John Smith</strong> <span class="pull-right text-muted">
										<em>Yesterday</em>
									</span>
								</div>
								<div>Lorem ipsum dolor sit amet, consectetur adipiscing
									elit. Pellentesque eleifend...</div>
						</a></li>
						<li class="divider"></li>
						<li><a class="text-center" href="#"> <strong>Read
									All Messages</strong> <i class="fa fa-angle-right"></i>
						</a></li>
					</ul> <!-- /.dropdown-messages --></li>
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-tasks fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-tasks">
						<li><a href="#">
								<div>
									<p>
										<strong>Task 1</strong> <span class="pull-right text-muted">40%
											Complete</span>
									</p>
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-success"
											role="progressbar" aria-valuenow="40" aria-valuemin="0"
											aria-valuemax="100" style="width: 40%">
											<span class="sr-only">40% Complete (success)</span>
										</div>
									</div>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<p>
										<strong>Task 2</strong> <span class="pull-right text-muted">20%
											Complete</span>
									</p>
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-info" role="progressbar"
											aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
											style="width: 20%">
											<span class="sr-only">20% Complete</span>
										</div>
									</div>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<p>
										<strong>Task 3</strong> <span class="pull-right text-muted">60%
											Complete</span>
									</p>
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-warning"
											role="progressbar" aria-valuenow="60" aria-valuemin="0"
											aria-valuemax="100" style="width: 60%">
											<span class="sr-only">60% Complete (warning)</span>
										</div>
									</div>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<p>
										<strong>Task 4</strong> <span class="pull-right text-muted">80%
											Complete</span>
									</p>
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-danger"
											role="progressbar" aria-valuenow="80" aria-valuemin="0"
											aria-valuemax="100" style="width: 80%">
											<span class="sr-only">80% Complete (danger)</span>
										</div>
									</div>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a class="text-center" href="#"> <strong>See
									All Tasks</strong> <i class="fa fa-angle-right"></i>
						</a></li>
					</ul> <!-- /.dropdown-tasks --></li>
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-bell fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-alerts">
						<li><a href="#">
								<div>
									<i class="fa fa-comment fa-fw"></i> New Comment <span
										class="pull-right text-muted small">4 minutes ago</span>
								</div>
						</a></li>



						<li class="divider"></li>
						<li><a href="#">
								<div>
									<i class="fa fa-upload fa-fw"></i> Server Rebooted <span
										class="pull-right text-muted small">4 minutes ago</span>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a class="text-center" href="#"> <strong>See
									All Alerts</strong> <i class="fa fa-angle-right"></i>
						</a></li>
					</ul> <!-- /.dropdown-alerts --></li>
				<!-- /.dropdown -->
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
						<i class="fa fa-caret-down"></i>
				</a>
					<ul class="dropdown-menu dropdown-user">
						<li><a href="#"><i class="fa fa-user fa-fw"></i> User
								Profile</a></li>
						<li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
						</li>
						<li class="divider"></li>
						<li><a href="login.html"><i class="fa fa-sign-out fa-fw"></i>
								Logout</a></li>
					</ul> <!-- /.dropdown-user --></li>
				<!-- /.dropdown -->
			</ul>
			<!-- /.navbar-top-links -->


			<!-- add hidden to get facest value -->

				<div class="navbar-default sidebar" role="navigation">
				<div class="sidebar-nav navbar-collapse">
					<ul class="nav" id="side-menu">
			

	<li class="sidebar-search">
							<div class="input-group custom-search-form">
								<html:form action="changeDB.do" focus="formulation"
									focusIndex="DBForm" method="GET">

									<div class="dropdown">
									
									<logic:iterate name="listDB" id="dbElt1" scope="session">
											<logic:equal name="dbElt1"  property="selected" value="true">
									
										
									<button class="btn btn-secondary dropdown-toggle"
											type="button" id="dropdownMenu2" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false">
											<bean:write name="dbElt1" property="name" /></button>
									
											</logic:equal>
									</logic:iterate>
									
									
										
										<div class="dropdown-menu" aria-labelledby="dropdownMenu2">
										
									
									<logic:iterate name="listDB" id="dbElt" scope="session">
											<logic:notEqual name="dbElt"  property="selected" value="true">
										<nested:hidden name="dbElt" styleId="nameeElt" property="real_name" />
										<html:submit  property="name"  styleClass="dropdown-item" >
										<bean:write name="dbElt" property="name" />
									</html:submit>
									<br />
											</logic:notEqual>
									</logic:iterate>
									
									</div> </div>

								</html:form>
							</div>
						</li>

						<!--************************ /input-group *****************************-->

						<li class="sidebar-search">
							<div class="input-group custom-search-form">
								<a><i class="fa fa-dashboard fa-fw"></i> Conditions</a>
								<html:form action="/query.do" focus="formulation"
									focusIndex="FacetForm" method="GET">
									<br />
									<logic:iterate name="attList" id="att" scope="session">
										<nested:hidden styleId="beana" property="value" />
										<bean:define id="attLi" name="att" property="value" />
										<li class="dropdown"><a class="dropdown-toggle"
											data-toggle="dropdown" href="#"><bean:write name="att"
													property="key" /><span class="fa arrow"></span> <span
												class="caret"></span></a>
											<ul class="dropdown-menu">
												<logic:iterate name="attLi" id="facestList">

													<li  ><a href="index.html"><bean:define
																name="facestList" id="beana" property="value" /> <bean:define
																name="facestList" property="key" id="keybean" />
															<li><a href="index.html"><label> <input
																		type="checkbox" name="items"
																		value='<bean:write name="keybean"/>,<bean:write name="beana"/>'>
																		<bean:write name="beana" /></label></a></li> 
																		
																		</a>
													</li>
																		<br />
												</logic:iterate>
											</ul></li>
									<br />
									</logic:iterate>
								</html:form>
							</div>
						</li>

						<!--************************* /.navbar-static-side *****************************-->


					</ul>
				</div>
				<!-- /.sidebar-collapse -->
			</div>

		</nav>


			<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">User's query result</h1>
									<div class="pull-right">
							<div class="btn-group">
								<button type="button"
									class="btn btn-default btn-xs dropdown-toggle"
									data-toggle="dropdown">
									Change View <span class="caret"></span>
								</button>
								<ul class="dropdown-menu pull-right" role="menu">
								
									
									
									
									<logic:iterate name="vispossible" id="vis" scope="session">
									
									
							<logic:notEqual name="vis"  property="status" value="true">
										<li>
									<a href="ChangeVisualization.jsp?typeVis='<bean:write name="vis"  property="name" />'"><bean:write name="vis"  property="name" />
									
								</a>		
									
								</li>
								</logic:notEqual>
						
						</logic:iterate>	
								<li><a href="#">Another action</a></li>
								</ul>
							</div>
						</div>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row"></div>
			
			
			
			
					<logic:iterate name="vispossible" id="vis" scope="session">
									
									
							<logic:equal name="vis"  property="status" value="true">
									
									<bean:define id="visualfile" name="vis"  property="jsonresult" />

					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">

								<!-- /.panel-heading -->
								<div class="panel-body">

									<div class="panel-heading">


										<div id="vis1"></div>
										<script type="text/javascript">

				var table = ${datajson}		
				var file = ${visualfile};	
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#vis1"}).data('table').insert(table)
	  chart({el:"#vis1"}).width(1100).height(600).update();    });
	//   chart({el:"#vis1"}).width(500).height(200).update(); });
}
parse(file);
</script>

										<!-- /.panel-body -->

										<!-- /.panel -->

									</div>
</div>


							</div>


						</div>




					</div>
						<!-- /end row -->
				</logic:equal>
						
						</logic:iterate>		
			
			
			
			
		
		
			<div class="row"></div>
			
			
			
			<div class="row">
					<logic:iterate name="vispossible" id="vis2" scope="session">
									
									
							<logic:notEqual name="vis2"  property="status" value="true">
									
									<bean:define id="visualfile2" name="vis2"  property="jsonresult" />

					
						<div class="col-lg-6">
							<div class="panel panel-default">

								<!-- /.panel-heading -->
								<div class="panel-body">

									<div class="panel-heading">


										<div id="vis<bean:write name="vis2"  property="name" />"></div>
										<script type="text/javascript">

				var table = ${datajson}		
				var file2 = ${visualfile2};	
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#vis<bean:write name="vis2"  property="name" />"}).data('table').insert(table)
	  chart({el:"#vis<bean:write name="vis2"  property="name" />"}).width(550).height(300).update();   });
	
}
parse(file2);
</script>

										<!-- /.panel-body -->

										<!-- /.panel -->

									</div>
</div>


							</div>


						</div>




					
				</logic:notEqual>
						
						</logic:iterate>		
		
	</div>
						<!-- /end row -->	
		
		
		
		
		
		
		
		

			
			
			
			
			
			
			
			
			
			
			
		</div>
		<!-- /.row -->
	</div>


	<!-- /.row -->

	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="template/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="template/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="template/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Morris Charts JavaScript -->
	<script src="template/bower_components/raphael/raphael-min.js"></script>
	<script src="template/bower_components/morrisjs/morris.min.js"></script>
	<script src="template/js/morris-data.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="template/dist/js/sb-admin-2.js"></script>

</body>

</html>






