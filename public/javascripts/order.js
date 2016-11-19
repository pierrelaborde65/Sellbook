moduleSellbook.controller('order', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("order");

    // Authentification
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');


    // Check SU Authentification
    if(!angular.isUndefined(idUser) && !angular.isUndefined(tokenUser)){
            var rqt = {
                method : 'GET',
                url : '/isConnected/' + idUser + '/' + tokenUser,
                data : $.param({id: idUser, token: tokenUser}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                // If the USER is a SC ok else redirect /
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


    // Return the current User Order
    $scope.getMyOrder = function() {
        console.log("order");
        var rqt = {
                method: 'GET',
                url : '/user/' + idUser + '/order',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.orders = data;

            $scope.priceTotalCart = 0;
        });
    };





});