package week6.d221230;

public class T {
    ZipCodeSearch zcs = null;

    // 메소드를 통해서도 객체 주입을 받을 수 있다 -> 싱글톤 패턴
    public ZipCodeSearch getInstance() {
        if (zcs == null) {  // 조건부로
            zcs = new ZipCodeSearch();
        }
        return zcs;
    }

    public static void main(String[] args) {
        T t = new T();
        t.zcs = t.getInstance();
        t.zcs.getZipCode("공덕동");
    }
}
