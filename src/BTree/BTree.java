package BTree;

import java.util.*;


public class BTree<Key extends Comparable<Key>, Value> {
	
	private static final int t = 4; // минимальная степень
	private int size = 0; // количество доступных ключей в дереве
	Node root; // корень B-дерева
	
	// Класс хранит ключ и соответствующее ему значение
	private final class Entry implements Comparable<Entry>{
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

		@Override
		public int compareTo(Entry o) {
			return key.compareTo(o.key);
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
					//	TODO: check how removes childs
					//	if (!isLeaf())
					//	childs.remove(i+1);
				}
			n = newSize;
		}
		
	}
	
	// Конструктор
	public BTree() { 
		root = new Node(0,true); 
	}
	
	// Возвращает количество доступных элементов в дереве
	public int getSize() { return size; }
	
	public void clear() {
		root = new Node(0,true);
		size = 0;
	}
	
	// Добавляет пару ключ-значение
	public void add(Key key, Value value) {
		if (root.n == 2*t-1) {
			Node oldRoot = root;
			Node newRoot = new Node(0, false);
			root = newRoot;
			newRoot.childs.add(oldRoot);
			split(newRoot,0);
			insert(newRoot,key,value);
		}
		else {
			insert(root, key, value);
		}
	}
	
	private void insert(Node node, Key key, Value value) {
		int i = Collections.binarySearch(node.data, new Entry(key,value));
		if (i >= 0) {
			// Проверка в случае наличия данного ключа
			Entry e = node.data.get(i);
			if (e.isDeleted()) {
				// Изменяется значение и статус ранее удаленного элемента
				e.setDeleted(false);
				e.setValue(value);
				++size;
			}
			else {
				// Не добавлять, если элемент с таким ключом уже существует
				System.err.println("Item with this key already exist");
				return;
			}
		}
		else {
			// Read Collections.binarySearch
			i = i*(-1) - 1;
			if (node.isLeaf()) {
				Entry newData = new Entry(key,value);
				node.data.add(i, newData);
				node.incCount();
				++size;
			}
			// Узел - не лист
			else {
	 			if (node.childs.get(i).n == 2*t-1) {
					split(node,i);
					if ( key.compareTo(node.data.get(i).getKey()) > 0 )
						++i;
				}
				insert(node.childs.get(i),key,value);
			}
			
		}
	}
	
	// Разбивает дочерний узел с индексом childIndex
	private void split(Node node, int childIndex) {
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
	public Value find(Key key) {		
		return search(root, key);
	}

	private Value search(Node node, Key key) {
		// Поиск нужного элемента
		int i = Collections.binarySearch(node.data, new Entry(key, null));
		
		if (i >= 0) {
			Entry e = node.data.get(i);
			return (e.isDeleted()) ? null : e.getValue();
		}
		else {
			// Read Collections.binarySearch
			i = i*(-1) - 1;
			return (node.isLeaf()) ? null : search(node.childs.get(i),key);
		}
	}
	
	/*
	 * Помечает элемент с заданным ключом как удаленный
 	 * (при этом структура дерева не изменяется(!)).
	 * В дальнейшем доступна вставка по данному ключу.
	 * Возвращает true, если элемент удален и false, если 
	 * элемент с данным ключом не найден
	 */
	public boolean remove(Key key) {
		return delete(root, key);
	}
	
	private boolean delete(Node node, Key key) {
		// Поиск нужного элемента
		int i = Collections.binarySearch(node.data, new Entry(key, null));
		if (i >= 0) {
			Entry e = node.data.get(i);
			if (e.isDeleted())
				return false;
			else {
				e.setDeleted(true);
				node.decCount();
				--size;
				return true;
			}
		}
		else {
			i = i*(-1) - 1;
			return (node.isLeaf()) ? false : delete(node.childs.get(i),key);
		}
	}

	
}
