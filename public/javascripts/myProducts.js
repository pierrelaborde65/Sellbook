moduleSellbook.controller('myProducts', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("myProducts");
    // Authentification
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');

    // Seller Authentification Check
    if(!angular.isUndefined(idUser) && !angular.isUndefined(tokenUser)){
            var rqt = {
                method : 'GET',
                url : '/isConnected/',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                // If the USER is a SC ok else redirect /
                if(data["statusUser"] != 1) {
                    $window.location.href = '/';
                }
            }).error(function(data) {
                 $window.location.href = '/';
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

    //GET all Products of the Seller
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

    // Search a Product by keyword %nameProduct%
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

    // Confirm Product suppression
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


    //Show Product update Form
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

    // Update Product (POST)
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

    // Hide Update Product Form
    $scope.cancelUpdateForm = function() {
        $scope.showMyProductsInfos = true;
        $scope.showUpdateProductForm = false;
        }
});