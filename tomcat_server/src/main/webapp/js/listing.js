// Show the user box, so he can select the pokemon to offer
 function showBox(id) {
    const ele = document.getElementById(id);
    ele.style.display = 'grid';
}
// Hide the user box
function hideBox(id) {
    const ele = document.getElementById(id);
    ele.style.display = 'none';
}