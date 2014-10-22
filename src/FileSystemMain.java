
import java.util.ArrayList;
import FileSystem.*;

import BTree.BTree;

	public class FileSystemMain {	
	
		public static void main(String[] args) {
			try {
				
				FileSystem fs = new FileSystem(1024,2);
				File file1 = new File("said.jpg",33);
				fs.add(file1);
				
				System.out.println(fs.sizeOf("said.jpg"));
				File newFile = fs.find("said.jpg");
				fs.add(new File("noizemc.mp3",100));
				fs.add(new File("private.zip",492));
				fs.add(new File("private.zip", 10));
				
				System.out.println();
			} catch (Exception e) {
				System.err.println(e);
			}
			
			
		}
	
	
}
