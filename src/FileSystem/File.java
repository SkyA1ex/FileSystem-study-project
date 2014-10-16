package FileSystem;

import java.util.Random;

public class File {
	private String name; // Имя файла
	private int size; // Размер файла в байтах
	public byte[] data;
	public File(String name, int size) {
		this.name = name;
		this.size = size;
		 data = new byte[size];
		new Random().nextBytes(data);
	}
	
	public int getSize() { return size; }
	public String getName() { return name; }
	

}