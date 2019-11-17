
createAdminMap();

var copterFirstLocation = { lat:home.lat, lon : home.lon};
var copterSecondLocation = {lat:home.lat + 0.05, lon : home.lon + 0.05};
var copterIdFirst = 6;
var copterIdSecond = 5;

var count = 1;


function updateSimulation() {

    if(count>10){           // launch a new copter every 10 seconds instead of old one
        copterIdFirst++;
        copterFirstLocation = { lat:home.lat, lon : home.lon};
        count=0;
    }

    copterFirstLocation.lat+=0.001;
    copterFirstLocation.lon+=0.001;

    copterSecondLocation.lat+=0.003;
    copterSecondLocation.lon+=0.001;
    var coptersTest = [
        { id : copterIdFirst, location: {lat:copterFirstLocation.lat, lon : copterFirstLocation.lon}},
        { id : copterIdSecond, location: {lat:copterSecondLocation.lat, lon : copterSecondLocation.lon}}
    ];
    //console.log("Going to send ", coptersTest);
    updateCopters(coptersTest);

    count++;
}


function showTelemetry(id){
    console.log("selected copter id: " + id);
}

setInterval(updateSimulation, 1000);

