

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import subroutines.ImageProcessing;
import Tools.FileManager;
import Tools.Iterator;

public class Main {

	public static void main(String[] args) throws IOException, ParseException, Exception {
		
		FileManager fm = new FileManager();

		Iterator it = new Iterator();
		
		
		it.loop_on_files("ImageProcessing", "exportTiles", "C:\\Users\\Riccardo\\Desktop\\imgLab\\DoorotAI\\images\\dark");
		
		
		
		        
		        
	    }//end MAIN
	}


		
