

import java.io.IOException;
import java.text.ParseException;
import javaTools.Iterator;

public class Main {

	public static void main(String[] args) throws IOException, ParseException, Exception {

		//args[0] reserved to Subroutine class_name
		//args[1] reserved to method_name
		//args[2] reserved to directory
		Iterator it = new Iterator();
		Boolean recursive = false;

		it.set_subroutine(args[0]);
		it.set_method(args[1]);
		it.set_directory(args[2]);

		//method custom arguments
		String[] custom_args = new String[args.length];
		for(int i =3; i<args.length; i++) custom_args[i] = args[i];


		it.loop_on_files(custom_args, recursive);
		
		        
	    }//end MAIN
	}


		
