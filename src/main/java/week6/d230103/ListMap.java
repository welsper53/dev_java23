package week6.d230103;

import util.DBConnectionMgr;
import util.DeptVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMap {
    DBConnectionMgr dbMgr = new DBConnectionMgr();
    Connection con = null;  // 오라클 서버와 연결통로 확보
    PreparedStatement pstmt = null; // 자바에서 오라클 서버로 DML전달
    ResultSet rs = null;    // 오라클 테이블에 제공하는 커서를 조작하는 함수 전달

    public List<Map<String, Object>> getEmpList() {
        List<Map<String, Object>> list = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT                          ");
        sql.append("        empno, ename, dname     ");
        sql.append("FROM emp, dept                   ");
        sql.append("WHERE emp.deptno = dept.deptno     ");

        try {
            con = dbMgr.getConnection();
            pstmt = con.prepareStatement(sql.toString());
            rs = pstmt.executeQuery();

            list = new ArrayList<>();       // list.size() = 0이다
            Map<String, Object> rmap = null;
            while (rs.next()) {
                rmap = new HashMap<>();         // 디폴트 생성자 호출 (0, null, null)
                rmap.put("empno", rs.getInt("empno"));
                rmap.put("ename", rs.getString("ename"));
                rmap.put("dname", rs.getString("dname"));

                list.add(rmap);              // 0부터 차례대로 들어간다
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    } // end of getEmpList
    public static void main(String[] args) {
        ListMap listMap = new ListMap();
        List<Map<String, Object>> list = null;

        list = listMap.getEmpList();

        System.out.println("list : "+ list);

        for (Map<String, Object> rmap : list) {
            System.out.println(rmap.get("empno")
                    + " " + rmap.get("ename")
                    + " " + rmap.get("dname"));
        }
    }

}
