

var home = {
    lat : 55.741146,
    lon : 37.531220
};

var coptersMap = new Map(); // keeps references to drones placeMark objects
var selectedCopter; // keeps a reference to the selected drone's placeMark. I need it to ensure having only one selected copter
var myMap; // the very map itself



// should call it once when loading the page
function createAdminMap(){
    ymaps.ready(function () {
            myMap = new ymaps.Map('map', {
            center: [home.lat, home.lon],
            zoom: 14
        }, {
            searchControlProvider: 'yandex#search'
        });

        // Создаём макет содержимого.
        var MyIconContentLayout = ymaps.templateLayoutFactory.createClass(
            '<div style="color: #FFFFFF; font-weight: bold;">$[properties.iconContent]</div>'
        );
    });
}




// creates a placeMark for a drone , adds it the map and saves the ref to it into a dictionary
function addCopterObject(copter){
  //  console.log("trying to add copter: " + copter.id);
    var location = copter.location;
    ymaps.ready(function () {
        var newPlaceMark = new ymaps.Placemark([location.lat, location.lon], {
            hintContent: copter.id, // save the id here to be able to get it once placeMark is selected
            balloonContent: copter.id
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
            hideIconOnBalloonOpen: false,
            openBalloonOnClick: false
        });
        var newCopter = {
            placeMark :  newPlaceMark,
            updated : true
        };
        coptersMap.set(copter.id, newCopter);
        newPlaceMark.events.add('click', clickOnPlaceMark);
        myMap.geoObjects.add(newPlaceMark);
    });
}

function clickOnPlaceMark(e) {
    var placeMark = e.get("target");
    if(selectedCopter!=null && placeMark===selectedCopter) return;
    placeMark.options.set("iconImageHref", 'images/selectedDrone.png');
    //call function to show telemetry
    if(selectedCopter!=null){
        selectedCopter.options.set("iconImageHref", 'images/drone.png');
    }
    selectedCopter = placeMark;
    var selectedCopterId = placeMark.properties._data.hintContent; // get selected drone id saved in a placeMark
    showTelemetry(selectedCopterId);
}

// update copter location on the map or add it to the map
function update(copter){
  var location = copter.location;
  if(coptersMap.has(copter.id)) {
     // console.log("has copter: " + copter.id);
      var copterObj = coptersMap.get(copter.id);
      copterObj.placeMark.geometry.setCoordinates([location.lat, location.lon]);
      copterObj.updated = true;
  }
  else{
      addCopterObject(copter);
  }
}


function deleteIfNotUpdated() {
   // console.log("delete or not");

    coptersMap.forEach(function (value, key, map) {
      //  console.log('Testing ' + key + "having updated = " + value.updated);
        if(value.updated===false){
          //  console.log("deleting copter: " + key);
            map.delete(key);                                // delete object if there was info about it in the last update
            myMap.geoObjects.remove(value.placeMark);
        }
    });



}


function updateCopters(copters){
    coptersMap.forEach(function (value, key, map) {
      //  console.log("reseting flag for " + key)
        value.updated = false;                      // reset update flags before processing new information
    });
    copters.forEach(update);
    deleteIfNotUpdated();
}






