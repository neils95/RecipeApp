##Recipe Search Application##
Neil Sanghrajka, Nicholas Musella, Joshua Klien

This application allows users to search for ranked recipes based on ingredients. Instead of using a database, we designed and implemented data structures for search, insertions and deletions to improve our understanding of data structure concepts.
Data is persisted using Java's Serializable interface.

###Installation:###

1. Download netbeans IDE with glassfish for Java EE application developement from the following link: https://netbeans.org/downloads/index.html
2. Clone repo.
3. In netbeans, open project and run web/homepage.jsp to run application on local server at port 8000

###Inserting new recipes:###
To add new recipes, stored in a .txt file follow the following template.

InsertRecipeTemplate.txt

	Try Something New
	Always insert this at the beginning of file
	Rating:
	0
	Ingredients
	Ingredient
	Directions
	Direction
	Start your recipe after ***************

	***************
	Try Something New
	<Recipe 1 Name>
	Rating:
	<Recipe 1 Rating>
	Ingredients
	<Ingredient 1>
	<Ingredient 2>
	<Ingredient n>
	Directions
	<Directions of recipe>
	***************
	Try Something New
	<Recipe 2 Name>
	Rating:
	<Recipe 2 Rating>
	Ingredients
	<Ingredient 1>
	<Ingredient 2>
	<Ingredient n>
	Directions
	<Directions of recipe>
	***************


###File description###
  * **MasterRecipeList.txt**

	1024 recipes that are initially loaded with name, ingredients, directions and popularity/rank

  * **IngredientNode.java**

	  * **Description:**
	This class contains several private elements which include an Array List of recipenodes, a rank, and a name string. The base constructor, IngredientNode, takes in a string name and sets the ingredient’s name to the given name, the rank to zero and creates a new RecipeNode array list of RecipeNode objects. This class also includes getter functions to return the recipeList array list, the ingredient’s name, and the ingredient’s rank.

    * **API reference**
    
    	IngredientNode: This function only sets and initializes the fields of the object.
    
    	getRank: Returns the rank field.
    	
    	getName: Returns the name field.
    
    	getRecipes: Returns the whole recipesList.
    
    	insertRecipe:  Given a recipesList of size N, the first for loop will run N times to check for and remove null recipes, so this runs in O(N).  Assuming a duplicate recipe was not found, the function then adds the recipe to the Array List and calls Collections.sort to sort the list.  If the size of the list is greater than 8, the last recipe in the list, the lowest ranked recipe, will be removed.

  * **IngredientsTable.java**

	  * **Description:**
    	IngredientsTable.java creates a class using a Hash Table to store the ingredients listed in each recipe. The class also designates a Priority Queue-backed minimum heap of RecipeNodes to store the set of recipes, an Array List with the top 8 recipes, and a Trie to store the set of ingredient names to be used for the autocomplete search function.
    
    	The base constructor IngredientsTable sets the initial capacity and load factor to 7919 and .9, respectively, for the hash table to store the ingredients in optimal time. The function also initializes the minimum heap to the maximum number of recipes, which is 1024, and initializes the trie. 
    
    	The IngredientsTable input constructor can take in the initial file name. It first initializes the data structures. Then reads in the file line by line and fills the data structures with the relevant information such as the rating, ingredients and directions using a switch statement for every line read. Lastly it error checks and closes the initial file as necessary. 
    
    	The insertRecipe function takes in a RecipeNode and an array list of IngredientNodes, and inserts each recipe into the priority queue with its corresponding ingredients into the ingredient has table. Note that this function is used ONLY for the initial loading of the table. 
    
    	The insertRecipesFromFile take a filename as input, which must be in the standard file format. This function loops through each line in the file and adds the ingredients, ratings and directions to the data structures in the appropriate positions. If the number of recipes exceeds 1024 the insertDelRecipe function is called to maintain the maximum number of recipes to 1024. This function inserts the recipe into the min heap priority queue and pops, or deletes, the recipe with the lowest popularity. 
    
    	The getRecipes function returns an array list of Recipe Nodes that contain a specific ingredient searched. This is used as a helper function for needed implementation.
    
    	The writePQToMaster function saves the all of the recipes’ contents to a file with a specified file name.
    
    	The getTopIngredients function takes in an ArrayList of strings and returns the 5 most highly-ranked ingredients in the hash table.  It does this by loading all ingredients into a priority queue-backed minimum heap and pops ingredients until the heap’s size is 5.  The rest of the ingredients then get entered into an Array List for returning.
    
    	The getTopRecipes function simply returns an Array List of the top 8 recipes in the heap.
    
    	Comparator c and RecipeComparator are only used for java’s built-in standard libraries.

  * **RecipeNode.java**
	
    * **Description:**
      This class provides the recipe node for the back end, which includes a rank and references to ingredients.  The recipe's rank must be given by the user when the recipe is created.

	 * **API reference**
  	  getContents(): returns constentsString with recipe's name, directions and rank
  
    	getRank(): returns recipes rank
  
      getName(): returns recipe's name
  
    	Comparator for use with standard java library

  * **Trie.java**

	  * **Description:**
  	Implements the Trie and Trienode class for creating and transversing a trie of ingredients. The structure uses a delimiter to signal the end of words internally, this means the delimiter may never occur in the words passed to the list. The delimiter used in this word to tell where words end. Without a proper delimiter either A. A lookup for 'win' would return false if the list also contained 'windows', or B. A lookup for 'mag' would return true if the only word in the list was 'magnolia.' The delimiter should never occur in a word added to the trie.
  
    * **API reference**
    	Trie(): creates a new case sensitive trie
    
    	Trie(bool): creates a new Trie all lower cases
    
    	add(String): adds an ingredient to the trie
    
    	addAll(String[]): adds an array of ingredients to the trie at once
    		add(): worker function
    
    	remove(String): removes an ingredient from the trie
    		remove(...): worker function
    
    	suggest(String): returns all ingredients with given prefix
    		suggest(...): worker function
    
    	getAll(): return all ingredients in the trie
    		getAll(...): worker funtion
    
    	Node class: represents a node in the trie since each node is a sub-trie of ingredients. Initalizes the root, size, maxDepth and ignoreCase elements

  * **Driver.java**

	  implements main function to test and run application

  * **ActionServlet.java**
	
	    Takes inputs from web page and sends to java back-end


###Front-End Files###

1. build.xml
2. FormSubmission.jsp 	
3. HomePage.css 	
4. HomePage.jsp 	o
5. InsertRecipePage.css 	
6. InsertRecipePage.jsp 	
7. RecipePage.css 	
8. RecipePage.jsp 	
9. homePageBackground.jpg 	
10. recipePageBackground.jpg
11. web.xml

