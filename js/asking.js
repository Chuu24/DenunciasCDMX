$(document).ready(function(){
        var pregunta;
        $("#askme").click(function(){
            pregunta = $("#question").val();
            if(pregunta !== ""){
                var q = document.createElement('p');
                q.style.wordWrap = 'break-word';
                q.innerHTML = "Preguntaste: " + pregunta;
                $("#answer").append(q);
                $("#answer").scrollTop($("#answer")[0].scrollHeight);
                $.ajax({
                    type: 'POST',
                    url: './ask',
                    data: {question: pregunta},
                    success: function(data){
                        var p = document.createElement('p');
                        p.style.wordWrap = 'break-word';
                        p.innerHTML = "Watson responde: " + data;
                        $("#answer").append(p);
                    }
                });
                //while ($("#answer").children().length > 10) {
                //    $("#answer").remove($("#answer").children(0));
                //}
                $("#answer").scrollTop($("#answer")[0].scrollHeight);
            }else{
                alert("You haven't asked anything.");
            }
        });
    });
    
$(document).ready(function(){
        var pregunta;
        var fec;
        var hor;
        $("#denuncia").click(function(){
            pregunta = $("#delegacion").val();
            fec = $("#fecha").val();
            hor = $("#hora").val();
            $.ajax({
                type: 'POST',
                url: './denuncia',
                data: {delegacion: pregunta,
                    fechad: fec,
                    horad: hor},
                success: function(data){
                    $("#hora").val("");
                    $("#fecha").val("");
                    alert("Denuncia registrada");
                }
            });
        });
    });