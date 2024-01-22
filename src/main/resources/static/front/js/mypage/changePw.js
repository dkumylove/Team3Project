window.addEventListener("DOMContentLoaded", function() {

    const verifiedCntPw = document.getElementById("verifiedCntPw"); // 비밀번호 변경 버튼
    const changePw = document.getElementById("changePw"); // 비밀번호 변경 버튼



    if (verifiedCntPw) {
        verifiedCntPw.addEventListener("click", function() {


            const { ajaxLoad, cntpwCheck } = commonLib;
            const cntpwd = frmChangePw.cntpwd.value.trim();

            if (!cntpwd) {
                alert('현재비밀번호를 입력하세요.');
                // frmJoin.email.focus();
                document.getElementById('cntpwd').focus();
                return;
            }


            cntpwCheck(cntpwd);


        });
    }

    if(changePw) {
        changePw.addEventListener("click", function() {
            alert('안녕');

                        const { ajaxLoad, updatePassword } = commonLib;
                        const newpwd = frmChangePw.newpwd.value.trim();
                        if(validatePassword()){
                        updatePassword(newpwd);
                        };

        });
    }

});

function callbackupdatePassword(data){
    if(data && data.success) {
    alert('성공적으로 변경되었습니다.');
    logoutAndRedirect('/member/login');
    } else{
    alert('변경에 실패하였습니다.');
    }
}

function logoutAndRedirect(redirectUrl) {
    // 세션 종료 로직을 여기에 추가 (세션 종료 API 호출 등)
    // 예시로 location.reload()을 사용하여 현재 페이지를 새로고침하는 대신
    // 로그인 페이지로 리다이렉트하는 예시를 제공합니다.
    window.location.href = redirectUrl;
}

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
        alert('불일치합니다!');
        // const verified = verified.innerHTML = "<span class='confirmed'>비밀번호가 일치하지 않습니다.</span>";
        location.reload();
    }
}


function validatePassword() {
        const newPassword = document.getElementById('newpwd').value;
        const checkpwd = document.getElementById('checkpwd').value;
        // 여기에서 적절한 비밀번호 유효성 검사를 수행하세요.
        // 예를 들어, 대소문자 각각 1개이상, 숫자 1개이상, 특문 1개이상 포함 여부 등을 확인할 수 있습니다.
        document.getElementById('password-error1').innerHTML = '';
        document.getElementById('password-error2').innerHTML = '';

        if (newPassword && checkpwd) {

                const regex = /^(?=.*[a-zA-Z])(?=.*\d)[\w!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]{8,}$/;

                if (!regex.test(newPassword)) {
                    document.getElementById('password-error1').innerHTML = "<span class='confirmed'>비밀번호는 대소문자, 숫자, 특수문자를 모두 포함하고 8자 이상이어야 합니다.</span>";
                } else if (newPassword !== checkpwd) {
                    document.getElementById('password-error2').innerHTML = "<span class='confirmed'>비밀번호가 일치하지 않습니다. 두 비밀번호를 확인해주세요.</span>";
                } else {
                    return true;
                }
            } else {
                document.getElementById('password-error2').innerHTML = "<span class='confirmed'>두 비밀번호를 입력하세요.</span>";
            }
}

