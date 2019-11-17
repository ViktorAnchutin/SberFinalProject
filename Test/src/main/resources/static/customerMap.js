
var drone;
var home = [55.741146, 37.531220];

function createCustomerMap(){

    ymaps.ready(function () {
        var myMap = new ymaps.Map('map', {
            center: home,
            zoom: 14
        }, {
            searchControlProvider: 'yandex#search'
        });

        // Создаём макет содержимого.
        var MyIconContentLayout = ymaps.templateLayoutFactory.createClass(
            '<div style="color: #FFFFFF; font-weight: bold;">$[properties.iconContent]</div>'
        );

        drone = new ymaps.Placemark(home, {
            hintContent: 'Drone with your order',
            balloonContent: 'I am coming !'
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


        myMap.geoObjects.add(drone);

    });
}

function updateDronePosition(lat, lon){
    drone.geometry.setCoordinates([lat, lon]);
}


/*
*   Simulation
* */
/*
createCustomerMap();

var simLat = 55.741146;
var simLon = 37.531220;
function update(){
    simLat+=0.0005;
    simLon-=0.0005;
    updateDronePosition(simLat, simLon);
}


setInterval(update, 1000);

*/