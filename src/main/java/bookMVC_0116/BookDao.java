package bookMVC_0116;

import java.util.List;
import java.util.Map;

public class BookDao {
    /**
     * 도서 정보 삭제 구현
     *
     * @param bkno - 도서번호
     * @return 1이면 삭제 성공, 0이면 삭제 실패
     */
    public int bookDelete(int bkno) {
        int result = 0;
        System.out.println("bookDelete호출(사용자가 선택한 도서번호) ===> " + bkno);



        return 0;
    } // end of bookDelete


    /**
     * 도서정보 수정하기
     *
     * @param bkVO - 수정
     * @return 1이면 수정 성공, 0이면 수정 실패
     */
    public int bookUpdate(BookVO bkVO) {
        int result = 0;
        // 롬복 어노테이션 @Data를 사용했기에 getter/setter 메소드는 없지만 사용가능하다
        // 단 VO타입이므로 전역변수에 담긴 값을 출력하려면 getter메소드 호출한다
        // 전역변수는 캡슐화로 인해 직접 접근은 "불가능하고
        // , 위변조로 인한 피해로부터 보호하기 위해 접근제한자는 반드시 private로 할것"
        System.out.println("bookDelete호출(사용자가 선택한 도서정보) ===> " + bkVO);



        return 0;
    } // end of bookUpdate


    /**
     * 도서정보 입력하기
     *
     * @param bkVO - 입력요청으로 입력 받은 값
     * @return 1이면 입력 성공, 0이면 입력 실패
     */
    public int bookInsert(BookVO bkVO) {
        int result = 0;
        // 롬복 어노테이션 @Data를 사용했기에 getter/setter 메소드는 없지만 사용가능하다
        // 단 VO타입이므로 전역변수에 담긴 값을 출력하려면 getter메소드 호출한다
        // 전역변수는 캡슐화로 인해 직접 접근은 "불가능하고
        // , 위변조로 인한 피해로부터 보호하기 위해 접근제한자는 반드시 private로 할것"
        System.out.println("bookDelete호출(사용자가 선택한 도서정보) ===> " + bkVO);



        return 0;
    } // end of bookInsert


    /**
     * 도서 목록 조회
     * SELECT bk_no, bk_title FROM book
     * WHERE bk_title(?) = ?
     * WHERE bk_author(?) = ?
     * WHERE bk_info(?) = ?
     *
     * @param cols 컬럼명 bk_title or bk_author or bk_info
     * @param keyword 텍스트필드에 사용자가 입력한 값
     * @return 검색결과는 n개 로우 List<Map<>>
     * 조인이 필수적인 경우에는 반드시 List<Map>형태가 유리하고
     * 그렇지 않은 경우라면 List<~~~VO>형태가 별로 차이가 없다
     * 단 조회결과로 얻은 정보를 자바코드에서 연산을 해야하는 경우라면
     * 제네릭 타입으로 Map보다는 ~~~VO가 유리하다
     * Map이면 리턴값이 무조건 Object이다. -> ClassCastingException
     * int i = Integer.parseInt(pMap.get("bk_no").toString());
     * int i = ~~~VO.getBkno();
     */
    public List<Map<String, Object>> getBookList(String cols, String keyword) {


        return null;
    }
}
