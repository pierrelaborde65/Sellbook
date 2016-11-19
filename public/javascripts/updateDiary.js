 moduleSellbook.controller('updateDiary', function($scope, $http, $window, $cookies, $cookieStore) {
     console.log("updateDiary");
     // Test if the person can stay on the page
     var idUser = $cookies.get('id');
     var token_person = $cookies.get('token');


     // Hide the error message at the beginning
     $scope.hideError = true;
     // Hide the success message at the beginning
     $scope.hideSuccess = true;
     $scope.hideID = true;
//show update page
    $scope.showUpdateDiary = function(diary) {
        console.log("Update Diary");
        var id=idDiary;
        var request = {
                method: 'GET',
                url : '/updateDiary/' + id ,
                data : $.param({id: id}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(request).success(function(data){
            var diaryUpdated = data;
            $scope.titleToUpdate = diaryUpdated.newTitle;
            $scope.descriptionToUpdate = diaryUpdated.newDescription;
            $scope.idDiary = diaryUpdated.idDiary;
        });
    };

    $scope.updateMyDiary = function(titleToUpdate, descriptionToUpdate, idDiary) {
        var request = {
            method : 'POST',
            url : '/updateDiary',
            data : $.param({idDiary: idDiary, newTitle: titleToUpdate, newDescription: descriptionToUpdate}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(request).success(function(data){
            $scope.getMyDiaries();
            $scope.hideSuccess = false;
            $scope.titleSuccess = "Diary updated successfully";
        });
     };

   });