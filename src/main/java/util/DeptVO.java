package util;

import lombok.Builder;
import lombok.Data;

@Data
// 접근자/생성자 자동 생성
// getter, setter 자동으로 사용가능(컴파일 시 추가된다)
//
@Builder
// 생성자를 자동으로 생성
public class DeptVO {
    int deptno;
    String dname;
    String loc;
}


