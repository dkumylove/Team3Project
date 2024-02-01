window.addEventListener("DOMContentLoaded", function() {
        // Get the select element
        const selectElement = document.querySelector('select[name="optionname"]');

        // Attach an event listener to the select element
        selectElement.addEventListener('change', function () {
            // Get the selected option value
            const selectedOption = selectElement.value.substring(0, 3);

            const { ajaxLoad } = commonLib;
            const url = `/api/admin/option/members?selectedOption=${selectedOption}`;

            ajaxLoad("GET", url, null, "json")
                    .then(data => {
                            document.getElementById('favorite').value = data.memberCount;
                        })
                    .catch(err => console.error(err))
        });

    });