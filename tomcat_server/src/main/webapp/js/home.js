const cws = new WebSocket(`ws://localhost:8081/home?username=${currentUsername}`);
const listingsWrapper = document.querySelector(".listings-wrapper");

// TODO QUANDO RICEVO UN MESSAGGIO DA WEBSOCKET, A SECONDA DEL MESSAGGIO DEVO AGGIUNGERE O ELIMINARE UNA LISTING
// To handle receiving of messages on websocket
cws.onmessage = (event) => {
    const message = JSON.parse(event.data);

    // TODO Control operation type (insert, delete)
    if (message.type && message.operation === "insert") {

        const timestamp = formatTimestamp(Date.now());

        // DEBUG
        console.log("Listing ID: " + message.listingID +
            "Winner: " + message.winner + " " +
            "Timestamp: " + timestamp + " " +
            "Username: " + message.username + " " +
            "PokemonName: " + message.pokemonName + " " +
            "ImageURL: " + message.imageURL
        );

        // Append the new listing
        appendListingComponent(message.listingID, timestamp, message.username, message.pokemonName, message.imageURL);

    } else if (message.type && message.operation === "delete"){
        console.log("Listing ID: " + message.listingID +
            " Operation: " + message.operation
        );
        hideListingComponent(message.listingID);
    }
};

// To dinamically hide the deleted listing
function hideListingComponent(listingID){
    const listingComponent = document.getElementById(listingID);
    listingComponent.style.display = 'none';
}

// To dinamically append a new listing in the home
function appendListingComponent(listingID, timestamp, username, pokemonName, imageURL) {

    const listingHref = contextPath + "/listing?listingID=" + listingID;

    const newListingComponent = document.createElement("div");
    newListingComponent.id = listingID;
    newListingComponent.classList.add("card");
    newListingComponent.classList.add("listing-card");

    newListingComponent.innerHTML = `
    <a onclick="closeWebsocket()" href="${listingHref}">
                    <img src="${imageURL}" class="img-box" alt="icons/placeholder_pokemon.png">
                    <h1>
                        ${pokemonName}
                    </h1>
                    <h3>
                        ${username}
                    </h3>
                    <h3>
                        Winner: null
                    </h3>
                    <h4>
                        ${timestamp}
                    </h4>
                </a>
  `;
    listingsWrapper.appendChild(newListingComponent);
}

// To format the timestamp correctly
function formatTimestamp() {

    const today = new Date();

    // Get year, month and day
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');

    // Get hours, minutes and seconds
    const hours = String(today.getHours()).padStart(2, '0');
    const minutes = String(today.getMinutes()).padStart(2, '0');
    const seconds = String(today.getSeconds()).padStart(2, '0');

    return `${year}:${month}:${day} ${hours}:${minutes}:${seconds}.0`
}

// Websocket error handling
cws.onerror = (event) => {
    console.error(event);
};

// To close the websocket connection
function closeWebsocket() {
    cws.close();
}
