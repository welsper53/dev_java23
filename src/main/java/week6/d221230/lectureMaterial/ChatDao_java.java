//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import com.jdbc.DBConnectionMgr2;
//
//public class ChatDao {
//	Connection con = null;
//	CallableStatement cstmt = null;
//	DBConnectionMgr2 dbMgr = new DBConnectionMgr2();
//	ResultSet rs = null;
//	public ChatDao() {
//	}
//	public String login(String mem_id, String mem_pwd) {
//		String mem_nick = null;
//		try {
//			con = dbMgr.getConnection();
//			cstmt = con.prepareCall("{call proc_chatlogin(?,?,?)}");
//			//오라클 서버와 연결 통로가 확보 되었고 두 개의 ?자리 중 첫번째가 read속성이므로
//			//p_empno를 첫번째 ?자리에 설정해야 함.
//			cstmt.setString(1, mem_id);
//			cstmt.setString(2, mem_pwd);
//			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
//			cstmt.execute();
//		    mem_nick = cstmt.getString(3);
//		} catch (Exception e) {
//			System.out.println(e.getMessage()+", "+e.toString());//힌트를 얻을 수 있다.
//		}
//		return mem_nick;
//	}
//}
//
///*
//variable r_name varchar2;
//exec proc_login('test','123',:r_name);
//print r_name;
//*/