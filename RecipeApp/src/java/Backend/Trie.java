package Backend;

/* Joshua Klein
 * 11/8/15
 * ENG EC504
 * Professor Moreshet
 * 
 * Project Back-End - Trie
 * 
 * This class provides the functionality for the Trie in the back-end.
 * The trie is used for the autocomplete bar in the GUI and contains
 * all ingredient names.
 * 
 */

import java.util.Hashtable;
import java.util.ArrayList;

public class Trie {

	//Node class
	public class TrNode {
		public Hashtable<Character, TrNode> t;
		public Character c;
		public boolean isLeaf;

		//Constructor
		public TrNode(Character c) {
			this.c = c;
			this.t = new Hashtable<Character, TrNode>();
			this.isLeaf = true;
		}

		//Inserts Trie Nodes
		public void insert(Character c) {
			if (t.get(c) == null) {
				t.put(c, new TrNode(c));
				isLeaf = false;
			}
		}
	}

	//Base of Trie
	private TrNode root;

	//Constructor
	public Trie() {
		root = new TrNode('\0');
	}

	//Inserts string
	public void insertString(String str) {
		TrNode crawler = root;
		String s = str.toLowerCase();
		for (int i = 0; i < s.length(); i++) {
			crawler.insert(s.charAt(i));
			crawler = crawler.t.get(s.charAt(i));
		}
	}
}