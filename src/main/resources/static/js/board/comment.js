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
          "boardNo": boardNo,
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
                  $('#comment-content').val('');
                  loadComments(); // 댓글 등록 후 새로고침하여 목록 갱신
                  
              }
          },
          error: function(jqXHR, textStatus, errorThrown) {
              alert("댓글 등록에 실패했습니다.");
              console.log("Status Code:", jqXHR.status);
              console.log("Response Text:", jqXHR.responseText);
          }
      });
  });

 // 댓글 목록 조회 함수
 function loadComments() {
  const boardNo = location.pathname.split("/")[3];
  $.ajax({
      url: '/comment/list/' + boardNo,
      type: 'GET',
      success: function(comments) {
          $('#commentList').empty();
          comments.forEach(function(comment) {
              let profileImg =comment.profileImg ? comment.profileImg : '/images/user.png'
              let commentHtml = `
              <li class="comment-row">
                  <p class="comment-writer">
                      <img src="${profileImg}">
                      <span>${comment.memberNickname}</span>
                      <span class="comment-date">${comment.commentWriteDate}</span>
                  </p>
                  <p class="comment-content">${comment.commentContent}</p>
                  <div class="comment-btn-area">
                      <button class="child-comment-btn">답글</button>
                      <button class="update-comment-btn">수정</button>
                      <button class="delete-comment-btn">삭제</button>
                      <span class="report-area">
                          <i class="fa-regular fa-face-angry" id="comment-report"></i>
                      </span>
                  </div>
              </li>`;
              $('#commentList').append(commentHtml);
          });
      },
      error: function() {
          alert("댓글 목록을 불러오는데 실패했습니다.");
      }
  });
}

// 페이지 로드 시 댓글 목록 불러오기
loadComments();
});