moduleSellbook.controller('newProduct',
    function($scope, $http, $window, $cookies, $cookieStore) {


        // Check if the person is already connected or not and redirect or not

        //NEED TO BE A SELLER ------------------------------------------------------------------------------
        // Check if there are cookies or not
        var idUser = 1;
        /*var idUser = $cookies.get('id');
        var tokenUser = $cookies.get('token');
        if(!angular.isUndefined(idUser) && !angular.isUndefined(tokenUser)){
            var rqt = {
                method : 'GET',
                url : '/isConnected/' + idUser + '/' + tokenUser,
                data : $.param({id: idUser, token: tokenUser}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                alert(data);
                // If the person is a SC ok else redirect /
                if(data["statusUser"] != 2) {
                    $window.location.href = '/';
                }
            });
        }*/
    //----------------------------------------------------------------------------------------------------------

        console.log("NewProdJS");
        // Show or not the error message depending on the return from the application
        $scope.hideSuccess = true;

        // When the user want to connect, if it success redirect to the right home, else display error message
        $scope.newProduct = function(nameProduct, descriptionProduct, priceSeller, quantityStock) {
         console.log("2");
            var rqt = {
                    method : 'POST',
                    url : '/newProduct',
                    data : $.param({nameProduct: nameProduct, descriptionProduct: descriptionProduct, priceSeller: priceSeller, quantityStock: quantityStock, id: idUser}),
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                    $scope.hideSuccess = false;
                    $scope.titleSuccess = data;
            });
        };

    });
