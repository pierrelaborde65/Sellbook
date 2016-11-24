moduleSellbook.controller('newProductAdmin', function($scope, $http, $window, $cookies, $cookieStore, $timeout) {

        // Authentification
        var idUser = $cookies.get('id');
        var tokenUser = $cookies.get('token');

        // Check Admin Authentification
        if(!angular.isUndefined(idUser) && !angular.isUndefined(tokenUser)){
            var rqt = {
                method : 'GET',
                url : '/isConnected/',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                // If the person is a Admin ok else redirect /
                if(data["statusUser"] != 2) {
                    $window.location.href = '/';
                }
            }).error(function(data) {
                $window.location.href = '/';
             });
        }
        else {
            $window.location.href = '/';
        }

        // Show or not the error message depending on the return from the application
        $scope.hideSuccess = true;

        // Product creation (POST)
        $scope.newProduct = function(idSeller, nameProduct, descriptionProduct, priceSeller, quantityStock) {
            var rqt = {
                    method : 'POST',
                    url : '/newProductAdmin',
                    data : $.param({idSeller: idSeller, nameProduct: nameProduct, descriptionProduct: descriptionProduct, priceSeller: priceSeller, quantityStock: quantityStock}),
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                    $scope.hideSuccess = false;
                    $scope.titleSuccess = data;
                    $timeout(function(){ $scope.hideSuccess = true; }, 1500);
            });
        };

        // Get all database Sellers
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


    });
