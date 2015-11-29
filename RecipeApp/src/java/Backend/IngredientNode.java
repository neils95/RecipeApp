package Backend;

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

import java.util.ArrayList;

public class IngredientNode{
		
		private ArrayList<RecipeNode> recipesList;
		private int rank;
		private String name;

		//Initializes node with PriorityQueue for Recipe names
		public IngredientNode(String name) {
			this.name = name;
			this.rank = 0;
			this.recipesList = new ArrayList<RecipeNode>();
		}

		//Increase and decrease rank (by 1) functions
		public void increaseRank()
		{rank++;}

		public void decreaseRank()
		{rank--;}

		//Returns rank of ingredient
		public int getRank()
		{return rank;}

		//Returns name of ingredient
		public String getName()
		{return name;}

		//Returns AL with recipes
		public ArrayList<RecipeNode> getRecipes()
		{return recipesList;}

		//Returns index of recipe with smallest rank
		public int leastImportantRecipeIndex() {
			int index = 0;
			for (int i = 1; i < recipesList.size(); i++) {
				if (recipesList.get(i) == null)
					continue;
				else if (recipesList.get(i).getRank() < recipesList.get(index).getRank())
					index = i;
			}
			return index;
		}

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
				if (recipesList.size() < 8) {
					recipesList.add(r);
					rank += r.getRank();
				} else {
					int s = leastImportantRecipeIndex();
					if (recipesList.get(s).getRank() <= r.getRank()) {
						rank += r.getRank() - recipesList.get(s).getRank();
						recipesList.remove(s);
						recipesList.add(r);
					}
				}
			}
		}
	}