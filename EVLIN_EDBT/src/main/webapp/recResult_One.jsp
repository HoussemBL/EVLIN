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
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/css/bootstrap-select.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/js/bootstrap-select.min.js"></script>

<!-- (Optional) Latest compiled and minified JavaScript translation files -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.11.2/js/i18n/defaults-*.min.js"></script>

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
<!--<script src="http://vega.github.io/vega-editor/vendor/vega.js"
	charset="utf-8"></script>
<script src="http://vega.github.io/vega-editor/vendor/vega-embed.js"
	charset="utf-8"></script> -->
	  <script src="vega-embed.js"></script> 
	 <script src="vega.js"></script>
	<!--  <script src="vega.min.js"></script>  -->

<script type="text/javascript">
	function submitForm() {
		alert(document.getElementById('beana').value);

	}
</script>
</head>

<body>



	<!-- Navigation -->


	<div id="page-wrapper">




		<nav class="navbar navbar-default">
			<div class="container-fluid">
	<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="pointRelais.jsp">EVLIN</a>
			</div>

				<div class="navbar-header">
					<a class="navbar-brand" href="#">History</a>
				</div>
				<ul class="nav navbar-nav">

					<logic:iterate name="provenance" id="prov" scope="session">
						<li class="active"><a href="#">(<bean:write name="prov"
									property="key" /> = <bean:write name="prov" property="value" />)
						</a></li>
					</logic:iterate>

					<logic:iterate name="temporary" id="tempo" scope="session">
						<li class="active"><a href="#">(<bean:write name="tempo"
									property="key" /> = <bean:write name="tempo" property="value" />)
						</a></li>
					</logic:iterate>
				</ul>
			</div>
		</nav>


		<div class="panel-body">


			<div class="row">

				<div class="col-sm-12">
					<div id="visrec"></div>
				</div>


			</div>






			<logic:notEmpty name="rec1">



				<logic:iterate name="vispossible" id="vis" scope="session">


					<logic:equal name="vis" property="status" value="true">

						<bean:define id="visualfile" name="vis" property="jsonresult" />

						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-default">

									<div class="panel-heading">
										<i class="fa fa-bar-chart-o fa-fw"></i> Recommended
										Visualization
										<div class="pull-right">
											<div class="btn-group">
												<button type="button"
													class="btn btn-default btn-xs dropdown-toggle"
													data-toggle="dropdown">
													Actions <span class="caret"></span>
												</button>
												<ul class="dropdown-menu pull-right" role="menu">


													<logic:iterate name="vispossible" id="visa" scope="session">


														<logic:notEqual name="visa" property="status" value="true">
															<li><a
																href="ChangeRecVis.jsp?type=Zoom&typeVis='<bean:write name="visa"  property="name" />'"><bean:write
																		name="visa" property="name" /> </a></li>
														</logic:notEqual>

													</logic:iterate>


												</ul>
											</div>
										</div>
									</div>





									<!-- /.panel-heading -->
									<div class="panel-body">




										<div id="visrec1"></div>




										<script type="text/javascript">
				var file222 = ${visualfile};
				var table222 = ${recset[0]};	
			
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {

	 chart({el:"#visrec1"}).data('table').insert(table222) 
	
	
	 chart({el:"#visrec1"}).height(550).width(900)
	  chart({el:"#visrec1"}).update(); 
	// var xx=chart({el:"#visrec1"});
	 var yy= "true";
	
	
	    });
	
}

parse(file222);


