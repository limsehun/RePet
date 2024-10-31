package edu.kh.repet.common.exception;

// 사용자 정의 예외 만드는 방법
// ==> 아무 Exception 클래스를 상속 받으면 된다!!
// 		Checked Exception 상속 ==> Checked 사용자 정의 예외
// 		UnChecked Exception 상속 ==> UnChecked 사용자 정의 예외
public class FileDeleteFailException  extends RuntimeException {
    
    public FileDeleteFailException() {
        super("이미지 삭제 실패");
    }
    
    public FileDeleteFailException(String message) {
        super(message);
    }
    
}
