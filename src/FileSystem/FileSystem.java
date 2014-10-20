package FileSystem;
import BTree.*;

public class FileSystem {
	//TODO: change second template agrument type
	private BTree<String, String> files; // Содержит информацию о файлах, хранящихся на диске
	private Disk disk; // Хранит файлы
	
	FileSystem(int size, int clusterSize) {
		//TODO: check for exceptions
		//TODO: CHANCGE TYPE
		files = new BTree<String, String>();
		disk = new Disk(size, clusterSize);
	}
	
	FileSystem(int size) {
		//TODO: check for exceptions
		//TODO: CHANCGE TYPE
		files = new BTree<String, String>();
		disk = new Disk(size, 4);
	}
	
	// Возвращает размер диска в байтах
	public int getSize() {
		return disk.getDiskSize()*disk.getClusterSize();
	}
	// Возвращает размер кластера в байтах
	public int getClusterSize() {
		return disk.getClusterSize();
	}
	
	void add(File file) {
		//TODO: add() implementation
	}
	
	// Возвращает размер файла по его имени
	int sizeOf(File file) {
		//TODO: sizeOf() implementation 
		return 0;
	}
	
	// Возвращает ссылку на файл по его имени
	File find(String fileName) {
		// TODO: find() implementation 
		return null;
	}
	
	void remove(String fileName) {
		//TODO: remove() implementation
	}
	
	
}




