document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('.submit-btn').addEventListener('click', function() {
        // 선택된 신고 사유 가져오기
        let reason = document.querySelector('input[name="reason"]:checked')?.value;
        let details = document.querySelector('.details-section textarea').value.trim();

        // 필수 신고 사유를 선택했는지 확인
        if (!reason) {
            alert("신고 사유를 선택해주세요.");
            return;
        }

        // 서버에 전송할 데이터 구성
        let reportData = {
            memberNo: loginMemberNo,
            boardNo: boardNo,
            reportContent: details,
            reportCategory: parseInt(reason)  // reportCategory는 숫자로 변환
        };

        // 서버로 Fetch 요청 보내기
        fetch('/reportBoard/report', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(reportData)
        })
        .then(response => {
            if (response.ok) return response.json();
            throw new Error("신고 중 오류가 발생하였습니다");
        })
        .then(data => {
            if (data > 0) {
                alert("신고가 성공적으로 접수되었습니다.");
                window.close(); // 팝업 닫기
            } else {
                alert("신고 접수에 실패했습니다.");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("신고 처리 중 오류가 발생했습니다.");
        });
    });
});
