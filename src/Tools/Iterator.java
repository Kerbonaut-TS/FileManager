package Tools;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class Iterator {
	

    FileManager fm = new FileManager();
    File[] file_list;
    String[] fileinfo  = new String [3];


    public void loop_on_files(String subroutine, String method, String input_folder) throws IOException,Exception{
    	

        file_list = fm.getFiles(input_folder, false);
        int countFiles = 0;

        for (File f : file_list) {
            // 0- fullpath , 1-folderpath, 2-filename, 3-extension
            fileinfo = fm.splitPath(f);
        	
            //run this method
        	this.run_method(subroutine, method, fileinfo);

            countFiles++;

            int progress = Math.round(((float) countFiles/(float) file_list.length )*100);
            fm.printLoadingBar(progress);

        }//end for each file

        System.out.println("Processed "+countFiles+" files in folder "+input_folder);

    }
	
	
	
	private Object run_method(String classname, String methodname, String[] args ) throws Exception {
		
		
            // The class in which the method is defined
            Class<?> myClass = Class.forName("subroutines."+classname);
            Method method = myClass.getMethod(methodname,  String[].class);
            

            // Create an instance of the class (if needed)
            Object instance = myClass.getDeclaredConstructor().newInstance();
            // Invoke the method on the instance
            Object result = method.invoke(instance,  new Object[] {filepath});

            return result;
		
	} 
	

	
	
	
	
	
	
	
	
	
	
	
	

}
