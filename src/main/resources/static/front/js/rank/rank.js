window.addEventListener("DOMContentLoaded", function() {
    sortByFavorite();
});


const sortByFavorite = () => {
    const table = document.getElementById("optionTable");
    const rows = table.getElementsByTagName("tr");
    let rank = 1;
    let previousPreference = null;
    let sameRankCount = 1;

    for (let i = 1; i < rows.length; i++) {
        const currentRow = rows[i];
        const preferenceCell = currentRow.getElementsByTagName("td")[2]; // 선호도 열
        const currentPreference = parseInt(preferenceCell.innerText);

        if (previousPreference !== null && currentPreference === previousPreference) {
            sameRankCount++;
        } else {
            if (sameRankCount > 1) {
                rank += sameRankCount - 1; // 같은 순위가 여러 개일 때 순위를 증가시킵니다.
                sameRankCount = 1;
            }
            rank = Math.max(rank, i); // 현재 순위와 현재 항목의 인덱스 중 큰 값을 선택하여 순위를 설정합니다.
        }

        const rankCell = currentRow.getElementsByClassName("rank")[0];
        rankCell.innerText = rank;
        previousPreference = currentPreference;
    }
};

