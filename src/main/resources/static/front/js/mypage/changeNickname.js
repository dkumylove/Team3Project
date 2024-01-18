window.addEventListener("DOMContentLoaded", function() {

    const changeNickname = document.getElementById("changeNickname"); // 비밀번호 변경 버튼

    if (changeNickname) {
        changeNickname.addEventListener("click", function() {

            alert('안녕');

            const { ajaxLoad, updateNickname } = commonLib;
            const newNickname = frmChangeNickname.newNickname.value.trim();

            if (!newNickname) {
                alert('닉네임을 입력하세요.');
                // frmJoin.email.focus();
                document.getElementById('newNickname').focus();
                return;
            }

            /* 이메일 확인 전 이미 가입된 이메일인지 여부 체크 S */
            //ajaxLoad("GET", `/api/mypage/changePwCheck?cntpwd=${cntpwd}`, null, "json")

            updateNickname(newNickname);

        });
    }

});


function callbackupdateNickname(data){
    if(data && data.success) {
    alert('성공!');
    window.location.href = '/mypage/profile';
    } else{
    alert('실패!');
    }
}
