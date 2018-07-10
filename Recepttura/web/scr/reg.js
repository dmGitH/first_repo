var t = new Date();
var tt = t.getTime();
var vanmeg = tt + 600000;
var perc = 10;
function startTime()
{

    var today = new Date();
    var h = today.getTime();



    if (vanmeg < h) {

        document.getElementById('txt').innerHTML = "A regisztraciós oldal érvényessége lejárt";
        retus();
        vanmeg = 0;
        return;
    } else {

        var sec = ((((vanmeg - h) / 1000)).toFixed(0) % 60);
        if (sec === 0) {
            perc--;
        }
        document.getElementById('txt').innerHTML = "Az oldal érvényességéből hátravan  : " + checkTime(perc) + " : " + checkTime(sec);

    }
    t = setTimeout('startTime()', 1000);
}

function validateForm()
{
    if (vanmeg === 0) {
        alert("A registrációs oldal érvényessége lejárt");
        return false;

    }
    var x = document.forms["form6"]["pw1"].value;
    var x2 = document.forms["form6"]["pw2"].value;
    var mail = document.forms["form6"]["neve"].value;
    var neve = document.forms["form6"]["email"].value;
    var elfogadom = document.forms["form6"]["elfogadom"].checked;
    var eredmeny = document.forms["form6"]["eredmeny"].value;
    var atok = document.forms["form6"]["atok"].value;
    var web = document.forms["form6"]["web"].value;
    var rolam = document.forms["form6"]["rolam"].value;

    if (mail == null || mail == "") {
        alert("Nem adott meg e-mail címet");
        return false;
    }
    if (neve == null || neve == "") {
        alert("Nem adott meg felhasználói nevet");
        return false;
    }

    var atpos = mail.indexOf("@");
    var dotpos = mail.lastIndexOf(".");
    if (atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= mail.length)
    {
        alert("Az email cím nem helyes");

        return false;

    }


    if (x == null || x == "" || x2 == null || x2 == "")
    {
        alert("Nem adott meg jelszót a belépéshez");
        return false;
    } else if (x.length < 6 || x2.length < 6) {
        alert("A jelszó hossza nem megfelelő");
        return false;
    } else if (x != x2) {
        alert("A két megadott jeszó nem egyforma");
        return false;
    }
    if (eredmeny == null || eredmeny == "" || eredmeny.length < 2)
    {
        alert("Kérem írja be a művelet eredményét");
        return false;
    }
    if (x.length > 79) {
        alert("A megadott jelszó túl hosszú.");
        return false;
    } else if (mail.length > 99) {
        alert("A megadott név túl hosszú.");
        return false;
    } else if (neve.length > 99) {
        alert("A megadott e-mail cím túl hosszú.");
        return false;
    } else if (web.length > 254) {
        alert("A megadott webcím túl hosszú.");
        return false;
    } else if (rolam.length > 999) {
        alert("A leírás túl hosszura sikerült.");
        return false;
    }

    if (Number(eredmeny)) {
        alert("A művelet eredményét szövegesen írja be");
        return false;
    }
    if (!elfogadom) {
        alert("A regisztrációs feltételek elfogadása nélkül, nem regisztrálhat az oldalon");
        return false;
    }
    var p1 = document.getElementById("p1");
    var p2 = document.getElementById("p2");
    var n1 = document.getElementById("n1");
    var e1 = document.getElementById("e1");
    var pp1 = document.getElementById("pp1");
    var pp2 = document.getElementById("pp2");
    var nn1 = document.getElementById("nn1");
    var ee1 = document.getElementById("ee1");
    var a1 = document.getElementById("a1");


    p1.value = "p1:" + password();
    p2.value = "p2:" + p1.value;
    n1.value = "n1:" + Math.random();
    e1.value = "e1:" + email();

    pp1.innerHTML = "<input name='pp1' type='hidden' value='" + datacript(atok, x) + "' >";
    pp2.innerHTML = "<input name='pp2' type='hidden' value='" + datacript(atok, x2) + "' >";
    nn1.innerHTML = "<input name='nn1' type='hidden' value='" + datacript(atok, mail) + "' >";
    ee1.innerHTML = "<input name='ee1' type='hidden' value='" + datacript(atok, neve) + "' >";

    var auth = e1.value + p1.value;
    a1.value = datacript(auth, atok);
    vanmeg = 5;
}

function datacript(param1, param2) {

    var tmp = "";
    var valto = 0;
    for (i = 0; i < param2.length; i++) {
        if (param1.length - 1 < valto) {
            valto = 0;
        }

        tmp = tmp + "," + (param2.charCodeAt(i) ^ param1.charCodeAt(valto));
        tmp = tmp.replace(" ", "-");
        valto++;
    }
    return tmp;

}

function email() {

    var h = Math.floor(Math.random() * 30) + 17;
    var k = "";
    var re = "";
    for (i = h; i > 0; i--) {
        k = 0;
        while (k < 97) {
            k = Math.floor(Math.random() * 122);
        }
        re = re + String.fromCharCode(k);
        if (i == 15)
            re = re + "@";
        else if (i == 3)
            re = re + ".";
        else if (i % 4 === 0)
            re = re + Math.floor(Math.random() * 9);
        else if (i % 8 === 0)
            re = re + Math.floor(Math.random() * 9);
        else if (i % 6 === 0)
            re = re + Math.floor(Math.random() * 9);
        else if (i % 5 === 0)
            re = re + Math.floor(Math.random() * 9);
        else if (i % 9 === 0)
            re = re + Math.floor(Math.random() * 9);
        ;
    }
    return re;

}
function password() {
    var h = Math.floor(Math.random() * 30) + 17;
    var k = "";
    var re = "";
    for (i = h; i > 0; i--) {
        k = 0;

        k = Math.floor(Math.random() * 200);

        re = re + String.fromCharCode(k);

    }
    return re;

}

function retus() {

    var p1 = document.getElementById("p1");
    var p2 = document.getElementById("p2");
    var n1 = document.getElementById("n1");
    var e1 = document.getElementById("e1");


    p1.value = "";
    p1.disabled;
    p2.value = "";
    p2.disabled;
    n1.value = "Adatbázis feltöltés";
    n1.disabled;
    e1.value = "Kérem várjon";
    e1.disabled;
    
}

function vitus() {
    var x = document.forms["form6"]["pw1"].value;
    if (x === "X")
        alert("A regisztrációs oldal érvényessége lejárt ha már egyszer elküldte a regisztrációját.\n\n\
        Ebben  az esetben kattintson újra a Regisztráció menügombon ha regisztrálni szeretne.\n\n\
        Ezt az üzenetet akkor is megkapja ha csak elhagyta az oldat.\n\n\
        Ha ez történt folytathatja a regisztrációt ezen a lapon is.");

}

function checkTime(i)
{
    if (i < 10)
    {
        i = "0" + i;
    }
    return i;
}