

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import Subroutines.ImageProcessing;
import Tools.FileManager;
import Tools.Iterator;

public class Main {

	public static void main(String[] args) throws IOException, ParseException, Exception {
		

		Iterator it = new Iterator();
		
		
		it.loop_on_files("ImageProcessing", "exportTiles", "C:\\Users\\Riccardo\\Desktop\\imgLab\\DoorotAI\\images\\dark");
		
		FileManager fm = new FileManager();
		String[] fp_array = fm.asArray( "C:\\Users\\Riccardo\\Desktop\\imgLab\\DoorotAI\\images\\dark");
		
		
		        
		        
	    }//end MAIN
	}


		
