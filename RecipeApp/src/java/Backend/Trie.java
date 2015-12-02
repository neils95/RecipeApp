package Backend;
//EC 504 Project: Recipe Search Appliction
//Professor Moreshet
//Nicolas Musella, Joshua Klien, Neil Sanghrajka

/*
Modified from original copy by Michael Clerx:

Reference:
**http://whiteboxcomputing.com/java/prefix_tree/
**Copyright 2010 Michael Clerx
**work@michaelclerx.com
*/

//Trie Implementation

import java.util.*;
import java.lang.*;
import java.io.*;

public class Trie
{
	
	public final static char DELIMITER = '\u0001';

	/**
	 * Creates a new case sensitive Trie
	 * @param ignoreCase
	 */
	public Trie()
	{
		this(false);
	}

	/**
	 * Creates a new Trie.
	 * @param ignoreCase Set this to true to make the trie ignore case (warning: this slows down
	 *            performance considerably!)
	 */
	public Trie(boolean ignoreCase)
	{
		root = new Node('r');
		size = 0;
		this.ignoreCase = ignoreCase;
	}

	/**
	 * Adds a word to the list.
	 * @param word The word to add.
	 * @return True if the word wasn't in the list yet
	 */
	public boolean add(String word)
	{
		if (word.length() == 0)
			throw new IllegalArgumentException("Word can't be empty");
		String w = (ignoreCase) ? word.toLowerCase() : word;
		if (add(root, w + DELIMITER, 0))
		{
			size++;
			int n = word.length();
			if (n > maxDepth) maxDepth = n;
			return true;
		}
		return false;
	}

	/**
	 * Adds an array of words to the list.
	 * @param word The words to add.
	 */
	public void addAll(String[] words)
	{
		for (String word : words)
			add(word);
	}

	/*
	 * Does the real work of adding a word to the trie
	 */
	private boolean add(Node root, String word, int offset)
	{
		if (offset == word.length()) return false;
		int c = word.charAt(offset);

		// Search for node to add to
		Node last = null, next = root.firstChild;
		while (next != null)
		{
			if (next.value < c)
			{
				// Not found yet, continue searching
				last = next;
				next = next.nextSibling;
			}
			else if (next.value == c)
			{
				// Match found, add remaining word to this node
				return add(next, word, offset + 1);
			}
			// Because of the ordering of the list getting here means we won't
			// find a match
			else break;
		}

		// No match found, create a new node and insert
		Node node = new Node(c);
		if (last == null)
		{
			// Insert node at the beginning of the list (Works for next == null
			// too)
			root.firstChild = node;
			node.nextSibling = next;
		}
		else
		{
			// Insert between last and next
			last.nextSibling = node;
			node.nextSibling = next;
		}

		// Add remaining letters
		for (int i = offset + 1; i < word.length(); i++)
		{
			node.firstChild = new Node(word.charAt(i));
			node = node.firstChild;
		}
		return true;
	}

	/**
	 * Removes a word from the list.
	 * @param word The word to remove.
	 * @return True if the word was found and removed.
	 */
	public boolean remove(String word)
	{
		if (word.length() == 0)
			throw new IllegalArgumentException("Word can't be empty");
		String w = (ignoreCase) ? word.toLowerCase() : word;
		if (remove(root, w + DELIMITER, 0, null, null, null))
		{
			size--;
			return true;
		}
		return false;
	}

	/*
	 * Removes a word from the list: searches for the word while retaining information about the
	 * last time it encountered a tree branch, and which branch of the tree it followed...
	 */
	private boolean remove(Node root, String word, int offset, Node branch, Node branchLast, Node branchNext)
	{
		if (offset == word.length())
		{
			// Word found, delete entry at last branch
			if (branch == null)
			{
				// No branches found in the tree, only one word!
				this.root.firstChild = null;
			}
			else
			{
				if (branchLast == null) branch.firstChild = branchNext;
				else branchLast.nextSibling = branchNext;
			}
			return true;
		}

		// Search for word
		int c = word.charAt(offset);
		Node last = null, next = root.firstChild;
		while (next != null)
		{
			if (next.value < c)
			{
				last = next;
				next = next.nextSibling;
			}
			else if (next.value == c)
			{
				// Test if this node had more than one child
				if (last != null || next.nextSibling != null)
				{
					branch = root;
					branchLast = last;
					branchNext = next.nextSibling;
				}
				return remove(next, word, offset + 1, branch, branchLast,
					branchNext);
			}
			else return false;
		}
		return false;
	}

	/**
	 * Returns all words in this list starting with the given prefix
	 * 
	 * @param prefix The prefix to search for.
	 * @return All words in this list starting with the given prefix, or if no such words are found,
	 *         an array containing only the suggested prefix.
	 */
	public ArrayList<String> suggest(String prefix)
	{
		return suggest(root, prefix, 0);
	}

	/*
	 * Recursive function for finding all words starting with the given prefix
	 */
	private ArrayList<String> suggest(Node root, String word, int offset)
	{
		if (offset == word.length())
		{
			ArrayList<String> words = new ArrayList<String>(size);
			char[] chars = new char[maxDepth];
			for (int i = 0; i < offset; i++)
				chars[i] = word.charAt(i);
			getAll(root, words, chars, offset);
			return words;
		}
		int c = word.charAt(offset);

		// Search for node to add to
		Node next = root.firstChild;
		while (next != null)
		{
			if (next.value < c) next = next.nextSibling;
			else if (next.value == c) return suggest(next, word, offset + 1);
			else break;
		}
		ArrayList<String> a = new ArrayList<String>();
		a.add(word);
		return a;
	}

	/**
	 * Returns all words in the list. If (ignoreCase=true) all words are returned in lower case. For
	 * large lists this will be a fairly slow operation.
	 * 
	 * @return A String array of all words in the list
	 */
	public ArrayList<String> getAll()
	{
		ArrayList<String> words = new ArrayList<String>(size);
		char[] chars = new char[maxDepth];
		getAll(root, words, chars, 0);
		return words;
	}

	/*
	 * Adds any words found in this branch to the array
	 */
	private void getAll(Node root, ArrayList<String> words, char[] chars, int pointer)
	{
		Node n = root.firstChild;
		while (n != null)
		{
			if (n.firstChild == null)
			{
				words.add(new String(chars, 0, pointer));
			}
			else
			{
				chars[pointer] = (char)n.value;
				getAll(n, words, chars, pointer + 1);
			}
			n = n.nextSibling;
		}
	}


	/*
	 * Represents a node in the trie. Because a node's children are stored in a linked list this
	 * data structure takes the odd structure of node with a firstChild and a nextSibling.
	 */
	private class Node
	{
		public int value;
		public Node firstChild;
		public Node nextSibling;

		public Node(int value)
		{
			this.value = value;
			firstChild = null;
			nextSibling = null;
		}
	}

	private Node root;
	private int size;
	private int maxDepth; // Not exact, but bounding for the maximum
	private boolean ignoreCase;
}

/*
Reference:
**http://whiteboxcomputing.com/java/prefix_tree/
**Copyright 2010 Michael Clerx
**work@michaelclerx.com
*/

