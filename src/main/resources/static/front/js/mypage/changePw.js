window.addEventListener("DOMContentLoaded", function() {

    const verifiedCntPw = document.getElementById("verifiedCntPw"); // 비밀번호 변경 버튼

    if (verifiedCntPw) {
        verifiedCntPw.addEventListener("click", function() {

            alert('안녕');

            const { ajaxLoad, cntpwCheck } = commonLib;
            const cntpwd = frmChangePw.cntpwd.value.trim();

            if (!cntpwd) {
                alert('현재비밀번호를 입력하세요.');
                // frmJoin.email.focus();
                document.getElementById('cntpwd').focus();
                return;
            }

            /* 이메일 확인 전 이미 가입된 이메일인지 여부 체크 S */
            //ajaxLoad("GET", `/api/mypage/changePwCheck?cntpwd=${cntpwd}`, null, "json")

            cntpwCheck(cntpwd);

            // 비밀번호 수정
//            cntpwCheck(cntpwd).then(data => {
//                                if (data.success) { // 확인완료
//                                    alert("확인되었습니다.");
//                                    frmChangePw.cntpwd.focus();
//                                } else {
//                                    alert("다시 입력해주세요.");
//                                    frmChangePw.cntpwd.focus();
//                                }
//                        }).catch(error => {
//                                   console.error("AJAX 요청 중 오류 발생:", error);
//                                   // 오류를 적절히 처리하세요. 예를 들어 사용자에게 일반적인 오류 메시지를 표시합니다.
//                               });
        });
    }
});



/**
* 인증메일 코드 검증 요청 후 콜백 처리
*
* @param data : 인증 상태 값
*/
function callbackcntPwVerifyCheck(data) {
    if (data && data.success) { // 인증 성공
        /**
        * 인증 성공시
        * 1. 인증 카운트 멈추기
        * 2. 인증코드 전송 버튼 제거
        * 3. 이메일 입력 항목 readonly 속성으로 변경
        * 4. 인증 성공시 인증코드 입력 영역 제거
        * 5. 인증 코드 입력 영역에 "확인된 이메일 입니다."라고 출력 처리
        */

        // 1. 버튼 제거
        const verifiedCntPw = document.getElementById("verifiedCntPw");
        verifiedCntPw.parentElement.removeChild(verifiedCntPw);

        // 2. 메세지 출력
        const verified = document.querySelector(".verified");
        verified.innerHTML = "<span class='confirmed'>확인되었습니다.</span>";

        // 3. 버튼 활성화
        const changePw = document.getElementById("changePw");
        changePw.removeAttribute('disabled');

    } else { // 인증 실패
        alert("비밀번호가 불일치 합니다.");
    }
}
