package Backend;

/* Joshua Klein
 * 11/8/15
 * ENG EC504
 * Professor Moreshet
 * 
 * Project Back-End
 * 
 * This class provides the main function to test and run the back
 * end for the recipes web app.
 * 
 * 
 * 
 */

import java.util.*;

public class Driver {

	public static void main(String []args) {
		IngredientsTable t = new IngredientsTable("MasterRecipeList.txt");
		//IngredientsTable t = new IngredientsTable();

		ArrayList<RecipeNode> r = new ArrayList<RecipeNode>();
                
		String str = "cheese";

		System.out.println("With initial set:");
		r = t.getRecipes(str);
		System.out.println("\nRecipes with "+str+":");
                String Recipes =new String();
		for (RecipeNode rn : r){
                    Recipes+= rn.getName()+"/n"+rn.getContents()+"/n/n";
                }
                System.out.println(Recipes);
	}
}