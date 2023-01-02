package semiProject.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ChatListView extends JFrame implements ActionListener {
    // [선언부]
    Vector<JButton> vList = new Vector<JButton>();

    JPanel jp_north = new JPanel();
    JButton jbtn_myPage = new JButton("마이페이지");
    JButton jbtn_firChan = new JButton("새 채팅");   // 친구추가 | 새 채팅


    JPanel jp_center = new JPanel();
    JLabel jlb_secChan = new JLabel("채팅 목록");    // 친구 | 채팅 목록
    JPanel jp_secChan = new JPanel();   // TODO: 중앙 패널 <- 리스트
    JLabel jlb_test = new JLabel("테스트용");
    JButton jbtn_cenList = null;    // TODO: 중앙 - 친구, 채팅 리스트
//    JScrollPane jsp = new JScrollPane();    // TODO: 리스트를 스크롤
//    JScrollPane jsp_center = new  // TODO: 스크롤

    JPanel jp_south = new JPanel();
    JButton jbtn_main = new JButton("내 화면");
    JButton jbtn_chat = new JButton("채팅방");


    public ChatListView() {
        for (int i=0; i<3; i++) {
            jbtn_cenList = new JButton(Integer.toString(i));
            vList.add(jbtn_cenList);
        }
    }

    public void initDisplay() {
    /*
        // 북
        jbtn_myPage.setBounds(10, 10, 150, 100);
        jbtn_firChan.setBounds(200, 10, 150, 100);
//        jbtn_myPage.setSize(150, 100);
//        jbtn_firChan.setSize(150, 100);

//        jp_north.setLayout(new GridLayout(1,2));
        jp_north.add(jbtn_myPage);
        jp_north.add(jbtn_firChan);

        // 중
//        jp_center.setLayout(new GridLayout(2,1));
        jp_center.add(jlb_secChan);
        jp_secChan.add(jbtn_cenList);
        jp_center.add(jp_secChan);

        // 남
//        jp_south.setLayout(new GridLayout(1,2));
        jp_south.add(jbtn_main);
        jp_south.add(jbtn_chat);

        this.add(jp_north);
        this.add(jp_center);
        this.add(jp_south);


        this.add("North", jp_north);
        this.add("Center", jp_center);
        this.add("South", jp_south);

     */
        this.setLayout(null);

        // 북
        jbtn_myPage.addActionListener(this);
        jbtn_myPage.setBounds(10, 10, 150, 40);
        this.add(jbtn_myPage);
        jbtn_firChan.addActionListener(this);
        jbtn_firChan.setBounds(175, 10, 150, 40);
        this.add(jbtn_firChan);


        // 중
        jlb_secChan.setBounds(10, 60, 330, 20);
        this.add(jlb_secChan);
        jp_secChan.setBounds(5, 85, 340, 420);
        jp_secChan.setLayout(new BorderLayout());
//        jp_secChan.add(jlb_test);
        for (int i=0; i<vList.size(); i++) {
            vList.get(i).setSize(300, 50);
            jp_secChan.add(vList.get(i));
            vList.get(i).addActionListener(this);
        }

        this.add(jp_secChan);


        // 남
        jbtn_main.addActionListener(this);
        jbtn_main.setBounds(10, 510, 150, 40);
        this.add(jbtn_main);
        jbtn_chat.addActionListener(this);
        jbtn_chat.setBounds(175, 510, 150, 40);
        this.add(jbtn_chat);

        this.setLocation(500, 100);
        this.setSize(350, 600);
        this.setTitle("채팅 목록");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        ChatListView cView = new ChatListView();
        System.out.println("생성자 시작");
        new ChatListView();
        System.out.println("화면 출력");
        cView.initDisplay();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if(obj == jbtn_myPage) {
            // 마이페이지 클릭
            System.out.println("jbtn_myPage(마이페이지클릭) 클릭");
        }
        else if(obj == jbtn_firChan) {
            // 친추/새채팅 클릭
            System.out.println("jbtn_firChan(" + jbtn_firChan.getText() +") 클릭");
        }
        else if (obj == jbtn_main) {
            // 친구목록 클릭
            System.out.println("jbtn_myPage(내 화면) 클릭");
            this.setTitle("친구목록");
            jbtn_firChan.setText("친구추가");
            jlb_secChan.setText("친구 목록");
        }
        else if (obj == jbtn_chat) {
            // 채팅목록 클릭
            System.out.println("jbtn_chat(채팅방) 클릭");
            this.setTitle("채팅목록");
            jbtn_firChan.setText("새 채팅");
            jlb_secChan.setText("채팅 목록");
        }
    }
}
