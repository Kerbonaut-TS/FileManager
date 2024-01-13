package Subroutines;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.source.tree.NewArrayTree;
import filters.StatFilter;
import filters.Tile;
import com.babelcoding.*;
import javaTools.FileManager;

public class ImageProcessing {
	
	
    public Boolean unit_test(String[] args) {

    	System.out.println(" Executing method on file "+args[0]);
        for (int i=1; i<args.length; i++) {
            System.out.println(" Custom argument #"+i + " : "+args[i]);
        }

        return true;
    	
    }
    

}
