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
    else {
        $window.location.href = '/';
    }



    // Hide the error message at the beginning
    $scope.hideError = true;
    // Hide the success message at the beginning
    $scope.hideSuccess = true;

    $scope.hideID = true;

    $scope.showMyProductsInfos = true;
    $scope.showUpdateProductForm = false;

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


    //Show update Form
    $scope.updateProduct = function(product) {
        var id = product.idProduct;
        $scope.showMyProductsInfos = false;
        $scope.showUpdateProductForm = true;

         var rqt = {
                        method : 'GET',
                        url : '/products/' + id,
                        data : $.param({id: id}),
                        headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                };
                $http(rqt).success(function(data){
                    var productGot = data;
                    console.log(data);
                    $scope.nameP = productGot.nameProduct;
                    $scope.descriptionP = productGot.descriptionProduct;
                    $scope.priceS = productGot.priceSeller;
                    $scope.quantityS = productGot.quantityStock;
                    $scope.idP = productGot.idProduct;
         });


    }

    $scope.updateP = function(nameProduct, descriptionProduct, priceSeller, quantityStock, idProduct) {
        console.log(nameProduct);
        console.log(descriptionProduct);
        console.log(idProduct);
        var rqt = {
            method : 'POST',
            url : '/updateProduct',
            data : $.param({idProduct: idProduct, nameProduct: nameProduct, descriptionProduct: descriptionProduct, priceSeller: priceSeller, quantityStock: quantityStock}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.getMyProducts();
            $scope.showMyProductsInfos = true;
            $scope.showUpdateProductForm = false;
            $scope.hideSuccess = false;
            $scope.titleSuccess = "The product has been updated";
        });
    }

    $scope.cancelUpdateForm = function() {
        $scope.showMyProductsInfos = true;
        $scope.showUpdateProductForm = false;
        }
});