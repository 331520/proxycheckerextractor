/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melonsoft.proxycheckerextractor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author ArbuzovA
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        logger.debug("");
        logger.debug("Start");
        Integer webdriverwait = 10;
        HashMap<String, String> proxyHM = new HashMap<String, String>();

        //classes
        SupportFactory sf = new SupportFactory();
        NewWebDriver newWebDriver = new NewWebDriver();

        //create new driver
        WebDriver driver = newWebDriver.driver();
        WebDriverWait wait = new WebDriverWait(driver, webdriverwait);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));

        driver.get("https://checkerproxy.net/archive/"+dtf.format(now));

        //are list loaded to applet?
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[2]/div/div[1]/div/div[1]/div[2]/div/p/span[1]")));

        for (int i = 0; i < 100; i++) {
            try {
                String proxyCounter = driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div[1]/div/div[1]/div[2]/div/p/span[1]")).getText();
                Thread.sleep(2000);
                logger.debug(proxyCounter + " proxies has been finded");
                if (!proxyCounter.equals("0")) {
                    break;
                }
            } catch (Exception ex) {
                System.out.println("error define proxy amount");
            }
        }

        String resultTable = driver.getPageSource();
        //String resultTable = driver.findElement(By.id("resultTable")).getAttribute("innerHTML");

        try {
            //Document doc = Jsoup.parse(resultTable);  
            Document doc = Jsoup.parse(resultTable);
            Element table = doc.select("table[id=resultTable]").first();
            Elements rows = table.select("tr");

            for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                Element row = rows.get(i);
                Elements cols = row.select("td");
                System.out.println("");
                System.out.println("");
                /*
                System.out.println("" + cols.get(0).text());
                System.out.println("" + cols.get(1).text());
                System.out.println("" + cols.get(2).text());
                System.out.println("" + cols.get(3).text());
                 */

                proxyHM.put("ip", cols.get(0).text());
                proxyHM.put("country", cols.get(1).text());
                proxyHM.put("type", cols.get(2).text());
                proxyHM.put("ip_source", "https://checkerproxy.net");
                sf.CreateProfile(proxyHM);
            }

            System.out.println("stop");

        } catch (Exception e) {
            System.out.println("error get jsoup document : " + e.getLocalizedMessage());
        }

        driver.close();
        driver.quit();

        /*
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://www.vogella.com");
        //HttpGet request = new HttpGet("https://checkerproxy.net/archive/2020-02-11");
        HttpResponse response = client.execute(request);

        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent()));

        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println("" + line);
            
        }
        
         */
    }
}
