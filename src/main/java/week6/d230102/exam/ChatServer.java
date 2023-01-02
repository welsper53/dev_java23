package week6.d230102.exam;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// 단일 상속만 되니깐 인터페이스 지원한다
// 인터페이스를 통해 스레드 구현방법
public class ChatServer implements  Runnable{
    // 메인 스레드이다
    public static void main(String[] args) {
        ChatServer cs = new ChatServer();
        Thread th = new Thread(cs);
        th.start();
    }

    @Override
    public void run() {
        int port = 3000;
        ServerSocket server = null;

        try {
            server = new ServerSocket(port);

            // 대기상태 ~~~~ 시간이 가다가
            // ChactClient 에서 new Socket("서버IP", 3000);
            // ==> 대기상태 풀림
            System.out.println("서버소켓 생성 완료 - 클라이언트 소켓 접속 waiting...");
            while (true) {
                // 아래 코드가 진행되는 시점은 언제지? => new Socket("192.168.10.72", 3000);
                System.out.println("클라이언트 소켓 접속 완료");
                Socket client = server.accept();
                // 접속한 클라이언트 정보 출력
                System.out.println("클라이언트측 : " + client.getInetAddress());

                // 대기상태 풀림
                // 금융권에서 주로 사용한다 - 보안 강화 - 직렬화 기법 - 마샬링, 언마샬링 구간
                // 말하기 : ObjectOutputStream -> writeObject(): 네트워크 전송 일어난다 - 패킷
                // 듣기  : ObjectInputStream -> readObject(): 듣기
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
