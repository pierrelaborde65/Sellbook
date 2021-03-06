moduleSellbook.controller('newProduct', function($scope, $http, $window, $cookies, $cookieStore, $timeout) {
        // Authentification
        var idUser = $cookies.get('id');
        var tokenUser = $cookies.get('token');

        // Check SC Authentification
        if(!angular.isUndefined(idUser) && !angular.isUndefined(tokenUser)){
            var rqt = {
                method : 'GET',
                url : '/isConnected/',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                // If the person is a SC ok else redirect /
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

        $scope.hideSuccess = true;

        // Product creation (POST)
        $scope.newProduct = function(nameProduct, descriptionProduct, priceSeller, quantityStock) {
            var rqt = {
                    method : 'POST',
                    url : '/newProduct',
                    data : $.param({nameProduct: nameProduct, descriptionProduct: descriptionProduct, priceSeller: priceSeller, quantityStock: quantityStock, id: idUser}),
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                    $scope.hideSuccess = false;
                    $scope.titleSuccess = data;
                    $timeout(function(){ $scope.hideSuccess = true; }, 1500);
            });

        };



    });
