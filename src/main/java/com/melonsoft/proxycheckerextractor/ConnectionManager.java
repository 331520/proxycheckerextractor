/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*
CREATE TABLE `proxy` (
	`ip` VARCHAR(25) NOT NULL,
	`proxy_type` VARCHAR(6) NULL DEFAULT NULL,
	`dtime_check` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	`country` VARCHAR(50) NOT NULL,
	`dtime_add` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`ip_source` VARCHAR(50) NULL DEFAULT NULL,
	UNIQUE INDEX `ip` (`ip`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
*/
package com.melonsoft.proxycheckerextractor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author captYossarian
 */
public class ConnectionManager {

     
    //private static String url = "jdbc:mysql://localhost:3306/teleswbot?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static String url = "";
    private static String username = "db_user";
    private static String password = "tegeran43";
    private static Connection con;

    public static Connection getConnection() {
        if (System.getProperty("os.name").contains("Windows")) {
            //development
            url = "jdbc:mysql://localhost:3306/teleswbot_dev?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        } else {
            //production
            url = "jdbc:mysql://localhost:3306/teleswbot?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";        }
                try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection." + ex);
            }
        return con;
    }
}