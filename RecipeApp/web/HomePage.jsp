<%-- 
    Document   : HomePage
    Created on : Nov 27, 2015, 9:16:27 PM
    Author     : Neil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <!-- Bootstrap links hosted by maxcdn -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        
        <!--link to home page CSS-->
        <link rel="stylesheet" href="HomePage.css">
        
        <title>RecipeApp</title>
    </head>
    <body>
        <div class="container-fluid">
            <!--Heading of page-->
            <div class="jumbotron" align="center" >
                <h1 id ="Heading" >Recipe Application</h1>
                <br><h2 id ="Caption" ><small>Search. Cook. Eat.</small></h2>
            </div>
           
            <!--Search bar, buttons for search and insert-->
            <div class="row" id="searchBar">
                <div class="col-sm-4"></div>
                <!--Search bar-->
                
                <form name="Search" action="RecipePage.jsp" method="POST">
                    <div class ="col-sm-4">
                        <input id="searchBar" type="text" name="searchBar" >
                    </div>
                    <!--Search button-->
                    <div class="col-sm-1" >
                        <input id="searchButton" class="btn btn-sm btn-info" type="submit" value="Search" name="Search" />
                    </div>
                </form>
                
                <div class="col-sm-1">
                    <a href="InsertRecipePage.jsp">
                        <input id ="addRecipeButton" class="btn btn-sm btn-info" type="button" value="Add recipe" name="addRecipe" />
                    </a>
                </div>
            </div>

        </div>
        
    </body>
</html>
