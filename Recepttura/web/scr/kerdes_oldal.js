var vanmeg = 180;
var j = 0;
function startTime()
{

    var today = new Date();
    var h = today.getTime();

    vanmeg = vanmeg - 1;

    if (vanmeg < 1) {
        document.getElementById('txt').innerHTML = "Az oldal érvényessége lejárt";
        if (j === 0) {
            if (vanmeg % 2 === 0)
                document.getElementById('elkuldve').innerHTML = "A művelet folyamatban".toUpperCase();
            else
                document.getElementById('elkuldve').innerHTML = "A művelet folyamatban";
        } else {
            document.getElementById('elkuldve').innerHTML = "A művelet befejezve";
            return;
        }
    } else {
        document.getElementById('txt').innerHTML = "MAX 180 másodpercig, vagy egy elküldésig érvényes ez az oldal. Ebből " + vanmeg + " sec van vissza";

    }
    t = setTimeout('startTime()', 1000);
}


function validateForm()
{

   
    if (vanmeg < 1) {
        alert("Az oldal érvényességi ideje lejárt. Frissítse ha a műveletet folytatni akarja.");
        return false;
    }
    elkuldve();
    vanmeg = 0;

}

function elkuldve() {

    document.getElementById('elkuldve').innerHTML = "A művelet folyamatban";
}

function stop(){
    document.getElementById('elkuldve').innerHTML = "A művelet befejezve";
    j=1;
    
}




