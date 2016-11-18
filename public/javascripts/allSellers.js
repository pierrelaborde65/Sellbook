moduleSellbook.controller('allSellers', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("all Sellers");
    // Test if the person can stay on the page
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');


    // Hide the error message at the beginning
    $scope.hideError = true;
    // Hide the success message at the beginning
    $scope.hideSuccess = true;

    //Get all the SC in the database
    $scope.getAllSellers = function() {
        console.log("AllSellers");
        var rqt = {
                method: 'GET',
                url : '/sellers',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.allSellersCompanies = data;
        });
    };

    // filter name Sellers by keyword
    $scope.searchSeller = function(nameSeller) {
        var rqt = {
                method : 'POST',
                url : '/searchSeller',
                data : $.param({nameSeller: nameSeller}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.allSellersCompanies = data;
        });
    };


});