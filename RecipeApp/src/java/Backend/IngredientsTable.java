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
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;

public class IngredientsTable {

	public Hashtable<String, IngredientNode> setOfIngredients;
	public PriorityQueue<RecipeNode> setOfRecipes;
	public Trie setOfIngredientNames;
	public ArrayList<RecipeNode> topRecipes;

	//Constructor
	public IngredientsTable() {
		//Sets initial capacity and load factor for hash table, and initializes min heap
		setOfIngredients = new Hashtable<String, IngredientNode>(7919, (float)0.9);
		setOfRecipes = new PriorityQueue<RecipeNode>(1024, RecipeNode.c);
		setOfIngredientNames = new Trie();
		topRecipes = new ArrayList<RecipeNode>();
	}

	//Constructor with initial file; includes file handling
	public IngredientsTable(String fileName) {
		//Sets initial capacity and load factor for hash table, and initializes min heap
		setOfIngredients = new Hashtable<String, IngredientNode>(7919, (float)0.9);
		setOfRecipes = new PriorityQueue<RecipeNode>(1024, RecipeNode.c);
		setOfIngredientNames = new Trie();
		topRecipes = new ArrayList<RecipeNode>();

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
								if (!w.matches("[^\\w']+")) {
									a.add(ingNode = new IngredientNode(w.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase()));
								}
							}
						} break;
					case 5: 
						if (line.contains("**********")) {
							r = new RecipeNode(rname, rrating, recipeContents);
							for (IngredientNode i : a) {
								i.insertRecipe(r);
								setOfIngredientNames.add(i.getName());
								r.ingredientsList.add(i);
							}
							insertRecipe(r, a);
							recipeContents = "";
							state = 0;
							topRecipes.add(r);
							Collections.sort(topRecipes, new RecipeComparator());
							if (topRecipes.size() > 8) topRecipes.remove(8);	
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
			if (setOfIngredients.get(i.getName()) == null) {
				setOfIngredients.put(i.getName(), i);
				setOfIngredients.get(i.getName()).rank += r.getRank();
			} else setOfIngredients.get(i.getName()).insertRecipe(r);
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
                        line = input.readLine();
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
								if (!w.matches("[^\\w']+")) {
									a.add(ingNode = new IngredientNode(w.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase()));
								}
							}
						} break;
					case 5: 
						if (line.contains("**********")) {
							r = new RecipeNode(rname, rrating, recipeContents);
							for (IngredientNode i : a) {
								i.insertRecipe(r);
								setOfIngredientNames.add(i.getName());
								r.ingredientsList.add(i);
							}
							insertDelRecipe(r, a);
							recipeContents = "";
							state = 0;
							topRecipes.add(r);
							Collections.sort(topRecipes, new RecipeComparator());
							if (topRecipes.size() > 8) topRecipes.remove(8);
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
			RecipeNode deletedRecipe = setOfRecipes.poll();
			setOfRecipes.add(r);

			//Adjusts ingredient ranks for newly deleted recipe
			//Also removes ingredient node and name from trie if size of 
			//recipesList in node is 0 (aka, no recipes have that ingredient)
			for (IngredientNode i : deletedRecipe.ingredientsList) {
				setOfIngredients.get(i.getName()).rank -= deletedRecipe.getRank();
				if (i.getRecipes().size() == 0) {
					setOfIngredientNames.remove(i.getName());
					setOfIngredients.remove(i.getName());
				}
			}

			//Adds recipe's ingredients to table
			for (IngredientNode i : a) {
				if (setOfIngredients.get(i.getName()) == null) setOfIngredients.put(i.getName(), i);
				else setOfIngredients.get(i.getName()).insertRecipe(r);
			}
		}
	}

	//Returns AL containing recipes that have specified ingredient
	public ArrayList<RecipeNode> getRecipes(String ingredientName) {
		try {
			return setOfIngredients.get(ingredientName).getRecipes();
		} catch (NullPointerException e) {
			System.out.println("Ingredient not found!");
			return null;
		}
	}

	//Rewrites recipes to master list from current PQ
	//NOTE: Assumes file ALREADY EXISTS
	public void writePQToMaster(String fileName) {
		try {
			//Set up file for overwriting
			File f = new File(fileName);
			f.createNewFile();
			FileWriter master = new FileWriter(f, false);

			//Gets PQ iterator
			Iterator<RecipeNode> i = setOfRecipes.iterator();

			//Writes to file using iterator
			RecipeNode r;
			while (i.hasNext()) {
				r = i.next();
				master.write(r.getContents());
			}

			master.close();


		} catch (IOException e) {
			System.out.println("IOException occured!");
		}
	}

	//Returns AL containing top 5 ingredients with given list of prefixes
	public ArrayList<IngredientNode> getTopIngredients(ArrayList<String> a) {
		//Instantiates PQ
		PriorityQueue<IngredientNode> top = new PriorityQueue<IngredientNode>(5, IngredientNode.c);

		//Adds every ingredient with names from string list in min heap
		for (String s : a) 
			if (setOfIngredients.get(s) != null) 
				top.add(setOfIngredients.get(s));

		//Removes head of PQ until only 5 items (top rated) remain
		while (top.size() > 5) top.poll();

		//Adds items from PQ into ArrayList for return
		ArrayList<IngredientNode> topIngredients = new ArrayList<IngredientNode>();

		IngredientNode i;
		while ((i = top.poll()) != null) topIngredients.add(0, i);

		return topIngredients;
	}

	//Returns AL containing top 8 recipes
	public ArrayList<RecipeNode> getTopRecipes() 
	{return topRecipes;}

	public static class RecipeComparator implements Comparator<RecipeNode> {
			@Override
			public int compare(RecipeNode a, RecipeNode b) {
				if (a.getRank() < b.getRank()) return 1;
				return -1;
			}
		}
}

