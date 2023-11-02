package Tools;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Dictionary;

public class Iterator {
	

    FileManager fm;
    File[] file_list;
    String[] fileinfo  = null;


    public void loop_on_files(String subroutine, String method, String dirpath) throws IOException,Exception{
    	
    	fm = new FileManager();
        file_list = fm.getFiles(dirpath, false);
        int countFiles = 0;

        for (File f : file_list) {
            // 0- fullpath , 1-folderpath, 2-filename, 3-extension
            fileinfo = fm.asArray(f);
        	
            //run this method
        	this.run_method(subroutine, method, fileinfo);

            countFiles++;

            int progress = Math.round(((float) countFiles/(float) file_list.length )*100);
            fm.printLoadingBar(progress);

        }//end for each file

        System.out.println("Processed "+countFiles+" files in folder "+dirpath);

    }
	
	
	
	private Object run_method(String classname, String methodname, String[] args ) throws Exception {
		
		
            // The class in which the method is defined
            Class<?> myClass = Class.forName("S uubroutines."+classname);
            Method method = myClass.getMethod(methodname,  String[].class);
            

            // Create an instance of the class (if needed)
            Object instance = myClass.getDeclaredConstructor().newInstance();
            // Invoke the method on the instance
            Object result = method.invoke(instance,  new Object[] {args});

            return result;
		
	} 
	

	
	
	
	
	
	
	
	
	
	
	
	

}
