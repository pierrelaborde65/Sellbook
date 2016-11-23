moduleSellbook.controller('allProducts', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("Products");
    // Authentification
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');


        // Check Authentification
        if(!angular.isUndefined(idUser) && !angular.isUndefined(tokenUser)){
            var rqt = {
                method : 'GET',
                url : '/isConnected/',
               // data : $.param({id: idUser, token: tokenUser}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).error(function(data) {
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

    $scope.showAllProductsInfos = true;

    $scope.showUpdateProductForm = false;

    $scope.showAddToShoppingCart = false;




    //Return all the database products through get method on /products
    $scope.getAllProducts = function() {
        $scope.everyProducts = [];
        console.log("AllProducts");
        var rqt = {
                method: 'GET',
                url : '/products',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        //Get our Product
        $http(rqt).success(function(data){
            for (var i = 0; i < data.length; i++) {
               $scope.everyProducts.push({
                   idProduct : data[i].idProduct,
                   /*seller: data[i],*/
                   from: "SellBook",
                   nameProduct : data[i].nameProduct,
                   priceSeller : (Math.round(data[i].priceSeller*100)/100),
                   quantityStock : data[i].quantityStock,
                   descriptionProduct : data[i].descriptionProduct
               });
               $scope.lengthData = data.length;
            }
        });
        //Get Product From IGDiscount
        var rqt = {
            method: 'GET',
            url : 'https://igdiscount.eu-gb.mybluemix.net/product/available',
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
         };
        $http(rqt).success(function(dataExt){
           for (var i = 0; i < dataExt.length; i++) {
               $scope.everyProducts.push({
                   idProduct : dataExt[i].id,
                   /*seller: dataExt[i].seller.companyName,*/
                   from: "IGDiscount",
                   nameProduct : dataExt[i].name,
                   priceSeller : (Math.round(dataExt[i].price*100)/100),
                   quantityStock : dataExt[i].quantity,
                   descriptionProduct : dataExt[i].description
               });
           }
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
            $scope.everyProducts = data;
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
    $scope.updateP = function(nameP, descriptionP, priceS, quantityS, idP) {
        var rqt = {
            method : 'POST',
            url : '/updateProduct',
            data : $.param({nameProduct: nameP, descriptionProduct: descriptionP, priceSeller: priceS, quantityStock: quantityS, idProduct: idP}),
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
        if (product.from == "SellBook"){
            $scope.idProductCart = product.idProduct;
            console.log(product.idProduct);
            $scope.quantityDesired = 1;
            $scope.quantityMax = product.quantityStock;
            $scope.hideSuccess = true;
            $scope.showAddToShoppingCart = true;
        }else if(product.from == "IGDiscount"){
            window.open("https://igdiscount.eu-gb.mybluemix.net/");
            //document.location.href="https://igdiscount.eu-gb.mybluemix.net/";
        }

    }

    // Add the Product with the quantity to shopping cart
    $scope.addToShoppingCart = function(idProduct,quantityDesired) {
        console.log(idUser);
        console.log(idProduct);
        console.log(quantityDesired);
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