</script>

										<!-- /.panel-body -->

										<!-- /.panel -->
									</div>

								</div>

							</div>
							

						</div>
						<!-- /end row -->
						
		<!------------------ other details now ----------->				
		<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-default">

									<div class="panel-heading">
										<i class="fa fa-bar-chart-o fa-fw"></i> Further details about recommended
										visualization
									
									</div>





									<!-- /.panel-heading -->
									<div class="panel-body">




										<div id="visrec1details"></div>




										<script type="text/javascript">
				var schemaOtherZI = ${file[1]};
	
				var tableother = ${recset[1]};	
				//var mappingAll = ${recset[3]};
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	//  chart({el:"#visrec1details"}).data('mapping').insert(mappingAll)
	 chart({el:"#visrec1details"}).data('table').insert(tableother) 
	 chart({el:"#visrec1details"}).height(550).width(900).update();    });
	
}
parse(schemaOtherZI);
</script>

										<!-- /.panel-body -->

										<!-- /.panel -->
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


						<logic:notEqual name="vis2" property="status" value="true">

							<bean:define id="visualfile2" name="vis2" property="jsonresult" />


							<div class="col-lg-6">
								<div class="panel panel-default">

									<!-- /.panel-heading -->
									<div class="panel-body">

										<div class="panel-heading">


											<div id="vis<bean:write name="vis2"  property="name" />"></div>
											<script type="text/javascript">
				var file2 = ${visualfile2};
				var table333 = ${datajson}	;
				var table222 = ${recset[0]};	
				var colors = ${recset[2]};
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#vis<bean:write name="vis2"  property="name" />"}).data('mapping').insert(colors)
		 chart({el:"#vis<bean:write name="vis2"  property="name" />"}).data('source').insert(table333)
		 chart({el:"#vis<bean:write name="vis2"  property="name" />"}).data('table').insert(table222) 
	 chart({el:"#vis<bean:write name="vis2"  property="name" />"}).height(350).width(500).update();    });
	
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



			</logic:notEmpty>



















			<!--   %%%%%%%%%%%%%%%%%%%%%  zoom in slice %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% -->


			<logic:notEmpty name="rec1_Slice">



				<logic:iterate name="vispossible" id="vis" scope="session">


					<logic:equal name="vis" property="status" value="true">

						<bean:define id="visualfile" name="vis" property="jsonresult" />

						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-default">



	
								<div class="panel-heading">
										<i class="fa fa-bar-chart-o fa-fw"></i> Recommended
										Visualization
										<div class="pull-right">
											<div class="btn-group">
												<button type="button"
													class="btn btn-default btn-xs dropdown-toggle"
													data-toggle="dropdown">
													Actions <span class="caret"></span>
												</button>
												<ul class="dropdown-menu pull-right" role="menu">


													<logic:iterate name="vispossible" id="visa" scope="session">


														<logic:notEqual name="visa" property="status" value="true">
															<li><a
																href="ChangeRecVis.jsp?type=ZoomIn_Slice&typeVis='<bean:write name="visa"  property="name" />'"><bean:write
																		name="visa" property="name" /> </a></li>
														</logic:notEqual>

													</logic:iterate>


												</ul>
											</div>
										</div>
									</div>





									<!-- /.panel-heading -->
									<div class="panel-body">




										<div id="vis1"></div>
										<script type="text/javascript">

				var tableZoomSlice = ${recset[0]}		
				var file = ${visualfile};	
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#vis1"}).data('table').insert(tableZoomSlice)
	//  chart({el:"#vis1"}).width(900).height(550).update();  
	  chart({el:"#vis1"}).update();    });
	
}
parse(file);
</script>

										<!-- /.panel-body -->

										<!-- /.panel -->


									</div>


								</div>


							</div>




						</div>
						<!-- /end row -->
						
				<!--%%%%%%%%%%%%%%%%%%%%%%%%%% show user more details in Zoom in Slice %%%%%%%%%%%%%%%%%%%%%%%%%%%% -->		
						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-default">




									<div class="panel-heading">
										<i class="fa fa-bar-chart-o fa-fw"></i> Detailed visualization
									
									</div>





									<!-- /.panel-heading -->
									<div class="panel-body">




										<div id="vis1details-ZS"></div>
										<script type="text/javascript">

				var tableZoomSliceDetails = ${recset[1]}		
				var file = ${visualfile};	
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#vis1details-ZS"}).data('table').insert(tableZoomSliceDetails)
	  chart({el:"#vis1details-ZS"}).width(1000).height(550).update();    });
	
}
parse(file);
</script>

										<!-- /.panel-body -->

										<!-- /.panel -->


									</div>


								</div>


							</div>




						</div>	
						
					</logic:equal>

				</logic:iterate>






				<div class="row"></div>

			

				<div class="row">
					<logic:iterate name="vispossible" id="vis2" scope="session">


						<logic:notEqual name="vis2" property="status" value="true">

							<bean:define id="visualfile2" name="vis2" property="jsonresult" />


							<div class="col-lg-6">
								<div class="panel panel-default">

									<!-- /.panel-heading -->
									<div class="panel-body">

										<div class="panel-heading">


											<div id="vis<bean:write name="vis2"  property="name" />"></div>
											<script type="text/javascript">

				var tableZoomSlice = ${recset[0]};	
				var file2 = ${visualfile2};	
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#vis<bean:write name="vis2"  property="name" />"}).data('table').insert(tableZoomSlice)
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


			</logic:notEmpty>

