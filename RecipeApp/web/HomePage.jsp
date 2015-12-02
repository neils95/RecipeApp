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

        <script>

            $(document).ready(function() {                        

                $('#user').keyup(function(event) {  

                    var username=$('#user').val();

                    $.get('ActionServlet',{user:username},function(responseText) { 

                        $('#welcometext').text(responseText);         

                    });

                });

            });

        </script>
        
    </head>
    
    <%
        IngredientsTable t = new IngredientsTable("MasterRecipeList.txt");
        //IngredientsTable t = new IngredientsTable();

        ArrayList<RecipeNode> r = new ArrayList<RecipeNode>();

        r=t.getTopRecipes();
    %>
    
    <body>
        <div class="container-fluid">
            <!--Heading of page-->
            <div class="jumbotron" align="center" >
                <h1 id ="Heading" >Recipe Application</h1>
                <br><h2 id ="Caption" ><small>Search. Cook. Eat.</small></h2>
            </div>
           
            <!--Search bar, buttons for search and insert-->
            
            <div class="row" id="searchBar">
                <div class="col-sm-4">
                    
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
            <div>
                <form id="form1">
                
                <input type="text" id="user"/>
                
                <input type="button" id="submit" value="Ajax Submit"/>

                <br/>

                <div id="welcometext"></div>
                    recipe changes will show here
                </form>
            </div>
                        
            
            
            
        </div>
        
    </body>
</html>
