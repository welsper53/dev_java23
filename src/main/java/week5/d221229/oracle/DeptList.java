package week5.d221229.oracle;

import util.DeptVO;
import util.DBConnectionMgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DeptList {
    Connection con = null;
    // 쿼리문 던질때
    PreparedStatement pstmt = null;
    // 쿼리 결과
    ResultSet rs = null;
    DBConnectionMgr dbMgr = new DBConnectionMgr();

    public List<DeptVO> getDeptList() {

        // 오라클에 연결
        con = dbMgr.getConnection();

        // open...cursor...fetch
        // [DEPT부서 SELECT문]
        // 쿼리문
        String sql = "SELECT deptno, dname, loc FROM dept";
        List<DeptVO> deptList = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(sql);
            // 커서
            rs = pstmt.executeQuery();// 오라클 서버에게 처리를 요청함.

            DeptVO dVO = null;
            // DeptVO dVO = new DeptVO();

            // 레코드가 있을경우 "rs.next()"==true
            while (rs.next()) {
                // fetch : 호출한 데이터를 메모리에 올리는 것
                dVO = DeptVO.builder().deptno(rs.getInt("deptno")).dname(rs.getString("dname")).loc(rs.getString("loc")).build();

//                // TODO: lombok이 아닌 원래 방식
//                dVO = new DeptVO();
//                 dVO.setDeptno(rs.getInt("deptno"));
//                 dVO.setDname(rs.getString("dname"));
//                 dVO.setLoc(rs.getString("loc"));

                deptList.add(dVO);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.toString());
        }
        return deptList;
    }

    public void getDeptno() {

    }

    public static void main(String[] args) {
        DeptList deptList = new DeptList();
        List<DeptVO> list = null;
        list = deptList.getDeptList();

        for (int i = 0; i < list.size(); i++) {
            DeptVO d = list.get(i);
            // System.out.println(deptList.get(i));
            System.out.println(d.getDeptno() + ", " + d.getDname() + ", " + d.getLoc());
        }
    }
}

