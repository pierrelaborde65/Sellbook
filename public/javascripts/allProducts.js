moduleSellbook.controller('allProducts', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("Products");
    // Test if the person can stay on the page
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');


    // Hide the error message at the beginning
    $scope.hideError = true;
    // Hide the success message at the beginning
    $scope.hideSuccess = true;

    $scope.product;

    // Hide the error and success message on the page
    $scope.hideErrorOrSuccessMessage = function() {
        if($scope.hideError == false) {
            $scope.hideError = true;
        }
        if($scope.hideSuccess == false) {
            $scope.hideSuccess = true;
        }
    }

    //Get all the product to fill the table for update the product
    $scope.getAllProducts = function() {
        console.log("AllProducts");
        var rqt = {
                method: 'GET',
                url : '/products',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.allProducts = data;
        });
    };

    // Get all sellers in the database
    $scope.getAllSellers = function() {
                    console.log("AllSellers");
                    var rqt = {
                            method: 'GET',
                            url : '/sellers',
                            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                    };
                    $http(rqt).success(function(data){
                        $scope.allSellers = data;
                    });
    };

    // When the user want to connect, if it success redirect to the right home, else display error message
    $scope.searchProduct = function(seller, nameProduct) {
        var idSeller;
        if (seller == null){
            idSeller = 0;
        } else {
            idSeller = seller.id;
        }
        var rqt = {
                method : 'POST',
                url : '/searchProduct',
                data : $.param({idSeller: idSeller, nameProduct: nameProduct}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.allProducts = data;
            console.log(data);
        });
    };



    // Hide the different choice and show the table with the product in the database
    $scope.showUpdateTable = function() {
        $scope.isChoiceShow = false;
        $scope.isTableShow = true;
        $scope.hideErrorOrSuccessMessage();
        $scope.getAllProducts();
    };

/*
   // Get all seller who will fill the combo
   $scope.getAllSeller = function() {
       var rqt = {
           method: 'GET',
           url : '/persons/seller',
           headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
       };
       $http(rqt).success(function(data){
           $scope.allSeller = data;
       });
   }

   // When the user select a company, we retrieve the company and get all products from his company
   $scope.hasChanged = function(seller) {
       if(seller.name != null) {
           id_seller = seller.name.id;
       }
   }
*/

});