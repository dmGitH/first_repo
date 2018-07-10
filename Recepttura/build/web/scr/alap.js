
function validateForm()
{
    var x = document.forms["belep"]["textfield"].value;
    if (x == null || x == "")
    {
        alert("Nem adott meg felhasználói nevet a belépéshez");
        return false;
    }
    var hexhtml = "";
    for (i = 0; i < x.length; i++) {

        hexhtml = hexhtml + "&#" + x.charCodeAt(i).toString()+";";

    }
    document.forms["belep"]["textfield"].value = hexhtml;
}

function torol() {

    var visszair = document.getElementById("bm");
    visszair.value = "";
}

//function elozmenyclear(){
//    var a=0;
//    if(a===0)
//    a++;
//}
