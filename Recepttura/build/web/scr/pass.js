/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var vanmeg;
function startTime()
{
    var y = document.forms["form1"]["HDID"].value;
    var today = new Date();
    var h = today.getTime();
    var sectime = document.forms["form1"]["HDTOKEN"].value;
    vanmeg = parseInt(((sectime / y) - h) / 1000) * -1;

    if (vanmeg > 180) {
        document.getElementById('txt').innerHTML = "A belépőoldal érvényessége lejárt";
        var invalid = document.getElementById("pass");
        invalid.innerHTML = "<input name='pass' type='hidden' value='xxxxxxx' >";
        return;
    } else {
        document.getElementById('txt').innerHTML = "180 másodpercig írhat be jelszót. " + vanmeg + " eltelt";

    }
    t = setTimeout('startTime()', 1000);
}


function validateForm()
{

    var x = document.forms["form1"]["pass"].value;
    var y = document.forms["form1"]["HDID"].value;
    var user = document.forms["form1"]["atok"].value;
    var visszair2 = document.getElementById("pass");
    
    if(vanmeg>180){
        alert("Kedves "+user+" a próbálkozásra rendelkezésre álló idő sajnos lejárt.");
        return false;
    }
    
    if (x == null || x == "")
    {
        alert("Nem adott meg jelszót");
        return false;
    } else if (x.length < 6) {
        alert("A jelszó nem lehet érvényes a belépéshez");
        visszair2.value="";
        return false;
    } else if (y < 0) {
        var t=setTimeout("alertMsg()",Math.floor(Math.random()*2000)+500);
        
        return false;
    }
    var oldx=x;
    x=x.toUpperCase()+x+x.toLowerCase();
    var hdid = document.forms["form1"]["HDTOKEN"].value;
    var atok = document.forms["form1"]["atok"].value;
    var temp = String(hdid);
    var visszair = document.getElementById("visszadob");
    var version = document.getElementById("vers");
    
    var err = "";
    var a = 0;
    var jsz = "";
    if (hdid.length > x.length) {
        for (i = 0; i < hdid.length; i++) {
            if (a > x.length - 1)
                a = 0;
            if(temp.charCodeAt(i) < x.charCodeAt(a))err = err + temp.charCodeAt(i) + x.charCodeAt(a);
            else err = err +  x.charCodeAt(a)+ temp.charCodeAt(i);
            a++;
            
        }
    } else {
        for (i = 0; i < x.length; i++) {
            if (a > hdid.length - 1)
                a = 0;
            if(temp.charCodeAt(a) < x.charCodeAt(i))err = err + temp.charCodeAt(a) + x.charCodeAt(i);
            else err = err +  x.charCodeAt(i)+ temp.charCodeAt(a);
            a++;

        }
    }
    for (k = 0; k < 20; k++) {
        err = err + err.charAt(k) + atok.charAt((atok.length - 2) - k);
    }
    err = xor(err);

    var vlis = new Array("BrowserCodeName: " + navigator.appCodeName, "BrowserName: " + navigator.appName, "BrowserVersion: " + navigator.appVersion, "CookiesEnabled: " + navigator.cookieEnabled, "Platform: " + navigator.platform, "User-agentheader: " + navigator.userAgent);
    var vlist = vlis.toString();

    while (vlist.match(" ") !== null)
    {
        vlist = vlist.replace(" ", "-");
    }
    for (i = 0; i < oldx.length; i++) {
        jsz = jsz + i;
    }

    visszair2.value = "";
    visszair2.value = jsz;
    visszair.innerHTML = "<input name='return' type='hidden' value=" + err + " >";
    version.innerHTML = "<input name='ver' type='hidden' value=" + vlist.toString() + " >";

}

function xor(param) {
    var ato = document.forms["form1"]["atok"].value;
    var tmp = "";
    var valto = 0;
    for (i = 0; i < param.length; i++) {
        if (ato.length - 1 < valto) {
            valto = 0;
        }

        tmp = tmp + (param.charCodeAt(i) ^ ato.charCodeAt(valto));
        tmp = tmp.replace(" ", "-");
        valto++;
    }
    return tmp;
}

function alertMsg(){
    
    alert("A felhasználói névhez nem ez a jelszó tartozik\nMég "+(180-vanmeg)+" másodpercig próbálkozhat");
    var visszair2 = document.getElementById("pass");
    visszair2.value="";
}

