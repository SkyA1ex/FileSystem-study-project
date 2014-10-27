package FileSystem;

import java.util.*;
import java.lang.*;

public class Disk {
	private byte[] memory;	// Массив байт - моделирует пространство диска
	private boolean[] isFree;	// Хранит информацию о свободных кластерах в памяти
	private final int clusterSize;	// Размер одного блока памяти
	private final int diskSize;	// Размер диска в кластерах
	
	// Конструктор. Размер кластера и диска задается в байтах
	public Disk(int diskSize, int clusterSize) {
		this.clusterSize = clusterSize;
		this.diskSize = diskSize/clusterSize;
		
		memory = new byte[this.diskSize*clusterSize];
		
		int i = 1;
		while ( i < this.diskSize*clusterSize ) {
			memory[i] = 0;
			++i;
		}
		
		isFree = new boolean[this.diskSize];
		i = 1;
		while ( i < this.diskSize ) {
			isFree[i] = true;
			++i;
		}
		
		// Резервирование байта для служебных целей
		memory[0] = -1;
		isFree[0] = false;
	}
	
	// Возвращает размер кластера
	public int getClusterSize() { return clusterSize; }
	// Возвращает размер диска в байтах
	public int getDiskSize() { return diskSize*clusterSize; }
	
	/*
	 * Выделяет память для файла, возвращает адрес, 
	 * если память данного размера может быть выделена 
	 * и ноль в противном случае
	*/
	public int allocate(byte data[], int fileSize) {
		int count = fileSize/clusterSize;
		if ((fileSize%clusterSize) > 0)
			count++;
		// Поиск последовательности свободных байт нужной длины
		int seq = 0;
		for (int i = 1; i < diskSize; ++i) {
			if ( isFree[i] == true ) {
				++seq;
				if (seq == count) {
					for (int j=i-seq+1; j<=i; ++j) {
						isFree[j] = false;
					}
					for (int j=0; j < fileSize; ++j)
						memory[(i-seq+1)*clusterSize+j] = data[j];
					return (i-seq+1)*clusterSize;
				}
			}
			else
				seq = 0;
		}
		return 0;
	}
	
	// Возвращает копию участка памяти длины length, начиная с startIndex
	public byte[] read(int startIndex, int length) {
		// Перевод в кластеры
		int start = startIndex/clusterSize;
		int count = length/clusterSize;
		if ((length%clusterSize) > 0)
			count++;
		if (start == 0)
			throw new SecurityException("Attempt to delete service data " +
					"with startIndex = " + String.valueOf(startIndex));
		if (start + count > diskSize)
			throw new IndexOutOfBoundsException("Index out of disk memory: " +
					"diskSize = " + String.valueOf(diskSize) +
					"; index = " + String.valueOf(start+count));
		
		byte data[] = new byte[length];
		for (int i = 0; i < length; ++i)
			data[i] = memory[startIndex+i];
		return data;
	}
	
	// Освобождает память размера fileSize, начиная с индекса startIndex
	public void free(int startIndex, int fileSize) {
		// Перевод в кластеры
		int start = startIndex/clusterSize;
		int count = fileSize/clusterSize;
		if ((fileSize%clusterSize) > 0)
			count++;
		
		if (start == 0)
			throw new SecurityException("Attempt to delete service data " +
					"with startIndex = " + String.valueOf(startIndex));
		
		if (start + count > diskSize)
			throw new IndexOutOfBoundsException("Index out of disk memory: " +
					"diskSize = " + String.valueOf(diskSize) +
					"; index = " + String.valueOf(start+count));
		else {
			for ( int i = start; i < start+count; ++i )
				isFree[i] = true;
			
		}
	}
}
