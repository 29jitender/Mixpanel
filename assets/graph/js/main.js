$( document ).ready( function() {
    
  function makeGraph( keys, values ) {
        var ctx = $("#myChart").get(0).getContext("2d");
        var data = {
            labels : ["January","February","March","April","May","June","July","January","February","March","April","May","June","July"],
            datasets : [
                {
                    fillColor : "rgba(220,220,220,0.5)",
                    strokeColor : "rgba(220,220,220,1)",
                    pointColor : "rgba(220,220,220,1)",
                    pointStrokeColor : "#fff",
                    data : [65,59,90,81,56,55,40,65,59,90,81,56,55,40]
                }
            ]
        };
        new Chart(ctx).Line(data);
    }

    makeGraph();  
})
  