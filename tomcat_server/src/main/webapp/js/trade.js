function Trade(OfferID,BoxID,Winner) {
    websocketUrl.send(
        JSON.stringify({
            OfferID,
            BoxID,
            Winner,
            operation:'trade',
        })
    );
}


function ModifyListingWinner(Trader){
    var winner = document.getElementById('winner');
    winner.innerHTML = Trader;
}

function changeTradeButton(OfferID) {

    const offerDivs = document.querySelectorAll('div[id^="Offer_"]');
    offerDivs.forEach(div => {
        const button = div.querySelector('button[id^="tradeButton_"]');
        const button1 = div.querySelector('button[id^="deleteButton_"]');
        if (button) {
            button.remove();
        }
        if (button1) {
            button1.remove();
        }
    });
    const winner = document.createElement("div");
    winner.textContent = `WINNER`;
    const offerIDWinner=document.getElementById('Offer_'+OfferID);
    offerIDWinner.appendChild(winner);

}
function closeListingPopup(Winner) {
        var message = "LISTING CLOSED\nWINNER: " + Winner;

        if (confirm(message)) {
        window.location.href = "http://localhost:8080/tomcat_server/home";
        }
}

function  closeDeleteListingPopup()  {
    var message = "LISTING DELETED\n";
    if (confirm(message)) {
        window.location.href = "http://localhost:8080/tomcat_server/home";
    }
}

