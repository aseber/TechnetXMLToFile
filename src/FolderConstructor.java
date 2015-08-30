import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FolderConstructor {

	static public File createFolder(String folderName) {
			
		File file = new File(folderName);
		file.mkdirs();
		return file;
		
	}
	
	static public File writeToFile(String fileName, String content) {
		
		File file = new File(fileName);
		
		try {
			
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(content);
			output.flush();
			output.close();
			
		}
		
		catch(IOException e) {
			
			e.printStackTrace();
			
		}
			
			return file;
		
	}
	
}
