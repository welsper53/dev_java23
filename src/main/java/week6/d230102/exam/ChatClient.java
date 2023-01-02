package week6.d230102.exam;

import java.net.InetAddress;

public class ChatClient {
    public void client() {
        int port = 3000;
        String serverIP = "192.168.10.72";
        /*
        java.net, java.io 인터넷회선 장애 문제발생 원인
        io 생성 시에 소켓을 통해 인스턴스활르 하니까 의존관계있다
        이런 패키지는 반드시 예외처리 할 것
        (Thread - 인터셉트를 당할 수 있다 -제어권을 빼았긴다)
        Object마다 lock flag 값이 있어서 스레드가 점유하면 잠금 - 다른 스레드 사용불가
        데드락 상태에 빠질 수 있다(안에서 잠금으로 다른 스레드가 사용불가)
        한정된 자원을 여러 사람이 이용하기 위해 제공되는 클래스가 Thread
        Thread.sleep(), join, yield, intercept
         */
        try {
            System.out.println(InetAddress.getLocalHost().getHostName());
        } catch (Exception e) {
            // 에러 메시지에 대한 history를 기억하고 라인번호와 함께 이력을 출력해줌
            // 디버깅 시에 꼭 필요하다
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatClient cc = new ChatClient();
        cc.client();
    }
}
