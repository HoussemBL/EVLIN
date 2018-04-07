<!DOCTYPE html>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<html lang="en">

<head>

<meta charset="utf-8">

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


<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/js/bootstrap-select.min.js"></script>

<!-- (Optional) Latest compiled and minified JavaScript translation files -->
<!--  <script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/js/i18n/defaults-*.min.js"></script>-->

<script src="http://vega.github.io/vega-editor/vendor/d3.min.js"
	charset="utf-8"></script>
<!-- <script src="http://vega.github.io/vega-editor/vendor/vega.js"
	charset="utf-8"></script>
  <script src="http://vega.github.io/vega-editor/vendor/vega-embed.js"
	charset="utf-8"></script> -->
 <script src="vega-embed.js"></script>
	 <script src="vega.js"></script>
	 <script src="vega.min.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/vega/3.0.0-beta.38/vega.js"></script>
<!--  <script src="https://cdnjs.cloudflare.com/ajax/libs/vega-lite/2.0.0-beta.6/vega-lite.js"></script>-->
   <script src="https://cdnjs.cloudflare.com/ajax/libs/vega-embed/3.0.0-beta.19/vega-embed.js"></script>

    <script src="https://vega.github.io/vega-editor/vendor/d3.geo.projection.min.js"></script>
    <script src="https://vega.github.io/vega-editor/vendor/topojson.js"></script>
    <script src="https://vega.github.io/vega-editor/vendor/d3.layout.cloud.js"></script>
    <script src="https://vega.github.io/vega/vega.min.js"></script>

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
				<a class="navbar-brand" href="index.html">EVLIN</a>
			</div>
			<!-- /.navbar-header -->


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
					<h1 class="page-header">Initial Visual Analytics</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row"></div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">

						<!-- /.panel-heading -->
						<div class="panel-body">

							<div class="panel-heading">
							
							
<logic:iterate name="listDB" id="dbtorender" scope="session">
	<logic:equal name="dbtorender"  property="selected" value="true">
	<logic:equal name="dbtorender"  property="real_name" value="USflight">
								<div id="vis92"></div>
								<script type="text/javascript">
									// parse a spec and create a visualization view
									function parse(spec) {

										vg.parse.spec(spec, function(error,
												chart) {

											chart({
												el : "#vis92"
											}).update();
										});

									}
									parse("usa/VegaUSA8.json");
								</script>

								<!-- /.panel-body -->

								<!-- /.panel -->

							</div>

	</logic:equal>
		<logic:equal name="dbtorender"  property="real_name" value="USflightsAll">
								<div id="vis92"></div>
								<script type="text/javascript">
									// parse a spec and create a visualization view
									function parse(spec) {

										vg.parse.spec(spec, function(error,
												chart) {

											chart({
												el : "#vis92"
											}).update();
										});

									}
									parse("usa/VegaUSA8.json");
								</script>

								<!-- /.panel-body -->

								<!-- /.panel -->

							</div>

	</logic:equal>
		<logic:equal name="dbtorender"  property="real_name" value="USflights2">
								<div id="vis92"></div>
								<script type="text/javascript">
									// parse a spec and create a visualization view
									function parse(spec) {

										vg.parse.spec(spec, function(error,
												chart) {

											chart({
												el : "#vis92"
											}).update();
										});

									}
									parse("usa/VegaUSA8.json");
								</script>

								<!-- /.panel-body -->

								<!-- /.panel -->

							</div>

	</logic:equal>
		<logic:equal name="dbtorender"  property="real_name" value="formulaOne">
								
							 <div id="vis"></div>

  <script type="text/javascript">
  //  var y12 ="maps/formulaOne.json" 
  var y12 ="maps/formulaOneDynamic.json"
    	var opt = {
    		"actions" : false,
    		  }
    vega.embed("#vis", y12,opt)
    .hover().addEventListener('click', function(event, item) {
      open('https://watson.analytics.ibmcloud.com/product','_self')
});;
  </script>

								<!-- /.panel-body -->

								<!-- /.panel -->

							</div>

	</logic:equal>
	
			<logic:equal name="dbtorender"  property="real_name" value="soccerDB">
								
							 <div id="vis"></div>

  <script type="text/javascript">
    var y12 ="maps/soccer2.json" 
    	var opt = {


    		"actions" : false,

    		  }
    		vega.embed("#vis",y12,opt)
  //  vega.embed("#vis", y12)
 //   .hover().addEventListener('click', function(event, item) {
    //  open('https://watson.analytics.ibmcloud.com/product','_self')

//});;
  </script>

								<!-- /.panel-body -->

								<!-- /.panel -->

							</div>

	</logic:equal>
	</logic:equal>
</logic:iterate>
							<!-- /.row -->
						</div>
						<div class="panel-heading">
							<i class="fa fa-bar-chart-o fa-fw"></i> Query formulation
							<div class="pull-right"></div>
						</div>
						<!-- /.panel-heading -->
						<html:form action="query2.do" focus="QueryInput" method="GET">
							<html:messages id="err_name" property="common.name.err">
								<div style="color: red">
									<bean:write name="err_name" />
								</div>
							</html:messages>
							<div class="panel-body">

								<div class="panel-footer">

									<div class="input-group">
										<!--   <input id="btn-input" type="text" class="form-control input-sm"
										placeholder="Type your SQL query here..." />-->
										<font size="+20"> <html:text name="QueryInputForm"
												property="content" styleId="btn-input"
												value="Type your SQL query here..."
												styleClass="form-control input-sm" />
										</font> <span class="input-group-btn">
											<button type="submit" class="btn btn-warning btn-sm"
												id="btn-chat">Send</button>
										</span>
									</div>

								</div>

							</div>
						</html:form>
						<!-- /.panel-body -->
					</div>


				</div>
				<!-- /.panel .chat-panel -->
			</div>
			<!-- /.col-lg-4 -->
		</div>
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
	<script src="template/js/bootstrap-select.js"></script>
	<!-- Custom Theme JavaScript -->
	<script src="template/dist/js/sb-admin-2.js"></script>



	<script src="template/js/popper.min.js"></script>
</body>

</html>