<!--    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%                  her we start   showing       extension   %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% -->

			<logic:notEmpty name="rec2full">

				<script type="text/javascript">
				var file222 = ${file[0]};
				var table333 = ${datajson}	;
				var table222 = ${recset[0]};	
				var table555 = ${recset[1]};		
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#visrec"}).data('source').insert(table333)
	 chart({el:"#visrec"}).data('table').insert(table222) 
	chart({el:"#visrec"}).data('table').insert(table555)
	 chart({el:"#visrec"}).height(550).width(900).update();    });
	
}
parse(file222);
</script>

			</logic:notEmpty>





<!--   %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  Extension %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% -->

			<logic:notEmpty name="rec2">

		<div class="row"></div>

			<div class="row">
					
						<div class="col-lg-6">
							<div class="panel panel-default">

								<!-- /.panel-heading -->
								<div class="panel-body">

											<div class="panel-heading">
										<i class="fa fa-bar-chart-o fa-fw"></i> Main recommended Visualization
									
									</div>


										<div id="visRecFirst"></div>
				<script type="text/javascript">
				var fileRecExt = ${file[3]};
				//var table333 = ${datajson}	;
				var tablePrinc = ${recset[0]};	
				//var colors1 = ${recset[4]};	
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	//  chart({el:"#visRecFirst"}).data('mapping').insert(colors1)
	//  chart({el:"#visRecFirst"}).data('source').insert(table333)
	 chart({el:"#visRecFirst"}).data('table').insert(tablePrinc) 
	chart({el:"#visRecFirst"}).height(550).width(550).update();    });
	
}
parse(fileRecExt);
</script>
					</div>  </div> 
</div>  



						<div class="col-lg-6">
							<div class="panel panel-default">

								<!-- /.panel-heading -->
								<div class="panel-body">

									<div class="panel-heading">

										<i class="fa fa-bar-chart-o fa-fw"></i> Main recommended Visualization with details about "others" values
									
									</div>

										<div id="visrec_detOther"></div>
				<script type="text/javascript">
				var file222 = ${file[0]};
				//var table333 = ${datajson}	;
				var table_detOther = ${recset[2]};	
				var colors2 = ${recset[5]};	
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#visrec_detOther"}).data('mapping').insert(colors2)
	//  chart({el:"#visrec_detOther"}).data('source').insert(table333)
	 chart({el:"#visrec_detOther"}).data('table').insert(table_detOther ) 
	chart({el:"#visrec_detOther"}).height(550).width(550).update();    });
	
}
parse(file222);
</script>
					</div>  </div> 
</div> 
 </div>


