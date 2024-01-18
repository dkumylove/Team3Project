function callbackFileUpload(files) {
    if (!files || files.length == 0) return;

    const file = files[0];

    const targetEl = document.getElementById("profile_image");
    const targetEl2 = document.getElementById("profile_image2");
    const targetEl3 = document.getElementById("profile_image3");
    targetEl.style.backgroundImage = targetEl2.style.backgroundImage = `url('${file.fileUrl}')`;
    targetEl.style.backgroundImage = targetEl3.style.backgroundImage = `url('${file.fileUrl}')`;

    updateMemberInfo();
}

function updateMemberInfo() {
    const { ajaxLoad } = commonLib;
    ajaxLoad("GET", "/api/member/update");
}