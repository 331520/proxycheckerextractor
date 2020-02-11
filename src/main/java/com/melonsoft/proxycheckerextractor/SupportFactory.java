/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melonsoft.proxycheckerextractor;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.apache.log4j.Logger;

/**
 *
 * @author ArbuzovA
 * sone additions classes
 */
public class SupportFactory {

    final static Logger logger = Logger.getLogger(SupportFactory.class);
    
    //Job list to check from file
    public List<String> JobList(String jobList) throws FileNotFoundException, IOException {
        logger.debug("Get job list");
        BufferedReader abc = new BufferedReader(new FileReader(jobList));
        List<String> lines = new ArrayList<>();
        String line;
        //Integer counter = 0;
        while ((line = abc.readLine()) != null) {
            logger.debug(line);
            //counter++;
            lines.add(line);
        }
        System.out.println(lines.size() + " : jobs has been finded");
        logger.debug(lines.size() + " : jobs has been finded");
        return lines;
    }

    //save table with data to file
    public void saveTable(String table) {
        logger.debug("Try to save table to file");
        // Now
        Date date = new Date(System.currentTimeMillis());

        // Conversion
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss");
        sdf.setTimeZone(TimeZone.getTimeZone("EEST"));
        String textDateTime = "monitoring_table_" + sdf.format(date) + ".html";

        try {
            File directory = new File("log");
            if (!directory.exists()) {
                directory.mkdir();
            }

            FileWriter fw = new FileWriter(directory + "/" + textDateTime);
            fw.write(table);
            fw.close();
            System.out.println("Table has been saved to file " + textDateTime);
            logger.debug("Table has been saved to file " + textDateTime);
        } catch (Exception e) {
            logger.debug("Error write table to : " + e.getLocalizedMessage());
            System.out.println("Error write table to : " + e.getLocalizedMessage());
        }
    }

    //get the difference between two dates
    public String getTimeFrom(String strDate) throws ParseException {
        Date asd = new SimpleDateFormat("M/d/yyyy h:mm:ss a").parse(strDate);
        Date now = new Date();
        /*
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a");
                    LocalDateTime asdLDT = LocalDateTime.parse(prevRepeatActualStartDate, formatter);
                    LocalDateTime nowLDT = LocalDateTime.now();
                    Duration duration = Duration.between(nowLDT, asdLDT);
                    long diff = Math.abs(duration.toMinutes());
                    System.out.println("" + diff);
         */
        long diff = now.getTime() - asd.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String timeFromLast = diffDays + " days. "+  diffHours + " hrs. " + diffMinutes + " min.";
        return timeFromLast;
    }
}
