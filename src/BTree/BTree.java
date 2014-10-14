package BTree;

import java.util.*;


public class BTree<Key extends Comparable<Key>, Value> {
	
	private static final int t = 4; // минимальная степень
	private int ht; // высота B-дерева
	Node root; // корень B-дерева
	
	// Класс хранит ключ и соответствующее ему значение
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
	
	private class Node {
		private int n; // количество ключей в узле
		private boolean leaf; // является ли узел листом
		private ArrayList<Entry> data = new ArrayList<Entry>();
		private ArrayList<Node> childs = new ArrayList<Node>(n+1);
		
		public Node(int n) {
			this.n = n;
			leaf = false;
		}
		public Node(int n, boolean isLeaf) {
			this.n = n;
			leaf = isLeaf;
		}		
		public boolean isLeaf() { return leaf; }
		private void setLeaf(boolean b) { leaf = b; } 
		
		public void incCount(){ ++n; }
		public void decCount(){ --n; }
		
	}
	
	// Конструктор
	public BTree() { 
		root = new Node(0); 
	}
	
	// Возвращает высоту B-дерева
	public int getHeight(){ return ht; }
	
	// Добавляет пару ключ-значение
	public void Add(Key key, Value value) {
		//TODO: Check all that below
		if (root.n == 2*t-1) {
			Node oldRoot = root;
			Node newRoot = new Node(0, false);
			root = newRoot;
			newRoot.childs.add(oldRoot);
			Split(newRoot,0);
			Insert(newRoot,key,value);
			
		}
		else {
			Insert(root, key, value);
		}
	}
	
	private void Insert(Node node, Key key, Value value) {
		//TODO: Check all that below
		int i = node.n;
		// Узел - лист дерева
		if (node.isLeaf()) {			
			// TODO:Check this cycle
			while ( i >= 0 && less(key, node.data.get(i).getKey()) ) {
				node.data.set(i+1, node.data.get(i));
				--i;
			}
			Entry newData = new Entry(key,value);
			node.data.set(i+1, newData);
			node.incCount();		
		}
		// Узел - не лист
		else {
			while ( i >= 0 && less(key, node.data.get(i).getKey()) ) {
				--i;
			}
			++i;
			if (node.childs.get(i).n == 2*t-1) {
				Split(node,i);
				if ( larger(key, node.data.get(i).getKey()) )
					++i;
			}
			Insert(node.childs.get(i),key,value);
		}
	}
	
	private void Split(Node node, int childIndex) {
		//TODO: write implementation
	}
	
	/* 
	 * Поиск по ключу, возващает значение, соответствующее ключу 
	 * или null, если ключ не найден
	*/
	public Value Find(Key key) {		
		//TODO: write implementation
		return Search(root, key, ht);
	}
	
	private Value Search(Node x, Key key, int ht){
		//TODO: write implementation
		return null;
	}
	
	private boolean larger(Key k1, Key k2) {
		return ( k1.compareTo(k2) > 0 );
	}
	private boolean less(Key k1, Key k2) {
		return ( k1.compareTo(k2) < 0 );
	}
	
	
}
