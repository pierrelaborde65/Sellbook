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
                console.log("test js");
                 $scope.hideSuccess = false;
                 $scope.titleSuccess = data;
                 var rqt2 = {
                    method : 'DELETE',
                    url : '/products/admin/' + sc.id,
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                 };
                 $http(rqt2).success(function(data){
                      $scope.hideError = true;
                      $scope.getAllSellers();
                  }).error(function(data) {
                      $scope.hideError = false;
                      $scope.titleError = data;
                  });
            }).error(function(data) {
                  $scope.titleError = data;
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
