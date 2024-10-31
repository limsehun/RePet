
// -------------------------main 화면 버튼들- ---------------------------------

document.querySelector("#hospital-btn").addEventListener("click", () => {
  window.location.href = "/hospital";
});

document.querySelectorAll(".map-image").forEach((button) => {
  button.addEventListener("click", () => {
    window.location.href = "/hospital";
  });


const selectAdoptList = () => {
  fetch("/main/selectList")
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
          </div>
        `;''

        document.querySelector(".adopt-list").appendChild(petCard);
        });

        // addEventModal();
 
    })
    .catch(err => {
      console.error(err);
      alert("데이터를 불러오는 중 오류가 발생했습니다.");
    });
};

document.addEventListener("DOMContentLoaded", () => {
  selectAdoptList();
});