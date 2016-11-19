moduleSellbook.controller('newDiary', function($scope, $http, $window, $cookies, $cookieStore) {

// Test if the person is allowed to be on that page
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');
    if(!angular.isUndefined(idUser) && !angular.isUndefined(tokenUser)){
        var request = {
                method : 'GET',
                url : '/isConnected/' + idUser + '/' + tokenUser,
                data : $.param({id: idUser, token: tokenUser}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };

        //if the user is an admin or sc redirect to '/'
            $http(request).success(function(data){
                // If the person is a SC ok else redirect /
                if(data["statusUser"] != 0) {
                    $window.location.href = '/';
                }
            });

            }

    // If no user connected redirect to '/'
   else {
        $window.location.href = '/';
    };
  $scope.hideSuccess = true;

//Create a new diary
    $scope.newDiary = function(titleDiary, descriptionDiary) {
        var dateDiary = new Date().toJSON().slice(0,10);
        var request = {
                method : 'POST',
                url : '/newDiary',
                data : $.param({titleDiary: titleDiary, descriptionDiary : descriptionDiary, dateDiary: dateDiary, id: idUser}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(request).success(function(data){
            $scope.hideSuccess = false;
            $scope.titleSuccess = data;
            $scope.titleDiary = '';
            $scope.descriptionDiary='';
        });
    }
});

/*function MoreObjectives()
{
var form = document.getElementsByTagName('form')[0],
    input = document.createElement('input');

input.setAttribute('type', 'text');
input.setAttribute('name', 'ObjectiveDiary');
form.appendChild(input);
}
*/

