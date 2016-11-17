moduleSellbook.controller('myProducts', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("myProducts");
    // Test if the User can stay on the page
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');


           //--------------------- Check SELLER ----------------------------------------------------
    if(!angular.isUndefined(idUser) && !angular.isUndefined(tokenUser)){
            var rqt = {
                method : 'GET',
                url : '/isConnected/' + idUser + '/' + tokenUser,
                data : $.param({id: idUser, token: tokenUser}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                // If the USER is a SC ok else redirect /
                if(data["statusUser"] != 1) {
                    $window.location.href = '/';
                }
            });
    }



    // Hide the error message at the beginning
    $scope.hideError = true;
    // Hide the success message at the beginning
    $scope.hideSuccess = true;

    $scope.isShowUpdateFormProduct = false;


    //Get seller's products
    $scope.getMyProducts = function() {
        console.log("MyProducts");
        var rqt = {
                method: 'GET',
                url : '/seller/' + idUser +'/products',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.myProducts = data;
        });
    };

     // When a user uses keywords to search a product
    $scope.searchProduct = function(nameProduct) {
        var rqt = {
                method : 'POST',
                url : '/searchProduct',
                data : $.param({idSeller: idUser, nameProduct: nameProduct}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.myProducts = data;
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

    // Delete the product
    $scope.confirmDelete = function(product) {
        if (confirm("Do you really want to delete this product ?")){
            var rqt = {
                            method : 'DELETE',
                            url : '/products/' + product.idProduct,
                            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                    };
                    $http(rqt).success(function(data){
                        $scope.getMyProducts();
                        $scope.hideSuccess = false;
                        $scope.titleSuccess = data;
                    });
        }
    }

    //Update ---------------------------------------------------------------------------------

    //Show update Form
    $scope.ShowUpdateFormProduct = function(product) {
        console.log("updateform");
        $scope.isShowUpdateFormProduct = true;
        $scope.product = product;
        $scope.nameUpdate = product["name"];
        $scope.descriptionUpdate = product["description"];
        $scope.priceUpdate = product["price"];
        $scope.quantityUpdate = product["quantity"];
    }




/*
    $scope.updateProduct = function(nameToUpdate, descriptionToUpdate, priceToUpdate, quantityToUpdate) {
        var rqt = {
            method : 'PUT',
            url : '/product/' + $scope.product["id"],
            data : $.param({newName: nameToUpdate, newDescription: descriptionToUpdate, newPrice: priceToUpdate, newQuantity: quantityToUpdate}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.getAllProductForSeller();
            $scope.isFormUpdateShow = false;
            $scope.hideSuccess = false;
            $scope.titleSuccess = "The product has been updated";
        });
    }*/



    $scope.cancelFormUpdate = function() {
        $scope.isShowUpdateFormProduct = false;
    }

});