package week7.d230113;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KiwiApp extends JFrame implements ActionListener {
    // [선언부]
    String imgPath = "D:\\intJ_java\\dev_java23\\dev_java23\\src\\main\\java\\images\\dvd\\";
    Image img[] = null;
    ImageIcon imgs[] = new ImageIcon[3];
    String imgNames[] = {"admin2.png", "delay.png", "broken2.png"};

    JPanel jp_center = new JPanel();
    JPanel jp_south = new JPanel();
    JButton jbtn1 = new JButton();
    JButton jbtn2 = new JButton();
    JButton jbtn3 = new JButton();
    JButton imgButton[] = {jbtn1, jbtn2, jbtn3};

    // 컨테이너 클래스는 JFrame에서만 주입 받을 수 있다
    // JPanel에서는 생성 불가능하다
    // -> 생성자 파라미터를 통해 넘겨서 사용한다
    Container conn = this.getContentPane();

    KiwiPanel1 kPanel1 = null;
    KiwiPanel2 kPanel2 = null;
    KiwiPanel3 kPanel3 = null;


    static Font font = null;


    // [생성자]
    public KiwiApp() {
    }

    public void initDisplay() {
        jbtn1.addActionListener(this);
        jbtn2.addActionListener(this);
        jbtn3.addActionListener(this);

        jp_center.setBackground(Color.GREEN);

        jp_south.setLayout(new GridLayout(1,3));

        for (int i=0; i<imgs.length; i++) {
            imgs[i] = new ImageIcon(imgPath + imgNames[i]);
            imgButton[i].setIcon(imgs[i]);
            imgButton[i].setBorderPainted(false);           // 버튼 테두리 선 페인팅 미표시
            imgButton[i].setFocusPainted(false);            // 클릭 시 테두리 선 미표시
            imgButton[i].setContentAreaFilled(false);       //
            jp_south.add(imgButton[i]);
        }

        this.add("Center", jp_center);
        this.add("South", jp_south);
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        KiwiApp kApp = new KiwiApp();
        kApp.initDisplay();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbtn1) {                   // jbtn1 클릭
            System.out.println("jbtn1 클릭");

            // kPanel2 출력되어 있을 경우
            if (kPanel2 != null) {
                conn.remove(kPanel2.jta);
                conn.remove(kPanel2.jtf);
                conn.remove(kPanel2);
            }
            if (kPanel3 != null) {
                conn.remove(kPanel3.jsp_dept);
                conn.remove(kPanel3);
            }

            kPanel1 = new KiwiPanel1(this);                 // 생성자 안에서 initDisplay() 호출
            this.add("Center", kPanel1);
            conn.revalidate();

        }

        else if (e.getSource() == jbtn2) {            // jbtn2 클릭
            System.out.println("jbtn2 클릭");
            kPanel2 = new KiwiPanel2();

            // kPanel1 출력되어 있을 경우
            if (kPanel1 != null) {
                conn.remove(kPanel1.jbtn1);
                conn.remove(kPanel1.jbtn2);
                conn.remove(kPanel1.jbtn3);
                conn.remove(kPanel1);
            } else if (kPanel3 != null) {
                conn.remove(kPanel3.jsp_dept);
                conn.remove(kPanel3);
            }

            kPanel2 = new KiwiPanel2(this);
            this.add("Center", kPanel2);
            conn.revalidate();

        }

        else if (e.getSource() == jbtn3) {            // jbtn3 클릭
            System.out.println("jbtn3 클릭");

            if (kPanel1 != null) {
                conn.remove(kPanel1.jbtn1);
                conn.remove(kPanel1.jbtn2);
                conn.remove(kPanel1.jbtn3);
                conn.remove(kPanel1);
            } else if (kPanel2 != null) {
                conn.remove(kPanel2.jta);
                conn.remove(kPanel2.jtf);
                conn.remove(kPanel2);
            }

            kPanel3 = new KiwiPanel3(this);
            this.add("Center", kPanel3);
            conn.revalidate();
        }
    }
}
