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
package Backend;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

public class Driver {

	public static void main(String []args) {

		//Tests ingredients table
		IngredientsTable t = new IngredientsTable("MasterRecipeList.txt");
               
		ArrayList<RecipeNode> r = new ArrayList<RecipeNode>();

		System.out.println("Enter ingredient:");
		Scanner in = new Scanner(System.in);
		String str = in.nextLine();

		System.out.println("\n"+str+" has a rank of "+t.setOfIngredients.get(str).getRank()+".");
		r = t.getRecipes(str);
		System.out.println("\nRecipes with "+str+":");
		for (RecipeNode rn : r) {
			System.out.print(rn.getName());
			System.out.println(" "+rn.getRank());
		}


		t.insertRecipesFromFile("AltRecipeList.txt");


		System.out.println("\n"+str+" has a rank of "+t.setOfIngredients.get(str).getRank()+".");
		r = t.getRecipes(str);
		System.out.println("\nRecipes with "+str+":");
		for (RecipeNode rn : r) {
			System.out.print(rn.getName());
			System.out.println(" "+rn.getRank());
		}
               
        }
}