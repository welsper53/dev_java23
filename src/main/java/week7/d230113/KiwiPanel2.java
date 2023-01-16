package week7.d230113;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KiwiPanel2 extends JPanel implements ActionListener {
    JTextArea jta = new JTextArea("");
    JTextField jtf = new JTextField("");

    KiwiApp kApp = null;

    public KiwiPanel2(){
        initDisplay();
    }
    public KiwiPanel2(KiwiApp kApp){
        this();
        this.kApp = kApp;
    }

    public void changeFontSize() {
        jta.setFont(KiwiPanel1.f);
        jtf.setFont(KiwiPanel1.f);
    }

    public void initDisplay() {
        if(KiwiPanel1.isSize) {
            changeFontSize();
        }

        this.setLayout(new BorderLayout());
        this.add("Center", jta);
        this.add("South", jtf);

//        // 단위테스트용
//        this.setSize(400, 400);
//        this.setVisible(true);
    }

    public static void main(String[] args) {
        new KiwiPanel2();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
