

function validateForm()
{
    var mennyiseg = document.forms["alapanyag_hozzaadas"]["mennyisege"].value;
    
    if (mennyiseg == null || mennyiseg == "") {
        alert("Nem adta meg az alapanyag mennyiségét");
        return false;
    }
    if(!isFinite(mennyiseg)){
        
        alert("A mennyiséget számmal kell megadnia");
        return false;
    }

}

function vissza() {

    var leiras = document.forms["alapanyag_hozzaadas"]["textarea"].value;
    document.getElementById('leiras').innerHTML = "<input name='leiras' type='hidden'  value='" + leiras + "'>";
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
    
    var sec = today.getTime();
    startTime();
}
var sec=0;
var perc=0;
function startTime()
{
	sec++
	if(sec%60===0){
		sec=0;
		perc++;
		if(perc===20){
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
    document.getElementById('txt').innerHTML = "Eltelt idő: "+checkTime(perc)+" : "+ checkTime(sec);
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