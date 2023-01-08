package week6.d230104;

import util.DBConnectionMgr;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ZipCodeSearchVIew extends JFrame implements ItemListener, FocusListener, ActionListener, MouseListener {
    // [[[[선언부]]]]
    MemberShip ms = null;

    //// 테이블 컬럼 변수 ////
    // 사용자가 선택한 zdo
    String zdo = null;
    // 사용자가 선택한 sigu
    String sigu = null;
    // 사용자가 선택한 dong
    String dong = null;
    // DB에서 가져온 zdos[]
    String[] zdos = null;
    // DB에서 가져온 sigus[]
    String[] sigus = null;
    // DB에서 가져온 dongs[]
    String[] dongs = null;

    // 중분류(sigu), 소분류(dong)
    String[] totals = {"전체"};

    //              [[NORTH]]               //
    // 콤보박스 3개 - JTextField, JButton
    // FlowLayout 배치 - 중앙에서 좌우로 펼쳐지면서 배치가 됨
    JPanel jp_north = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JComboBox jcb_zdo = null;
    JComboBox jcb_sigu = null;
    JComboBox jcb_dong = null;
    //////////////////////////////////////////


    // [[오라클 서버 연결 공통 코드]] //
    DBConnectionMgr dbMgr = new DBConnectionMgr();  // Driverclass, 커넥션 정보
    Connection con = null;                          // 인터페이스
    // 쿼리문 메모리에 올리기
    PreparedStatement pstmt = null;                 // 인터페이스-동적쿼리처리
    ResultSet rs = null;                            // 오라클서버의 커서를 조작하는 인터페이스
                                                    // -> next(), previous()
    ////////////////////////////////

    // 동 이름을 입력받는 텍스트필드와 조회 버튼 추가
    // 생성자 파라미터 자리를 이용하면 추가적인 메소드 호출 없이도 해당 화면에 대한 추가적인 초기화
    // 작업이 가능하니까 코드의 양을 줄일 수 있다
    // 이른 인스턴스화이다.
    JTextField jtf_search = new JTextField("동 이름을 입력하세요", 20);
    JButton jbtn_search = new JButton("조회");

    // 테이블 생성
    String[] cols = {"우편번호", "주소"};
    String[][] data = new String[3][3];
    DefaultTableModel dtm_zipcode = new DefaultTableModel(data, cols);
    JTable jtb_zipcode = new JTable(dtm_zipcode);
    JTableHeader jth_zipcode = jtb_zipcode.getTableHeader();
    JScrollPane jsp_zipcode = new JScrollPane(jtb_zipcode, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


    // [[[[생성자]]]]
    public ZipCodeSearchVIew() {
        jtf_search.addActionListener(this);
        jbtn_search.addActionListener(this);
        jtf_search.addFocusListener(this);
        zdos = getZdoList();
        jcb_zdo = new JComboBox(zdos);
        jcb_zdo.addItemListener(this);
        jcb_sigu = new JComboBox(totals);
        jcb_sigu.addItemListener(this);
        jcb_dong = new JComboBox(totals);
        jcb_dong.addItemListener(this);
    }
    public ZipCodeSearchVIew(MemberShip ms) {
        // 디폴트 생성자 호출 -> JFrame 내 변수 선언 등
        this();

        this.ms = ms;
        this.initDisplay();
    }


    // 대분류 정보 초기화에 대한 DB조회하기 구현
    public String[] getZdoList() {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT '전체' zdo FROM dual ");
        sql.append("UNION ALL ");
        sql.append("SELECT zdo FROM ( ");
        sql.append("                 SELECT DISTINCT(zdo) zdo ");
        sql.append("                 FROM zipcode_t ORDER BY zdo ASC) ");

        try {
            // con의 주소번지가 확인되면 오라클 서버와 연결통로가 확보되었다
            con = dbMgr.getConnection();
            pstmt = con.prepareStatement(sql.toString());
            // 오라클에서 생성된 테이블의 커서 디폴트위치는 항상 isTop이다
            rs = pstmt.executeQuery();
            Vector<String> v = new Vector<String>();
            while (rs.next()) {
                String zdo = rs.getString("zdo");
                v.add(zdo);
            }

            // 시도 콤보박스에 들어갈 배열 생성하기
            zdos = new String[v.size()];
            // 벡터에 들어있는 값 String[]에 집어넣기
            v.copyInto(zdos);
        } catch (SQLException se) {
            System.out.println(se.toString());  // getMessage()
            System.out.println(sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 사용한 자원 반납하기!! <-생성된 역순으로 할것
            // 생략해도 언젠가 반납은 이루어진다
            // but. 명시적으로 반납처리 권장한다
            //      왜냐하면 오라클 서버에서 커넥션을 강제로 종료해버린다
            try {
                dbMgr.freeConnection(con, pstmt, rs);
            } catch (Exception e) {
                // 디버깅
                e.printStackTrace();
            }
        }
        return zdos;
    } // end of getZdoList()


    public String[] getSiguList(String zdo) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT '전체' sigu FROM dual ");
        sql.append("UNION ALL ");
        sql.append("SELECT sigu FROM ( ");
        sql.append("                 SELECT DISTINCT(sigu) sigu ");
        sql.append("                 FROM zipcode_t " );
        sql.append("                 WHERE zdo = ? ");
        sql.append("                 ORDER BY sigu ASC) ");

        try {
            con = dbMgr.getConnection();
            pstmt = con.prepareStatement(sql.toString());

            // 쿼리문 내 [?]의 파라미터를 집어넣는다!!!!
            pstmt.setString(1, zdo);

            rs = pstmt.executeQuery();

            // copyInto 메소드를 사용하기 위해
            Vector<String> v = new Vector<String>();

            while (rs.next()) {
                String sigu = rs.getString("sigu");
                v.add(sigu);
            }

            // 시구 콤보박스에 들어갈 배열 생성하기
            sigus = new String[v.size()];
            // 벡터에 들어있는 값 String[]에 집어넣기
            v.copyInto(sigus);
        } catch (SQLException se) {
            System.out.println(se.toString());  // getMessage()
            System.out.println(sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 사용한 자원 반납하기!! <-생성된 역순으로 할것
            // 생략해도 언젠가 반납은 이루어진다
            // but. 명시적으로 반납처리 권장한다
            //      왜냐하면 오라클 서버에서 커넥션을 강제로 종료해버린다
            try {
                dbMgr.freeConnection(con, pstmt, rs);
            } catch (Exception e) {
                // 디버깅
                e.printStackTrace();
            }
        }
        return sigus;
    } // end of getSiguList()


    public String[] getDongList(String sigu) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT '전체' dong FROM dual ");
        sql.append("UNION ALL ");
        sql.append("SELECT dong FROM ( ");
        sql.append("                 SELECT DISTINCT(dong) dong ");
        sql.append("                 FROM zipcode_t " );
        sql.append("                 WHERE sigu = ? ");
        sql.append("                 ORDER BY dong ASC) ");

        try {
            con = dbMgr.getConnection();
            pstmt = con.prepareStatement(sql.toString());

            // 쿼리문 내 [?]의 파라미터를 집어넣는다!!!!
            pstmt.setString(1, sigu);

            rs = pstmt.executeQuery();

            // copyInto 메소드를 사용하기 위해
            Vector<String> v = new Vector<String>();

            while (rs.next()) {
                String dong = rs.getString("dong");
                v.add(dong);
            }

            // 시구 콤보박스에 들어갈 배열 생성하기
            dongs = new String[v.size()];
            // 벡터에 들어있는 값 String[]에 집어넣기
            v.copyInto(dongs);
        } catch (SQLException se) {
            System.out.println(se.toString());  // getMessage()
            System.out.println(sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 사용한 자원 반납하기!! <-생성된 역순으로 할것
            // 생략해도 언젠가 반납은 이루어진다
            // but. 명시적으로 반납처리 권장한다
            //      왜냐하면 오라클 서버에서 커넥션을 강제로 종료해버린다
            try {
                dbMgr.freeConnection(con, pstmt, rs);
            } catch (Exception e) {
                // 디버깅
                e.printStackTrace();
            }
        }
        return dongs;
    } // end of getDongList()


    // [[[[화면처리부]]]]
    public void initDisplay() {
        jtb_zipcode.addMouseListener(this);
        jth_zipcode.setBackground(Color.orange);
        jth_zipcode.setFont(new Font("맑은고딕", Font.BOLD, 15));
        jth_zipcode.getColumnModel().getColumn(0).setPreferredWidth(100); // 우편번호 간격
        jth_zipcode.getColumnModel().getColumn(1).setPreferredWidth(500); // 주소 간격
        // 그리드 색상 설정
        jtb_zipcode.setGridColor(Color.red);

        jp_north.add(jcb_zdo);
        jp_north.add(jcb_sigu);
        jp_north.add(jcb_dong);

        jp_north.add(jtf_search);
        jp_north.add(jbtn_search);

        this.add("North", jp_north);
        this.add("Center", jsp_zipcode);

        this.setLocation(600, 300);
        this.setSize(630, 400);
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void refreshData(String zdo, String dong) {
        System.out.println("refreshData 호출 성공");
        StringBuilder sql = new StringBuilder();

        sql.append("select                      ");
        sql.append("        zipcode, address    ");
        sql.append("from zipcode_t              ");
        sql.append("where 1=1                   ");
        if (zdo!= null && zdo.length()>0) {              // 빈문자열이 아닌것도 필터링
            sql.append("and zdo = ?             ");
        } // zdo가 존재할 때만
        if (dong!= null && dong.length()>0) {
            sql.append("and dong like ? ||'%'   ");
        } // dong이 입력받았을 때만

        int i=1;

        try {
            con = dbMgr.getConnection();    // 오라클서버와 연결
            // 들어있는 타입과 형전환이 잘못 선택되면 ClassCastingException이 발생가능
            pstmt = con.prepareStatement(sql.toString());       // 틀어있는 타입이 String이니까

            // 선택한 콤보박스 값
            if (zdo!=null && zdo.length()>0) {
                System.out.println("zdo : " + zdo);
                pstmt.setString(i++, zdo);
            }
            if (dong!=null && dong.length()>0) {
                System.out.println("dong : " + dong);
                pstmt.setString(i++, dong);
            }


            // 입력, 수정, 삭제인 경우에는 executeUpdate()사용하고 리턴타입은 int
            // 조회인 경우에는 executeQuery()를 사용하고 리턴타입은 ResultSet
            // 테이블을 생성할 때는 execute()를 사용함
            // 업무가 바뀌더라도 변하는건 테이블명과 컬럼명만 변한다
            // -> ORM(myBatis)솔루션 -> JPA(Hibernate)기술 출현(-쿼리문이 없다), 활용
            rs = pstmt.executeQuery();

            List<Map<String, Object>> zipList = new ArrayList<>();
            Map<String, Object> rmap = null;

            while (rs.next()) {
                rmap = new HashMap<>();
                rmap.put("zipcode", rs.getString("zipcode"));
                rmap.put("address", rs.getString("address"));
                zipList.add(rmap);
            }
            // 컬렉션에서 제공하는 클래스는 주소번지 출력하더라도 그 구조안에 있는 값들이 출력됨 - toString() 오버라이딩
            System.out.println(zipList);
            // 화면 처리기 - 테이블 새로고침
            // 조회결과가 있니?
            if (zipList.size() > 0) {
                System.out.println("zipList : " + zipList);

                // 조회를 연속하여 요청할 경우 기존에 조회된 화면은 지워주자
                // JTable은 양식일 뿐이고 실제데이터는 DefaultTableModel
                while (dtm_zipcode.getRowCount() > 0) {
                    dtm_zipcode.removeRow(0);
                }   // end of while

                // 새로 조회된 결과 출력하기
                for (int x=0; x<zipList.size(); x++) {
                    Map<String, Object> rmap2 = zipList.get(x);
                    Vector<String> oneRow = new Vector<>();
                    oneRow.add(0, rmap2.get("zipcode").toString());
                    oneRow.add(1, rmap2.get("address").toString());
                    dtm_zipcode.addRow(oneRow);
                }
            }

        } catch (SQLException se) {
            System.out.println(se.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 사용한 자원 명시적으로 반드시 반납할 것
        // -> 안하면 JVM이 언젠가는 해줌
        // --> 그 시간을 앞 당기는 코드임
        dbMgr.freeConnection(con, pstmt, rs);
    } // end of refreshData

    // [[[[메인메소드]]]]
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        ZipCodeSearchVIew zcsv = new ZipCodeSearchVIew();
        zcsv.initDisplay();
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        // 이벤트가 감지되는 소스 가져오기
        Object obj = ie.getSource();

        if (obj == jcb_zdo) {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                zdo = zdos[jcb_zdo.getSelectedIndex()];
                System.out.println("선택한 ZDO ====> " + zdo);

                sigus = getSiguList(zdo);
                // 대분류가 결정이 되었을 때 sigus를 초기화 해줘야 한다
                // 기존에 디폴트로 전체 상수값을 넣어두었으니 이것을 삭제하고
                jcb_sigu.removeAllItems();
                // 새로 DB서버에서 읽어온 값으로 아이템을 추가해야 한다.
                // -> 초기화
                for (int i=0; i<sigus.length; i++) {
                    jcb_sigu.addItem(sigus[i]);
                }
            }
        } // end of jcb_zdo
        else if (obj == jcb_sigu) {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                sigu = sigus[jcb_sigu.getSelectedIndex()];
                System.out.println("선택한 SIGU ====> " + sigu);

                dongs = getDongList(sigu);
                // 대분류가 결정이 되었을 때 sigus를 초기화 해줘야 한다
                // 기존에 디폴트로 전체 상수값을 넣어두었으니 이것을 삭제하고
                jcb_dong.removeAllItems();
                // 새로 DB서버에서 읽어온 값으로 아이템을 추가해야 한다.
                // -> 초기화
                for (int i=0; i<dongs.length; i++) {
                    jcb_dong.addItem(dongs[i]);
                }
            }
        } // end of jcb_dong
        else if (obj == jcb_dong) {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                dong = dongs[jcb_dong.getSelectedIndex()];
                System.out.println("선택한 DONG ====> " + dong);
            }
        }
    } // end of itemStateChanged

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == jtf_search) {
            jtf_search.setText("");
        }
    }
    // 아래 메소드는 구현할 필요가 없지만 지우면 에러가 발생한다 -> 추상메소드이기 때문
    // 인터페이스를 implements하면 반드시 구현체 클래스가 되어야 하므로
    // 인터페이스가 소지한 모든 추상메소드를 구현해야 한다
    @Override
    public void focusLost(FocusEvent e) {
        // {}로만 묶여 있어도 구현으로 본다 <- JVM
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if (jbtn_search == obj || jtf_search == obj) {
            String myDong = jtf_search.getText();
            System.out.println("선택한 myDONG ====> " + myDong);
            refreshData(zdo, myDong);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getClickCount() == 2) {
            System.out.println("더블클릭");

            // JTable에서 사용자가 선택한 로우의 index값을 담기
            int index = jtb_zipcode.getSelectedRow();

            for(int i=0; i<dtm_zipcode.getRowCount(); i++) {
                if(jtb_zipcode.isRowSelected(i)) {
                    String zipcode = dtm_zipcode.getValueAt(i, 0).toString();
                    String address = dtm_zipcode.getValueAt(i, 1).toString();
                    System.out.println("zipcode ====> " + zipcode);
                    System.out.println("address ====> " + address);

                    ms.jtf_zipcode.setText(zipcode);
                    ms.jtf_address.setText(address);

                    this.dispose();
                }
            }
        }   // end of mouseReleased
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
