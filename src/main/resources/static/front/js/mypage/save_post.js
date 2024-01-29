window.addEventListener("DOMContentLoaded", function() {
    const subjects = document.getElementsByClassName("subject");
    for (const el of subjects) {
        // 제목 클릭 -> 내용이 이미 보이고 있으면 감추기, 감춰진 상태 -> 보이기 (토글)
        el.addEventListener("click", function() {
            const contentEl = el.nextElementSibling;
            const classList = contentEl.classList;
            classList.toggle('hide');

            // 아이콘 토글
            const toggleIcons = el.getElementsByClassName('toggle-icons')[0];
            const downIcon = toggleIcons.querySelector('.xi-angle-down');
            const upIcon = toggleIcons.querySelector('.xi-angle-up');

            if (classList.contains('hide')) {
                downIcon.style.display = '';
                upIcon.style.display = 'none';
            } else {
                downIcon.style.display = 'none';
                upIcon.style.display = '';
            }
        });
    }
});