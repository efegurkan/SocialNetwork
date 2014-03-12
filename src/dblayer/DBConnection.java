/*
 * Copyright (C) 2014 Efe Gürkan YALAMAN
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dblayer;

/**
 *
 * @author efegurkan
 */

import core.Member;
import core.Status;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DBConnection {
    private static DBConnection instance = null;
    Connection con;
    //TODO fix
    
    public void connect() throws ClassNotFoundException, SQLException{
        try {
                InputStream input = this.getClass().getResourceAsStream("/dblayer/config.xml");
                XPath xpath = XPathFactory.newInstance().newXPath();
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(input));
                String jdbcUrl = (String) xpath.compile("//config//jdbc//url").evaluate(doc, XPathConstants.STRING );
                String jdbcDriver = (String) xpath.compile("//config//jdbc//driver").evaluate(doc, XPathConstants.STRING);
                String jdbcUsername = (String) xpath.compile("//config//jdbc//username").evaluate(doc, XPathConstants.STRING);
                String jdbcPassword = (String) xpath.compile("//config//jdbc//password").evaluate(doc, XPathConstants.STRING);
                
                Class.forName(jdbcDriver);
                con = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
                
        } catch (XPathExpressionException | SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public boolean register(String name, String email, String password) throws SQLException{
        try (CallableStatement statement = con.prepareCall("{call userregister(?, ?, ?)}");) {
            statement.setObject(1, name);
            statement.setObject(2, email);
            statement.setObject(3, password);
            return statement.execute();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public Member login(String email, String password) throws SQLException{
        //get members id from db
        try(CallableStatement statement = con.prepareCall("{call userlogin(?,?)}");){
        statement.setObject(1,email);
        statement.setObject(2, password);
        statement.execute();
        ResultSet rs = statement.getResultSet();
        while(rs.next()){
            return this.getMemberObject(rs.getLong(1));
        }
        }catch(Exception ex){
        System.out.println(ex.getMessage());
        }
        return null;
    }
    
    public Member getMemberObject(long memberId){
        Member ret = null;
        //get member data and create object with id
        String storedFuncStr = "{ call getuserdatafromid(?) }";
        try(CallableStatement cs = con.prepareCall(storedFuncStr);){
            cs.setLong(1, memberId);
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while (rs.next()) {
                ret = Member.createMember(rs.getLong("member_id"), rs.getString("member_name"), rs.getString("member_password"), rs.getString("member_email"));
            }
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        return ret;
        
    }
    
    public ArrayList<Status> getMembersFeed(long memberId){
        ArrayList<Status> ret = new ArrayList<>();
        
        String storedFuncStr = "{ call getallstatusposts(?) }";
        try(CallableStatement cs = con.prepareCall(storedFuncStr);){
            cs.setLong(1, memberId);
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while(rs.next()){
                Status stat = Status.createStatus(rs.getLong("sender_id"),
                        rs.getLong("status_id"),
                        rs.getString("status_text"),
                        rs.getTimestamp("status_date").getTime());
                ret.add(stat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ret;
    }
    
    //Singleton
    protected DBConnection(){}//Block inheritence
    public static DBConnection getInstance(){
        if(instance == null)
            instance = new DBConnection();
        return instance;
    }
}