<!-- end row -->





		<div class="row"></div>

			<div class="row">
					
						<div class="col-lg-6">
							<div class="panel panel-default">

								<!-- /.panel-heading -->
								<div class="panel-body">

									<div class="panel-heading">
										<i class="fa fa-bar-chart-o fa-fw"></i> Main recommended Visualization for the full range of data
									
									</div>



												<div id="rec2withoutpair"></div>
															<script type="text/javascript">
																var file222 = ${file[0]};
																//var table333 = ${datajson}	;
																var tablewithout = ${recset[1]};	
																var colors3 = ${recset[6]};	
																
												// parse a spec and create a visualization view
												function parse(spec) {
												
												  vg.parse.spec(spec, function(error, chart) {
													  chart({el:"#rec2withoutpair"}).data('mapping').insert(colors3)
													//  chart({el:"#rec2withoutpair"}).data('source').insert(table333)
													 chart({el:"#rec2withoutpair"}).data('table').insert(tablewithout) 
													chart({el:"#rec2withoutpair"}).height(550).width(550).update();    });
													
												}
												parse(file222);
												</script>
					</div>  </div> 
</div>  



						<div class="col-lg-6">
							<div class="panel panel-default">

								<!-- /.panel-heading -->
								<div class="panel-body">

										<div class="panel-heading">
										<i class="fa fa-bar-chart-o fa-fw"></i> Main recommended Visualization for the full range of data with more details
									
									</div>


													<div id="rec2withoutpairFull"></div>
															<script type="text/javascript">
																var file222 = ${file[0]};
															//	var table333 = ${datajson}	;
																var tablewithoutFull = ${recset[3]};	
																var colors4 = ${recset[7]};	
																
												// parse a spec and create a visualization view
												function parse(spec) {
												
												  vg.parse.spec(spec, function(error, chart) {
													  chart({el:"#rec2withoutpairFull"}).data('mapping').insert(colors4)
													 // chart({el:"#rec2withoutpairFull"}).data('source').insert(table333)
													 chart({el:"#rec2withoutpairFull"}).data('table').insert(tablewithoutFull) 
													chart({el:"#rec2withoutpairFull"}).height(550).width(550).update();    });
													
												}
												parse(file222);
												</script>
					</div>  </div> 
</div> 
 </div>



	<div class="row"></div>

			<div class="row">
					
						<div class="col-lg-6">
							<div class="panel panel-default">

								<!-- /.panel-heading -->
								<div class="panel-body">

										<div class="panel-heading">
										<i class="fa fa-bar-chart-o fa-fw"></i> Main recommended Visualization 
									
									</div>
									
					<div id="bubble1"></div>

																<script type="text/javascript">
																var fileBubb = ${file[2]};
																var tableBubb = ${recset[0]};	
																
																
												// parse a spec and create a visualization view
												function parse(spec) {
												
												  vg.parse.spec(spec, function(error, chart) {
												
													 chart({el:"#bubble1"}).data('table').insert(tableBubb) 
													chart({el:"#bubble1"}).height(550).width(550).update();    });
													
												}
												parse(fileBubb);
												</script>				
									
														</div>  </div> 
						</div> 
						


						<div class="col-lg-6">
							<div class="panel panel-default">

								<!-- /.panel-heading -->
								<div class="panel-body">

										<div class="panel-heading">
										<i class="fa fa-bar-chart-o fa-fw"></i> Main recommended Visualization for the full range of data 
									
									</div>
									
																						<div id="bubble2"></div>
																		<script type="text/javascript">
																			var fileBubb2 = ${file[2]};
																			var tableBubb2 = ${recset[1]};	
																			
																			
															// parse a spec and create a visualization view
															function parse(spec) {
															
															  vg.parse.spec(spec, function(error, chart) {
															
																 chart({el:"#bubble2"}).data('table').insert(tableBubb2) 
																chart({el:"#bubble2"}).height(550).width(550).update();    });
																
															}
															parse(fileBubb2);
															</script>
									
									
														</div>  </div> 
							</div> 
							 




</div>

<!-- end this row -->






			</logic:notEmpty>


