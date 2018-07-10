/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function setCounter()
{
    var today = new Date();
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
    // add a zero in front of numbers<10
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById('belepett').innerHTML = "Inaktivítás esetén kiléptetés " + h + ":" + m + ":" + s + " időtől számítva 20 perc";
    startTime();
}

function startTime()
{
    var today = new Date();
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
    // add a zero in front of numbers<10
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById('txt').innerHTML = h + ":" + m + ":" + s;
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

function kilepett(){
    javascript:window.location.replace("/Recepttura");
}