/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melonsoft.proxycheckerextractor;

import java.io.IOException;
import java.util.ArrayList; 
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

        //classes
        SupportFactory sf = new SupportFactory();
        NewWebDriver newWebDriver = new NewWebDriver();

        //create new driver
        WebDriver driver = newWebDriver.driver();
        WebDriverWait wait = new WebDriverWait(driver, webdriverwait);

        driver.get("https://checkerproxy.net/archive/2020-02-11");

        //are list loaded to applet?
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[2]/div/div[1]/div/div[1]/div[2]/div/p/span[1]")));

        for (int i = 0; i < 10; i++) {
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

        String resultTable = "<!DOCTYPE html><html><head><title>First parse</title></head><body>" + driver.findElement(By.id("resultTable")).getAttribute("innerHTML")+ "</body></html>";
        //String resultTable = driver.findElement(By.id("resultTable")).getAttribute("innerHTML");
        
        
        try {
        //Document doc = Jsoup.parse(resultTable);  
        Document doc = Jsoup.connect("https://checkerproxy.net/archive/2020-02-11").get();
        Elements content = doc.body().getElementsByTag("body");
        Element table = doc.select("tbody").get(1); //select the first table.
        Element table1 = doc.body().getElementsByTag("tr").first(); //select the first table.
        Elements rows = table.select("tr");
            for (Element row : rows) {
                System.out.println("" + row.text());
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
