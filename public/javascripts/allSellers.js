moduleSellbook.controller('allSellers', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("all Sellers");
    // Test if the person can stay on the page
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');


    // Hide the error message at the beginning
    $scope.hideError = true;
    // Hide the success message at the beginning
    $scope.hideSuccess = true;

    $scope.hideID = true;

    $scope.showAllSCInfos = true;
    $scope.showUpdateSCForm = false;

    //Get all the SC in the database
    $scope.getAllSellers = function() {
        console.log("AllSellers");
        var rqt = {
                method: 'GET',
                url : '/sellers',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.allSellersCompanies = data;
        });
    };

    // filter name Sellers by keyword
    $scope.searchSeller = function(nameSeller) {
        var rqt = {
                method : 'POST',
                url : '/searchSeller',
                data : $.param({nameSeller: nameSeller}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.allSellersCompanies = data;
        });
    };


    // Delete the product
    $scope.confirmDelete = function(sc) {
        if (confirm("Do you really want to delete this user ?")){
            var rqt = {
                            method : 'DELETE',
                            url : '/user/' + sc.id,
                            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                    };
                    $http(rqt).success(function(data){
                        // il faut supprimer les produits associées ici
                        $scope.getAllSellers();
                        $scope.hideSuccess = false;
                        $scope.titleSuccess = data;
                    });
        }
    }

    //Show update Form
    $scope.updateSC = function(sc) {
        var id = sc.id;
        $scope.showAllSCInfos = false;
        $scope.showUpdateSCForm = true;

         var rqt = {
            method : 'GET',
            url : '/user/' + id,
            data : $.param({id: id}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
         };
         $http(rqt).success(function(data){
            var scGot = data;
            console.log(data);
            $scope.name = scGot.name;
            $scope.siret = scGot.siret;
            $scope.email = scGot.email;
            $scope.numberAddress = scGot.numberAddress;
            $scope.streetAddress = scGot.streetAddress;
            $scope.cityAddress = scGot.cityAddress;
            $scope.postCodeAddress = scGot.postCodeAddress;
            $scope.phoneNumber = scGot.phoneNumber;
            $scope.descriptionSeller = scGot.descriptionSeller;
            $scope.idSC = scGot.id;
         });
    }


        $scope.updateSeller = function(name, siret, email, numberAddress, streetAddress, cityAddress, postCodeAddress, phoneNumber, descriptionSeller, idSC) {
            var rqt = {
                method : 'POST',
                url : '/updateSeller',
                data : $.param({name: name, siret: siret, email: email, numberAddress: numberAddress, streetAddress: streetAddress, cityAddress: cityAddress, postCodeAddress: postCodeAddress, phoneNumber: phoneNumber, descriptionSeller: descriptionSeller, id: idSC}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                $scope.getAllSellers();
                $scope.showAllSCInfos = true;
                $scope.showUpdateSCForm = false;
                $scope.hideSuccess = false;
                $scope.titleSuccess = "The seller has been updated";
            });
        }

        $scope.cancelUpdateForm = function() {
            $scope.showAllSCInfos = true;
            $scope.showUpdateSCForm = false;

        }


});