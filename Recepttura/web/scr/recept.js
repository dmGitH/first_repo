/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validateForm()
{
    var etelneve = document.forms["form6"]["etelneve"].value;

    if (etelneve == null || etelneve == "") {
        alert("Nem adta meg az étel nevét");
        return false;
    }
    
}



function setCounter()
{
    
    var today = new Date();
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
  
    m = checkTime(m);
    s = checkTime(s);

    var sec = today.getTime();
    startTime();
}
var sec = 0;
var perc = 0;
function startTime()
{
    sec++;
    if (sec % 60 === 0) {
        sec = 0;
        perc++;
        if (perc === 20) {
            var today = new Date();
            var h = today.getHours();
            var m = today.getMinutes();
            var s = today.getSeconds();

            m = checkTime(m);
            s = checkTime(s);

            document.getElementById('txt').innerHTML = "A felhasználót a szerver kiléptette";
            return;
        }
    }
    document.getElementById('txt').innerHTML = "Eltelt idő: " + checkTime(perc) + " : " + checkTime(sec);
    t = setTimeout('startTime()', 1000);
}
function checkTime(i)
{
    if (i < 10)
    {
        i = "0" + i;
    }
    return i;
}