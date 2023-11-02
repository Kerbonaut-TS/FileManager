package Subroutines;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import filters.StatFilter;

public class CompareImages {
	
	
	
	
	
    public Boolean  compareStats(String[] args) throws IOException  {
    	
    	
  	  //#####  REPLACE HERE THE PREPROCESSING SCRIPT   (EDA output)
      
      StatFilter f1= new StatFilter();
      StatFilter f2= new StatFilter();


      f1.setSource(args[0]);

      BufferedImage img = f1.applyOperation("log");
      f2.setImage(img);
      f2.createTiles(7,7);
      int[] sorted = f2.sortTilesBy("mean", false);
      int[] top_six_tiles = Arrays.copyOfRange(sorted, 0, 6);

      String outpath = args[1] +"\\tiles\\"+args[2]+".png";
      f2.saveTiles(outpath, top_six_tiles);
  	
  	
  	
  	
  	return true;
  	
  }

}
