package bookMVC_0116;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
개발 방법론에서 디자인 패턴 중 MVC 패턴을 알아보자
>   M: 모델 계층 (~~~Logic.java + ~~~Dao.java) => 서비스 계층-비즈니스로직(업무) 계층
    V: 뷰 계층
    C: 컨트롤 계층

프레임워크를 왜 원하는가?
>   실력차이를 줄이기 위해
    틀이 정해진다
    클래스, 메소드 선언(단, 파라미터타입과 갯수는 정해야한다)
    개발자는 비즈니스 로직에만 집중할 수 있다

~~~Dao가 있고 없고는 MVC패턴에 영향이 없다
>   다만 오라클 서버와 연계에 반복되는 코드를 줄여주고 오픈소스나 라이브러리
      (iBatis, MyBatis<반자동>, Hibernate<완전자동-SQL문이 없다>)를 조립하기 위한 요구사항으로 만들어 사용하는 클래스이다.

클래스 쪼개기(생성자) 배울 순서
>   POJO(Pure) -> Spring(maven) -> Spring Boot(Gradle)-완결편
 */

public class BookManager extends JFrame  implements ActionListener {
    JButton jbtn_sel = new JButton("조회");       // Select 문
    JButton jbtn_ins = new JButton("입력");       // Insert 문
    JButton jbtn_upd = new JButton("수정");       // Update 문
    JButton jbtn_del = new JButton("삭제");       // Delete 문
    JButton jbtn_board = new JButton("게시판");   //

    JPanel jp_north = new JPanel();

    String gubun = "bookMgr";                         // 도서CRUD = bookMgr | 게시판CRUD = boardMgr

    public BookManager() {
        // 생성자 안에 화면출력 메소드 사용하는 이유
        // 요청에 따른 페이지 갱신처리, 화면갱신, 화면초기화 일때
        // initDisplay();
        // ->속지(JPanel, JScrollPane)로 사용되는 페이지일 때
        // 그러나 스레드 구현 시에는 이슈가 발생하기 때문에 주의해야한다
        //  문법에러 - 쉬움
        //  논리에러 - 트러블슈팅 - NullPointException, 예외상황
    }

    public void initDisplay () {
        // 이벤트 소스와 이벤트 처리클래스 매핑
        jbtn_sel.addActionListener(this);
        jbtn_ins.addActionListener(this);
        jbtn_upd.addActionListener(this);
        jbtn_del.addActionListener(this);
        jbtn_board.addActionListener(this);

        jp_north.add(jbtn_sel);
        jp_north.add(jbtn_ins);
        jp_north.add(jbtn_upd);
        jp_north.add(jbtn_del);
        jp_north.add(jbtn_board);

        this.add("North", jp_north);

        this.setTitle("도서관리시스템 Ver1.0");
        this.setLocation(100, 100);
        this.setSize(500, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        BookManager bManager = new BookManager();
        // 메인메소드와 Runable을 통해 구현하는 스레드를 분리할 수 있는 경우에 사용한다
        // 메인메소드와 자기자신이 run 구현체 클래스 역활을 병행하는 컨셉일 때
        // 지연 발생 - 화면이 출력안됨, 소켓 accept가 미발생 => 죽은것
        bManager.initDisplay();                     // 리펙토링
    }

    // 오버라이드 하는 이유
    // > 장치마다 결정되지 않았기 때문
    @Override
    public void actionPerformed(ActionEvent e) {    // 콜백함수 -웹, 앱, 하이브리드앱 개발
        // 조회|입력|수정|삭제 버튼이 클릭되면 이벤트를 JVM이 감지하고
        // 감지되면 JVM이 actionPerformed 메소드를 알아서 호출
        Object obj = e.getSource();

        BookController bookController = null;
        BoardController boardController = null;

        // 게시판 CRUD
        if(obj == jbtn_board) {
            gubun = "boardMgr";

            if ("boardMgr".equals(gubun)) {
                System.out.println("게시판 선택 => " + boardController);
                boardController = (BoardController) FrontController.getController(gubun);

                // 게시판 컨트롤러가 결정되면 다시 디폴트값으로 초기화
                gubun = "bookMgr";
            }
        } // end of boardMgr

        // 도서 CRUD
        else if ("bookMgr".equals(gubun)) {
            System.out.println("도서관리 선택");
            bookController = (BookController) FrontController.getController(gubun);
            System.out.println("도서관리 선택 => " + bookController);

            // if문은 무조건 조건을 따진다
            // else if문은 앞조건을 수렴하면 뒤에 있는 코드는 실행 기회를 갖지 않는다
            if (obj == jbtn_sel) {
                System.out.println("조회 버튼");

            }
            else if (obj == jbtn_ins) {
                System.out.println("입력 버튼");

            }
            else if (obj == jbtn_upd) {
                System.out.println("수정 버튼");

            }
            else if (obj == jbtn_del) {
                System.out.println("삭제 버튼");

            }
        } // end of bookMgr

    }
}
