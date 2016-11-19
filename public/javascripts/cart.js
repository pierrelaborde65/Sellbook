moduleSellbook.controller('cart', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("cart");
    // Authentification
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');


    // SU Authentification Check
    if(!angular.isUndefined(idUser) && !angular.isUndefined(tokenUser)){
            var rqt = {
                method : 'GET',
                url : '/isConnected/' + idUser + '/' + tokenUser,
                data : $.param({id: idUser, token: tokenUser}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                // If the USER is a SU ok else redirect /
                if(data["statusUser"] != 0) {
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


    //Get all database products
    $scope.getCartProducts = function() {
        console.log("cart");
        var rqt = {
                method: 'GET',
                url : '/user/' + idUser + '/cart',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.shoppingCart = data;
            $scope.priceTotalCart = 0;
            for(var i = 0; i < data.length; i++) {
                $scope.priceTotalCart += data[i].price * data[i].quantity;
            }

        });
    };


});