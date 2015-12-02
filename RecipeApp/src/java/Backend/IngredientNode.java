/* Joshua Klein
 * 11/8/15
 * ENG EC504
 * Professor Moreshet
 * 
 * Project Back-End - IngredientNode
 * 
 * This class provides the ingredient node for the back-end.
 * The ingredient node contains a rank based on the sum of its
 * recipe's rank, as well as an array list of recipes that have
 * the ingredient in it.
 * 
 */
package Backend;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class IngredientNode {
		
		private ArrayList<RecipeNode> recipesList;
		public int rank;
		private String name;

		//Initializes node with AL for Recipe names
		public IngredientNode(String name) {
			this.name = name;
			this.rank = 0;
			this.recipesList = new ArrayList<RecipeNode>();
		}

		//Returns rank of ingredient
		public int getRank()
		{return rank;}

		//Returns name of ingredient
		public String getName()
		{return name;}

		//Returns AL with recipes
		public ArrayList<RecipeNode> getRecipes()
		{return recipesList;}

		//If list's size is 8, insert will automatically remove least ranked
		//recipe, and then it will add to list.  Adjusts rank as needed.  Also
		//will not add recipe if its rank is lower than all the recipes currently
		//in a full recipes list and will remove null (previously deleted) recipes.
		public void insertRecipe(RecipeNode r) {
			//Remvoes null recipes
			boolean dupe = false;
			for (RecipeNode rn : recipesList) {
				if (rn == null)
					recipesList.remove(rn);
				if (rn.getName() == r.getName()) {
					dupe = true;
					break;
				}
			}

			//Checks size of recipes list and inserts accordingly
			if (!dupe) {
				if (recipesList.size() == 0) recipesList.add(r);
				else  {
					recipesList.add(r);
					Collections.sort(recipesList, new RecipeComparator());
					rank += r.getRank();
					if (recipesList.size() > 8) recipesList.remove(8);
				}
			}	
		}

		public static class RecipeComparator implements Comparator<RecipeNode> {
			@Override
			public int compare(RecipeNode a, RecipeNode b) {
				if (a.getRank() < b.getRank()) return 1;
				return -1;
			}
		}
	}