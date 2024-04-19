

// To search listings
function searchListing() {
    let input = document.getElementById('searchbar').value
    input = input.toLowerCase();
    let togglePastListings = document.getElementById("toggle-past-listings");
    let currentListings = document.getElementsByClassName('listing-card');
    let pastListings = document.getElementsByClassName('listing-card-past');


    // Handle current listings
    for (i = 0; i < currentListings.length; i++) {
        if (currentListings[i].querySelector("h1").innerText.toLowerCase().startsWith(input)) {
            currentListings[i].style.display = "flex";
        }
        else {
            currentListings[i].style.display = "none";
        }
    }

    // Handle past listings
    for (i = 0; i < pastListings.length; i++) {
        if ((togglePastListings.checked) && pastListings[i].querySelector("h1").innerText.toLowerCase().startsWith(input)) {
            pastListings[i].style.display = "flex";
        }
        else {
            pastListings[i].style.display = "none";
        }
    }
}

// Event listener for keypress
document.addEventListener("keypress", function(event) {
    if (event.key === "Enter") {
        searchListing();
    }
});