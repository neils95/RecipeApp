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
 ArrayList <String> list = new ArrayList();

 
 public ActionServlet() {
        // TODO Auto-generated constructor stub
        
    }
 
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  String name=null;
  
  
  list.add(request.getParameter("user"));
  name = "Hello "+request.getParameter("user");
  if(request.getParameter("user").toString().equals("")){
   name="Hello User";
  }
  response.setContentType("Array");  
  response.setCharacterEncoding("UTF-8"); 
  response.getWriter().write(list.toString()); 

  
 }

  
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  
 }

 }