<!--   %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Extension_Slice  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% -->

			<logic:notEmpty name="rec2_Slice">
				<script type="text/javascript">
				var schema = ${file[0]};
				var table = ${recset[0]};	
					
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	
	 chart({el:"#visrec"}).data('table').insert(table) 
	
	// chart({el:"#visrec"}).height(550).width(900).update();
	 chart({el:"#visrec"}).update();     });
	
}
parse(schema);
</script>
			</logic:notEmpty>




<!--   %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  drill %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% -->


			<logic:notEmpty name="rec3">


				<script type="text/javascript">
				var sche = ${file[0]};		
				var scr1drill=  ${recset[0]};	
				//var scr1drillcolors=  ${recset[1]};	
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {

 // chart({el:"#visrec"}).data('mapping').insert(scr1drillcolors)
 chart({el:"#visrec"}).data('table').insert(scr1drill)
	//chart({el:"#visrec"}).height(550).width(900).update();   
	chart({el:"#visrec"}).update();  
  });
	
}
parse(sche);
</script>
			</logic:notEmpty>

<!--   %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  drill_slice %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% -->

			<logic:notEmpty name="rec3_Slice">


			<!--	 <script type="text/javascript">
					var schemadrillSlice = $
					{
						file[0]
					};
					var scrdrillSlice = $
					{
						recset[0]
					};

					// parse a spec and create a visualization view
					function parse(spec) {

						vg.parse.spec(spec, function(error, chart) {

							chart({
								el : "#visrec"
							}).data('table').insert(scrdrillSlice)

							chart({
								el : "#visrec"
							}).update();
						});

					}
					parse(schemadrillSlice);
				</script> -->





				<logic:iterate name="vispossible" id="vis" scope="session">


					<logic:equal name="vis" property="status" value="true">

						<bean:define id="visualfile" name="vis" property="jsonresult" />

						<div class="row">
							<div class="col-lg-12">
								<div class="panel panel-default">



	
								<div class="panel-heading">
										<i class="fa fa-bar-chart-o fa-fw"></i> Recommended
										Visualization
										<div class="pull-right">
											<div class="btn-group">
												<button type="button"
													class="btn btn-default btn-xs dropdown-toggle"
													data-toggle="dropdown">
													Actions <span class="caret"></span>
												</button>
												<ul class="dropdown-menu pull-right" role="menu">


													<logic:iterate name="vispossible" id="visa" scope="session">


														<logic:notEqual name="visa" property="status" value="true">
															<li><a
																href="ChangeRecVis.jsp?type=Drill_Slice&typeVis='<bean:write name="visa"  property="name" />'"><bean:write
																		name="visa" property="name" /> </a></li>
														</logic:notEqual>

													</logic:iterate>


												</ul>
											</div>
										</div>
									</div>





									<!-- /.panel-heading -->
									<div class="panel-body">




										<div id="visDrillSlice"></div>
										<script type="text/javascript">

				var tableZoomSlice = ${recset[0]}		
				var file = ${visualfile};	
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#visDrillSlice"}).data('table').insert(tableZoomSlice)
	  chart({el:"#visDrillSlice"}).update();    });
	
}
parse(file);
</script>

										<!-- /.panel-body -->

										<!-- /.panel -->


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


						<logic:notEqual name="vis2" property="status" value="true">

							<bean:define id="visualfile2" name="vis2" property="jsonresult" />


							<div class="col-lg-6">
								<div class="panel panel-default">

									<!-- /.panel-heading -->
									<div class="panel-body">

										<div class="panel-heading">


											<div id="vis<bean:write name="vis2"  property="name" />"></div>
											<script type="text/javascript">

				var tableZoomSlice = ${recset[0]};	
				var file2 = ${visualfile2};	
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#vis<bean:write name="vis2"  property="name" />"}).data('source').insert(tableZoomSlice)
	  chart({el:"#vis<bean:write name="vis2"  property="name" />"}).data('table').insert(tableZoomSlice)
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





			</logic:notEmpty>



			<br></br> <br></br>



		</div>

	</div>
	<!-- /.panel-body -->

	<!-- /.row -->





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






