package Backend;

/* Joshua Klein
 * 11/8/15
 * ENG EC504
 * Professor Moreshet
 * 
 * Project Back-End - Recipe Node
 * 
 * This class provides the recipe node for the back end, which
 * includes a rank and references to ingredients.  The recipe's
 * rank is given arbitrarily when the recipe is created.
 * 
 */

import java.util.Comparator;

public class RecipeNode{

		private int rank;
		private String recipeName, contentsString;

		//Constructors
		public RecipeNode(String recipeName, int rank, String contentsString) {
			this.recipeName = recipeName;
			this.contentsString = contentsString;
			this.rank = rank;
		}

		//Returns contentsString (with recipe's name, directions, etc)
		public String getContents()
		{return contentsString;}

		//Returns rank of recipe
		public int getRank() 
		{return rank;}

		//Returns recipe's name
		public String getName()
		{return recipeName;}

		//For Comparator use with standard java library (necessary 
		//for using java's built-in priority queue)
		public static Comparator<RecipeNode> c = new Comparator<RecipeNode>(){
			@Override
			public int compare(RecipeNode a, RecipeNode b) {
				if (a.getRank() < b.getRank()) return -1;
				return 1;
			}
		};
	}