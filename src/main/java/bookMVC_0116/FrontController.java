package bookMVC_0116;

public class FrontController {
    /*
    리턴 타입을 Object로 한 이유
    >
     */
    public static Object getController(String gubun) {
        Object obj = null;      // BookController or BoardController

        if("bookMgr".equals(gubun)) {
            obj = new BookController();
        } else if("boardMgr".equals(gubun)) {
            obj = new BoardController();
        }

        return obj;
    }

//    public static FrontController getController(String gubun) {
//        FrontController obj = null;      // BookController or BoardController
//
//        if("bookMgr".equals(gubun)) {
//            obj = new BookController();
//        } else if("boardMgr".equals(gubun)) {
//            obj = new BoardController();
//        }
//
//        return obj;
//    }
}
