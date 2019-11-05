import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Survey {
    private Connection conn;
    private ResultSet rs = null;
    private Statement stmt = null;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "wh45wh45";
    private static final String URL = "jdbc:mysql://52.79.52.106/javaProject?serverTimezone=Asia/Seoul";

    private String id = "";
    private int q1 = 0;
    private int q2 = 0;
    private int q3 = 0;
    private int q4 = 0;
    private int q5 = 0;
    private int q6 = 0;
    private int q7 = 0;
    private int q8 = 0;
    private int q9 = 0;
    // 생성자 리눅스 서버의 데이터베이스에 접속한다.
    public Survey(){
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
    
    // 생성자 리눅스 서버의 데이터베이스에 접속하고 질문변수들을 set 한다.
    public Survey(String id, int q1, int q2, int q3, int q4, int q5, int q6, int q7, int q8, int q9){
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
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
        this.q7 = q7;
        this.q8 = q8;
        this.q9 = q9;
    }
    
    // 설문에 참여했는지 확인해주는 메서드
    public boolean participationCheck(String id){
        int participation = 1;
        
        String sql = "select participation from user where id = '";
        sql += id + "'";
        try{
            rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                participation = rs.getInt(1);
            }
            
        }catch(Exception e){
            System.out.println("ERROR!");
        }
        
        if(participation == 1){
            return true;
        }else{
            return false;
        }
    }
    
    public void sqlInsert(){
        String sql = "insert into survey values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setInt(2, q1);
            pstmt.setInt(3, q2);
            pstmt.setInt(4, q3);
            pstmt.setInt(5, q4);
            pstmt.setInt(6, q5);
            pstmt.setInt(7, q6);
            pstmt.setInt(8, q7);
            pstmt.setInt(9, q8);
            pstmt.setInt(10, q9);
            pstmt.executeUpdate();
        
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        sql = "update user set participation = 1 where id = '";
        sql += id +"'";
        try {
            pstmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Survey.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
                if(pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        } 
    }
    
    // 문제 번호와 보기 번호, 성별을 입력받아 통계를 보여주는 메서드
    public int Numeric(int question, int answer, int gender){
        String sql = "select count(q" + Integer.toString(question) +") from survey natural join user";
        if(gender == 1){
            sql += " where gender = 'M'";
        }else if(gender == 2){
            sql += " where gender = 'W'";
        }
        int all = 0;
        int select = 0;
        try {
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                all = rs.getInt(1);
            }
            sql = "select count(q" + Integer.toString(question) +") from survey natural join user";
            if(gender == 1){
                sql += " where gender = 'M'";
                sql += " and q" + Integer.toString(question) + " = " + Integer.toString(answer);
            }else if(gender == 2){
                sql += " where gender = 'W'";
                sql += " and q" + Integer.toString(question) + " = " + Integer.toString(answer);
            }else{
                sql += " where q" + Integer.toString(question) + " = " + Integer.toString(answer);
            }
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                select = rs.getInt(1);
            }
            return (select*100)/all;
        } catch (SQLException ex) {
            Logger.getLogger(Survey.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
