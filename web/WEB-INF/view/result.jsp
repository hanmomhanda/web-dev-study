<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.fasterxml.jackson.databind.JsonNode"%>
<%
JsonNode rootNode = (JsonNode)request.getAttribute("resultJsonNode");
System.out.println("rootNode : " + rootNode);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
    <title>Basic Book Search</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/web-dev-study/public/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="/web-dev-study/public/css/sticky-footer.css" rel="stylesheet">
</head>
<body>
<!-- Wrap all page content here -->
    <div id="wrap">

      <!-- Begin page content -->
      <div class="container">
        <div class="page-header">
          <h1>Basic Web - 도서 검색</h1>
        </div>
        <div class='row'>
        <form id='frmSearch' action='/web-dev-study/SearchServlet' method='POST'>
            <div class='col-xs-8'><input id='txtKeyword' name='keyword' type="text" class="form-control" placeholder="검색어를 입력하세요" value=${keyword}></div>
            <div class='col-xs-4'><a id='btnSearch' class="btn btn-default" href="#" role="button">Search</a></div>            
        </form>
        </div>        
      </div>
      <p>
      <div class="bs-example">
	      <div class="row">
	      <%
	        for ( JsonNode node : rootNode.path("channel").path("item") ) {
	      %>
	        <div class="col-sm-6 col-md-4">
	          <div class="thumbnail">
	            <img src=<%= node.path("cover_l_url").toString() %> data-src=<%= node.path("cover_l_url").toString() %> alt="Thumbnail">
	            <div class="caption">
	              <h3><%= node.path("title").toString().replace("\"", "").replace("&lt;", "<").replace("&gt;", ">") %></h3>
	              <p><%= node.path("description").toString().replace("\"", "") %></p>
	              <p><a href=<%= node.path("link").toString() %> class="btn btn-primary" role="button" target="_blank">상세보기</a></p>
	            </div>
	          </div>
	        </div>
	      <%
	        }
	      %>
	      </div>
	    </div><!-- /.bs-example -->
    </div><!-- /wrap -->
    
    <div id="footer">
      <div class="container">
        <p class="text-muted credit">Powered by <a href='http://getbootstrap.com/' target='_blank'>BootStrap</a></p>
      </div>
    </div>
<script src="/web-dev-study/public/js/jquery.js"></script>
<script src="/web-dev-study/public/js/bootstrap.min.js"></script>
<script>
jQuery(document).ready( function () {
    
    var goSearch = function () {
    	$('#frmSearch').submit();
    };
    
    $("#btnSearch").click( goSearch );
});
</script>
</body>
</html>