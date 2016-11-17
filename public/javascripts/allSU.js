moduleSellbook.controller('allSU', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("all SU");
    // Test if the person can stay on the page
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');


    // Hide the error message at the beginning
    $scope.hideError = true;
    // Hide the success message at the beginning
    $scope.hideSuccess = true;

    //Get all the SU in the database
    $scope.getAllSU = function() {
        console.log("AllSU");
        var rqt = {
                method: 'GET',
                url : '/simpleUsers',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.allSimpleUsers = data;
        });
    };

    // filter name SU by keyword
    $scope.searchSU = function(nameSU) {
        var rqt = {
                method : 'POST',
                url : '/searchSU',
                data : $.param({nameSU: nameSU}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.allSimpleUsers = data;
        });
    };


});