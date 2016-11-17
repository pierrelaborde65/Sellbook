moduleSellbook.controller('newProductAdmin', function($scope, $http, $window, $cookies, $cookieStore) {


        // Check if the person is already connected or not and redirect or not

        //NEED TO BE AN ADMIN ------------------------------------------------------------------------------
        // Check if there are cookies or not
        var idUser = $cookies.get('id');
        var tokenUser = $cookies.get('token');
        if(!angular.isUndefined(idUser) && !angular.isUndefined(tokenUser)){
            var rqt = {
                method : 'GET',
                url : '/isConnected/' + idUser + '/' + tokenUser,
                data : $.param({id: idUser, token: tokenUser}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                // If the person is a SC ok else redirect /
                if(data["statusUser"] != 2) {
                    $window.location.href = '/';
                }
            });
        }
        else {
            $window.location.href = '/';
        }
    //----------------------------------------------------------------------------------------------------------
        // Show or not the error message depending on the return from the application
        $scope.hideSuccess = true;

        // When the user want to connect, if it success redirect to the right home, else display error message
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


    });
