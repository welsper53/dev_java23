package week6.d221230;

import util.DBConnectionMgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ZipCodeSearch {
    Connection con = null;     // 인터페이스
    PreparedStatement pstmt = null;     // 인터페이스
    ResultSet rs = null;    // 인터페이스

    DBConnectionMgr dbMgr = new DBConnectionMgr();


    public Integer[] getZipCode(String dong) {
        System.out.println("getZipCode 호출 성공 ==> " + dong);
        Integer[] zipCodes = null;
        //// 다형성 (List > Vector)
        // List<Integer> imsi = new Vector<>();
        Vector<Integer> imsi = new Vector<>();

        // 쿼리문 작성
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT              ");
        sql.append("        zipcode");
        sql.append(" FROM zipcode_t");
        sql.append(" WHERE dong LIKE ?||'%'");
        // 오라클 접속 안될 가능성
        try {
            con = dbMgr.getConnection();
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setString(1, dong);
            rs = pstmt.executeQuery();

            while (rs.next()) {
//                System.out.println(rs.getInt("zipcode"));
                int code = rs.getInt("zipcode");
                imsi.add(code);
            }

            zipCodes = new Integer[imsi.size()];
            imsi.copyInto(zipCodes);

            for (int c : zipCodes) {
                System.out.println(c);
            }

        }catch(Exception e) {
            e.printStackTrace();        // 라인번호출력, 이력출력
        } finally {
            // 사용한 자원 반납하기
            dbMgr.freeConnection(con, pstmt, rs);
        }

        return zipCodes;
    }
//    public List<Integer> getZipCode2(String dong) {
//
//
//    }

    public static void main(String[] args) {
        ZipCodeSearch zcs = new ZipCodeSearch();
        zcs.getZipCode("역삼");
    }
}

