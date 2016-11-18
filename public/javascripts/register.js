moduleSellbook.controller('register', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("register");


    $scope.hideErrorSU = true;
    $scope.hideSuccessSU = true;
    $scope.hideErrorSC = true;
    $scope.hideSuccessSC = true;


    // register a SU in the database and log him in
    $scope.registerSU = function(name, email, numberAddress, streetAddress, cityAddress, postCodeAddress, phoneNumber, password) {
            console.log("registerSU");
            var request = {
                    method : 'POST',
                    url : '/registerSU',
                    data : $.param({name: name, email: email, numberAddress: numberAddress, streetAddress: streetAddress, cityAddress: cityAddress, postCodeAddress: postCodeAddress, phoneNumber: phoneNumber, password: password}),
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(request).success(function(data){
                $scope.hideSuccessSU = false;
                $scope.titleSuccess = data;
                //login
                        var rqt = {
                                method : 'POST',
                                url : '/login',
                                data : $.param({email: email, password: password}),
                                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                        };
                        $http(rqt).success(function(data){
                            $scope.hideError = true;
                            window.location.reload();
                        }).error(function(data) {
                            $scope.hideError = false;
                            $scope.titleError = data;
                        });
            }).error(function(data) {
                $scope.hideErrorSU = false;
                $scope.titleError = data;
            });
     };

    // register a SC in the database and log him in
     $scope.registerSC = function(name, siret, email, numberAddress, streetAddress, cityAddress, postCodeAddress, phoneNumber, descriptionSeller, password) {
             console.log("registerSC");
             var request = {
                     method : 'POST',
                     url : '/registerSC',
                     data : $.param({name: name, siret: siret, email: email, numberAddress: numberAddress, streetAddress: streetAddress, cityAddress: cityAddress, postCodeAddress: postCodeAddress, phoneNumber: phoneNumber, descriptionSeller: descriptionSeller, password: password}),
                     headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
             };
             $http(request).success(function(data){
                 $scope.hideSuccessSC = false;
                 $scope.titleSuccess = data;
                //login
                 var rqt = {
                         method : 'POST',
                         url : '/login',
                         data : $.param({email: email, password: password}),
                         headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                 };
                 $http(rqt).success(function(data){
                     $scope.hideError = true;
                     window.location.reload();
                 }).error(function(data) {
                     $scope.hideError = false;
                     $scope.titleError = data;
                 });
             }).error(function(data) {
                 $scope.hideErrorSC = false;
                 $scope.titleError = data;
                });
      };


});

function checkPassSU()
      {
          //Store the password field objects into variables ...
          var password = document.getElementById('password');
          var confirmPassword = document.getElementById('confirmPassword');
          //Store the Confimation Message Object ...
          var message = document.getElementById('confirmMessage');
          //Store the button submit
          var button = document.getElementById('buttonRegister');
          //Set the colors we will be using ...
          var goodColor = "#66cc66";
          var badColor = "#ff6666";
          //Compare the values in the password field
          //and the confirmation field
          if(password.value == confirmPassword.value){
              //The passwords match.
              //Set the color to the good color and inform
              //the user that they have entered the correct password
              confirmPassword.style.backgroundColor = goodColor;
              message.style.color = goodColor;
              message.innerHTML = "Passwords Match!"
          }else{
              //The passwords do not match.
              //Set the color to the bad color and
              //notify the user.
              confirmPassword.style.backgroundColor = badColor;
              message.style.color = badColor;
              message.innerHTML = "Passwords Do Not Match!"
              button.disabled = true ;
          }
      }

function checkPassSC()
    {
        //Store the password field objects into variables ...
        var password = document.getElementById('passwordSC');
        var confirmPassword = document.getElementById('confirmPasswordSC');
        //Store the Confimation Message Object ...
        var message = document.getElementById('confirmMessageSC');
        //Store the button submit
        var button = document.getElementById('buttonRegisterSC');
        //Set the colors we will be using ...
        var goodColor = "#66cc66";
        var badColor = "#ff6666";
        //Compare the values in the password field
        //and the confirmation field
        if(password.value == confirmPassword.value){
            //The passwords match.
            //Set the color to the good color and inform
            //the user that they have entered the correct password
            confirmPassword.style.backgroundColor = goodColor;
            message.style.color = goodColor;
            message.innerHTML = "Passwords Match!"
        }else{
            //The passwords do not match.
            //Set the color to the bad color and
            //notify the user.
            confirmPassword.style.backgroundColor = badColor;
            message.style.color = badColor;
            message.innerHTML = "Passwords Do Not Match!"
            button.disabled = true ;
        }
    }

function checkSiret()
    {
        //Store the siret field object into variable ...
        var siret = document.getElementById('siret');
        //Store the Confimation Message Object ...
        var message = document.getElementById('confirmMessageSiret');
        //Set the colors we will be using ...
        var goodColor = "#66cc66";
        var badColor = "#ff6666";
        // check if there are only numeric input
        var isnum = /^\d+$/.test(siret.value);
        //Compare the siret field to the format
        if(siret.value.length == 14 && isnum==true){
            //Set the color to the good color and inform
            //the user that they have entered the correct format fo siret
            siret.style.backgroundColor = goodColor;
            message.style.color = goodColor;
            message.innerHTML = "ok!"
        }else{
            //Set the color to the bad color and
            //notify the user.
            siret.style.backgroundColor = badColor;
            message.style.color = badColor;
            message.innerHTML = "A SIRET is composed of 14 numbers!"
        }
    }
