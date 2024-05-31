function Trade(OfferID,BoxID,TraderID) {
    console.log("Ok sto inviando Trade");
    console.log(BoxID);
    websocketUrl.send(
        JSON.stringify({
            OfferID,
            BoxID,
            operation:'trade',
        })
    );
    console.log("ok ho inviato trade");
}


function ModifyListingWinner(Trader){
    var winner = document.getElementById('winner');
    winner.innerHTML = Trader;
}

function changeTradeButton(OfferID) {

    const offerDivs = document.querySelectorAll('div[id^="Offer_"]');
    offerDivs.forEach(div => {
        const button = div.querySelector('button[id^="tradeButton_"]');
        if (button) {
            button.remove();
        }
    });
    const winner = document.createElement("div");
    winner.textContent = `WINNER`;
    const offerIDWinner=document.getElementById('Offer_'+OfferID);
    offerIDWinner.appendChild(winner);

}
function closeListingPopup(Winner) {
        var message = "LISTING CLOSED\nWINNER: " + Winner;
        alert(message);
        setTimeout(function() {
            window.location.href = "http://localhost:8080/tomcat_server/home";
        }, 2500); // 5000 millisecondi = 5 secondi
}

function  closeDeleteListingPopup()  {
    var message = "LISTING DELETED\n";
    alert(message);
    setTimeout(function() {
        window.location.href = "http://localhost:8080/tomcat_server/home";
    }, 2500); // 5000 millisecondi = 5 secondi
}

