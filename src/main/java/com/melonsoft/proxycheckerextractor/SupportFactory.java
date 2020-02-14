/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melonsoft.proxycheckerextractor;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 *
 * @author ArbuzovA
 * sone additions classes
 */
public class SupportFactory {

    final static Logger logger = Logger.getLogger(SupportFactory.class);
    
     void CreateProfile(HashMap<String, String> hm) throws SQLException {
        logger.debug("CreateProfile");
        Connection con = getConnection();
        Statement stmt = null;

        try {
            con.setAutoCommit(true);
            stmt = con.createStatement();
            String sql = "INSERT INTO proxy (ip,proxy_type,country,ip_source) VALUES ('" + hm.get("ip") + "','" + hm.get("type") + "','" + hm.get("country") + "','" + hm.get("ip_source")+ "');";
            logger.debug("sql : " + sql);
            //System.out.println("sql statement : " + sql);
            stmt.executeUpdate(sql);
            //System.out.println("Insert visited link done successfully");
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Operation insert stop with error: " + e.getClass().getName() + ": " + e.getMessage());
            logger.debug("Operation insert stop with error: " + e.getClass().getName() + ": " + e.getMessage());
             con.close();
        }
    }
     
     
     public static Connection getConnection() {
         
        String url = "";
        String username = "db_user";
        String password = "tegeran43";
        Connection con = null;
         
        if (System.getProperty("os.name").contains("Windows")) {
            //development
            url = "jdbc:mysql://localhost:3306/proxybase?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        } else {
            //production
            url = "jdbc:mysql://localhost:3306/proxybase?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";        }
    
                try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection." + ex);
            }
         
        return con;
    }

}
