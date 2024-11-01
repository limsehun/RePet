

document.addEventListener('DOMContentLoaded', function () {

    const searchButton = document.querySelector('#boardSearch button');

    searchButton.addEventListener('click', function () {
        const key = document.querySelector('#boardSearch select').value;
        const query = document.querySelector('#boardSearch input').value.trim();

        if (key && query) {
            // Perform search or redirect to search results
            const currentUrl = window.location.pathname;
            const searchUrl = `${currentUrl}?key=${key}&query=${encodeURIComponent(query)}`;
            window.location.href = searchUrl;
        } else {
            alert('Please select a search key and enter a query.');
        }
    });
});
