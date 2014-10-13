package BTree;

import java.util.*;

public class BTree<Key extends Comparable<Key>, Value> {
	
	private static final int t = 4; // минимальная степень
	private int HT; // высота B-дерева
	Node root; // корень B-дерева
	
	private class Node {
		private int n; // количество ключей в узле
		private boolean isLeaf; // является ли узел листом 
		private ArrayList<Entry> data = new ArrayList<Entry>(n);
		private ArrayList<Node> childrens = new ArrayList<Node>(n+1);
		
		public Node(int n) {
			this.n = n;
		}
		
		// класс хранит ключ и соответствующее ему значение 
		private final class Entry {
			private Key key;
			private Value value;
			
			public Entry(Key key, Value value) {
				this.key = key;
				this.value = value;
			}
			
			public Key getKey() {
				return key;
			}

			public Value getValue() {
				return value;
			}

			public Value setValue(Value newValue) {
				Value old = value;
				value = newValue;
				return old;
			}
			
		}
		
		
	}
	
	// конструктор
	public BTree() { 
		root = new Node(0); 
	}
	
	// возвращает высоту B-дерева
	public int getHeight(){ return HT; }
	
	public void Add(Key key, Value value) {
		Insert(key,value,HT);
		//TODO: write implementation
	}
	
	private void Insert(Key key, Value value, int HT) {
		//TODO: write implementation
	}
	
	public Value Find(Key key) {		
		//TODO: write implementation
		return Search(root, key, HT);
	}
	
	private Value Search(Node x, Key key, int HT){
		//TODO: write implementation
		return null;
	}
	
	
	
}
