// summernote 초기화
$(document).ready(function() {
  $('#summernote').summernote({
    width: 1000,
    height: 600,
    lang: "ko-KR",
    placeholder: "내용을 입력해주세요",
    focus: true,
    toolbar: [
      ['fontsize', ['fontsize']],
      ['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
      ['color', ['forecolor', 'color']],
      ['table', ['table']],
      ['para', ['ul', 'ol', 'paragraph']],
      ['height', ['height']],
      ['insert', ['picture']]
    ],
  
  });

  // 게시글 등록 버튼 클릭 시 이벤트
  $('#writeBtn').on('click', function() {
    let title = $('#subject').val().trim();
    let content = $('#summernote').val().trim();

    // 제목과 내용이 입력되지 않았을 경우 경고
    if(title === "") {
      alert("제목을 입력해주세요");
      return;
    }

    if(content === "" ){
      alert("내용을 입력해주세요");
      return;
    }

    let formData = new FormData();
    formData.append("boardTitle", title);
    formData.append("boardContent", content);

    // 게시글 등록 비동기 처리
    $.ajax({
      url: '/editBoard/insert',
      type: 'POST',
      data: formData,
      contentType: false,
      processData: false,
      success: function(response) {


        if(response > 0) {
          console.log(response);

          alert("게시글이 성공적으로 등록되었습니다");
          location.href = '/board/2';
        }
     
        
        
        
      },

      error: function() {
        alert("게시글 등록에 실패했습니다");
      }
    });
  });
  


});


