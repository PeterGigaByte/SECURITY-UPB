/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.h2.Driver;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Arrays;



public class Database {
    
    final static class MyResult {
        private final boolean first;
        private final String second;
        
        public MyResult(boolean first, String second) {
            this.first = first;
            this.second = second;
        }
        public boolean getFirst() {
            return first;
        }
        public String getSecond() {
            return second;
        }
    }

    protected static MyResult addToH2(String name,String password,String salt) throws IOException, SQLException {
        DriverManager.registerDriver(new Driver());
        Connection conn = DriverManager.getConnection("jdbc:h2:"+"./Database/my","root","password");
        Statement st = conn.createStatement();

        int i = st.executeUpdate("INSERT INTO REGISTRATION(name,pass,salt) VALUES('"+name+"','"+password+"','"+salt+"')");
        System.out.println(i + " Record inserted");
        conn.close();
        return new MyResult(true, "");


    }
    protected static MyResult find(String name,String password) throws IOException, SQLException, NoSuchAlgorithmException {
        if(!exist(name)){
            return new MyResult(false,"");
        }
        DriverManager.registerDriver(new Driver());
        Connection conn = DriverManager.getConnection("jdbc:h2:"+"./Database/my","root","password");
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * FROM REGISTRATION WHERE name='"+name+"'");
        String stringSalt = null;
        String stringPasswordSalted = null;
        while (resultSet.next()){
            stringPasswordSalted = resultSet.getString(3);
            stringSalt = resultSet.getString(4);
        }
        if(password.length()==0){
            return new MyResult(true,"Zadajte heslo, nenehávajte prázdne políčko.");
        }
        byte[] salt = SaltHashing.stringToByte(stringSalt);
        byte[] passwordSalted = SaltHashing.stringToByte(stringPasswordSalted);
        byte[] newPasswordSalted = SaltHashing.getHashWithSalt(password,salt);
        if(Arrays.equals(passwordSalted, newPasswordSalted)){
            return new MyResult(true,"Úspešné prihlásenie.");
        }else{
            return new MyResult(true,"Nesprávne heslo.");
        }



    }
    
    protected static boolean exist(String name) throws IOException, SQLException {
        DriverManager.registerDriver(new Driver());
        Connection conn = DriverManager.getConnection("jdbc:h2:"+"./Database/my","root","password");
        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * FROM REGISTRATION WHERE name='"+name+"'");
        boolean exist = false;
        while(resultSet.next()){
            if(resultSet.getInt(1)!=0){
                exist=true;
            }
        }
        conn.close();
        return exist;
    }
    
}
