const cws = new WebSocket(`ws://localhost:8081/home?username=${currentUsername}`);
// TODO DA METTERE QUANDO SI FANNO LE PROVE CON ERLANG
// const listingsWrapper = document.querySelector(".center.section-wrapper");

// Send the "delete" request to the servlet and remove the listing from the user profile
async function deleteListing(listingID, path, user) {
    const response = await fetch(`${path}?listingID=${listingID}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        },
    });
    if (response.status === 200) {
        const listingList = document.querySelector("div.center.section-wrapper");
        listingList && listingList.removeChild(document.getElementById(listingID));
        // TODO DOPO L'ELIMINAZIONE DI UNA LISTING DEVO NOTIFICARE COLORO CHE HANNO FATTO
        //  UNA OFFER ED ELIMINARE LE OFFER RIGUARDANTE QUELLA LISTING
        //  CON ERLANG?
        nws && nws.send(JSON.stringify({ type: "delete", listingID }));
    }
}

// Send post request to the servlet and handle new listing
function handleNewListing(path, boxID, pokemon){

    const timestamp = Date.now();
    const operation = "insert";

    // Send listing with websocket
    cws.send(
        JSON.stringify({
            boxID: boxID,
            timestamp: timestamp,
            operation: operation,
        })
    );
    // Close websocket connection with ListingServer erlang node
    // TODO DA TOGLIERE QUANDO SI FANNO LE PROVE CON ERLANG
    closeWebsocket();

    // Send post request to the servlet (for user redirection)
    // TODO DA TOGLIERE QUANDO SI FANNO LE PROVE CON ERLANG
    document.getElementById("redirectFormListing_" + boxID).submit();
}

// To close the websocket connection
function closeWebsocket() {
    cws.close();
}

// TODO FUNZIONE DELETE LISTING
// Handle the elimination of a listing
function handleDeleteListing () {

}

/*

// TODO QUANDO RICEVO UN MESSAGGIO DA WEBSOCKET, A SECONDA DEL MESSAGGIO DEVO AGGIUNGERE O ELIMINARE UNA LISTING
// To handle receiving of messages on websocket
cws.onmessage = (event) => {
    const message = JSON.parse(event.data);
    if (message.type && message.type === "listing") {

        const timestamp = formatTimestamp(Date.now());

        // TODO DEBUG
        console.log("Listing ID: " + message.listingID +
            "Winner: " + message.winner + " " +
            "Timestamp: " + timestamp + " " +
            "Username: " + message.username + " " +
            "PokemonName: " + message.pokemonName + " " +
            "ImageURL: " + message.imageURL
        );
        appendListingComponent(message.listingID, timestamp, message.username, message.pokemonName, message.imageURL);
        return;
    }
};

// To dinamically append a new listing in the home
// TODO MODIFICARE LA FUNZIONE PER AGGIUNGERE DINAMICAMENTE UNA NUOVA LISTING
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
*/
