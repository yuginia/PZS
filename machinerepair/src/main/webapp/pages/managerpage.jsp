<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Orders Management</title>

<style>
h3.left {
    position: absolute;
    left: 10px;
    top: 0px;
}

h3.right {
    position: absolute;
    right: 10px;
    top: 0px;
}

tr.odd {background-color: #EEDDEE}

tr.even {background-color: #EEEEDD}

.pg-normal {
color: #000000;
font-size: 15px;
cursor: pointer;
background: #D0B389;
padding: 2px 4px 2px 4px;
}

.pg-selected {
color: #fff;
font-size: 15px;
background: #000000;
padding: 2px 4px 2px 4px;
}

table.yui {
font-family:arial;
border-collapse:collapse;
border: solid 3px #7f7f7f;
font-size:small;
}

table.yui td {
padding: 5px;
border-right: solid 1px #7f7f7f;
}

table.yui.even {
background-color: #EEE8AC;
}

table.yui.odd {
background-color: #F9FAD0;
}

table.yui th {
border: 1px solid #7f7f7f;
padding: 5px;
height: auto;
background: #D0B389;
}

table.yui th a {
text-decoration: none;
text-align: center;
padding-right: 20px;
font-weight:bold;
white-space:nowrap;
}

table.yui tfoot td {
border-top: 1px solid #7f7f7f;
background-color:#E1ECF9;
}

table.yui thead td {
vertical-align:middle;
background-color:#E1ECF9;
border:none;
}

table.yui thead.tableHeader {
font-size:larger;
font-weight:bold;
}

table.yui thead.filter {
text-align:right;
}

table.yui tfoot {
background-color:#E1ECF9;
text-align:center;
}

table.yui.tablesorterPager {
padding: 10px 0 10px 0;
}

table.yui.tablesorterPager span {
padding: 0 5px 0 5px;
}

table.yui.tablesorterPager input.prev {
width: auto;
margin-right: 10px;
}

table.yui.tablesorterPager input.next {
width: auto;
margin-left: 10px;
}

table.yui.pagedisplay {
font-size:10pt; 
width: 30px;
border: 0px;
background-color: #E1ECF9;
text-align:center;
vertical-align:top;
}

#sidebar {
  position: fixed;
  top: 40px;
  left: 40px;
  width: 200px;
  border-right:2px solid #aaa;
}
 
#content {
  margin: 0 40px 40px 280px;
}
</style>

<script type="text/javascript">

function Pager(tableName, itemsPerPage) {
this.tableName = tableName;
this.itemsPerPage = itemsPerPage;
this.currentPage = 1;
this.pages = 0;
this.inited = false;

this.showRecords = function(from, to) {
var rows = document.getElementById(tableName).rows;
// i starts from 1 to skip table header row
for (var i = 1; i < rows.length; i++) {
if (i < from || i > to)
rows[i].style.display = 'none';
else
rows[i].style.display = '';
}
}

this.showPage = function(pageNumber) {
if (! this.inited) {
alert("not inited");
return;
}

var oldPageAnchor = document.getElementById('pg'+tableName+this.currentPage);
oldPageAnchor.className = 'pg-normal';
this.currentPage = pageNumber;
var newPageAnchor = document.getElementById('pg'+tableName+this.currentPage);
newPageAnchor.className = 'pg-selected';
var from = (pageNumber - 1) * itemsPerPage + 1;
var to = from + itemsPerPage - 1;
this.showRecords(from, to);
}

this.prev = function() {
if (this.currentPage > 1)
this.showPage(this.currentPage - 1);
}

this.next = function() {
if (this.currentPage < this.pages) {
this.showPage(this.currentPage + 1);
}
}

this.init = function() {
var rows = document.getElementById(tableName).rows;
var records = (rows.length - 1);
this.pages = Math.ceil(records / itemsPerPage);
this.inited = true;
}

this.showPageNav = function(pagerName, positionId) {
if (! this.inited) {
alert("not inited");
return;
}

var element = document.getElementById(positionId);
var pagerHtml = '<span onclick="' + pagerName + '.prev();" class="pg-normal"> � Prev </span> ';
for (var page = 1; page <= this.pages; page++)
pagerHtml += '<span id="pg' + tableName + page + '" class="pg-normal" onclick="' + pagerName + '.showPage(' + page + ');">' + page + '</span> ';

pagerHtml += '<span onclick="' + pagerName + '.next();" class="pg-normal"> Next �</span>';
element.innerHTML = pagerHtml;
}
}
</script>
</head>

<body>
	
	<h1 align = "center">ORDERS MANAGEMENT</h1>
	
	<div id="sidebar">
		<p><a href="<c:url value="/index"/>">Home</a></p>
		<c:if test="${fn:contains(user_token_authorities, 'ROLE_ADMIN')}">			
			<p><a href="<c:url value="/adminpage"/>">Switch to administrative tools</a></p>						
		</c:if>
		<hr>
		<p><a href="<c:url value="/logout"/>">Log out</a></p>
	</div>	
	
	<div id="content">
	<h2>Pending Orders:</h2>	
	</div>
  	
</body>
</html>