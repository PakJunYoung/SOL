
import java.sql.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonghoon
 */
public class User {
    private Connection conn;
    private ResultSet rs = null;
    private Statement stmt = null;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "wh45wh45";
    private static final String URL = "jdbc:mysql://52.79.52.106/javaProject?serverTimezone=Asia/Seoul";

    private String id = "";
    private String pw = "";
    private String name = "";
    private String email = "";
    private String birth = "";
    private String phone = "";
    private String gender = "";
    Calendar cal = Calendar.getInstance();
    
    public User(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
 
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("클래스 적재 실패!!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("연결 실패!!");
        }
    }
    
    public User(String id, String pw, String name, String email, String birth, String phone, String gender){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
 
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("클래스 적재 실패!!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("연결 실패!!");
        }

        this.id = id;
        this.pw = pw;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.phone = phone;
        this.gender = gender;
    }
    
    public String Login(String id, String pw){
        String sql = "select * from user";
        
        try {
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                if(id.equals(rs.getString(1)) && pw.equals(rs.getString(2))){
                    return rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String getName(String id){
        String sql = "select name from user where id = '" + id + "'";
        try {
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void sqlInsert(){
        String sql = "insert into user values(?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pw);
            pstmt.setString(3, name);
            pstmt.setString(4, email);
            pstmt.setString(5, birth);
            pstmt.setString(6, phone);
            pstmt.setString(7, gender);
            pstmt.setInt(8, 0);
            pstmt.executeUpdate();
            
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    public boolean idCheck(String id){
        String sql = "select * from user;";
        try{
            rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                if(id.equals(rs.getString(1))){
                    return false;
                }
            }
            return true;
        }catch(Exception e){
            System.out.println("ERROR!");
        }
        return false;
    }
    
    public String searchID(String name, String email){
        String sql = "select * from user;";
        try {
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                if(name.equals(rs.getString(3)) && email.equals(rs.getString(4))){
                    return rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean searchPW(String name, String id, String email){
        String sql = "select * from user;";
        try {
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                if(name.equals(rs.getString(3)) && id.equals(rs.getString(1)) && email.equals(rs.getString(4))){
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void changePW(String id, String pw){
        String sql = "update user set password = '";
        sql += pw + "'";
        sql += " where id = '";
        sql += id + "'";
        try {
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "비밀번호를 성공적으로 변경하였습니다.");
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
