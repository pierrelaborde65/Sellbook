var moduleSellbook = angular.module('Sellbook', ['ngCookies'] )
moduleSellbook.controller('login', function($scope, $http, $window, $cookies) {


    // Show or not the error message depending on the return from the application
    $scope.hideError = true;

    // User login - IF success redirect to the right home, ELSE display error message
    $scope.login = function(email, password) {
        var rqt = {
                method : 'POST',
                url : '/login',
                data : $.param({email: email, password: password}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.hideError = true;
            window.location.reload();
        }).error(function(data) {
            $scope.hideError = false;
            $scope.titleError = data;
        });
    };

    // User logout - Discard Cookie
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

});