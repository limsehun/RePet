/* 댓글 등록 버튼 클릭 시 */
$(document).ready(function() {
  
  $('#addComment').on('click', function() {
      // 댓글 내용 가져오기
      let commentContent = $('#comment-content').val().trim();

    
      if(commentContent === "") {
          alert("댓글 내용을 입력해주세요.");
          return;
      }

       // 로그인 여부 검사
       if(loginCheck === false) {
        alert("로그인 한 유저만 댓글 작성이 가능합니다");
        return;
      }

      // 서버로 보낼 데이터 구성
      let formData = {
          "boardNo": board.boardNo,
          "commentContent": commentContent
      };

      // Ajax 요청으로 댓글 등록
      $.ajax({
          url: '/comment/add',
          type: 'POST',
          data: JSON.stringify(formData),
          contentType: 'application/json; charset=utf-8',
          success: function(response) {
              if(response > 0) {
                  alert("댓글이 성공적으로 등록되었습니다.");
                  location.reload(); // 댓글 등록 후 새로고침하여 목록 갱신
              }
          },
          error: function(jqXHR, textStatus, errorThrown) {
              alert("댓글 등록에 실패했습니다.");
              console.log("Status Code:", jqXHR.status);
              console.log("Response Text:", jqXHR.responseText);
          }
      });
  });
});