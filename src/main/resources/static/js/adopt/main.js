
const moreBtn = document.getElementById('more-adopt-card');
const adoptListAll = document.querySelector(".adopt-list")

moreBtn.addEventListener("click", () =>{
  selectAdoptList();
})  

const selectAdoptList = (map) => {

  let selectUrl ="/adopt/selectAdoptList"

  if(cp !== undefined){
    selectUrl += `&cp=${cp}`;
  }

  fetch("selectUrl")
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("조회 실패");
  })
  .then(map => {

    const adoptList = map.adoptList;
    const pagination = map.pagination;

    console.log(pagination)
    console.log(adoptList)

    adoptList.forEach( adopt => {
        const petCard = document.createElement('div');
        petCard.className = 'pet-card';
        petCard.innerHTML = `
          <div class="pet-image">
              <img class="open-api-img" src="${adopt.popfile ? adoptList.popfile : '/images/default.png'}" />
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
        `;
        adoptListAll.appendChild(petCard);
    });

  })
  .catch(err=>console.log(err));
};

document.addEventListener("DOMContentLoaded", () => {
    selectAdoptList();
});


// moreBtn.addEventListener('click',function() {
//   adoptList();
// })

// const div = document.createElement('div');
// const span = document.createElement('strong');
// const p = document.createElement('p');
// const img = document.createElement('img');

// function adoptList() {
//   fetch("/adopt/main")
//   .then(response => {
//     if(response.ok) return response.json();
//     throw new Error("조회 실패");
//   })
//   .then(html => {
//     console.log(html);
//     displayPets(adoptList);
//   })
//   .catch(err=>console.log(err));
// }; 


// function displayPets(adoptList) {
//   const petList = document.getElementById('pet-list');

//   adoptList.forEach(adopt => {
//       const petCard = document.createElement('div');
//       petCard.className = 'pet-card';
//       petCard.innerHTML = `
//           <div class="pet-image">
//               <img class="open-api-img" src="${adopt.popfile ? adopt.popfile : '/images/default.png'}" />
//           </div>
//           <div class="content">
//               <div class="condition">${adopt.processState}</div>
//               <p>
//                   <strong>
//                       ${adopt.kindCd} / ${adopt.sexCd}
//                   </strong>
//               </p>
//               <p>
//                   나이: ${adopt.age}
//               </p>
//           </div>
//       `;
//       petList.appendChild(petCard);
//   });

  
// };