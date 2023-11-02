package Subroutines;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

import Tools.FileManager;
import filters.StatFilter;

public class CompareImages {
	
	
    public Boolean  summon_same_stats(String[] args) throws IOException  {
    	
    	
      String target = "C:\\Users\\Riccardo\\Desktop\\unclassified\\3444.jpg";
      
      StatFilter f1= new StatFilter();
      StatFilter f2= new StatFilter();
      FileManager fm2;
      
      ArrayList<File> transfer_list = new ArrayList<File> ();
      
      
      f1.setSource(args[0]);
      f2.setSource(target);
      
      Dictionary candidate_dict = f1.getTile(0).getStats();
      Dictionary target_dict = f2.getTile(0).getStats();
      
      Boolean found = compare_dict(target_dict, candidate_dict, 3);
      
      if(found){
          transfer_list.add(new File(args[0]));   
      }    
  	
  	
      File[] fileArray = transfer_list.toArray(new File[transfer_list.size()]);
      fm2 = new FileManager(fileArray);
      
      fm2.summon("C:\\Users\\Riccardo\\Desktop\\testfiles\\summoned", "copy");
  	
  	return true;
  	
  }


 // find a more efficient way to loop

    public double round_doubles(double d, int decimals){
            BigDecimal bd = new BigDecimal(d);        
            bd = bd.setScale(decimals, RoundingMode.HALF_UP); // RoundingMode.HALF_UP for normal rounding
            return bd.doubleValue();           

    }

    public Boolean compare_dict(Dictionary dict1, Dictionary dict2, int decimals){
         
    	if (dict1.size() != dict2.size()) {
                System.out.println("Dictionaries have different sizes");
                return false; 
            }//end if
        
         Enumeration<String> keys = dict1.keys();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            
            //(!dict2.containsKey(key)){
            //  System.out.println("Dictionaries have different content");
            //  return false; 
            ///end if
        
            //decimal rounding 
            double value1 =  (double) dict1.get(key)*1.0; 
            double value2 =  (double) dict2.get(key)*1.0; 
            double rounded1 = round_doubles(value1, decimals);
            double rounded2 = round_doubles(value2, decimals);
            
            Boolean identical_stats = (rounded1 == rounded2);
            if (!identical_stats) {return false;}
            else{System.out.println("Same "+key);}
            
        }//end while


        return true;
    }










































}




