 moduleSellbook.controller('updateDiary', function($scope, $http, $window, $cookies, $cookieStore) {
     console.log("updateDiary");
     // Test if the person can stay on the page
     var idUser = $cookies.get('id');
     var token_person = $cookies.get('token');


     // Hide the error message at the beginning
     $scope.hideError = true;
     // Hide the success message at the beginning
     $scope.hideSuccess = true;
     // Hide diary's updating form at the beginning
     $scope.updateForm = false;

     $scope.diary;
    $scope.initForm = function(id, title, description) {
        $scope.titleToUpdate = title;
        $scope.descriptionToUpdate = description;
        $scope.idToUpdate = id;
    }
//show update Form
    $scope.showUpdateDiary = function() {
        console.log("Update Diary");
        var request = {
                method: 'GET',
                url : '/updateDiary/' + idDiary ,
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(request).success(function(data){
            $scope.myDiaries = data;
        });
    };

    $scope.updateDiary = function(titleToUpdate, descriptionToUpdate) {
        var request = {
            method : 'PUT',
            url : '/myDiaries/'+ idDiary ,
            data : $.param({newTitle: titleToUpdate, newDescription: descriptionToUpdate}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(request).success(function(data){
            $scope.getMyDiaries();
            $scope.updateForm = false;
            $scope.hideSuccess = false;
            $scope.titleSuccess = "Diary updated successfully";
        });
     };

 // Display the prefilled form with former data
            $scope.updateFormDiary = function(diary) {
            $scope.updateForm = true;
            $scope.diary = diary;
            $scope.titleToUpdate = diary["title"];
            $scope.descriptionToUpdate = diary["description"];
            };

    // Hide diary updating form if the user cancels
            $scope.cancelFormUpdate = function() {$scope.updateForm = false;};

   });