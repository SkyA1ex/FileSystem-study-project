
import java.util.ArrayList;
import FileSystem.*;

import BTree.BTree;

	public class FileSystemMain {	
	
		public static void main(String[] args) {
			try {
				Disk d = new Disk(4,1024);
				int file1 = d.allocate(20);
				int file2 = d.allocate(9);
				d.free(file1, 20);
				int file3 = d.allocate(100000);
				d.free(3, 10);
				System.out.println();
			} catch (Exception e) {
				System.err.println(e.toString());
			}		
			
		}
	
	
}
