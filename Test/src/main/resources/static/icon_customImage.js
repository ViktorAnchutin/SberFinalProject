
var drone2;
var drone1;

createMap();

function createMap(){

    ymaps.ready(function () {
        var myMap = new ymaps.Map('map', {
            center: [55.741146, 37.531220],
            zoom: 14
        }, {
            searchControlProvider: 'yandex#search'
        });

        // Создаём макет содержимого.
        var MyIconContentLayout = ymaps.templateLayoutFactory.createClass(
            '<div style="color: #FFFFFF; font-weight: bold;">$[properties.iconContent]</div>'
        );

        drone1 = new ymaps.Placemark(myMap.getCenter(), {
            hintContent: 'Собственный значок метки',
            balloonContent: 'Это kkk красивая метка'
        }, {
            // Опции.
            // Необходимо указать данный тип макета.
            iconLayout: 'default#image',
            // Своё изображение иконки метки.
            iconImageHref: 'images/drone.png',
            // Размеры метки.
            iconImageSize: [60, 60],
            // Смещение левого верхнего угла иконки относительно
            // её "ножки" (точки привязки).
            iconImageOffset: [-30, -30],
            hideIconOnBalloonOpen: false
        });

        drone2 = new ymaps.Placemark(myMap.getCenter(), {
            hintContent: 'Собственный значок метки',
            balloonContent: 'Это kkk красивая метка'
        }, {
            // Опции.
            // Необходимо указать данный тип макета.
            iconLayout: 'default#image',
            // Своё изображение иконки метки.
            iconImageHref: 'images/drone.png',
            // Размеры метки.
            iconImageSize: [60, 60],
            // Смещение левого верхнего угла иконки относительно
            // её "ножки" (точки привязки).
            iconImageOffset: [-30, -30],
            hideIconOnBalloonOpen: false
        });


        myMap.geoObjects
            .add(drone1)
            .add(drone2);
    });
}





var lat = 55.741146;
var lon = 37.531220;

function updateMap(){


    lat+=0.001;
    lon+=0.001;
    drone2.geometry.setCoordinates([lat, lon]);
}


setInterval(updateMap, 1000);



var host = "localhost";
var port = 8080;


/*
* {
*       {
*           "copterId":32,
*           "location":
*               {
*                   "lat":345234,
*                   "lon":354356
*               }
*       } ,
*
*       {
*           "copterId":33,
*           "location":
*               {
*                   "lat":345234,
*                   "lon":354356
*               }
*       }
* }
*
* */

/*
* get drones
*
* **/


/*
function updatePosition(){
    var settings = {
        "crossDomain": true,
        "url": "http://" + host + ":" + port + "/copters?working",
        "method": "GET",
    }

    $.ajax(settings).done(function (response) {
        var newRow = "<td>" + response[0].id + "</td><td>" + response[0].login + "</td>";
        $("#tableBody").html("<tr>" + newRow  + "</tr>") ;

        for(var i=1; i<response.length; i++)
        {
            var anotherRow = "<td>" + response[i].id + "</td><td>" + response[i].login + "</td>";
            $("#tableBody").append("<tr>" + anotherRow  + "</tr>") ;
        }

    }).fail(function (jqXHR, textStatus) {
        console.log(jqXHR.status + " " + jqXHR.statusText + ". " + jqXHR.responseText);
    });


}



function loadHistory() {
    var settings = {
        "crossDomain": true,
        "url": "http://" + host + ":" + port + "/chat/chat",
        "method": "GET",
    }
    $.ajax(settings).done(function (response) {
        $("#history").html(response.replace(/\n/g, "<br />"))
        $("#history").scrollTop($("#history")[0].scrollHeight);
    }).fail(function (jqXHR, textStatus) {
        console.log(jqXHR.status + " " + jqXHR.statusText + ". " + jqXHR.responseText);
    });
}
function say() {
    var msg = $('#msgform').serialize();
    var name = $('#nameform').serialize();
    var settings = {
        "method": "POST",
        "crossDomain": true,
        "url": "http://" + host + ":" + port + "/chat/say",
        "data": name + "&" + msg
    };
    $.ajax(settings).done(function (response) {
        $('#msgform').trigger("reset");
        loadHistory();
    }).fail(function (jqXHR, textStatus) {
        alert(jqXHR.status + " " + jqXHR.statusText + ". " + jqXHR.responseText);
        console.log(jqXHR.status + " " + jqXHR.statusText + ". " + jqXHR.responseText);
    });
}
function login() {
    var name = $('#nameform').serialize();
    var settings = {
        "method": "POST",
        "crossDomain": true,
        "url": "http://" + host + ":" + port + "/chat/login",
        "data": name
    };
    $.ajax(settings).done(function (response) {
        loadHistory();
    }).fail(function (jqXHR, textStatus) {
        alert(jqXHR.status + " " + jqXHR.statusText + ". " + jqXHR.responseText);
        console.log(jqXHR.status + " " + jqXHR.statusText + ". " + jqXHR.responseText);
    });
}
loadHistory();
setInterval(loadHistory, 10000);
*/