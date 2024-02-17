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



    public Boolean  exportTiles(String[] args) throws IOException  {


        //#####  REPLACE HERE THE PREPROCESSING SCRIPT   (EDA output)

        StatFilter f1= new StatFilter();

        String outpath = args[1] +File.separator+"tiles"+File.separator+args[2]+".png";

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

    public Boolean add_frame(String[] args) throws IOException {


        //import
        Layer frame = new Layer();
        Layer pic = new Layer();
        System.out.println("reading file: "+args[0]);
        System.out.println("reading frame: "+args[1]);
        frame.import_image(args[1]);
        pic.import_image(args[0]);

        //resize
        int new_height = 1230;
        int new_width = (int) Math.floor((new_height / (double) pic.getHeight()) * pic.getWidth());
        System.out.println("resizing " + pic.getHeight() + "x" + pic.getWidth() + " into " + new_height + "x" + new_width);
        Tile tile = pic.resize(new_height, new_width);

        //add overlay
        frame.overlay(tile, 131, 61);

        //save
        File file = new File(args[0]);
        String name = file.getName();
        frame.savetoFile(file.getParent() + "//" + name + "_solid.jpg", "jpg");
        return true;
    }
    

}
