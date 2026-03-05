// js/adminOngoing.js

// DOM 요소 가져오기
const tableContainer = document.getElementById('admin-stock-table-container');
const modal = document.getElementById('sellModal');
const sellForm = document.getElementById('sellForm');

// 1. 페이지 로드 시 진행 중인 투자 목록 불러오기
document.addEventListener('DOMContentLoaded', fetchOngoingInvestments);

async function fetchOngoingInvestments() {
    try {
        // [주의] 백엔드에 '진행중인 투자 목록'을 반환하는 API가 있어야 합니다.
        // 기존에 일반 회원용으로 만든 API(/api/investments/ongoing)를 재사용하거나
        // 관리자용 API(/api/admin/investments/ongoing)를 따로 만드셔도 됩니다.
        const response = await axios.get('/api/investment/ongoing');
        const investments = response.data;

        renderTable(investments);
    } catch (error) {
        console.error('데이터 로드 실패:', error);
        tableContainer.innerHTML = '<p class="error-message">데이터를 불러오지 못했습니다.</p>';
    }
}

// 2. 테이블 렌더링 함수
function renderTable(investments) {
    if (!investments || investments.length === 0) {
        tableContainer.innerHTML = '<p class="loading-message">진행 중인 투자가 없습니다.</p>';
        return;
    }

    let html = `
        <table>
            <thead>
                <tr>
                    <th>등록일</th>
                    <th>종목명</th>
                    <th>진입가</th>
                    <th>목표가</th>
                </tr>
            </thead>
            <tbody>
    `;

    investments.forEach(item => {
        // 각 행(tr)에 클릭 이벤트 연결: 클릭 시 openModal 함수 실행
        // data- 속성을 사용하여 필요한 정보를 HTML에 심어둡니다.
        html += `
            <tr class="clickable-row" 
                data-date="${item.enrollDate}" 
                data-ticker="${item.tickerName}" 
                data-entry="${item.entryPrice}">
                
                <td>${item.enrollDate}</td>
                <td><strong>${item.tickerName}</strong></td>
                <td>${Number(item.entryPrice).toLocaleString()}</td>
                <td>${Number(item.tp).toLocaleString()}</td> 
            </tr>
        `;
    });

    html += `</tbody></table>`;
    tableContainer.innerHTML = html;

    // 테이블 행에 마우스 올렸을 때 커서 변경 (CSS로 처리해도 됨) // 스타일: 마우스 커서 손가락 모양으로 변경
    const rows = document.querySelectorAll('.clickable-row');
    rows.forEach(row => row.style.cursor = 'pointer');
}

// 이벤트 리스너 추가 (이벤트 위임)
// 테이블 컨테이너에 클릭 이벤트를 걸어두면, 그 안의 tr을 클릭했을 때도 감지할 수 있습니다.
tableContainer.addEventListener('click', function(event) {
    // 클릭된 요소가 tr(행) 또는 그 내부 요소인지 확인
    const row = event.target.closest('tr');

    // 클릭된 row가 있고, 우리가 만든 clickable-row 클래스를 가지고 있다면
    if (row && row.classList.contains('clickable-row')) {
        // row의 data- 속성에서 정보를 꺼냅니다.
        const date = row.dataset.date;
        const ticker = row.dataset.ticker;
        const entry = row.dataset.entry;

        // 모달 열기 함수 호출
        openModal(date, ticker, entry);
    }
});

// 3. 모달 열기 함수
function openModal(enrollDate, tickerName, entryPrice) {

    // ID 대신 날짜와 종목명을 숨겨진 input에 저장
    document.getElementById('modalEnrollDate').value = enrollDate;
    document.getElementById('modalTickerName').value = tickerName;

    // 화면 표시용 텍스트 업데이트
    document.getElementById('displayTickerName').textContent = tickerName;
    document.getElementById('displayEntryPrice').textContent = Number(entryPrice).toLocaleString();

    // 입력창 초기화
    document.getElementById('modalSellPrice').value = '';

    // 모달 표시
    modal.classList.remove('hidden');
    // 매도가 입력창에 포커스
    setTimeout(() => document.getElementById('modalSellPrice').focus(), 100);
}

// 4. 모달 닫기 함수
function closeModal() {
    modal.classList.add('hidden');
}


// 5. 폼 제출 (투자 종료 처리)
sellForm.addEventListener('submit', async function(event) {
    event.preventDefault(); // 기본 제출 방지

    //폼에서 데이터를 가져올 때 ID 대신 날짜와 종목명을 가져옴
    const tickerName = document.getElementById('modalTickerName').value;
    const enrollDate = document.getElementById('modalEnrollDate').value;
    const sellPrice = document.getElementById('modalSellPrice').value;

    if (!sellPrice) {
        alert("매도가를 입력해주세요.");
        return;
    }

    try {
        // 서버로 데이터 전송
        // 백엔드 컨트롤러는 이 요청을 받아서:
        // 1. id로 투자를 찾고
        // 2. sellPrice를 저장하고
        // 3. 현재 날짜 - 시작 날짜로 '보유기간' 계산
        // 4. sellPrice > entryPrice 비교하여 'SUCCESS/FAIL' 결정
        console.log(tickerName);
        console.log(enrollDate);
        console.log(sellPrice);
        await axios.post('/admin/investment/result/enroll', {
            tickerName: tickerName,
            enrollDate: enrollDate, // 예: "2024-06-16"
            sellPrice: sellPrice
        });

        alert('투자가 종료되었습니다.');
        closeModal();
        fetchOngoingInvestments(); // 목록 새로고침

    } catch (error) {
        console.error('등록 실패:', error);
        alert('처리 중 오류가 발생했습니다.');
    }
});