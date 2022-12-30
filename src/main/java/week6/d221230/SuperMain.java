package week6.d221230;

class Sup {

    void stop() {
        System.out.println("Sup stop호출");
    }

    void go() {
        System.out.println("Sup go호출");

    }
}
class Sub extends Sup {
    Sub() {
        super.stop();
        // 현재 활성화되어 있는...
        this.stop();    // 현재 인스턴스화 되어 있는 -생성되어있는 객체

    }
    @Override
    void stop() {
        System.out.println("Sub stop호출");
    }
}


public class SuperMain {

    public static void main(String[] args) {
        Sup sup = new Sup();
        Sub sub = new Sub();

        // 이럴때 부모가 가진 stop메소드는 은닉메소드(shadow method)라고 한다
        sup = sub;
        sup.stop();

//        // this나 super예약어는 static이 있는 메소드 영역에서 사용할 수 없다 -컴파일에러
//        super.stop();

    }
}
