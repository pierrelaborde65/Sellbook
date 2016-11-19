moduleSellbook.controller('orderAdmin', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("orderAdmin");
    // Test if the person can stay on the page
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');


           //--------------------- Check Admin ----------------------------------------------------
    if(!angular.isUndefined(idUser) && !angular.isUndefined(tokenUser)){
            var rqt = {
                method : 'GET',
                url : '/isConnected/' + idUser + '/' + tokenUser,
                data : $.param({id: idUser, token: tokenUser}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                // If the USER is a SC ok else redirect /
                if(data["statusUser"] != 2) {
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


    //Get all the products in the database
    $scope.getAllOrderInProgress = function() {
        var state = "check payment";
        var rqt = {
                method: 'GET',
                url : '/order/' + state,
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.orders = data;
        });
    };

    $scope.acceptPayment = function(order) {
        var rqt = {
                method: 'POST',
                url : '/order/' + order.id + '/paid',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            console.log(data);
            $scope.titleSuccess = data;
            $scope.hideSuccess = false;
            $scope.getAllOrderInProgress();
        });
    };



    $scope.deniedPayment = function(order) {
        var state = "check payment";
        var rqt = {
                method: 'DELETE',
                url : '/order/' + order.id ,
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
                $scope.titleSuccess = data;
                $scope.hideSuccess = false;
                $scope.getAllOrderInProgress();

        });
    };


});