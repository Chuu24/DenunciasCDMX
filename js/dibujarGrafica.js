var can, ctx,numSamples,xScalar, yScalar,radius, quarter;
var dataName,q1Value,fillColor;

$( document ).ready(function(){
    $.ajax({
	    type: 'POST',
		url: './grafica',
		data: {},
		success: function(data){
		var indiceBarra = data.indexOf("|");;
		var cantidades = data.substr(0,indiceBarra);
		var horas = data.substr(indiceBarra+1,data.length);
		graficar(cantidades, horas);
	    }
	});
});

function graficar(Valores,Nombres){
    var nombres1 = Nombres;
    var indiceComilla1 = nombres1.substr(1,nombres1.length-2);
    dataName = indiceComilla1.split(",");

    var valores1 = Valores;
    var indiceComilla = valores1.substr(1,valores1.length-2);
    q1Value = indiceComilla.split(",").map(Number);

    var q2Value = [ 900000, 900000, 700000, 1800000 ];
    var q3Value = [ 800000, 700000, 600000, 900000 ];
    fillColor = ["#4D24D9", "#FFD40E", "#E60D8C", "#ACF70E", "#492FA2", "#EBC92E", "#BB247C", "#A3DC2B", "#2E2748", "#52622E", "#872D62"];
    // set this value for your data
    numSamples = 9;
    can = document.getElementById("can");
    ctx = can.getContext("2d");
    drawPie(dataName,q1Value);
}

function drawPie(dataName,q1Value) {
    radius = can.height / 3;
    var midX = can.width / 2;
    var midY = can.height / 2;
    ctx.strokeStyle = "black";
    ctx.font = "18pt Helvetica";
    ctx.textAlign = "center";
    ctx.textBaseline = "middle";
    // get data set
    var dataValue = q1Value;

    // calculate total value of pie
    var total = 0;
    for (var i = 0; i < numSamples; i++) {
        total += dataValue[i];
    }
    // get ready to draw
    ctx.clearRect(0, 0, can.width, can.height);
    var oldAngle = 0;

    // for each sample
    for (var i = 0; i < numSamples; i++) {
        // draw wedge
        var portion = dataValue[i] / total;
        var wedge = 2 * Math.PI * portion;
        ctx.beginPath();
        var angle = oldAngle + wedge;
        ctx.arc(midX, midY, radius, oldAngle, angle);
        ctx.lineTo(midX, midY);
        ctx.closePath();
        ctx.fillStyle = fillColor[i];
        ctx.fill();    // fill with wedge color
        ctx.stroke();  // outline in black

        // print label
        // set angle to middle of wedge
        var labAngle = oldAngle + wedge / 2;
        // set x, y for label outside center of wedge
        // adjust for fact text is wider than it is tall
        var labX = midX + Math.cos(labAngle) * radius * 1.3;
        var labY = midY + Math.sin(labAngle) * radius * 1.4;
        // print name and value with black shadow
        ctx.save();
        ctx.shadowColor = "black";
        ctx.shadowOffsetX = 1;
        ctx.shadowOffsetY = -1;
        ctx.fillStyle = fillColor[i];
        ctx.font="15px Verdana";
        if(Math.sin(labAngle) > 0){
			ctx.fillText(dataName[i], labX, labY);
			ctx.fillText(dataValue[i], labX, labY - 15);
		}else{
			ctx.fillText(dataName[i], labX, labY);
			ctx.fillText(dataValue[i], labX, labY + 15);
		}
        ctx.restore();
        // update beginning angle for next wedge
        oldAngle += wedge;
    }
}