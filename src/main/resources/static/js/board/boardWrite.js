$(function() {
  $("#contents").summernote({
    width:1000,
    height:600,
    lang:"ko-KR",
    placeholder:"내용을 입력해주세요",
    focus : true,
    toolbar: [
        // 글자 크기 설정
        ['fontsize', ['fontsize']],
        // 굵기, 기울임꼴, 밑줄,취소 선, 서식지우기
        ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
        // 글자색
        ['color', ['forecolor','color']],
        // 표만들기
        ['table', ['table']],
        // 글머리 기호, 번호매기기, 문단정렬
        ['para', ['ul', 'ol', 'paragraph']],
        // 줄간격
        ['height', ['height']],
        // 그림첨부, 링크만들기, 동영상첨부
        ['insert',['picture']]
        // 코드보기, 확대해서보기, 도움말
        //['view', ['codeview','fullscreen', 'help']]
    ],
    onImageUpload: function(files) {
      uploadImages(files); // 여러 이미지 업로드 
    }
  });
});

// 이미지 업로드 처리
function uploadImages(files) {
  const formData = new FormData();
  for(let i = 0; i < files.length; i++){
    formData.append("files", files[i]);
  }

  const boardNo = /* 게시글 번호 로직 추가 */
  formData.append("boardNo", boardNo);

  $.ajax({
    url: "/editBoard/uploadImages",
    method: "POST",
    data: formData,
    processData: false,
    contentType: false,
    success: function (response) {
      response.urls.forEach(url => {
        $('#contents').summernote('insertImage', url);
      });
    },
    error: function(err){
      if(err.status === 401){
        alert("로그인이 필요합니다");
        window.location.href = "/login";
      
      } else {
        console.error("Image upload failed:", err);
      }
    }
  });
}

$("#writeBtn").click(function () {
  
    const title = $("subject").val().trim(); // 제목 입력값 가져오기
    const content = $("contents").val().trim(); // 내용 입력값 가져오기

    // 입력값이 비어 있는지 확인
    if(!title) {
      alert("제목을 입력해 주세요"); // 제목이 비어 있을 경우 경고
      $("#subject").focus(); // 제목 입력 필드로 포커스 이동
      return;
    }

    if(!content) {
      alert("내용을 입력해 주세요");
      $("#contents").summernote('focus');
      return;
    }

    // 유효성 검사를 통과한 경우에만 AJAX 요청 수행
    const postData = {
      boardTitle : title,
      boardContent : content
    };

    $.ajax({
      url: "/editBoard/register",
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify(postData),
      success: function (response) {
        if(response.success) {
          alert("게시글 등록되었습니다");
          window.location.href = "/board/2";

        } else {
          alert("게시글 등록에 실패했습니다.");
        }
      },

      error: function (err) {
        if(err.status === 401) {
          alert("로그인이 필요합니다");
          window.location.href = "/login";

        } else {
          console.error("Post registration failed", err);
          alert("게시글 등록 중 오류가 발생했습니다");
        }
      }
    })
})

  

