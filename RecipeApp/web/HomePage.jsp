<%-- 
    Document   : HomePage
    Created on : Nov 27, 2015, 9:16:27 PM
    Author     : Neil
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="Backend.*" %>
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
        <script src="http://code.jquery.com/jquery-latest.js">   
        </script>
        
        <!-- JS code to implement autocomplete. Every time the user enters a character, the word in the search bar
        is sent to the server which queries the trie and returns the top 5 ingredients with the particular prefix.
        AJAX is used for this, only a part of the page is refreshed and not the entire page-->
        <script>

            $(document).ready(function() {                        

                $('#user').keyup(function(event) {  

                    var username=$('#user').val();

                    $.get('ActionServlet',{user:username},function(responseText) { 

                        $('#IngredientSuggestions').text(responseText);         

                    });

                });

            });

        </script>
        
    </head>
    
    <%
        //Create ingredient table from Master Recipe text file
        IngredientsTable t = new IngredientsTable("MasterRecipeList.txt");
        ArrayList<RecipeNode> r = new ArrayList<RecipeNode>();
        r=t.getTopRecipes();// R contains the top 8 recipes in the Master Recipe text file. This is displayed on left side of the page
    %>
    
    <body>
        <div class="container-fluid">
            
            <!--Heading of page-->
            <div class="jumbotron" align="center" >
                <h1 id ="Heading" >Recipe Application</h1>
                <br><h2 id ="Caption" ><small>Search. Cook. Eat.</small></h2>
            </div>
           
            <!--Top 8 recipes,Search bar, buttons for search and insert-->
            
            <div class="row" id="searchBar">
                <div class="col-sm-4">
                    
                    <!-- Display the top 8 recipes on left side of page-->
                    <div class="panel-group" id="accordion" >
                
                    <%
                        String collapse="collapse";
                        int i= 0;
                        for (RecipeNode rn : r){
                            collapse="collapse"+i;
                            i++;


                        //System.out.println(Recipes);
                    %>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#<%=collapse%>"><%=i%>. <%= rn.getName()%></a>
                                <p style="float:right"><%= rn.getRank() %></p>
                          </h4>
                        </div>
                        <div id="<%=collapse%>" class="panel-collapse collapse">
                          <div class="panel-body">
                                <pre style="font-size: 0.5em"> <%=rn.getContents()%></pre>
                        </div>
                        </div>
                    </div>
                    <%

                        }
                    %>
                    </div><!--end of panel group-->
                    
                </div>
                
                <!--Search bar-->
                <form name="Search" action="RecipePage.jsp" method="POST">
                    <div class ="col-sm-4">
                        <input id="user" type="text"  name="user"/>
                        <strong><h4 class="alert alert-success"><u>Ingredient Suggestions:</u> <div id="IngredientSuggestions"></div></h4></strong>
                    </div>
                    <!--Search button-->
                    <div class="col-sm-1" >
                        <input id="searchButton" class="btn btn-lg btn-info" type="submit" value="Search" name="Search" />
                    </div>
                </form>
                
                <div class="col-sm-1">
                    <a href="InsertRecipePage.jsp">
                        <input id ="addRecipeButton" class="btn btn-lg btn-info" type="button" value="Add recipe" name="addRecipe" />
                    </a>
                </div>
            </div>
            <div>
                
                
                
               
            </div>
                        
            
            
            
        </div>
        
    </body>
</html>
