const cws = new WebSocket(`ws://10.2.1.97:8082/home?username=${currentUsername}`);

// Send post request to the servlet and handle new listing
function handleNewListing(path, boxID, username){
    const timestamp = Date.now();
    const operation = "insert";
     console.log(currentUsername)
    // Send listing with websocket
    cws.send(
        JSON.stringify({
            username: username,
            boxID: boxID,
            timestamp: timestamp,
            operation: operation,
        })
    );
}

// Handle the elimination of a listing
function handleDeleteListing (listingID) {
    const timestamp = Date.now();
    const operation = "delete";

    // Send listing with websocket
    cws.send(
        JSON.stringify({
            username: currentUsername,
            listingID: listingID,
            timestamp: timestamp,
            operation: operation,
        })
    );
}

// To handle receiving of messages on websocket
cws.onmessage = (event) => {
    const message = JSON.parse(event.data);

    // "Delete" Listing Operation
    if (message.type && message.operation === "delete"){
        console.log("Listing ID: " + message.listingID +
            " Operation: " + message.operation
        );
        hideListingComponent(message.listingID);
    }
    // "Insert" Listing Operation
    else if (message.type && message.operation === "insert"){
        console.log("Listing ID: " + message.listingID +
            " Operation: " + message.operation +
            " Redirecting..."
        );

        // Close the websocket and redirect the user to the listings section of his profile
        closeWebsocket();
        document.getElementById("redirectFormListing_" + message.boxID).submit();
    }
};

// To dinamically hide the deleted listing
function hideListingComponent(listingID){
    const listingComponent = document.getElementById(listingID);
    listingComponent.style.display = 'none';
}

// To close the websocket connection
function closeWebsocket() {
    cws.close();
}
