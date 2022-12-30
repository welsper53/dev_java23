package week6.d221230;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class S1 {
    int age = 30;
}
class S2 {
    String animal = "사자";
    int leg = 4;
}
class S3 {
    // 3개 짜리 클래스 설계

}


public class MapTest1 {
    List<Map<String, Object>> list = null;

    public static void main(String[] args) {
        S1 s1 = new S1();
        System.out.println(s1.age);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("s1", s1);
        S1 s2 = (S1)map1.get("s1");
        System.out.println(s2.age);

        Map<String, String> map11 = new HashMap<>();
//        map11.put("s1", s1);

        System.out.println("=============================");
        Map<String, Object> map2 = new HashMap<>();

        S2 s3 = new S2();
        map2.put("s2", s3);
        S2 s4 = (S2)map2.get("s2");
        if (s4.animal instanceof  String) {
            System.out.println("문자열 입니다.");
            System.out.println("animal = " + s4.animal);
        }
        // Deprecated -> 더이상 지원하지 않는 메소드 작성방법
        if (new Integer(s4.leg) instanceof  Integer) {
            System.out.println("정수형 입니다");
            System.out.println("leg = " + s4.leg);
        }


    }
}
