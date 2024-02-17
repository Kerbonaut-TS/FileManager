package Subroutines;
import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import filters.StatFilter;
import javaTools.FileManager;
import javaTools.SmartFile;

import javax.swing.*;

public class CompareImages {


    public Boolean unit_test(String[] args) {

        System.out.println(" Executing method on file " + args[0]);
        for (int i = 1; i < args.length; i++) {
            System.out.println(" Custom argument #" + i + " : " + args[i]);
        }

        return true;

    }

    public Boolean summon_related(String args[]) throws IOException {

            /*METHOD ARGS:

        USER ARGS:
        [1]: class/subroutine
        [2]: method
        [3]: iteration directory

                LOCAL ARGS
                0: file from iteration directory
        [4]     1: summon directory
        [5]     2: time or name
        [6]     3: move or copy
         */

        FileManager fm = new FileManager();

        // get method arguments
        SmartFile candidate = new SmartFile(args[0]);  //move or copy
        File summon_dir = new File(args[1]);
        String option1 = args[2];  //time or name
        String option2 = args[3];  //move or copy

        File[] summoners = fm.getFiles(summon_dir.getAbsolutePath(), false);

        List<Path> destinations = new ArrayList<>();
        List<Path> sources = new ArrayList<>();


        for (File s : summoners) {

            SmartFile summoner = new SmartFile(s.getAbsoluteFile().toString());

            if (summoner.isRelatedTo(candidate, option1)) {

                sources.add(Path.of(candidate.getAbsolutePath()));
                destinations.add(Path.of(summoner.getParent() + File.separator + candidate.getFilename()));
            }
        }
        if (option2.contains("move")) {
            fm.moveFiles(sources, destinations);
        } else if (option2.contains("copy")) {
            fm.copyFiles(sources, destinations);
        }
        return true;
    }


    public Boolean summon_same_stats(String[] args) throws IOException {
        /*METHOD ARGS:

        USER ARGS:
        [1]: class/subroutine
        [2]: method
        [3]: iteration directory

                LOCAL ARGS
                0: file from iteration directory
        [4]     1: summon directory
        [5]     2: threshold
        [6]     3: move or copy
         */

        // get method arguments
        SmartFile candidate = new SmartFile(args[0]);  //move or copy
        File summon_dir = new File(args[1]);
        int threshold = Integer.parseInt(args[2]);
        String option = args[3];

        if (candidate.isImage()) {

            FileManager fm = new FileManager();
            File[] summoners = fm.getFiles(summon_dir.getAbsolutePath(), false);
            List<Path> destinations = new ArrayList<>();
            List<Path> sources = new ArrayList<>();

            for (File s : summoners) {

                SmartFile smartFile = new SmartFile(s.getAbsolutePath());
                if (smartFile.isImage()) {
                    SmartFile summoner = new SmartFile(s.getAbsoluteFile().toString());

                    //SUMMON LOGIC
                    StatFilter f1 = new StatFilter();
                    StatFilter f2 = new StatFilter();
                    f1.setSource(candidate.getAbsolutePath());
                    f2.setSource(s.getAbsolutePath());

                    Dictionary candidate_dict = f1.getTile(0).getStats();
                    Dictionary target_dict = f2.getTile(0).getStats();
                    System.out.println(candidate_dict);

                    Boolean similar_stats = compare_dict(target_dict, candidate_dict, threshold);

                    if (similar_stats) {
                        sources.add(Path.of(candidate.getAbsolutePath()));
                        destinations.add(Path.of(summoner.getParent() + File.separator + candidate.getFilename()));
                    }

                    // TRANSFER FILES
                    if (option.contains("move")) {
                        fm.moveFiles(sources, destinations);
                    } else if (option.contains("copy")) {
                        fm.copyFiles(sources, destinations);
                    }
                }//if is image
            }//end for
        }//end if

        return true;

    }//end summon same stats


    public double round_doubles(double d, int decimals) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(decimals, RoundingMode.HALF_UP); // RoundingMode.HALF_UP for normal rounding
        return bd.doubleValue();

    }

    public Boolean compare_dict(Dictionary dict1, Dictionary dict2, int decimals) {

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
            double value1 = (double) dict1.get(key) * 1.0;
            double value2 = (double) dict2.get(key) * 1.0;
            double rounded1 = round_doubles(value1, decimals);
            double rounded2 = round_doubles(value2, decimals);

            Boolean identical_stats = (rounded1 == rounded2);
            if (!identical_stats) {
                return false;
            } else {
                System.out.println("Same " + key);
            }

        }//end while


        return true;
    }

}






