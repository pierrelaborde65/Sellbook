moduleSellbook.controller('newSUAdmin', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("newSUAdmin");


    $scope.hideErrorSU = true;
    $scope.hideSuccessSU = true;
    $scope.hideErrorSC = true;
    $scope.hideSuccessSC = true;


    // register a SU in the database
    $scope.newSU = function(name, email, numberAddress, streetAddress, cityAddress, postCodeAddress, phoneNumber, password) {
            console.log("newSU");
            var request = {
                    method : 'POST',
                    url : '/registerSU',
                    data : $.param({name: name, email: email, numberAddress: numberAddress, streetAddress: streetAddress, cityAddress: cityAddress, postCodeAddress: postCodeAddress, phoneNumber: phoneNumber, password: password}),
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(request).success(function(data){
                $scope.hideSuccessSU = false;
                $scope.titleSuccess = data;

            }).error(function(data) {
                $scope.hideErrorSU = false;
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


