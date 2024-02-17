package javaTools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Stream;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;




public class FileManager {


	String dirpath ="";
	Boolean scanned = false;
	int filecount = 0;
	File[] files= null;
    List<String> dirs = new ArrayList<>();


	public FileManager() {		
	
	
	}

	public FileManager(File [] files) {

		this.scanned = true;
		this.files = this.removeDirectories(files);


	}

	//LISTING /COUNTING

	public File [] getFiles(String targetDir, Boolean recursive) throws IOException{

		  /* get a list of all files in the target directory  (Optional recursive: and subDirectories) */

		  this.dirpath = targetDir;

		
		  //get list of files
		  File dir = new File(targetDir);

		  File[] fileList = dir.listFiles();
		  File[] checklist = fileList.clone();

		 if(recursive) {

			  //scan for subfolders and go recursive

			  if (checklist != null) {
			    for (File child : checklist) {
			    	if(child.isDirectory()) 
						 fileList = Stream.concat(Arrays.stream(fileList), Arrays.stream(this.getFiles(child.getAbsolutePath(), true))).toArray(File[]::new);
			    }//end for	
			  }//end if			  
			 
		 }//end if recursive
		 
		  this.scanned = true;
		  this.files = this.removeDirectories(fileList);
		  

		  
		  return this.files;
		
	}//end getFileList
	
	public List<String> getDirs(){
	
		if(!scanned) System.out.print("Error. Call getFiles() first");	
		return this.dirs;
	}
	
	public int countFiles() {
		
		
		if(!scanned) System.out.print("Error. Call getFiles() first");		
		return this.filecount;
		
		
	}
	
	
	// MOVING FILES
	
	public void summon_all(String destination, String method)   throws IOException{
		
		List<Path>  sources = new ArrayList<>();
		List<Path>  destinations = new ArrayList<>();
		
		for (int i= 0; i<this.files.length; i++) {
			
			sources.add(Paths.get(files[i].getAbsolutePath()));
			destinations.add(Paths.get(destination+"//"+ files[i].getName()));
			
		}
		
		if(method.contains("copy")) {
			
			this.copyFiles(sources, destinations);

			
		}else if (method.contains("move")) {
			
			this.moveFiles(sources, destinations);

		}
		
	}
	

	public void moveFiles(List<Path> sources, List<Path> destinations) throws IOException {
		
	    if (sources.size() != destinations.size()) {
	        throw new IllegalArgumentException("Source and destination arrays must have the same length.");
	    }

	    for (int i = 0; i < sources.size(); i++) {

	        File newDir = new File(destinations.get(i).toString());

	        if (!newDir.exists()) {
	            newDir.mkdirs();
	        }
	        
	        System.out.println("Moving "+sources.get(i)+"    to   "+ destinations.get(i));
	        Files.move(sources.get(i), destinations.get(i), StandardCopyOption.REPLACE_EXISTING);
	    }
	}
	
	public void copyFiles(List<Path> sources, List<Path> destinations) throws IOException {
		
	    if (sources.size() != destinations.size()) {
	        throw new IllegalArgumentException("Source and destination arrays must have the same length.");
	    }

	    for (int i = 0; i < sources.size(); i++) {

			File newDir = new File(destinations.get(i).toString());

	        if (!newDir.exists()) {
	            newDir.mkdirs();
	        }

			System.out.println("Coping.." + sources.get(i)+" >> " + destinations.get(i));
			Files.copy(sources.get(i), destinations.get(i), StandardCopyOption.REPLACE_EXISTING);
	    }
	}


	//UTILITIES
	
	
	private File[] removeDirectories(File[] fileList) {
		
		int dir_count,i;
		dir_count = 0;
		i=0;
		
		for (File child : fileList) if(child.isDirectory()) dir_count++;
	    		
		int file_count = fileList.length - dir_count;
		File[] newList = new File[file_count];
		
		
		for (File child : fileList) {
			 if(child.isDirectory()) {
				 this.dirs.add(child.getAbsolutePath());
			 }else {
				 newList[i] = child;
				 i++;
			 }
		}
		
		this.filecount = newList.length;
	    		
		return newList;

	}
	
	
	public String[] asArray(String fp) {
		
		File f = new File(fp);	
		return this.asArray(f);
	}
	
	public String[] asArray(File f) {
		
		Dictionary <String,String> file_dict = this.splitPath(f);
		
		String[] array = new String[5];
		
		array[0] =  file_dict.get("abspath").toString();
		array[1] =  file_dict.get("parent").toString();
		
		 if (!f.isDirectory()){
			array[2] =  file_dict.get("name").toString();
			array[3] = 	file_dict.get("extension").toString();
			array[4] = 	file_dict.get("filename").toString();
		 }
		return array;
	}
	
	public Dictionary <String,String> splitPath(Path p) {return this.splitPath(p.toFile()); }
	
	public Dictionary<String,String> splitPath (File f) {
		
		Dictionary <String,String> file_dict = new Hashtable<String,String>() ;
		file_dict.put("abspath", f.getAbsolutePath());
		file_dict.put("parent",  f.getParentFile().getAbsolutePath());

				 
		 if (!f.isDirectory()){

			 //filename breakdown
			 String filename = f.getName();
			 file_dict.put("filename", filename);
			 
			 int dot = filename.indexOf(".");
			 int underscore = filename.indexOf("_");
			 int end = filename.length();
			 
			 file_dict.put("name", filename.substring(0, dot));
			 if(underscore >0 ) file_dict.put("version:", filename.substring(0, underscore));
			 file_dict.put("extension", filename.substring(dot+1));

			 
		 }
		 
		 return file_dict;

		}//end splitPath
	

		
	public static void printLoadingBar(int percentage) {
	        System.out.print("Progress: [" + String.format("%3d", percentage) + "%] [");
	        int numBars = percentage / 10;
	        for (int i = 0; i < 10; i++) {
	            if (i < numBars) {
	                System.out.print("=");
	            } else {
	                System.out.print("");
	            }
	        }
	        System.out.print("\r");
	    }

	
	
		
}
