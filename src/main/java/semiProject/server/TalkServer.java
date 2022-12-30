package semiProject.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//선언과 생성을 분리하는 코딩 전개
//자바는 단일상속만 가능함
//자바는 단일상속의 단점을 보완하기 위해 인터페이스는 다중으로 처리가능함(구현체클래스)
//상속을 받거나 implements하면 부모클래스나 인터페이스가 정의하고 있는 메소드를
// 재정의 할 수 있다(Overriding-선언부는 완전 일치해야 한다)
//인터페이스는 오로지 추상메소드만 갖는다 - Runnable도 인터페이스 이니까 추상메소드가 있다
// 그게 run메소드이다.
public class TalkServer extends JFrame implements Runnable, ActionListener {
    // ==============================[선언부]=============================
    //클라이언트측에서 new Socket하면 그 소켓정보를 받아서 쓰레드로 넘김
    TalkServerThread tst = null;

    //동시에 여러 명(Client-소켓)이 접속하니까 List - Vector<>(); 멀티스레드 안전, 속도  느림
    List<TalkServerThread> globalList = null;

    ServerSocket server = null;
    Socket socket = null;

    JTextArea jta_log = new JTextArea(10, 30);
    JScrollPane jsp_log = new JScrollPane(jta_log, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    JButton jbtn_log = new JButton("로그저장");

    // ==============================[생성자]=============================
    public TalkServer() {
        //initDisplay();//시점문제- 스케쥴링
    }

    // 화면그리기
    public void initDisplay() {
        jbtn_log.addActionListener(this);
        this.add("North", jbtn_log);
        this.add("Center", jsp_log);
        this.setSize(500, 400);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 스레드가 두 개이므로 화면요청과 start() 순서를 바꾸더라도 각자 처리가 된다
    public static void main(String[] args) { // 메인스레드라고도 한다 -> entry point
        TalkServer ts = new TalkServer();
        ts.initDisplay();
        // 내안에 run메소드가 재정의(오버라이드) 되어 있으니까
        Thread th = new Thread(ts); // 스레드 생성 시 파라미터로 TalkServer객체를 넘긴다.
        // 스레드 풀(Pool)에 있는 스레드 중에서 우선순위를 따지고 차례대로 입장한다(ready상태)
        th.start();//run() 호출됨 - 지연발생함 - 클라이언트가 접속할때까지 기다림...
    }

    // 서버소켓과 클라이언트 소켓을 연결
    @Override
    public void run() {
        //서버에 접속해온 클라이언트 스레드 정보를 관리할 벡터 생성하기
        // 벡터는 멀티스레드에서 안전 - 서버에 동시 접속자 수가 여러 명이니까
        // 그래서 벡터 안에는 클라이언트를 관리하는 스레드 추가해야 한다
        // 그 스레드는 메시지를 듣고 (청취하고) 들은 내용을 그대로 클라이언트 측에 내보냄
        //   msg = 200#토마토#오늘스터디할까?
        //   -> [StringTokenizer st = new StringTokenizer(msg,"#");]
        //   st.nextToken(): String - 200				// 프로토콜
        //   st.nextToken(): String - 토마토			// 닉네임
        //   st.nextToken(): String - 오늘스터디할까?	// 메세지
        globalList = new Vector<>();//멀티스레드안전해서 ArrayList대신 사용함

        boolean isStop = false;

        // try-catch블록은 네트워크는 항상 장애가 발생할 수 있다 - 예외처리를 함
        // try-catch 사이에는 예외가 발생할 가능성이 있는 코드를 넣는다
        //  콜백함수란? 시스템에서 필요할 때 대신 호출해주는 메소드
        //  언제 호출되나요? -> 스레드인스턴스변수.start(); 요청하면 run메소드가 호출된다
        //    왜 이렇게 처리하나요? - 여러 스레드가 존재하고 경합이 벌어지므로 우선순위를 따져가며 호출함
        //  어떻게 호출하나요?
        //  왜 반드시 run메소드를 재정의해야 할까요?
        try {
            server = new ServerSocket(3000);//서버포트번호 설정하기
            jta_log.append("Server Ready.........\n");//대기 탐 - 멈춤 - wait
            while(!isStop) { // 언제 while문 안으로 진입하지? -> "new Socket(서버ip,port);"
                socket = server.accept();
                jta_log.append("client info:"+socket+"\n");
                jta_log.append("client info:"+socket.getInetAddress()+"\n");
                TalkServerThread tst = new TalkServerThread(this);
                tst.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//////////////////////// [[ end of run ]] /////////////////////////

    @Override
    public void actionPerformed(ActionEvent e) {
        //로그를 파일로 저장하기

    }
}


/*
 * 채팅서버 구축하기
 * 클라이언트 측에서 접속하면 접속해온 정보를 서버 측 화면에서 볼 수 있다.
 * (JFrame을 상속 했다 -> 로그를 남김)
 * 자바는 단일 상속만 가능함. - 이러한 약점을 인터페이스로 지원한다
 * - 그래서 다중인터페이스 구현체는 가능함
 *
 * 스레드 구현방법
 * 1) Thread 상속하기
 * 2) Runnable 인터페이스를 implements하기
 *
 * 현재 TalkServer는 JFrame을 상속받고 있어서 Runnable을 implements하였음
 *
 */
