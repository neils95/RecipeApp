<%-- 
    Document   : recipePage
    Created on : Nov 28, 2015, 3:10:29 AM
    Author     : Neil
--%>

<%@page import="java.io.File"%>
<%@page import="java.io.FileReader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Backend.*" %>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Recipes</title>
        
        <meta name="viewport" content=""width="device-width, intial-scale=1">
        
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        
        <link type="text/css" rel="stylesheet" href="RecipePage.css"/>
    </head>
    
    <%
        IngredientsTable t = new IngredientsTable("MasterRecipeList.txt");
        //IngredientsTable t = new IngredientsTable();

        ArrayList<RecipeNode> r = new ArrayList<RecipeNode>();

        String str = request.getParameter("searchBar");

        //System.out.println("With initial set:");
        r = t.getRecipes(str);
        //System.out.println("\nRecipes with "+str+":");


    %>
        
    <body>
        
        <div class="container-fluid">
            
            <!-- Heading of the page -->
            <div class="jumbotron" >
                <h1><%= str%></h1>
                <p>Showing you the best recipes that contain "<%= str%>"</p>
            </div>
            
            <!--Panel group for displaying recipes-->
            
            <div class="panel-group" id="accordion" style="margin-left:5%;margin-right:5%">
                
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
                            <pre> <%=rn.getContents()%></pre>
                    </div>
                    </div>
                </div>
                <%
                    
                    }
                %>
            </div><!--end of panel group-->
        
            <a href="HomePage.jsp">
                <div class="well well-sm" align="center">
                        Search for other ingredients.
                </div>
            </a>
        </div><!--end of container-fluid-->
    
    </body>
</html>
