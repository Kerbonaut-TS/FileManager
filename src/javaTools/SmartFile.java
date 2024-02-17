package javaTools;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.util.Locale;

public class SmartFile extends File {

    public SmartFile(String pathname) {
        super(pathname);
    }

    @Override
    public String getName() {
        String originalName = super.getName();
        int dotIndex = originalName.lastIndexOf('.');
        return (dotIndex == -1) ? originalName : originalName.substring(0, dotIndex);
    }

    public String getFilename() {
        return super.getName();
    }

    public String getExtension() {
        String originalName = super.getName();
        int dotIndex = originalName.lastIndexOf('.');
        return (dotIndex == -1) ? originalName : originalName.substring(dotIndex, originalName.length());
    }

    public boolean isImage(){

        String ext = this.getExtension();
        boolean is_image = ext.toLowerCase().contains(".jpg") || ext.toLowerCase().contains(".RAF") || ext.contains(".jpeg")|| ext.contains(".png");

        return is_image;
    }



    public boolean isRelatedTo (SmartFile file2, String type) {

        //file bits have names in position 2

        if (type.equals("name"))
            return (this.getName().contains(file2.getName())) || (file2.getName().contains(this.getName())) ? true : false;

        if (type.equals("time")) {

            String dateTaken1 = this.getExifTag("Date/Time Original");
            String dateTaken2 = file2.getExifTag("Date/Time Original");

            if (dateTaken1 != null && dateTaken2 != null) {
                return (dateTaken1.equals(dateTaken2) || dateTaken2.equals(dateTaken1)) ? true : false;
            } else {
                return false;
            }

        } else {

            System.out.println("Error: Please set type as name or time");
            return false;


        }
    }


        public String getExifTag (String select_tag) {

            try{ Metadata metadata = ImageMetadataReader.readMetadata(this.getAbsoluteFile());
                for (Directory directory : metadata.getDirectories()) {
                    for (Tag tag : directory.getTags()) {

                        if(tag.getTagName().equals(select_tag)) return  tag.getDescription();

                    }//for each tag

                }//for each dir



            } catch (Throwable e) {e.printStackTrace();}

            return null;

        }//end method



    }
