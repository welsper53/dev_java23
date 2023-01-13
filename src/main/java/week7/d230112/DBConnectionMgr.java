package week7.d230112;


import java.sql.*;

public class DBConnectionMgr {
    public static final String _DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final String _URL = "jdbc:oracle:thin:@192.168.10.72:1521:orcl11";

    public static String _USER = "scott";
//    public static String _USER = "kiwi";        // 프로시저 설정한 계정
    public static String _PW = "tiger";

    public DBConnectionMgr(){}
    public DBConnectionMgr(String _USER, String _PW){
        // static으로 선언된 변수는 this나 super 같은 예약어 사용 불가능하다
        this._USER = _USER;
        this._PW = _PW;
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(_DRIVER);
            con = DriverManager.getConnection(_URL, _USER, _PW);
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 클래스를 찾을 수 없습니다.");
        } catch (Exception e) {
            System.out.println("오라클 서버와 커넥션 실패!!");
        } // end of try-catch

        return con;
    } // end of getConnection()

    public void freeConnection(Connection con, Statement stmt) {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } // end of freeConnection

    public void freeConnection(Connection con, PreparedStatement pstmt) {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } // end of freeConnection - [INSERT, UPDATE, DELETE]

    public void freeConnection(Connection con, Statement stmt, ResultSet rs) {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } // end of freeConnection

    public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } // end of freeConnection - [SELECT]



    // 오라클 서버와 연계에 필요한 객체
    // 사용 후에는 반드시 자원 반납 할 것 - 명시적으로 (묵시적으로 언젠가는 처리된다)
    public void freeConnection(Connection con, CallableStatement cstmt) {
        if (cstmt != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        DBConnectionMgr dbMgr = new DBConnectionMgr();
        Connection con = dbMgr.getConnection();
        System.out.println("con ===> " + con);
    }
}