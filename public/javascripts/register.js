moduleSellbook.controller('register', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("register");
    // CREATE (POST) a person in the database with the information enter in the form

    $scope.hideErrorSU = true;
    $scope.hideSuccessSU = true;
    $scope.hideErrorSC = true;
    $scope.hideSuccessSC = true;



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

     // CREATE (POST) a person in the database with the information enter in the form
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
             }).error(function(data) {
                 $scope.hideErrorSC = false;
                 $scope.titleError = data;
             });
      };



});