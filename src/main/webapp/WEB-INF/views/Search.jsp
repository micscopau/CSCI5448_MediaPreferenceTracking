<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search Movies</title>
</head>
<body>
<h3>Hi ${fName}</h3>
	<a href="login"> Recommendation</a>
	<a href="Search"> Search</a>
	<a href="MyLists-Ratings"> MyLists</a>
	<a href="logout">Log out</a>
	<a href="editprofile">Edit Profile</a>


		
<h3> Search movies in database</h3>

			<form:form action="Search" method="post" modelAttribute="userForm">
				<form:input path="searchTitle" />
				<input type="submit" value="Search">
			</form:form>

	<h4> List of Movie Results </h4>
				
				

   <form:form action="previousSearch" method="GET" align="center">
   	<input type="submit" name="previous" value="Previous" />
   	</form:form>
   	
   	<form:form action="nextSearch" method="GET" align="center">
 		<input type="submit" name="next" value="Next" />
 	</form:form>
 	
 <p></p>
			
<form:form action="dbupdate" method="POST" modelAttribute="movieListWrapper">
<table border="1" width="100%">
 <tr>
    <th>Movie Title</th>
    <th>Ratings</th>		
    <th>Ignore</th>
    <th>Wish</th>
 </tr>
<c:forEach items="${movieListWrapper.allmovies}"    var="movie"   varStatus="status" >  
<tr style="background-color:white;color: black;text-align: center;" height="30px" >  
<td align="left"> ${movie.movieTitle}"</td>  
<td><form:input  type="number"  path="allmovies[${status.index}].ratings"  min="0"  max="5"  value="${movie.ratings}" style="width: 35px;"/></td>  
<td><form:input  path="allmovies[${status.index}].ignore"    value="${movie.ignore}" style="width: 50px;"/></td>  
<td><form:input  path="allmovies[${status.index}].wish"      value="${movie.wish}" style="width: 50px;"/><br></td>
</tr>  
</c:forEach>

</table>
<input type="submit" name="action" value="Save" />
</form:form>

<p></p>

   <form:form action="previousSearch" method="GET" align="center">
   	<input type="submit" name="previous" value="Previous" />
   	</form:form>
   	
   	<form:form action="nextSearch" method="GET" align="center">
 		<input type="submit" name="next" value="Next" />
 	</form:form>

</body>
</html>