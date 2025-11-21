// performance.js

// DOM이 완전히 로드된 후에 스크립트를 실행합니다.
document.addEventListener('DOMContentLoaded', function () {

    // 데이터를 가져와서 화면에 테이블을 그리는 메인 함수
    async function renderPerformanceTable() {
        const container = document.getElementById('performance-table-container');

        try {
            // 1. 서버로부터 데이터 비동기 요청
            const performanceList = await getPerformanceList();

            // 데이터가 없거나 비어있는 경우
            if (!performanceList || performanceList.length === 0) {
                container.innerHTML = '<p class="loading-message">표시할 데이터가 없습니다.</p>';
                return;
            }

            // 2. 데이터로 테이블 HTML 문자열 생성
            const tableHtml = createTableHtml(performanceList);

            // 3. 컨테이너의 내용을 생성된 테이블 HTML로 교체
            container.innerHTML = tableHtml;

        } catch (error) {
            console.error('데이터를 불러오는 데 실패했습니다:', error);
            container.innerHTML = '<p class="loading-message">데이터를 불러오는 중 오류가 발생했습니다.</p>';
        }
    }

    // Axios를 이용해 서버에서 데이터를 가져오는 함수
    async function getPerformanceList() {
        const response = await axios.get("http://localhost:8080/stock/result", {
            headers: {'Content-Type': 'application/json'}
        });
        return response.data;
    }

    // 전달받은 데이터로 테이블 전체의 HTML 문자열을 만드는 함수
    function createTableHtml(data) {
        // thead (테이블 헤더) 부분 생성
        const header = `
            <thead>
                <tr>
                    <th>등록일</th>
                    <th>종목명</th>
                    <th>보유 기간</th>
                    <th>매도가</th>
                    <th>수익률</th>
                    <th>진행상황</th>
                </tr>
            </thead>
        `;

        // tbody (테이블 바디) 부분 생성
        const body = data.map(item => `
            <tr>
                <td>${item.enrollDate}</td>
                <td>${item.tickerName}</td>
                <td>${item.holdTerm}</td>
                <td>${item.sellPrice.toLocaleString()}</td>
                <td>${item.earningRate}%</td>
                <td>${createStatusPill(item.status)}</td>
            </tr>
        `).join(''); // map으로 생성된 배열을 하나의 문자열로 합침

        // asembled table
        return `
            <table>
                ${header}
                <tbody>
                    ${body}
                </tbody>
            </table>
        `;
    }

    // SUCCESS/FAIL 상태에 따라 다른 스타일의 HTML 태그를 반환하는 함수
    function createStatusPill(status) {
        if (status === 'SUCCESS') {
            return `<span class="status-pill status-success">SUCCESS</span>`;
        } else if (status === 'FAIL') {
            return `<span class="status-pill status-fail">FAIL</span>`;
        }
        return status; // 그 외의 경우 텍스트만 반환
    }

    // 페이지 로드 시 메인 함수 실행
    renderPerformanceTable();
});