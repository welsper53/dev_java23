package bookMVC_0116;

/*
[인터페이스]
 - 추상메소드만 가질 수 있다 -일반메소드는 불가능함
 - 생성자 X
 - 일반 전역변수 X
 */
public interface Controller {
    public abstract FrontController getController(String gubun);

}
