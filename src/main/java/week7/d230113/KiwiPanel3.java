package week7.d230113;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KiwiPanel3 extends JPanel implements ActionListener {
    String header[] = { "부서번호", "부서명", "지역" };
    String datas[][] = new String[0][3];
    String[][] depts = { { "10", "개발부", "판교" }
                        , { "20", "경영부", "서울" }
                        , { "30", "영업부", "수원" } };

//    DefaultTableModel dtm_dept = new DefaultTableModel(datas, header);
    DefaultTableModel dtm_dept = new DefaultTableModel(depts, header);
    JTable jtb_dept = new JTable(dtm_dept);
    JScrollPane jsp_dept = new JScrollPane(jtb_dept, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    // JTable의 헤더 글자크기도 변경하기
    JTableHeader jth = jtb_dept.getTableHeader();


    KiwiApp kApp = null;
    static Font f = null;


    public KiwiPanel3(){
//        for (int i = 0; i < depts.length; i++) {
//            Vector<Object> oneRow = new Vector<>();
//            oneRow.add(depts[i][0]);
//            oneRow.add(depts[i][1]);
//            oneRow.add(depts[i][2]);
//            dtm_dept.addRow(oneRow);
//        }
        initDisplay();
    }
    public KiwiPanel3(KiwiApp kApp){
        this();
        this.kApp = kApp;
    }

    public void changeFontSize() {
        jth.setFont(KiwiPanel1.f);
    }

    public void initDisplay() {
        if(KiwiPanel1.isSize) {
            changeFontSize();
        }

        // JPanel은 디폴트 레이아웃이 FlowLayout
        // JDialog, JFrame은 디폴트 레이아웃이 BorderLayout
        this.setLayout(new BorderLayout());
        this.add("Center", jsp_dept);

//        // 단위테스트용
//        this.setSize(400, 400);
//        this.setVisible(true);
    }

    public static void main(String[] args) {
        new KiwiPanel3();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
