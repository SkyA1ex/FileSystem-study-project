package BTree;

import java.util.*;


public class BTree<Key extends Comparable<Key>, Value> {
	
	private static final int t = 4; // минимальная степень
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
		
		public void setSize(int newSize) { 
			if (newSize < n)
				for (int i = newSize; i < n; ++i) {
					data.remove(newSize);
					// TODO: check how removes childs
					//if (!isLeaf())
					//	childs.remove(i+1);
				}
			else {
				//TODO:need anything??
			}
			n = newSize;
		}
		
	}
	
	// Конструктор
	public BTree() { 
		root = new Node(0,true); 
	}
	
	// Добавляет пару ключ-значение
	public void Add(Key key, Value value) {
		//TODO: What will be in the case of existing key?!
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
		int i = node.n;
		// Узел - лист дерева
		if (node.isLeaf()) {
			while ( i > 0 && less(key, node.data.get(i-1).getKey()) )
				--i;
			Entry newData = new Entry(key,value);
			if ( i == node.n )
				node.data.add(newData);
			else
				node.data.add(i, newData);
			node.incCount();
		}
		// Узел - не лист
		else {
			while ( i > 0 && less(key, node.data.get(i-1).getKey()) )
				--i;
			// TODO:Test code below
			// ++i; // not needed i think 
 			if (node.childs.get(i).n == 2*t-1) {
				Split(node,i);
				if ( larger(key, node.data.get(i).getKey()) )
					++i;
			}
			Insert(node.childs.get(i),key,value);
		}
	}
	
	// Разбивает дочерний узел с индексом childIndex
	private void Split(Node node, int childIndex) {
		Node leftChild = node.childs.get(childIndex);
		// Новый потомок, который будет правым
		Node rightChild = new Node(t-1, leftChild.isLeaf());
		// Копирование значений в правый узел
		for (int j = 0; j < t-1; ++j)
			rightChild.data.add(leftChild.data.get(j+t));
		// Копирование потомков, если не лист
		if (!leftChild.isLeaf())
			for ( int j = 0; j < t; ++j )
				rightChild.childs.add(leftChild.childs.get(j+t));
		Entry upper = leftChild.data.get(t-1);
		leftChild.setSize(t-1);
		
		node.data.add(childIndex, upper);
		node.childs.add(childIndex+1, rightChild);
		node.setSize(node.n + 1);	
	}
	
	/* 
	 * Поиск по ключу, возващает значение, соответствующее ключу 
	 * или null, если ключ не найден
	*/
	/*
	public Value Find(Key key) {		
		//TODO: write implementation for Find
		return Search(root, key, ht);
	}

	*/
	private boolean larger(Key k1, Key k2) {
		return ( k1.compareTo(k2) > 0 );
	}
	private boolean less(Key k1, Key k2) {
		return ( k1.compareTo(k2) < 0 );
	}
	
	
}
