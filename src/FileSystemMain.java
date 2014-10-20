
import java.util.ArrayList;
import FileSystem.*;

import BTree.BTree;

	public class FileSystemMain {	
	
		public static void main(String[] args) {
			try {
				FileSystem fs = new FileSystem(1024,4);
				File file1 = new File("said.jpg",33);
				fs.add(file1);
				
				System.out.println(fs.sizeOf("said.jpg"));
				File newFile = fs.find("said.jpg");
				fs.add(new File("qqq.qp",100));
				fs.add(new File("third.zip",492));
				fs.add(new File("q",1000000));
				
				
				System.out.println();
			} catch (Exception e) {
				System.err.println(e);
			}		
			
		}
	
	
}
