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
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/js/bootstrap-select.min.js"></script>

<!-- (Optional) Latest compiled and minified JavaScript translation files -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/js/i18n/defaults-*.min.js"></script>

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
<script src="http://vega.github.io/vega-editor/vendor/vega.js"
	charset="utf-8"></script>
<script src="http://vega.github.io/vega-editor/vendor/vega-embed.js"
	charset="utf-8"></script>

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
				<a class="navbar-brand" href="index.html">Database</a>
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
									<i class="fa fa-twitter fa-fw"></i> 3 New Followers <span
										class="pull-right text-muted small">12 minutes ago</span>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<i class="fa fa-envelope fa-fw"></i> Message Sent <span
										class="pull-right text-muted small">4 minutes ago</span>
								</div>
						</a></li>
						<li class="divider"></li>
						<li><a href="#">
								<div>
									<i class="fa fa-tasks fa-fw"></i> New Task <span
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
								<html:form action="/query.do" focus="formulation"
									focusIndex="FacetForm" method="GET">
									<html:submit styleClass="fa fa-search">
										Confirm
									</html:submit>
							</div> <!--************************ /input-group *****************************-->
						</li>




						<logic:iterate name="attList" id="att" scope="session">
							<nested:hidden styleId="beana" property="value" />
							<li><a href="index.html"> <i
									class="fa fa-dashboard fa-fw"></i> <bean:write name="att"
										property="key" /><span class="fa arrow"></span></a>
								<ul class="nav nav-second-level">
									<bean:define id="attLi" name="att" property="value" />

									<logic:iterate name="attLi" id="facestList">

										<li><a><bean:define name="facestList" id="beana"
													property="value" /> <bean:define name="facestList"
													property="key" id="keybean" />

												<div class="checkbox">
													<label><input type="checkbox" name="items"
														value='<bean:write name="keybean"/>,<bean:write name="beana"/>'>
														<bean:write name="beana" /></label>
												</div> </a></li>
									</logic:iterate>
								</ul>
						</logic:iterate>

						</html:form>
						</li>

						<!--************************* /.navbar-static-side *****************************-->
					</ul>
				</div>
				<!-- /.sidebar-collapse -->
			</div>


		</nav>


		<div id="page-wrapper">
			<div class="row">
			<div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Recommended Visual Analytics 
    
                            <div class="pull-right">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                                        Actions
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu pull-right" role="menu">
                                        <li><a href="Extension2.jsp">More views</a>
                                        </li>
                                    
                                        </li>
                                    
                                    </ul>
                                </div>
                            </div>
                        </div>
			</div>
<br>	</br>	
				<div id="vis123"></div>
				<script type="text/javascript">

				var tab = ${datajs}	;
				var file0 = ${file[3]};		
					
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#vis123"}).data('table').insert(tab)
	  chart({el:"#vis123"}).height(450).width(750).update();    });
	//   chart({el:"#vis1"}).width(500).height(200).update(); });
}
parse(file0);
</script>	
		
		
	<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">History</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="#">(		<bean:write name="fieldInterest" scope="session"/> = <bean:write name="valueInterest" scope="session"/>) </a></li>
   
      <li><a href="#">(<bean:write name="vis" scope="session"/>
					
					)</a></li> 
     
    </ul>
  </div>
</nav>	
		
		
		
			
			
					
		<div class="panel-body">






				First Visualization
				
	<div id="vis1"></div>

				<script type="text/javascript">
				var file1222 = ${file[0]};
				var table1333 = ${datajs}	;
				var table1555 = ${recset[5]};	
				var table1222 = ${recset[4]};		
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#vis1"}).data('source').insert(table1333)
	  chart({el:"#vis1"}).data('table').insert(table1222) 
	chart({el:"#vis1"}).data('table').insert(table1555)
	 chart({el:"#vis1"}).height(450).width(700).update();    });
	
}
parse(file1222);
</script>			
				
				
				
				
		<br></br>		
				
				
				
				
				
	<div id="vis25"></div>
  				<br></br> <br></br>
  		
				<div id="vis5"></div>



<logic:empty name="chiffre1"> 

				
				<script type="text/javascript">
				var file = ${file[2]};
				var table = ${recset[0]};	
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#vis5"}).data('table').insert(table)
	
	  
	chart({el:"#vis5"}).height(350).width(700).update();   





		   });
	
}
parse(file);
</script>
</logic:empty>

<logic:notEmpty name="chiffre1">

			<script type="text/javascript">
				var file1 = ${file[2]};
				var table1 = ${recset[0]};	
				var table2 = ${recset[1]};	
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#vis5"}).data('table').insert(table1)
	chart({el:"#vis5"}).data('table').insert(table2)
	  
	chart({el:"#vis5"}).height(300).width(600).update();   





		   });
	
}
parse(file1);
</script>
</logic:notEmpty>


















  				
  <logic:empty name="chiffre2"> 				


				<script type="text/javascript">
				var file22 = ${file[1]};
				var table22 = ${recset[2]};	
				var table333 = ${datajs}	;	
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#vis25"}).data('source').insert(table333)
	  chart({el:"#vis25"}).data('table').insert(table22)
	
	  
	  chart({el:"#vis25"}).height(450).width(700).update();    });
	
}
parse(file22);
</script>
</logic:empty> 


  <logic:notEmpty name="chiffre2"> 				
	

				<script type="text/javascript">
				var file223 = ${file[1]};
				var table333 = ${datajs}	;
				var table223 = ${recset[2]};	
				var table553 = ${recset[3]};		
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#vis25"}).data('source').insert(table333)
	  chart({el:"#vis25"}).data('table').insert(table223)
	chart({el:"#vis25"}).data('table').insert(table553)
	  
	  chart({el:"#vis25"}).height(450).width(700).update();    });
	
}
parse(file223);
</script>


</logic:notEmpty> 






			</div>
					
						</div>
						<!-- /.panel-body -->						

			
			
			

			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->

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






