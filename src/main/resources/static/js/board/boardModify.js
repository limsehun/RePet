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
    callbacks: {
      onImageUpload: function(files) {
        
          uploadImage(files[0]);
        
      }
    }
  });


  // 이미지 업로드 함수
  function uploadImage(file) {
    let formData = new FormData();
    formData.append("file", file);
    // formData.append("boardNo", board.boardNo);

    $.ajax({
      url: '/editBoard/uploadImage',
      type: 'POST',
      data: formData,
      contentType: false,
      processData: false,
      success: function(data) {
        console.log(data);
        $('#summernote').summernote('insertImage', data);
      },
      error: function(jqXHR, textStatus, errorThrown) {
        console.log("Image upload failed.");
        console.log("Status Code:", jqXHR.status);
        console.log("Response Text:", jqXHR.responseText);
        console.log("Error Type:", textStatus);
        console.log("Error Thrown:", errorThrown);
      }
    });
  }

// ----------------------------------------------------------------------------------------


  // 게시글 수정 버튼 클릭 시 이벤트
  $('#writeBtn').on('click', function() {
    let title = $('#subject').val().trim();
    let content = $('#summernote').summernote('code').trim();
    let boardNo = board.boardNo; 

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
    formData.append("boardNo", boardNo);


    // 게시글 등록 비동기 처리
    $.ajax({
      url: '/editBoard/update',
      type: 'POST',
      data: formData,
      contentType: false,
      processData: false,
      success: function(response) {


        if(response > 0) {
          console.log(response);

          alert("게시글이 성공적으로 수정되었습니다");
          location.href = `/board/2/${boardNo}`;
        }
     
        
        
        
      },

      error: function() {
        alert("게시글 수정이 실패했습니다");
      }
    });
  });
  


});
// -----------------------------------------------------------------------------------



