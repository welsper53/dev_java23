package week7.d230112;

import java.sql.CallableStatement;
import java.sql.Connection;

// 클래스 설계에 있어서 DAO패턴의 사용은 필수가 되었다
// 특히나 MyBatis와 같은 ORM솔루션이 제공되면서 더 강조되었다.
// JDBC API -> MyBatis -> JPA(Hibernate)-쿼리문이 없다 (추상적), 클래스 설계
public class ChatDao {
    Connection con = null;          // 인터페이스 - 비벼지는 부분들 (연계, 연동)
    CallableStatement cstmt = null;
    DBConnectionMgr dbMgr = new DBConnectionMgr();

    public String login(String mem_id, String mem_pw) {
        String mem_name = null;

        try {
            con = dbMgr.getConnection();        // 물리적으로 떨어져 있는 오라클 서버와 연결통로 확보
            cstmt = con.prepareCall("{call proc_login(?, ?, ?)}");
            cstmt.setString(1, mem_id);
            cstmt.setString(2, mem_pw);
            cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
            cstmt.executeUpdate();
            mem_name = cstmt.getString(3);
            System.out.println(mem_name);           // 프로시저 반환값
        } catch (Exception e) {
            e.printStackTrace();
        }


//        return "회원가입했던";
        return mem_name;
    }

    public static void main(String[] args) {
        ChatDao cDao = new ChatDao();
        cDao.login("tomato", "123");
    }
}
