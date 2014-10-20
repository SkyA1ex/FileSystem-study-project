package FileSystem;
import java.util.AbstractMap.SimpleEntry;

import BTree.*;
import FileSystem.FileSystemException.*;

public class FileSystem {
	
	// Класс информации о файле для B-дерева
	private class FileInfo {
		private int address;
		private int length;
		
		private FileInfo(int address, int fileLength) {
			this.address = address;
			length = fileLength;
		}
		
		private int getAddress() { return address; }

		private int getLength() { return length; }
	}
	
	private BTree<String, FileInfo> files; // Содержит информацию о файлах, хранящихся на диске
	private Disk disk; // Хранит файлы
	private final int DEFAULT_CLUSTER_SIZE = 4;
	
	public FileSystem(int size, int clusterSize) {
		if (size < clusterSize)
			throw new DiskSizeException("Disk size must be more than clusterSize;");
		files = new BTree<String, FileInfo>();
		disk = new Disk(size, clusterSize);
	}
	
	public FileSystem(int size) {
		if (size < DEFAULT_CLUSTER_SIZE)
			throw new DiskSizeException("Disk size must be more than clusterSize;");
		files = new BTree<String, FileInfo>();
		disk = new Disk(size, DEFAULT_CLUSTER_SIZE);
	}
	
	// Возвращает размер диска в байтах
	public int getSize() {
		return disk.getDiskSize()*disk.getClusterSize();
	}
	// Возвращает размер кластера в байтах
	public int getClusterSize() {
		return disk.getClusterSize();
	}
	
	// Cуществует ли файл с таким именем
	public boolean isExist(String fileName) {
		return files.find(fileName) != null;		
	}
	
	public void add(File file) {
		int address = disk.allocate(file.data, file.getSize());
		if ( address != 0)
			files.add(file.getName(), new FileInfo(address,file.getSize()));
		else throw new NotEnoughMemoryException("Not enough memory for allocate file: " +
											file.getName() + ";");		
	}
	
	// Возвращает размер файла по его имени
	public int sizeOf(String fileName) {
		FileInfo fi = files.find(fileName);
		if (fi != null)
			return fi.getLength();
		else throw new FileNotFoundException("File with this name does't exist: " +
											fileName + ";");
	}
	
	// Возвращает копию файла по его имени
	public File find(String fileName) {
		FileInfo fi = files.find(fileName);
		if (fi != null)
			return new File(fileName, disk.read(fi.address, fi.length), fi.length);
		else
			return null;
	}
	
	// Удаляет файл
	public void remove(String fileName) {
		FileInfo fi = files.find(fileName);
		if ( fi != null) {
			disk.free(fi.address, fi.length);
			files.remove(fileName);
		}
		else throw new FileNotFoundException("File with this name does't exist: " +
					fileName + ";");
	}
	
	
}




