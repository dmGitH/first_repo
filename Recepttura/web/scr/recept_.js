/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validateForm()
{
    var etelneve = document.forms["form6"]["etelneve"].value
    var mennyiseg = document.forms["form6"]["mennyiseg"].value
    if (etelneve == null || etelneve == "") {
        alert("Nem adta meg az étel nevét");
        return false;
    }
    if (mennyiseg == null || mennyiseg == "") {
        alert("Nem adta meg az alapanyag mennyiségét");
        return false;
    }
    if (!Number(mennyiseg)) {
        alert("A mennyiséget számmal kell megadnia");
        return false;
    }

}

function setCounter()
{
    var today = new Date();
    var h = today.getHours();
    var m = today.getMinutes();
    var s = today.getSeconds();
    // add a zero in front of numbers<10
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById('belepett').innerHTML = "Kiléptetés " + h + ":" + m + ":" + s + " időtől számítva";
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
