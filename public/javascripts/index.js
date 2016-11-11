angular.module('SellbookLogin', ['ngCookies'])
.controller('login', function($scope, $http, $window, $cookies, $cookieStore) {
/*
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
            if(data["statut"] == 2) {
                $window.location.href = '/newProduct';
            }
            else {
                $window.location.href = '/';
            }
        });
    }
*/

/*
    $scope.isConnected = function(){
       var idUser = $cookies.get('id');
       var tokenUser = $cookies.get('token');
       var rqt = {
                method : 'GET',
                url : '/isConnected/' + idUser + '/' + tokenUser,
                data : $.param({id: idUser, token: tokenUser}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
        $http(rqt).success(function(data){
            $scope.hideError = true;
        }).error(function(data) {
            $scope.hideError = false;
            $scope.titleError = data;
        });

    }

*/



    // Show or not the error message depending on the return from the application
   // $scope.hideError = true;

    // When the user want to connect, if it success redirect to the right home, else display error message
    $scope.login = function(email, password) {
        var rqt = {
                method : 'POST',
                url : '/login',
                data : $.param({email: email, password: password}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.hideError = true;
        }).error(function(data) {
            $scope.hideError = false;
            $scope.titleError = data;
        });
    };

    $scope.logout = function() {
        var idUser = $cookies.get('id');
        var tokenUser = $cookies.get('token');
        var rqt = {
                method : 'POST',
                url : '/logout',
                data : $.param({id: idUser, token: tokenUser}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $window.location.href = '/';
        }).error(function(data) {
             $scope.hideError = true;
             $scope.titleError = data;
        });
    };



    // If the user doesn't want create an account and back, it redirects to the "/"
    $scope.back = function() { $window.location.href = '/'; };

    

});