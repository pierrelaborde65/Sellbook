moduleSellbook.controller('newComment', function($scope, $http, $window, $cookies, $cookieStore) {

// Test if the person is allowed to be on that page
    var idUser = $cookies.get('id');
    var token_person = $cookies.get('token');
    if(!angular.isUndefined(idUser) && !angular.isUndefined(tokenUser)){
        var request = {
                method : 'GET',
                url : '/isConnected/' + idUser + '/' + tokenUser,
                data : $.param({id: idUser, token: tokenUser}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
        $http(request).error(function(data){
            $window.location.href = '/';
        })

        //if the user is an admin or sc redirect to '/'
        .success(function(data) {
            if(data["StatusUser"] != 0){
                $window.location.href = '/';
            }
        });
    }
    // If no user connected redirect to '/'
    else {
        $window.location.href = '/';
    }

  $scope.hideSuccess = true;

  //Create a new ocomment
      $scope.newComment = function(titleObjective, descriptionObjective) {
          var request = {
                  method : 'POST',
                  url : '/comment',
                  data : $.param({messageComment: messageComment}),
                  headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
          };
          $http(request).success(function(data){
              $scope.hideSuccess = false;
              $scope.titleSuccess = data;
          });
          }