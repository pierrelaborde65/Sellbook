moduleSellbook.controller('allProducts', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("Products");
    // Authentification
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');


    // Hide the error message at the beginning
    $scope.hideError = true;
    // Hide the success message at the beginning
    $scope.hideSuccess = true;

    $scope.hideID = true;

    $scope.showAllProductsInfos = true;

    $scope.showUpdateProductForm = false;

    $scope.showAddToShoppingCart = false;

    //Return all the database products through get method on /products
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

    // Return all the database sellers through get method on /sellers
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

     // Return the product matching the keyword %nameProduct%
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

    // Confirm the deletion of a Product
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

    // Allow showing Update form for Product
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

    // Update the Product
    $scope.updateP = function(nameProduct, descriptionProduct, priceSeller, quantityStock, idProduct) {
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

    // Hide Update product Form -> Show all products
    $scope.cancelUpdateForm = function() {
        $scope.showAllProductsInfos = true;
        $scope.showUpdateProductForm = false;
    }

    // Show add Shopping Cart pop-up
    $scope.ShowAddToShoppingCart = function(product) {
        $scope.idProductCart = product.idProduct;
        $scope.quantityDesired = 1;
        $scope.quantityMax = product.quantityStock;
        $scope.hideSuccess = true;
        $scope.showAddToShoppingCart = true;
    }

    // Add the Product with the quantity to shopping cart
    $scope.addToShoppingCart = function(idProduct,quantityDesired) {
        var rqt = {
            method : 'POST',
            url : '/user/addToCart',
            data : $.param({idUser: idUser, idProduct: idProduct, quantityDesired: quantityDesired}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            console.log(data);
            $scope.hideSuccess = false;
            $scope.titleSuccess = "The product has been add to your cart";
            $scope.showAddToShoppingCart = false;
        });
    }


    // Hide the Shopping Cart Pop-up
    $scope.cancelAddToShoppingCart = function() {
        $scope.showAddToShoppingCart = false;
    }


});