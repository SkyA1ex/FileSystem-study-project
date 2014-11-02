import java.util.ArrayList;
import java.util.*;

import FileSystem.*;

import BTree.BTree;

public class FileSystemMain {

	public static void main(String[] args) {
		try {

			FileSystem fs = new FileSystem(1024, 4);
			File file1 = new File("said.jpg", 33);
			fs.add(file1);

			System.out.println(fs.sizeOf("said.jpg"));
			File newFile = fs.find("said.jpg");
			fs.add(new File("noizemc.mp3", 100));
			fs.add(new File("private.zip", 10));
			fs.add(new File("private2.zip", 12));
			fs.add(new File("private3.zip", 11));
			fs.add(new File("private4.zip", 44));
			fs.add(new File("private5.zip", 30));
			fs.add(new File("private6.zip", 20));
			File found = fs.find("private4.zip");
			fs.remove("private3.zip");

			/*
			 * ArrayList<Integer> list = new ArrayList<Integer>();
			 * list.add(11);list.add(1);list.add(5);list.add(9);
			 * Collections.sort(list); int ii = Collections.binarySearch(list,
			 * 100); ii*=-1; ii-=1; list.add(ii,100); System.out.println(ii);
			 */
			System.out.println("said.jpg");

		} catch (Exception e) {
			System.err.println(e);
		}

	}

}
