angular.module('Sellbook', [])
.controller('newProduct', function($scope, $http, $window, $cookies, $cookieStore) {
var app = angular.module('newProduct', ['ngRoute']);
    // Check if the person is already connected or not and redirect or not

    //NEED TO BE A SELLER ------------------------------------------------------------------------------

    alert("JSENTER");
    // Show or not the error message depending on the return from the application
    //$scope.hideError = true;

    // When the user want to connect, if it success redirect to the right home, else display error message
    $scope.newProduct = function(nameProduct, descriptionProduct, priceSeller, quantityStock) {
        alert("funcenter");
        var rqt = {
                method : 'POST',
                url : '/newProduct',
                data : $.param({nameProduct: nameProduct, descriptionProduct: descriptionProduct, priceSeller: priceSeller, quantityStock: quantityStock}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
         $http(rqt).success(function(data){
                    $scope.hideSuccess = false;
                    $scope.titleSuccess = data;
         });

    };


});