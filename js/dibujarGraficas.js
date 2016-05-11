var y, can, yScalar;
// data sets -- set literally or obtain from an ajax call
var dataN = [];
var dataV = [];

$( document ).ready(function(){
    $.ajax({
	    type: 'POST',
		url: './graficas',
		data: {},
		success: function(data){
		var indiceBarra = data.indexOf("|");;
		var cantidades = data.substr(0,indiceBarra);
		var horas = data.substr(indiceBarra+1,data.length);
		dataV = cantidades.split(",");
		dataN = horas.split(",");
		init();
	    }
	});
});

function init() {
	var ctx, minVal, maxVal,
            xScalar, numSamples;
	// set these values for your data

	numSamples = 16;
	maxVal = 1000;
	var lol = -1;
	var stepSize = 200;
	var colHead = 50;
	var rowHead = 30;
	var margin = 10;
	var header = "Denuncias"
	can = document.getElementById("cans");
	ctx = can.getContext("2d");
	ctx.fillStyle = "black"
	yScalar = (can.height - colHead - margin) / (maxVal);
	xScalar = (can.width - rowHead) / (numSamples + 1);
	ctx.strokeStyle = "rgba(128,128,255, 0.5)"; // light blue line
	ctx.beginPath();
	// print  column header
	ctx.font = "10pt Helvetica"
	ctx.fillText(header, 0, colHead - margin);
	// print row header and draw horizontal grid lines
	ctx.font = "8pt Helvetica"
	var count =  0;
	for (scale = maxVal; scale >= 0; scale -= stepSize) {
		y = colHead + (yScalar * count * stepSize);
		ctx.fillText(scale, margin,y + 5);
		ctx.moveTo(rowHead, y)
		ctx.lineTo(can.width, y)
		count++;
	}
	ctx.stroke();
	// set a color and a shadow
	ctx.fillStyle = "#e4007c";
	ctx.shadowColor = 'rgba(128,128,128, 0.5)';
	ctx.shadowOffsetX = 2;
	ctx.shadowOffsetY = 1;
	// translate to bottom of graph and scale x,y to match data
	ctx.translate(0, can.height - margin);
	ctx.scale(xScalar, -1 * yScalar);
	// draw bars
	for (i = 0; i < 16; i++) {
		ctx.fillRect(i + 3, 0, 0.3, dataV[i]);
	}
	ctx.setTransform(1, 0, 0, 1, 0, 0);
	// label samples
	ctx.fillStyle = "black";
	ctx.font = "5pt Helvetica";
	ctx.textBaseline = "bottom";
	for (i = 0; i < 16; i++) {
		calcY(dataV[i]);
		ctx.fillText(dataN[i], xScalar * (i + 2), y - (margin * lol));
		if(lol == 1){
			lol = -1;
		}else{
			lol = 1;
		}
	}
}

function calcY(value) {
	y = can.height - parseInt(value) * yScalar;
}