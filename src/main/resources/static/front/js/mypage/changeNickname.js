window.addEventListener("DOMContentLoaded", function() {

    const changeNickname = document.getElementById("changeNickname"); // 비밀번호 변경 버튼

    if (changeNickname) {
        changeNickname.addEventListener("click", function() {


            const { ajaxLoad, updateNickname } = commonLib;
            const newNickname = frmChangeNickname.newNickname.value.trim();

            if (!newNickname) {
                alert('닉네임을 입력하세요.');
                // frmJoin.email.focus();
                document.getElementById('newNickname').focus();
                return;
            }

            updateNickname(newNickname);

        });
    }

});


function callbackupdateNickname(data){
    if(data && data.success) {
        alert('성공적으로 변경되었습니다');
        window.location.href = '/mypage/profile';
    } else{
        alert('변경에 실패하였습니다.');
    }
}

