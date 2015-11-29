package Backend;

/* Joshua Klein
 * 11/8/15
 * ENG EC504
 * Professor Moreshet
 * 
 * Project Back-End - IngredientsTable
 * 
 * This class provides the functionality for the ingredients table.
 * It handles inserts, deletes, and rank alterations. It also 
 * contains the priority queue-backed minimum heap which contains
 * all of the recipe nodes.
 * 
 */

import java.io.*;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
//Potential built-in maps/tables to use
// import java.util.Dictionary;
// import java.util.HashMap;
// import java.util.HashSet;
import java.util.Hashtable;
// import java.util.IdentityHashMap;
// import java.util.LinkedHashMap;
// import java.util.LinkedHashSet;
// import java.util.TreeMap;
// import java.util.WeakHashMap;

public class IngredientsTable {

	public Hashtable<String, IngredientNode> setOfIngredients;
	public PriorityQueue<RecipeNode> setOfRecipes; 
	public Trie setOfIngredientNames;

	//Constructor
	public IngredientsTable() {
		//Sets initial capacity and load factor for hash table, and initializes min heap
		setOfIngredients = new Hashtable<String, IngredientNode>(7919/*, 0.5*/);
		setOfRecipes = new PriorityQueue<RecipeNode>(1024, RecipeNode.c);
		setOfIngredientNames = new Trie();
	}

	//Constructor with initial file; includes file handling
	public IngredientsTable(String fileName) {
		//Sets initial capacity and load factor for hash table, and initializes min heap
		setOfIngredients = new Hashtable<String, IngredientNode>(7919/*, 0.5*/);
		setOfRecipes = new PriorityQueue<RecipeNode>(1024, RecipeNode.c);
		setOfIngredientNames = new Trie();

		//Formats and inserts recipes from file, if it exists
		//Note that the format for the initial list is different
		//from a general case file
		try {
			FileReader file = new FileReader(fileName);
			BufferedReader input = new BufferedReader(file);

			String recipeContents = "", rname = null, line = null;
			String [] words;
			RecipeNode r;
			IngredientNode ingNode;
			ArrayList<IngredientNode> a = null;
			int state = 0, rrating = 0;

			//Reads through text file
			while ((line = input.readLine()) != null) {
				recipeContents += line + "\n";
				switch (state) {
					case 0: 
					if (!line.contains("Try Something New")) {
						rname = line; 
						state++;
					} break;
					case 1: if (line.contains("Rating:")) state++; break;
					case 2: rrating = Integer.parseInt(line); state++; break;
					case 3: 
						if (line.contains("Ingredients")) {
							state++;
							a = new ArrayList<IngredientNode>();
						} break;
					case 4: 
						if (line.contains("Directions")) state++;
						else {
							for (String w : line.split("\\s+")) {
								if (!w.matches(".*\\d.*")) {
									a.add(ingNode = new IngredientNode(w.toLowerCase()));
								}
							}
						} break;
					case 5: 
						if (line.contains("**********")) {
							r = new RecipeNode(rname, rrating, recipeContents);
							for (IngredientNode i : a) {
								i.insertRecipe(r);
								setOfIngredientNames.insertString(i.getName());
							}
							insertRecipe(r, a);
							recipeContents = "";
							state = 0;
						} break;
				}
			}
			//Closes file
			input.close();
		} catch(FileNotFoundException e) {
			System.out.println("File not found!");
		} catch(IOException e) {
			System.out.println("IOException occured!");
		}
	}

	//Inserts recipe into PQueue and associated ingredients into table
	//Assumes max table capacity not reached (set to 1024 for project)
	//Also assumes that recipe/ingredient nodes have been created already
	public void insertRecipe(RecipeNode r, ArrayList<IngredientNode> a) {
		//Adds recipe to PQueue
		setOfRecipes.add(r);

		//Adds recipe's ingredients to table
		for (IngredientNode i : a) {
			if (setOfIngredients.get(i.getName()) == null) setOfIngredients.put(i.getName(), i);
			else setOfIngredients.get(i.getName()).insertRecipe(r);
		}
	}

	//Retrieves file if it exists and adds recipes from file to table
	//Note that the file's format must follow the standard template and
	//NOT the style of the master list used to initialize the table
	public void insertRecipesFromFile(String fileName) {
		try {
			FileReader file = new FileReader(fileName);
			BufferedReader input = new BufferedReader(file);

			String recipeContents = "", rname = null, line = null;
			String [] words;
			RecipeNode r;
			IngredientNode ingNode;
			ArrayList<IngredientNode> a = null;
			int state = 0, rrating = 0;

			//Reads through text file
			while ((line = input.readLine()) != null) {
				recipeContents += line + "\n";
				switch (state) {
					case 0: 
					if (!line.contains("Try Something New")) {
						rname = line; 
						state++;
					} break;
					case 1: if (line.contains("Rating:")) state++; break;
					case 2: rrating = Integer.parseInt(line); state++; break;
					case 3: 
						if (line.contains("Ingredients")) {
							state++;
							a = new ArrayList<IngredientNode>();
						} break;
					case 4: 
						if (line.contains("Directions")) state++;
						else {
							for (String w : line.split("\\s+")) {
								if (!w.matches(".*\\d.*")) {
									a.add(ingNode = new IngredientNode(w.toLowerCase()));
								}
							}
						} break;
					case 5: 
						if (line.contains("**********")) {
							r = new RecipeNode(rname, rrating, recipeContents);
							for (IngredientNode i : a) {
								i.insertRecipe(r);
								setOfIngredientNames.insertString(i.getName());
							}
							insertDelRecipe(r, a);
							recipeContents = "";
							state = 0;
						} break;
				}
			}
			//Closes file
			input.close();
		} catch(FileNotFoundException e) {
			System.out.println("File not found!");
		} catch(IOException e) {
			System.out.println("IOException occured!");
		}
	}

	//Once number of recipes reaches 1024, this function is used 
	public void insertDelRecipe(RecipeNode r, ArrayList<IngredientNode> a) {
		if (setOfRecipes.peek().getRank() < r.getRank()) {
			//Adds recipe to PQueue and removes head
			setOfRecipes.poll();
			setOfRecipes.add(r);

			//Adds recipe's ingredients to table
			for (IngredientNode i : a) {
				if (setOfIngredients.get(i.getName()) == null) setOfIngredients.put(i.getName(), i);
				else setOfIngredients.get(i.getName()).insertRecipe(r);
			}
		}
	}

	//Returns AL containing recipes that have specified ingredient
	public ArrayList<RecipeNode> getRecipes(String ingredientName) {
		return setOfIngredients.get(ingredientName).getRecipes();
	}
}