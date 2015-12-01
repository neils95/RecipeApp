<%-- 
    Document   : FormSubmission
    Created on : Nov 29, 2015, 9:52:05 PM
    Author     : Neil
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Backend.RecipeNode"%>
<%@page import="Backend.IngredientsTable"%>
<%@page import="java.io.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Bootstrap links hosted by maxcdn -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        
        <title>Form Submission</title>
    </head>
    <%
        String saveFile = new String();
        String contentType = request.getContentType();
        if((contentType!=null) && (contentType.indexOf("multipart/form-data") >=0)){
            DataInputStream in= new DataInputStream(request.getInputStream());
            int formDataLength =request.getContentLength();
            byte dataBytes[]= new byte[formDataLength];
            int byteRead =0;
            int totalBytesRead =0;
            
            while(totalBytesRead <formDataLength){
                byteRead =in.read(dataBytes,totalBytesRead,formDataLength);
                totalBytesRead+= byteRead;
            }
            
            String file = new String( dataBytes );
            saveFile = file.substring(file.indexOf("filename=\"")+10);
            saveFile =saveFile.substring(0,saveFile.indexOf("\n"));
            saveFile = saveFile.substring(saveFile.lastIndexOf("\\")+1,saveFile.indexOf("\""));

            int lastIndex =contentType.lastIndexOf("=");
            String boundary = contentType.substring(lastIndex+1,contentType.length());

            int pos;

            pos =file.indexOf("filename=\"");
            pos=file.indexOf("\n",pos+1);
            pos=file.indexOf("\n",pos+1);
            pos=file.indexOf("\n",pos+1);

            int boundaryLocation =file.indexOf(boundary,pos)-4;
            int startPos = ((file.substring(0,pos)).getBytes()).length;
            int endPos =((file.substring(0,boundaryLocation)).getBytes()).length;
            
            saveFile ="C:/Users/Neil/GlassFish_Server/glassfish/domains/domain1/config/" +saveFile;
            File ff = new File(saveFile);

            try{
                FileOutputStream fileOut =new FileOutputStream(ff);
                fileOut.write(dataBytes ,startPos,(endPos-startPos));
                fileOut.flush();
                fileOut.close();

            }
            catch (Exception e){
                out.println(e);
            }
            
        }
        IngredientsTable t = new IngredientsTable("MasterRecipeList.txt");
        t.insertRecipesFromFile(saveFile);
        
    %>
    <body style="background-image: url(recipePageBackground.jpg)">
        <div class="well well-sm" style="margin:15% 20% 0% 20%;border:dashed blueviolet"><center>
            <h1>Thank you for contributing to our application!</h1>
            <p>We would not be able to survive without your contribution. Our goal is to provide our customers with the latest recipes and your contribution helps us get closer to that goal. We hope you enjoyed using the application and please direct any feedback to recipe@bu.edu. </p>
            <a href="HomePage.jsp">
                <input class ="btn btn-primary" type="button" value="Keep Searching" name="returnHome" />
            </a>
            <a href="InsertRecipePage.jsp">
                <input class ="btn btn-primary" type="button" value="Add more recipes" name="returnHome" />
            </a>
        </center></div>
    </body>
</html>
