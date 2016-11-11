
moduleSellbook.controller('register', function($scope, $http, $window, $cookies, $cookieStore) {

    // CREATE (POST) a person in the database with the information enter in the form
    $scope.registerSU = function(name, email, numberAddress, streetAddress, cityAddress, postCodeAddress, phoneNumber, password) {
            var request = {
                    method : 'POST',
                    url : '/registerSU',
                    data : $.param({name: name, email: email, numberAddress: numberAddress, streetAddress: streetAddress, cityAddress: cityAddress, postCodeAddress: postCodeAddress, phoneNumber: phoneNumber, password: password}),
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(request).success(function(data){
                var request = {
                        method: 'GET',
                        url : '/',
                        headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                };
            }).error(function(data) {
                $scope.hideError = false;
                $scope.titleError = data;
            });
     };

     // CREATE (POST) a person in the database with the information enter in the form
     $scope.registerSC = function(name, siret, email, numberAddress, streetAddress, cityAddress, postCodeAddress, phoneNumber, descriptionSeller, password) {
             var request = {
                     method : 'POST',
                     url : '/registerSC',
                     data : $.param({name: name, siret: siret, email: email, numberAddress: numberAddress, streetAddress: streetAddress, cityAddress: cityAddress, postCodeAddress: postCodeAddress, phoneNumber: phoneNumber, descriptionSeller: descriptionSeller, password: password}),
                     headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
             };
             $http(request).success(function(data){
                 $scope.hideError = true;
             }).error(function(data) {
                 $scope.hideError = false;
                 $scope.titleError = data;
             });
      };



});