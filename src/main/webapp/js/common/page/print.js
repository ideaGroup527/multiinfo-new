function printData(id)
{
    var headstr = "<html><head><title></title></head><body>";
    var footstr = "</body>";
    var newstr = $(id).html();
    var can = document.getElementsByTagName("canvas");
    var img = can[0].toDataURL("image/png");
    var oldstr = document.body.innerHTML;
    document.body.innerHTML = headstr+newstr+'<img src="'+img+'"/>'+footstr;
    window.print();
    document.body.innerHTML = oldstr;
    return false;
}