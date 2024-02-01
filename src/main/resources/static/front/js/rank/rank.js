window.addEventListener("DOMContentLoaded", function() {
    sortByFavorite();
});


   function sortByFavorite() {
        var table, rows, switching, i, x, y, shouldSwitch;
        table = document.getElementById("optionTable");
        switching = true;
        while (switching) {
            switching = false;
            rows = table.rows;
            for (i = 1; i < (rows.length - 1); i++) {
                shouldSwitch = false;
                x = rows[i].getElementsByTagName("TD")[2]; // 선호도 열
                y = rows[i + 1].getElementsByTagName("TD")[2]; // 다음 행의 선호도 열
                if (parseInt(x.innerHTML) < parseInt(y.innerHTML)) {
                    shouldSwitch = true;
                    break;
                }
            }
            if (shouldSwitch) {
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switching = true;
            }
        }
        updateRank();
    }

   function updateRank() {
        var table = document.getElementById("optionTable");
        var rows = table.rows;
        for (var i = 1; i < rows.length; i++) {
            rows[i].getElementsByTagName("TD")[0].innerHTML = i; // 순위 열 업데이트
        }
    }


