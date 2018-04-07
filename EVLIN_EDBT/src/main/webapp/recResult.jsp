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

	

		<!-- Navigation -->
	

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
                                        <li><a href="#">Another action</a>
                                        </li>
                                    
                                    </ul>
                                </div>
                            </div>
                        </div>
			</div>
	  				
				<script type="text/javascript">

				var tab = ${datajs}	;
				var file0 = ${file[3]};		
					
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#vis123"}).data('table').insert(tab)
	 
	  chart({el:"#vis123"}).height(450).width(750).update(); 
	  });
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
		

	<div class="row">
	 
  <div class="col-sm-12"><div id="visrec1"></div></div>
   <div class="col-sm-12"><div id="visZoomSlice"></div></div>
  <div class="col-sm-12">	<div id="visrec2"></div></div>
   <div class="col-sm-12">	<div id="visrec2Slice"></div></div>
    <div class="col-sm-12"><div id="visrec3Drillonly"></div></div>
    <div class="col-sm-12"><div id="visrec3DrillSlice"></div></div>
    <!--  <div class="col-sm-12"><div id="vis123"></div></div> -->
</div>


	





				<script type="text/javascript">
				var file222 = ${file[0]};
				var table333 = ${datajs}	;
				var table222 = ${recset[0]};	
				var table555 = ${recset[1]};		
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#visrec1"}).data('source').insert(table333)
	 chart({el:"#visrec1"}).data('table').insert(table222) 
	chart({el:"#visrec1"}).data('table').insert(table555)
	 chart({el:"#visrec1"}).height(550).width(900).update();    });
	
}
parse(file222);
</script>


		
  
  
  
 				<script type="text/javascript">
				var schemaZoomSlice = ${file[5]};
				var tableZoomSlice = ${recset[2]};	
				var tableZoomSliceOther = ${recset[3]};	
				
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	
		chart({el:"#visZoomSlice"}).data('table').insert(tableZoomSliceOther)	 
	 chart({el:"#visZoomSlice"}).data('table').insert(tableZoomSlice) 
	 chart({el:"#visZoomSlice"}).height(550).width(400).update();    });
	
}
parse(schemaZoomSlice);
</script> 
  
  
  			
  				
  				
  				
  <logic:empty name="chiffre2"> 				
				<script type="text/javascript">
				var schemarec2 = ${file[2]};
				var table33 = ${datajs}	;
				var table22 = ${recsetExtension[0]};	
					
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#visrec2"}).data('source').insert(table33)
	  chart({el:"#visrec2"}).data('table').insert(tableTop10)
	chart({el:"#visrec2"}).height(550).width(900).update();    });
	
}
parse(schemarec2);
</script>
</logic:empty> 


  <logic:notEmpty name="chiffre2"> 				
<script type="text/javascript">
				var schemarec2 = ${file[2]};
				var table33 = ${datajs}	;
				var tableTop10 = ${recsetExtension[0]};	
				var tableOther = ${recsetExtension[1]};		
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#visrec2"}).data('source').insert(table33)
	  chart({el:"#visrec2"}).data('table').insert(tableTop10)
	chart({el:"#visrec2"}).data('table').insert(tableOther)
	  
	  chart({el:"#visrec2"}).height(550).width(900).update();    });
	
}
parse(schemarec2);
</script>
</logic:notEmpty> 




<script type="text/javascript">
				var schema = ${file[1]};
				var table = ${recsetExtensionSlice[0]};	
						
				
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	
	 chart({el:"#visrec2Slice"}).data('table').insert(table) 
	
	 chart({el:"#visrec2Slice"}).height(550).width(900).update();    });
	
}
parse(schema);
</script> 










<logic:empty name="chiffre1"> 

				
				<script type="text/javascript">
				var sche = ${file[3]};		
				var scr1drill=  ${recset[4]};	
			
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {

	  chart({el:"#visrec3Drillonly"}).data('source').insert(scr1drill)
 chart({el:"#visrec3Drillonly"}).data('table').insert(scr1drill)
	chart({el:"#visrec3Drillonly"}).height(550).width(900).update();   
  });
	
}
parse(sche);
</script>
</logic:empty>

<logic:notEmpty name="chiffre1">

			<script type="text/javascript">
			var schemadrill = ${file[3]};	
			var scrdrill=  ${recset[4]};	
			var srcdrillComplemnt = ${recset[5]};	
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#visrec3Drillonly"}).data('source').insert(srcdrillComplemnt)
	//	chart({el:"#visrec3Drillonly"}).data('table').insert(srcdrill)
	  	  chart({el:"#visrec3Drillonly"}).data('table').insert(srcdrillComplemnt)
	
	  
	chart({el:"#visrec3Drillonly"}).height(550).width(900).update();   
		   });
	
}
parse(schemadrill);
</script>
</logic:notEmpty>


  	
  	
  			<script type="text/javascript">
			var schemadrillSlice = ${file[4]};	
			var scrdrillSlice=  ${recset[6]};	
			
// parse a spec and create a visualization view
function parse(spec) {

  vg.parse.spec(spec, function(error, chart) {
	  chart({el:"#visrec3DrillSlice"}).data('source').insert(scrdrillSlice)
	//	chart({el:"#visrec3Drillonly"}).data('table').insert(srcdrill)
	  	  chart({el:"#visrec3DrillSlice"}).data('table').insert(scrdrillSlice)
	
	  
	chart({el:"#visrec3DrillSlice"}).height(550).width(900).update();   
		   });
	
}
parse(schemadrillSlice);
</script>	
  	
  	
  				


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






