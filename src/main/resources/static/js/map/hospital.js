document.addEventListener("DOMContentLoaded", function() {
    // 위치 오류 처리 함수
    function showError(error) {
        const errorMessages = {
            [error.PERMISSION_DENIED]: "사용자가 위치 정보를 허용하지 않았습니다.",
            [error.POSITION_UNAVAILABLE]: "위치 정보를 사용할 수 없습니다.",
            [error.TIMEOUT]: "요청 시간이 초과되었습니다.",
            [error.UNKNOWN_ERROR]: "알 수 없는 오류가 발생했습니다."
        };
        document.getElementById('location').innerHTML = errorMessages[error.code] || "알 수 없는 오류가 발생했습니다.";
    }

    // 네이버 지도 API 로드 및 초기화
    async function loadNaverMapScript() {
        try {
            const response = await fetch('/api/naver-map-key');
            if (!response.ok) throw new Error('네이버 지도 API 키를 가져오지 못했습니다.');
            const apiKey = await response.text();

            const script = document.createElement('script');
            script.src = `https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${apiKey}&submodules=geocoder`;
            script.onload = getLocationAndSearch;
            document.head.appendChild(script);
        } catch (error) {
            console.error('네이버 지도 API 키 로드 중 오류 발생:', error);
            document.getElementById('location').innerHTML = "네이버 지도 API 키를 불러오는 데 문제가 발생했습니다.";
        }
    }

    // 사용자 위치 정보 가져오기 및 병원 검색
    function getLocationAndSearch() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(async (position) => {
                const lat = position.coords.latitude;
                const lon = position.coords.longitude;
                initializeMap(lat, lon);
                await displayAllHospitals(lat, lon);
            }, showError);
        } else {
            document.getElementById('location').innerHTML = "이 브라우저는 Geolocation을 지원하지 않습니다.";
        }
    }

    let map;
    let searchMarkers = [];

    // 지도 초기화
    function initializeMap(lat, lon) {
        document.getElementById('location').innerHTML = `위도: ${lat}, 경도: ${lon}`;
        map = new naver.maps.Map('map', {
            center: new naver.maps.LatLng(lat, lon),
            zoom: 15
        });
        new naver.maps.Marker({
            position: new naver.maps.LatLng(lat, lon),
            map: map,
            title: "내 위치",
            icon: {
                content: '<div style="background-color:red;width:20px;height:20px;border-radius:50%;"></div>',
                anchor: new naver.maps.Point(10, 10)
            }
        });
    }

    // 기존 마커 삭제
    function clearMarkers() {
        searchMarkers.forEach(marker => marker.setMap(null));
        searchMarkers = [];
    }

    // 병원 데이터 표시
    async function displayAllHospitals(lat, lon) {
        clearMarkers();
        let nearbyHospitals = [];
        const responses = await Promise.allSettled([
            searchNearbyHospitals('동물병원', lat, lon),
            fetchSeoulAnimalHospitals(lat, lon),
            fetchGyeonggiAnimalHospitals(lat, lon)
        ]);

        responses.forEach((res, index) => {
            if (res.status === 'fulfilled') {
                console.log(`API ${index + 1} 응답 성공:`, res.value);
                nearbyHospitals.push(...res.value);
            } else {
                console.error(`API ${index + 1} 요청 실패:`, res.reason);
            }
        });

        if (nearbyHospitals.length === 0) {
            alert("5km 이내에 병원이 없습니다.");
        } else {
            displaySearchResultsOnMap(nearbyHospitals); // 병원이 있을 때만 호출
        }
    }

    // 검색 결과를 지도에 표시
    function displaySearchResultsOnMap(hospitals) {
        hospitals.forEach(hospital => {
            const marker = new naver.maps.Marker({
                position: new naver.maps.LatLng(hospital.latitude, hospital.longitude),
                map: map,
                title: hospital.name
            });
            searchMarkers.push(marker);
        });
    }

    // 검색 버튼 클릭 이벤트
    function handleSearch() {
        const keyword = document.getElementById("search-input").value.trim();
        if (keyword) {
            clearMarkers();
            fetch(`/api/searchHospitals?keyword=${encodeURIComponent(keyword)}`)
                .then(response => response.json())
                .then(displaySearchResultsOnMap)
                .catch(error => console.error("검색 API 오류:", error));
        } else {
            alert("검색어를 입력해주세요.");
        }
    }

    document.addEventListener('DOMContentLoaded', loadNaverMapScript);
});
