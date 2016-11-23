moduleSellbook.controller('myDiaries', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("myDiaries");
    // Test if the person can stay on the page
    var idUser = $cookies.get('id');
    var tokenUser = $cookies.get('token');



        // SU Authentification Check
        if(!angular.isUndefined(idUser) && !angular.isUndefined(tokenUser)){
                var rqt = {
                    method : 'GET',
                    url : '/isConnected/',
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                };
                $http(rqt).success(function(data){
                    // If the USER is a SU ok else redirect /
                    if(data["statusUser"] != 0) {
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
    // Hide diary's updating form at the beginning
    $scope.updateForm = false;

    $scope.diary;


    //Get all the diaries
    $scope.getMyDiaries = function() {
        console.log("My Diaries");
        var request = {
                method: 'GET',
                url : '/user/' + idUser +'/myDiaries',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(request).success(function(data){
            $scope.myDiaries = data;
        });
    };


    // Delete the diary and show success message
    $scope.deleteDiary = function(diary) {
        var request = {
                method : 'DELETE',
                url : '/diaries/' + diary.idDiary,
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(request).success(function(data){
            $scope.getMyDiaries();
            $scope.hideSuccess = false;
            $scope.titleSuccess = data;
        });
    }

    //Update the diary
/*
    $scope.updateDiary = function(titleToUpdate, descriptionToUpdate) {
        var request = {
            method : 'PUT',
            url : '/diaries/' + $scope.diary["idDiary"],
            data : $.param({newTitle: titleToUpdate, newDescription: descriptionToUpdate}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(request).success(function(data){
            $scope.getMyDiaries();
            $scope.updateForm = false;
            $scope.hideSuccess = false;
            $scope.titleSuccess = "Diary updated successfully";
        });

    // Display the prefilled form with former data
            $scope.updateFormDiary = function(diary) {
            $scope.updateForm = true;
            $scope.diary = diary;
            $scope.titleToUpdate = diary["title"];
            $scope.descriptionToUpdate = diary["description"];
            }

    // Hide diary updating form if the user cancels
            $scope.cancelFormUpdate = function() {$scope.updateForm = false;}
}*/
});