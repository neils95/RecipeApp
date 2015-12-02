/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 /**
 * Servlet implementation class ActionServlet
 */

 public class ActionServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;

 
 public ActionServlet() {
        // TODO Auto-generated constructor stub
        
    }
 
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  String Suggestions=null;
  ArrayList <IngredientNode> IngredientSuggestionList = new ArrayList();
  ArrayList<String> IngredientSuggestions =new ArrayList();
  IngredientsTable t = new IngredientsTable("MasterRecipeList.txt");
 
  String prefix =request.getParameter("user");
  
  IngredientSuggestionList = t.getTopIngredients(t.setOfIngredientNames.suggest(prefix));
  
  
  
  if(request.getParameter("user").toString().equals("")){
   Suggestions="Ingredient suggestions will appear here";
  }
  response.setContentType("Array");  
  response.setCharacterEncoding("UTF-8"); 
  for(IngredientNode ingredient:IngredientSuggestionList){
      response.getWriter().write( ingredient.getName()+"{"+ingredient.getRank()+"}, ");
  }
  
 }

  
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  
 }

 }
