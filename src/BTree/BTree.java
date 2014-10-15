package BTree;

import java.util.*;


public class BTree<Key extends Comparable<Key>, Value> {
	
	private static final int t = 4; // минимальная степень
	Node root; // корень B-дерева
	
	// Класс хранит ключ и соответствующее ему значение
	private final class Entry {
		private Key key;
		private Value value;
		private boolean deleted;
		
		public Entry(Key key, Value value) {
			this.key = key;
			this.value = value;
			this.deleted = false;
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
		
		public void setDeleted(boolean b) { deleted = b; }
		
		public boolean isDeleted() { return deleted; }
		
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
		// Поиск нужного индекса для добавления
		while (i > 0 && less(key, node.data.get(i-1).getKey()))
			--i;
		if (i > 0 && equal(node.data.get(i-1).getKey(), key)) {
			if (node.data.get(i-1).isDeleted()) {
				// Изменяется значение и статус ранее удаленного элемента
				node.data.get(i-1).setDeleted(false);
				node.data.get(i-1).setValue(value);
			}
			else {
				// Не добавлять, если элемент с таким ключом уже существует
				System.err.println("Item with this key already exist");
				return;
			}
			
		}
		// Узел - лист дерева
		if (node.isLeaf()) {
			Entry newData = new Entry(key,value);
			if ( i == node.n )
				node.data.add(newData);
			else
				node.data.add(i, newData);
			node.incCount();
		}
		// Узел - не лист
		else {
			// TODO:Test code below
			// ++i; // not needed I think 
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
	public Value Find(Key key) {		
		return Search(root, key);
	}

	private Value Search(Node node, Key key) {
		int i = 0;
		while ( i < node.n && larger(key, node.data.get(i).getKey()) )
			++i;
		if ( i < node.n && equal(key, node.data.get(i).getKey()) )
			if (node.data.get(i).isDeleted())
				return null;
			else
				return node.data.get(i).getValue();
		else if (node.isLeaf())
			return null;
		else 
			return Search(node.childs.get(i), key);
	}
	
	/*
	 * Помечает элемент с заданным ключом как удаленный
 	 * (при этом структура дерева не изменяется(!)).
	 * В дальнейшем доступна вставка по данному ключу.
	 * Возвращает true, если элемент удален и false, если 
	 * элемент с данным ключом не найден
	 */
	public boolean Remove(Key key) {
		return Delete(root, key);
	}
	
	private boolean Delete(Node node, Key key) {
		int i = 0;
		while ( i < node.n && larger(key, node.data.get(i).getKey()) )
			++i;
		if ( i < node.n && equal(key, node.data.get(i).getKey()) ) 
			if (node.data.get(i).isDeleted())
				return false;
			else {
				node.data.get(i).setDeleted(true);
				return true;
			}
		else if (node.isLeaf())
			return false;
		else
			return Delete(node.childs.get(i),key);
	}
	
	
	
	private boolean larger(Key k1, Key k2) {
		return ( k1.compareTo(k2) > 0 );
	}
	private boolean less(Key k1, Key k2) {
		return ( k1.compareTo(k2) < 0 );
	}
	private boolean equal(Key k1, Key k2) {
		return ( k1.compareTo(k2) == 0);
	}
	
	
}
