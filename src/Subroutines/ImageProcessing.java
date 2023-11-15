package Subroutines;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import filters.StatFilter;

public class ImageProcessing {
	
	
    public Boolean unit_test(String filepath) {
        
    	
    	System.out.println(filepath);
    	
    	return true;
    	
    }
    
    
    
    public Boolean  exportTiles(String[] args) throws IOException  {
    	
    	
    	  //#####  REPLACE HERE THE PREPROCESSING SCRIPT   (EDA output)
        
        StatFilter f1= new StatFilter();

        String outpath = args[1] +"\\tiles\\"+args[2]+".png";
        
        File file = new File(outpath);
        
        if (!file.exists()) {
	        
	        f1.setSource(args[0]);
	
	        BufferedImage cropped_img = f1.findTile("mean", "max", "175x175", 30);
	        f1.setImage(cropped_img);
	        f1.applyOperation("log");
	        f1.showImage();
	        f1.saveTiles(outpath);
	
        }
    	
    	return true;
    	
    }
	

}
