package FileSystem.FileSystemException;

public class FileSystemException extends RuntimeException {
	private String msg;

	public FileSystemException(String errorMessage) {
		msg = errorMessage;
	}
	
	public String toString() { return msg; }
}
