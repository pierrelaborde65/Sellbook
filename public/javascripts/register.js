angular.module('Sellbook', ['ngCookies'])
.controller('register', function($scope, $http, $window, $cookies, $cookieStore) {

    // CREATE (POST) a person in the database with the information enter in the form
    $scope.createSU = function(name, email, numberAddress, streetAddress, cityAddress, postCodeAddress, phoneNumber, password) {
            var request = {
                    method : 'POST',
                    url : '/user',
                    data : $.param({name: name, email: email, numberAddress: numberAddress, streetAddress: streetAddress, cityAddress: cityAddress, postCodeAddress: postCodeAddress, phoneNumber: phoneNumber, password: password}),
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(request).success(function(data){
                var request = {
                        method: 'GET',
                        url : '/users',
                        headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                };
                $http(request).success(function(data){
                    $window.location.href = '/index';
                });
            }).error(function(data) {
                $scope.hideError = false;
                $scope.titleError = data;
            });
        };



});