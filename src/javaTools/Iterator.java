package javaTools;

import com.sun.source.tree.NewArrayTree;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Dictionary;

public class Iterator {
	

    FileManager fm;
    String method;
    String subroutine;

    public void set_method(String method){

        this.method = method;

    }

    public void set_subroutine(String subroutine){

        this.subroutine = subroutine;

    }

    private String[] add_argument(String new_item, String[] args){

        String[] all_args = new String[args.length+1];
        all_args[0] = new_item;

        for(int i=0; i<args.length; i++) all_args[i+1] = args[i];

        return all_args;

    }



    public void loop_on_files(String directory, String[] args) throws IOException,Exception{
    	
    	fm = new FileManager();
        File [] file_list = fm.getFiles(directory, false);
        int countFiles = 0;

        for (File f : file_list) {

             String[] method_args = this.add_argument(f.getAbsolutePath(), args);

            //run this method
        	this.run_method(this.subroutine, this.method, method_args);

            //update progressbar
            countFiles++;
            int progress = Math.round(((float) countFiles/(float) file_list.length )*100);
            fm.printLoadingBar(progress);

        }//end for each file


        System.out.println("Processed "+countFiles+" files in folder "+args[0]);

    }
	
	
	
	private Object run_method(String classname, String methodname, String[] args ) throws Exception {
		
		
            // The class in which the method is defined
            Class<?> myClass = Class.forName("Subroutines."+classname);
            Method method = myClass.getMethod(methodname,  String[].class);
            

            // Create an instance of the class (if needed)
            Object instance = myClass.getDeclaredConstructor().newInstance();
            // Invoke the method on the instance
            Object result = method.invoke(instance,  new Object[] {args});

            return result;
		
	} 
	

	
	
	
	
	
	
	
	
	
	
	
	

}
