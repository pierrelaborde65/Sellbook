moduleSellbook.controller('orderSeller', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("orderSeller");
    // Test if the person can stay on the page
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');


    // Check Seller Authentification
    if(!angular.isUndefined(idUser) && !angular.isUndefined(tokenUser)){
        var rqt = {
            method : 'GET',
            url : '/isConnected/',
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            // If the person is an Admin ok else redirect /
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


    //Get all the products in the database
    $scope.getMyOrders = function() {
        var rqt = {
                method: 'GET',
                url : '/seller/' + idUser + '/order',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.orders = data;
        });
    };

    $scope.deliver = function(order) {
        var rqt = {
                method: 'POST',
                url : '/order/' + order.id + '/delivered',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            console.log(data);
            $scope.titleSuccess = data;
            $scope.hideSuccess = false;
            $scope.getMyOrders();
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

    $scope.hideDeliver = function(state){
        if(state != "paid"){
            return true;
        }else{
            return false;
        }
    }


});