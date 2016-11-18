moduleSellbook.controller('allProducts', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("Products");
    // Test if the person can stay on the page
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');


    // Hide the error message at the beginning
    $scope.hideError = true;
    // Hide the success message at the beginning
    $scope.hideSuccess = true;

    $scope.hideID = true;

    $scope.showAllProductsInfos = true;
    $scope.showUpdateProductForm = false;

    //Get all the products in the database
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

     // When a user uses keywords to search a product
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

    // Delete the product
    $scope.confirmDelete = function(product) {
        if (confirm("Do you really want to delete this product ?")){
            var rqt = {
                            method : 'DELETE',
                            url : '/products/' + product.idProduct,
                            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                    };
                    $http(rqt).success(function(data){
                        $scope.getAllProducts();
                        $scope.hideSuccess = false;
                        $scope.titleSuccess = data;
                    });
        }
    }

    //Show update Form
        $scope.updateProduct = function(product) {
            var id = product.idProduct;
            $scope.showAllProductsInfos = false;
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
            $scope.getAllProducts();
            $scope.showAllProductsInfos = true;
            $scope.showUpdateProductForm = false;
            $scope.hideSuccess = false;
            $scope.titleSuccess = "The product has been updated";
        });
    }

    $scope.cancelUpdateForm = function() {
        $scope.showAllProductsInfos = true;
        $scope.showUpdateProductForm = false;

    }


});