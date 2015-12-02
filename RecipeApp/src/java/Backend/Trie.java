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
 * Source for returning all strings: 
 * http://stackoverflow.com/questions/13685687/how-to-print-all-words-in-a-trie
 */

import java.util.Hashtable;
import java.util.ArrayList;

public class Trie {

	//Node class
	public class TrNode implements java.io.Serializable{
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
			t.put(c, new TrNode(c));
			this.isLeaf = false;
		}
	}

	//Base of Trie
	public TrNode root;

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

	//Returns all substrings in AL created from prefix
	public ArrayList<String> getAllStrings(String prefix) {
		TrNode crawler = root;
		String s = prefix.toLowerCase(), str = "";
		ArrayList<String> suffixList = new ArrayList<String>();

		//Gets crawler to appropriate position on tree
		for (int i = 0; i < s.length(); i++) {
			crawler = crawler.t.get(s.charAt(i));
		}

		//Adds strings to AL

		getAllWords(prefix, crawler);


		return suffixList;
	}

	public void getAllWords(String prefix, TrNode base) {
		if (base.isLeaf) System.out.println(prefix);
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < 26; i++) {
			char next = alphabet.charAt(i);
			if (base.t.get(i) != null) {
				System.out.println("Hi");
				String  s = prefix;
				prefix += next;
				getAllWords(s, base.t.get(i));
				prefix = s;
			}
		}
	}
}