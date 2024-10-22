



/* 다음 주소 API로 주소 검색하기 */
function findAddress() {
  new daum.Postcode({
    oncomplete: function (data) {
      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

      // 각 주소의 노출 규칙에 따라 주소를 조합한다.
      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
      var addr = ''; // 주소 변수
      
      //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
      if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
        addr = data.roadAddress;
      } else { // 사용자가 지번 주소를 선택했을 경우(J)
        addr = data.jibunAddress;
      }

      // 우편번호와 주소 정보를 해당 필드에 넣는다.
      document.getElementById('postcode').value = data.zonecode;
      document.getElementById("address").value = addr;
      // 커서를 상세주소 필드로 이동한다.
      document.getElementById("detailAddress").focus();
    }
  }).open();
}

// 주소 검색 버튼 클릭 시
const searchAddress = document.querySelector("#searchAddress");
searchAddress.addEventListener("click", findAddress);


const checkObj = {
  "email"           : false,
  "password"        : false,
  "nickname"        : false,
  "phone"           : false,
  "check-btn"       : false,
  "submit-btn"      : false,
  "authKey"         : false
};



/* ----- 닉네임 유효성 검사 ----- */
// 1) 닉네임 유효성 검사에 사용되는 요소 얻어오기
const memberNickname = document.querySelector("#nickname");
const checkBtn       = document.querySelector("#check-btn");
const nickMessage = document.querySelector("#nickMessage");

// 2) 닉네임 관련 메시지 작성
const nickMessageObj = {};
nickMessageObj.normal = "한글,영어,숫자로만 3~10글자";
nickMessageObj.invaild = "유효하지 않은 닉네임 형식 입니다";
nickMessageObj.duplication = "이미 사용중인 닉네임 입니다.";
nickMessageObj.check = "사용 가능한 닉네임 입니다.";

// 3) 닉네임 입력 시 마다 유효성 검사
memberNickname.addEventListener("input", () => {

  checkBtn.addEventListener("click", () => {

    
      // 입력 받은 닉네임
      const inputNickname = memberNickname.value.trim();
    
      // 4) 입력된 닉네임이 없을 경우
      if(inputNickname.length === 0){
        nickMessage.innerText = nickMessageObj.normal;
        alert("nickMessage.classList");
        checkObj.memberNickname = false;
        memberNickname.value = "";
        return;
      }
    
      // 5) 닉네임 유효성 검사(정규 표현식)
      const regEx = /^[a-zA-Z0-9가-힣]{3,10}$/; // 한글,영어,숫자로만 3~10글자
    
      if(regEx.test(inputNickname) === false){
        nickMessage.innerText = nickMessageObj.invaild;
        alert("nickMessage.classList");
        checkObj.memberNickname = false;
        return;
      }
    
      // 6) 닉네임 중복 검사
      fetch("/member/nicknameCheck?nickname=" + inputNickname)
      .then(response => {
        if(response.ok) return response.text();
        throw new Error("닉네임 중복 검사 에러");
      })
      .then(count => {
        if(count == 1){
          nickMessage.innerText = nickMessageObj.duplication;
          alert("nickMessage.classList");
          checkObj.memberNickname = false;
          return;
        }
    
        nickMessage.innerText = nickMessageObj.check;
        alert("nickMessage.classList");
        checkObj.memberNickname = true;
    
      })
      .catch(err => console.error(err));
  } )
});