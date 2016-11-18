moduleSellbook.controller('allSU', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("all SU");
    // Test if the person can stay on the page
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');


    // Hide the error message at the beginning
    $scope.hideError = true;
    // Hide the success message at the beginning
    $scope.hideSuccess = true;

    $scope.hideID = true;

    $scope.showAllSUInfos = true;
    $scope.showUpdateSUForm = false;

    //Get all the SU in the database
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

    // filter name SU by keyword
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

    // Delete the product
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

    //Show update Form
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

        $scope.cancelUpdateForm = function() {
            $scope.showAllSUInfos = true;
            $scope.showUpdateSUForm = false;

        }


});