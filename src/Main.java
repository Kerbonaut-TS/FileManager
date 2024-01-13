

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import Subroutines.ImageProcessing;
import javaTools.FileManager;
import javaTools.Iterator;

public class Main {

	public static void main(String[] args) throws IOException, ParseException, Exception {


		String[] arguments = {"hello", "world!"};

		Iterator it = new Iterator();
		it.set_subroutine("ImageProcessing");
		it.set_method("unit_test");

		it.loop_on_files("\\path\\to\\folder", arguments);
		
		        
	    }//end MAIN
	}


		
