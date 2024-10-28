const moreBtn = document.getElementById('more-adopt-card'); // 더보기 버튼
const adoptListAll = document.querySelector(".adopt-list"); // 입양 리스트

const dog = document.querySelector(".dog"); // 강아지 버튼
const cat = document.querySelector(".cat"); // 고양이 버튼
const others = document.querySelector(".others"); // 기타 동물 버튼

let page = 1; // 현재 페이지 번호
let upkind = "all"; // 기본 동물 종류 설정

// selectAdoptList 함수 정의
const selectAdoptList = (page, upkind) => {
  fetch("/adopt/selectAdoptList?page=" + page + "&upkind=" + upkind)
    .then(response => {
      if (response.ok) return response.json();
      throw new Error("조회 실패");
    })
    .then(map => {
      const adoptList = map.adoptList;

      adoptList.forEach(adopt => {
        const petCard = document.createElement('div');
        petCard.className = 'pet-card';
        petCard.innerHTML = `
          <div class="pet-image">
              <img class="open-api-img" src="${adopt.popfile ? adopt.popfile : '/images/default.png'}" />
          </div>
          <div class="content">
            <div class="condition">${adopt.processState}</div>
            <p>
              <strong>
                ${adopt.kindCd} / ${adopt.sexCd}
              </strong>
            </p>
            <p>
              나이: ${adopt.age}
            </p>
            <ul class="hidden">
              <li>${adopt.neuterYn}</li>
              <li>${adopt.careNm}</li>
              <li>${adopt.careTel}</li>
              <li>${adopt.careAddr}</li>
              <li>${adopt.noticeNo}</li>
              <li>${adopt.noticeSdt}</li>
              <li>${adopt.noticeEdt}</li>
              <li>${adopt.happenPlace}</li>
              <li>${adopt.specialMark}</li>
            </ul>
          </div>
        `;''

        adoptListAll.appendChild(petCard);
        });

        addEventModal();
 
    })
    .catch(err => {
      console.error(err);
      alert("데이터를 불러오는 중 오류가 발생했습니다.");
    });
};

// 이벤트 리스너 설정
moreBtn.addEventListener("click", () => {
  page++;
  selectAdoptList(page, upkind);
});

// 동물 종류 선택 이벤트 핸들러
dog?.addEventListener("click", () => {
  upkind = 'dog';
  page = 1; 
  adoptListAll.innerHTML = ''; 
  selectAdoptList(page, upkind);
});

cat?.addEventListener("click", () => {
  upkind = 'cat';
  page = 1; 
  adoptListAll.innerHTML = '';
  selectAdoptList(page, upkind);
});

others?.addEventListener("click", () => {
  upkind = 'others';
  page = 1; 
  adoptListAll.innerHTML = '';
  selectAdoptList(page, upkind);
});

// 페이지 로드 시 초기 데이터 로딩
document.addEventListener("DOMContentLoaded", () => {
  selectAdoptList(page, upkind);
});



// ------------------- 모달창 ----------------------
const closeBtn = document.querySelector(".close-btn");
const dtModalBg = document.querySelector(".dtModal-bg");

let flag = false;

const addEventModal = () => {
  adoptListAll.addEventListener("click", (event) => {
    
    const petCard = event.target.closest(".pet-card");
    if (petCard && !flag) { // .pet-card 요소가 클릭된 경우
      flag = true;
      const adoptInfo = {
        kindCd: petCard.querySelector('.content strong').innerText.split(' / ')[0],
        age: petCard.querySelector('.content p:nth-of-type(2)').innerText.split(' : ')[1],
        sexCd: petCard.querySelector('.content strong').innerText.split(' / ')[1],
        neuterYn: petCard.querySelector('.hidden li:nth-child(1)').innerText,
        popfile: petCard.querySelector('.open-api-img').src,
        careNm: petCard.querySelector('.hidden li:nth-child(2)').innerText,
        careTel: petCard.querySelector('.hidden li:nth-child(3)').innerText,
        careAddr: petCard.querySelector('.hidden li:nth-child(4)').innerText,
        noticeNo: petCard.querySelector('.hidden li:nth-child(5)').innerText,
        noticeSdt: petCard.querySelector('.hidden li:nth-child(6)').innerText,
        noticeEdt: petCard.querySelector('.hidden li:nth-child(7)').innerText,
        happenPlace: petCard.querySelector('.hidden li:nth-child(8)').innerText,
        specialMark: petCard.querySelector('.hidden li:nth-child(9)').innerText
      };
      
      const modal = document.createElement('div');
      modal.className = 'popup-container';
      modal.innerHTML = `
      <span class="close-btn">×</span>
      <div class="popup-header">
      ${adoptInfo.kindCd} | ${adoptInfo.age? adoptInfo.age:"정보없음"} <br>
      ${adoptInfo.sexCd} | ${adoptInfo.neuterYn? adoptInfo.neuterYn:"여부없음"}
      </div>
      <img class="open-api-img" src="${adoptInfo.popfile}" />
      <div class="info-list">
      <div class="info-item" class="careNm">보호소: ${adoptInfo.careNm}</div>
      <div class="info-item" class="careTel">보호소 TEL: ${adoptInfo.careTel}</div>
      <div class="info-item" class="careAddr">보호소 주소: ${adoptInfo.careAddr}</div>
      <div class="info-title">상태</div>
      <div class="info-item" class="noticeNo">공고번호: ${adoptInfo.noticeNo}</div>
      <div class="info-item" class="vnoticeDt">공고기간: ${adoptInfo.noticeSdt} ~ ${adoptInfo.noticeEdt}</div>
      <div class="info-item" class="happenPlace">발견장소: ${adoptInfo.happenPlace}</div>
      <div class="info-item" class="specialMark">특징: ${adoptInfo.specialMark}</div>
      </div>
      `;
      
      // 모달을 화면에 추가
      document.body.appendChild(modal);
      openModal();
      
      const scrollPosition = modal.offsetTop;
      window.scrollTo({
        top : scrollPosition - 400,
        behavior : "smooth"
      })

      // 모달 닫기 이벤트
      modal.querySelector(".close-btn").addEventListener("click", () => {
        document.body.removeChild(modal);
        closeModal();
        flag = false;
      });
    }
  });

};



function openModal() {
  dtModalBg.classList.remove('popup-hidden');

}

function closeModal() {
  dtModalBg.classList.add('popup-hidden');
}