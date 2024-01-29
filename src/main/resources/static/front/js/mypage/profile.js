function callbackFileUpload(files) {
    if (!files || files.length == 0) return;

    const file = files[0];

    const targetEl = document.getElementById("profile_image");
    const targetEl2 = document.getElementById("profile_image2");
    const targetEl3 = document.getElementById("profile_image3");

    // 업로드된 파일의 url을 이용하여 프로필 이미지 업데이트
    targetEl.style.backgroundImage = targetEl2.style.backgroundImage = targetEl3.style.backgroundImage = `url('${file.fileUrl}')`;

    // 회원정보 업데이트
    updateMemberInfo();
}

function updateMemberInfo() {
    const { ajaxLoad } = commonLib;

    // 서버에 회원정보 업데이트 요청
    ajaxLoad("GET", "/api/mypage/update");
}