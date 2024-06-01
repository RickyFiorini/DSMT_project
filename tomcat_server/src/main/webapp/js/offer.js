
const OfferWrapper = document.querySelector(".section-wrapper");
const websocketUrl = new WebSocket(`ws://localhost:8081/listing?listingID=${listingID}&username=${currentUsername}`);

function format(/** @type {Date}*/ date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    const seconds = String(date.getSeconds()).padStart(2, "0");
    return `${year}:${month}:${day} ${hours}:${minutes}:${seconds}.0`;
}

function appendOfferComponent(id,imageUrl,PokemonName,Trader,PrimaryType,Attack,Defense,BoxID) {
    const newOfferComponent = document.createElement("div");
    console.log("sto aggiungendo")
    newOfferComponent.classList.add("card", "offer-card");
    newOfferComponent.id = 'Offer_' + id;
    newOfferComponent.innerHTML = `
     <img src="${imageUrl}" class="img-box" alt="Image placeholder">
     <h1>
          ${PokemonName}
     </h1>
      <h2>
          ${PrimaryType}
     </h2>
      <h3>
      Trader: ${Trader}
      </h3>
      <h4>
      Atk: ${Attack} Def: ${Defense}
      </h4>
  `;
    OfferWrapper.appendChild(newOfferComponent);
    if (currentUsername === Trader) {
        const deleteButton = document.createElement('button');
        deleteButton.classList.add('listing-button');
        deleteButton.textContent = 'Delete';
        deleteButton.id = 'deleteButton_'+id;
        deleteButton.onclick = function () {
            Delete(id,BoxID); //
        };
        newOfferComponent.appendChild(deleteButton);
    }

    const listingOwnerUsername = document.getElementById("ListingOwner").textContent.trim();
   console.log(listingOwnerUsername)
    if (currentUsername === listingOwnerUsername ) {
        const tradeButton = document.createElement('button');
        tradeButton.classList.add('listing-button');
        tradeButton.id = 'tradeButton_'+id;
        tradeButton.textContent = 'Trade';
        tradeButton.onclick = function () {
            Trade(id,BoxID,Trader);
        };
        newOfferComponent.appendChild(tradeButton);
    }
}

function handleSend(BoxID) {
    console.log("Ok sto inviando inviare");
    const instant = Date.now();
        websocketUrl.send(
            JSON.stringify({
                BoxID,
                timestamp: instant,
                operation:'insert',
            })
        );
        console.log("ok ho inviato");
        console.log(boxID);
}
function Delete(OfferID,BoxID) {
    console.log("Ok sto inviando delete");
    console.log(BoxID);
    websocketUrl.send(
        JSON.stringify({
            OfferID,
            BoxID,
            operation:'delete',
        })
    );
    console.log("ok ho inviato delete");
}


function DeleteOfferComponent(OfferID) {
    const offerComponent = document.getElementById('Offer_'+OfferID);
    if (offerComponent) {
        offerComponent.parentNode.removeChild(offerComponent);

     }
}

function changeButtonBoxInsert(BoxId) {
    var listedStatus = document.getElementById('listedStatus' + BoxId);
    listedStatus.textContent = "Listed: true";
    var selectButton = document.getElementById('selectButton_' + BoxId);
    selectButton.remove();

}

function changeButtonBoxDelete(BoxId) {
    var box=document.getElementById(BoxId);
    var listedStatus = document.getElementById('listedStatus' + BoxId);
    listedStatus.textContent = "Listed: false";
    const SelectButton = document.createElement('button');
    SelectButton.classList.add('listing-button');
    SelectButton.textContent = 'Select';
    SelectButton.id = "selectButton_"+BoxId;
    SelectButton.type = 'submit';
    SelectButton.onclick = function () {
        handleSend(BoxId); //
    };
    box.appendChild(SelectButton);
}



websocketUrl.onmessage = (event) => {
    const message = JSON.parse(event.data);
    console.log("ok ho ricevuto");
    if (message.type && message.type === "offer") {
        const { OfferID, ImageUrl, PokemonName, Trader, PrimaryType, Attack, Defense,BoxID} = message;
        appendOfferComponent(OfferID,ImageUrl,PokemonName,Trader,PrimaryType,Attack,Defense,BoxID);
        console.log(OfferID);
        if(currentUsername === Trader) {
            console.log(BoxID);
            changeButtonBoxInsert(BoxID);
        }
        return;
    }
    if (message.type && message.type === "delete") {
        const { OfferID,Trader, BoxID} = message;
        DeleteOfferComponent(OfferID);
        if(currentUsername === Trader) {
            console.log(BoxID);
            changeButtonBoxDelete(BoxID);
        }
    }
    if (message.type && message.type === "listing") {
        closeDeleteListingPopup();

    }
    if (message.type && message.type === "trade") {
        const {OfferID, Trader, UserListing} = message;
            ModifyListingWinner(Trader);
            changeTradeButton(OfferID);
        if (UserListing !== currentUsername) {
            closeListingPopup(Trader);
        }
    }
}


websocketUrl.onerror = (event) => {
    console.error(event);
};
