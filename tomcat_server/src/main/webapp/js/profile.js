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

