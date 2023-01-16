package week7.d230113;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KiwiPanel1 extends JPanel implements ActionListener {
    // 이른 인스턴스화로 할까? 아님 게으른으로 할까?
    // 생성자는 필요한가?
    // 생성자가 필요하다고 생각했다면 파라미터는 필요한가?
    // 필요하다고 생각했다면 어떤 타입이 왜 와야 하는지 말할 수 있다
    JButton jbtn1 = new JButton("배경화면");
    JButton jbtn2 = new JButton("글꼴");
    JButton jbtn3 = new JButton("글자크기");

    // 반드시 static으로 해야 하는 이유는 패널1번에서 결정된 설정이
    // 패널2, 패널3에도 반영되어야 하기 때문
    static Font f = null;
    static boolean isSize = false;

    KiwiApp kApp = null;

    public KiwiPanel1(){
        initDisplay();
    }
    public KiwiPanel1(KiwiApp kApp){
        this();
        this.kApp = kApp;
    }


    public void initDisplay() {
        this.setLayout(null);
        jbtn1.setBounds(140, 80, 100, 30);
        jbtn2.setBounds(140, 150, 100, 30);
        jbtn3.setBounds(140, 220, 100, 30);
        jbtn3.addActionListener(this);

        this.add(jbtn1);
        this.add(jbtn2);
        this.add(jbtn3);

        // 단위테스트용
//        this.setSize(400,400);
//        this.setVisible(true);
    }

    public static void main(String[] args) {
        KiwiPanel1 kPanel1 = new KiwiPanel1();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if (obj == jbtn3) {
          if (!isSize) {
              f = new Font("굴림체", Font.BOLD, 16);
              jbtn1.setFont(f);
              jbtn2.setFont(f);
              jbtn3.setFont(f);

              kApp.font = f;

              isSize = true;
          } else {
              f = new Font("굴림체", Font.PLAIN, 16);
              jbtn1.setFont(f);
              jbtn2.setFont(f);
              jbtn3.setFont(f);

              kApp.font = f;

              isSize = false;
          }
        }

    }
}


/*
생성자 안에서 initDisplay() 호출하는 것과 그렇지 않은것의 차이는 뭘까?
위치의 문제이며 화면 정의서의 요구 사항에 따라 다른 선택이 되어야 한다.
 */