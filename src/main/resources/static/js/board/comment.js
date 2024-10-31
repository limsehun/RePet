$(document).ready(function() {
  // 댓글 등록 버튼 클릭 시
  $('#addComment').on('click', function() {
      let commentContent = $('#comment-content').val().trim();

      if(commentContent === "") {
          alert("댓글 내용을 입력해주세요.");
          return;
      }

      // 로그인 여부 검사
      if (typeof loginMemberNo === 'undefined' || loginMemberNo === null) {
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
              if (response > 0) {
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
                  let profileImg = comment.profileImg ? comment.profileImg : '/images/user.png';
                  let isAuthor = typeof loginMemberNo !== 'undefined' && comment.memberNo === loginMemberNo;

                  let commentHtml = `
                  <li class="comment-row" data-comment-no="${comment.commentNo}">
                      <p class="comment-writer">
                          <img src="${profileImg}">
                          <span>${comment.memberNickname}</span>
                          <span class="comment-date">${comment.commentWriteDate}</span>
                      </p>
                      <p class="comment-content">${comment.commentContent}</p>
                      <div class="comment-btn-area">
                          ${isAuthor ? `
                          <button class="update-comment-btn">수정</button>
                          <button class="delete-comment-btn">삭제</button>` : ''}
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


  // 댓글 수정 버튼 클릭 이벤트
$(document).on('click', '.update-comment-btn', function() {
    let commentRow = $(this).closest('li');
    let commentContent = commentRow.find('.comment-content').text().trim();

    // 댓글 내용을 수정 가능한 textarea로 변경
    commentRow.find('.comment-content').replaceWith(`
        <textarea class="edit-comment-content" style="resize:none">${commentContent}</textarea>
    `);

    

    $(this).replaceWith('<button class="save-comment-btn">저장</button>');
    commentRow.find('.delete-comment-btn').replaceWith('<button class="cancel-edit-btn">취소</button>');
});

// 댓글 수정 저장 버튼 클릭 이벤트
$(document).on('click', '.save-comment-btn', function() {
    let commentRow = $(this).closest('li');
    let commentNo = commentRow.data('comment-no');
    let newContent = commentRow.find('.edit-comment-content').val().trim();

    if (newContent === "") {
        alert("댓글 내용을 입력해주세요.");
        return;
    }

    $.ajax({
        url: '/comment/update/' + commentNo,
        type: 'PUT',
        data: JSON.stringify({ "commentContent": newContent }),
        contentType: 'application/json; charset=utf-8',
        success: function(response) {
            if (response > 0) {
                alert("댓글이 성공적으로 수정되었습니다.");
                // 댓글 내용 수정 후 textarea를 다시 p 태그로 변경
                commentRow.find('.edit-comment-content').replaceWith(`<p class="comment-content">${newContent}</p>`);
                commentRow.find('.save-comment-btn').replaceWith('<button class="update-comment-btn">수정</button>');
                commentRow.find('.cancel-edit-btn').replaceWith('<button class="delete-comment-btn">삭제</button>');
            } else {
                alert("댓글 수정에 실패했습니다.");
            }
        },
        error: function() {
            alert("댓글 수정 중 오류가 발생했습니다.");
        }
    });
});

// 댓글 수정 취소 버튼 클릭 이벤트
$(document).on('click', '.cancel-edit-btn', function() {
    let commentRow = $(this).closest('li');
    let originalContent = commentRow.find('.edit-comment-content').text().trim();

    // 수정 취소 시 textarea를 다시 p 태그로 변경
    commentRow.find('.edit-comment-content').replaceWith(`<p class="comment-content">${originalContent}</p>`);
    commentRow.find('.save-comment-btn').replaceWith('<button class="update-comment-btn">수정</button>');
    $(this).replaceWith('<button class="delete-comment-btn">삭제</button>');
});

  // 댓글 삭제
  $(document).on('click', '.delete-comment-btn', function() {
      if (!confirm("정말로 이 댓글을 삭제하시겠습니까?")) {
          return;
      }

      let commentNo = $(this).closest('li').data('comment-no');

      $.ajax({
          url: '/comment/delete/' + commentNo,
          type: 'DELETE',
          success: function(response) {
              if (response > 0) {
                  alert("댓글이 성공적으로 삭제되었습니다.");
                  loadComments(); // 댓글 삭제 후 목록 갱신
              } else {
                  alert("댓글 삭제에 실패했습니다.");
              }
          },
          error: function() {
              alert("댓글 삭제 중 오류가 발생했습니다.");
          }
      });
  });
});
