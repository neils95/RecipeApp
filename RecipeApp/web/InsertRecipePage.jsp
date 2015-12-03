<%-- 
    Document   : InsertRecipePage
    Created on : Nov 29, 2015, 9:08:04 PM
    Author     : Neil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Insert Recipe</title>
        
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        
        <link type="text/css" rel="stylesheet" href="InsertRecipePage.css"/>

    </head>
    
    
    <body>
        
        <div class="container-fluid" >
            
            <div class="jumbotron">
                <h1 id ="heading" >Join a global cookbook</h1>
                <br><h2 id ="caption" ><small>Add your own recipe</small></h2>
            </div>
            
            <div  id ="insertRecipeBox">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3><center>Upload Recipe Text File</center></h3>
                    </div>
                    <div class="panel-body">
                        <center>
                            <!-- form to upload a text file. The file gets sent to the server, and copied on the server.
                            It is read from there and inserted into IngredientTable-->
                            <form name="submitRecipeForm" action="FormSubmission.jsp" enctype="multipart/form-data" method="POST">
                                <input type="file" name="recipeFileUpload" value="" style="width:80%" />
                                <br>
                                <input class="btn btn-sm btn-primary" type="submit" value="Upload" name="uploadButton"/>
                            </form>
                        </center>
                    </div>
                </div>
            </div>
            
        </div>
    </body>
</html>
