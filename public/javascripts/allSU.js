moduleSellbook.controller('allSU', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("all SU");
    // Authentification
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');


    // Hide the error message at the beginning
    $scope.hideError = true;
    // Hide the success message at the beginning
    $scope.hideSuccess = true;

    $scope.hideID = true;

    $scope.showAllSUInfos = true;
    $scope.showUpdateSUForm = false;

    //Return all database SU
    $scope.getAllSU = function() {
        console.log("AllSU");
        var rqt = {
                method: 'GET',
                url : '/simpleUsers',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.allSimpleUsers = data;
        });
    };

    // Search SU by keyword matcing %nameSU%
    $scope.searchSU = function(nameSU) {
        var rqt = {
                method : 'POST',
                url : '/searchSU',
                data : $.param({nameSU: nameSU}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.allSimpleUsers = data;
        });
    };

    // Confirm suppression
    $scope.confirmDelete = function(su) {
        if (confirm("Do you really want to delete this user ?")){
            var rqt = {
                            method : 'DELETE',
                            url : '/user/' + su.id,
                            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                    };
                    $http(rqt).success(function(data){
                        $scope.getAllSU();
                        $scope.hideSuccess = false;
                        $scope.titleSuccess = data;
                    });
        }
    }

    //Show SU update form
    $scope.updateSU = function(su) {
        var id = su.id;
        $scope.showAllSUInfos = false;
        $scope.showUpdateSUForm = true;

         var rqt = {
            method : 'GET',
            url : '/user/' + id,
            data : $.param({id: id}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
         };
         $http(rqt).success(function(data){
            var suGot = data;
            console.log(data);
            $scope.name = suGot.name;
            $scope.email = suGot.email;
            $scope.numberAddress = suGot.numberAddress;
            $scope.streetAddress = suGot.streetAddress;
            $scope.cityAddress = suGot.cityAddress;
            $scope.postCodeAddress = suGot.postCodeAddress;
            $scope.phoneNumber = suGot.phoneNumber;
            $scope.idSU = suGot.id;
         });
    }

        // Update SU
        $scope.updateSimpleUser = function(name, email, numberAddress, streetAddress, cityAddress, postCodeAddress, phoneNumber, idSU) {
            var rqt = {
                method : 'POST',
                url : '/updateSimpleUser',
                data : $.param({name: name, email: email, numberAddress: numberAddress, streetAddress: streetAddress, cityAddress: cityAddress, postCodeAddress: postCodeAddress, phoneNumber: phoneNumber, id: idSU}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                $scope.getAllSU();
                $scope.showAllSUInfos = true;
                $scope.showUpdateSUForm = false;
                $scope.hideSuccess = false;
                $scope.titleSuccess = "The simple user has been updated";
            });
        }

        // Hide SU update form
        $scope.cancelUpdateForm = function() {
            $scope.showAllSUInfos = true;
            $scope.showUpdateSUForm = false;

        }


});