package Tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;




public class FileManager {
	
	
	String path ="";
	Boolean scanned = false;
	int filecount = 0;
	File[] files= null;
    List<String> dirs = new ArrayList<>();

	
	public FileManager() {

	
	
	}
	
	
	public File [] getFiles(String targetDir, Boolean recursive) {
		
		  /* get a list of all files in the target directory  (Optional recursive: and subDirectories) */
		  
		  this.path = targetDir;
		
		
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
	
	public void moveFiles(Path[] sources, Path[] destinations) throws IOException {
		
	    if (sources.length != destinations.length) {
	        throw new IllegalArgumentException("Source and destination arrays must have the same length.");
	    }

	    for (int i = 0; i < sources.length; i++) {
	        File newDir = new File(destinations[i].toString());

	        if (!newDir.exists()) {
	            newDir.mkdirs();
	        }

	        Files.move(sources[i], destinations[i], StandardCopyOption.REPLACE_EXISTING);
	    }
	}
	
	
	public void copyFiles(Path[] sources, Path[] destinations) throws IOException {
		
	    if (sources.length != destinations.length) {
	        throw new IllegalArgumentException("Source and destination arrays must have the same length.");
	    }

	    for (int i = 0; i < sources.length; i++) {
	        File newDir = new File(destinations[i].toString());

	        if (!newDir.exists()) {
	            newDir.mkdirs();
	        }

	        Files.copy(sources[i], destinations[i], StandardCopyOption.REPLACE_EXISTING);
	    }
	}
	
	

	
	public String  getExifTag(File file, String s) {
		
		try{ Metadata metadata = ImageMetadataReader.readMetadata(file.getAbsoluteFile());
		for (Directory directory : metadata.getDirectories()) {
		    for (Tag tag : directory.getTags()) {
		        
		    	if(tag.getTagName().equals(s)) return  tag.getDescription();

		    }//for each tag
		    
		
		   /* //error handling
		    if (directory.hasErrors()) {
		        for (String error : directory.getErrors()) {
		            System.err.format("ERROR: %s", error);
		        }//end for
		    }*/
		    
		}//for each dir
		
		
		
		} catch (Throwable e) {e.printStackTrace();} 
		
		return null;

	}//end method
	
	public double  getFileSize (File file) {
		
		try {
		Path path = Paths.get(file.getAbsolutePath());
		
	     long bytes = Files.size(path);
         //System.out.println(String.format("%,d bytes", bytes));
         
		return (double) bytes;
		
		}catch(Exception e) {e.printStackTrace();}
		
		return 0;
		
	}
		
	public boolean areRelated (String[] fbit1, String[] fbit2, String type) {

		//file bits have names in position 2
		
	   if(type.equals("name"))
		   return (fbit1[2].contains(fbit2[2]) || fbit2[2].contains(fbit1[2]) ) ?  true : false;
	   
	   if(type.equals("time")) {
		   
		   File f1 = new File(fbit1[0]);
		   File f2 = new File(fbit2[0]);
		   
		   String dateTaken1 = this.getExifTag(f1, "Date/Time Original");
		   String dateTaken2 = this.getExifTag(f2, "Date/Time Original");
		   
		   if(dateTaken1!=null && dateTaken2!=null) {
			   return (dateTaken1.equals(dateTaken2) || dateTaken2.equals(dateTaken1) ) ?  true : false;
		   }else {return false;}
		   
	   }else {
		   
		   System.out.println("Error: Please set type as name or time");
		   return false;
		      
		   
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
	
	public String[] splitPath (File child) {
		 
		String [] output = new String[4];
		 
		 if (child.isDirectory()){
			 return null;
		 }else {
			 //all
			 output[0] = child.getAbsolutePath();
			 //path
			 output[1] = child.getParentFile().getAbsolutePath()+"\\";
			 //extension
			 output[3] = child.getName().substring(child.getName().length()-3,child.getName().length());
			 //filename
			 output[2] = child.getName().substring(0,child.getName().length()-4);	
			 
		 }
		 
		 return output;

		}//end splitPath
	
	private String getFilepath(String filepath) {

	    File theFile = new File(filepath);
	    return theFile.getParent();
	    
	}
		
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
