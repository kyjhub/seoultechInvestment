// postData.js 파일의 가장 위에 이 코드를 추가하세요.
console.log("postData.js 파일이 성공적으로 연결 및 실행되었습니다.");
alert("postData.js 파일 연결 성공!"); // 또는 더 확실하게 alert을 사용

let inputEmail;
let realAthNum;
let inputAthNum;
const config = {
    headers:{"Content-Type": 'application/json'}
}

function postEmailInText(event) { //email 입력값 변수에 저장
    event.preventDefault();
    inputEmail = document.getElementById("email").value;

    const mailDomain = inputEmail.split("@")[1];
    if (mailDomain != "seoultech.ac.kr") {
        alert("서울과학기술대학교 이메일이 아닙니다.\n서울과학기술대학교 이메일을 입력해주세요.");
        return false;
    }

    const data = {
        email: inputEmail,
        athNum: null
    }
    axios.post("http://localhost:8080/email",
        JSON.stringify(data),
        config
    ).then(function (response) {
        realAthNum = response.data; //서버가 제공한 응답데이터
    });
}

function postResultOfAth(event) {
    event.preventDefault();
    inputAthNum = Number(document.getElementById("verification-code").value);

    if (realAthNum === inputAthNum) {
        alert("이메일 인증을 성공했습니다. 회원가입을 해주세요.");
        location.href = "http://localhost:8080/signUp";
    }
    else{
        alert("인증번호가 틀렸습니다.");
    }
}
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('emailBtn').addEventListener('click', postEmailInText);
    document.getElementById('authCodeBtn').addEventListener('click', postResultOfAth);
});