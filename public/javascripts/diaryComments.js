ModuleSellbook.controller('myDiaryComments', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("diaryComments");
    // Test if the person can stay on the page
    var idUser = $cookies.get('id');
    var token_person = $cookies.get('token');


    // Hide the error message at the beginning
    $scope.hideError = true;
    // Hide the success message at the beginning
    $scope.hideSuccess = true;

    $scope.comment;

    //Get all objectives
    $scope.getDiaryOComments = function() {
        console.log("Comments");
        var request = {
                method: 'GET',
                url : '/user/' + idUser +'/diary/' + idDiary +'/comments',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(request).success(function(data){
            $scope.diaryComments = data;
        });
    };

    //Update a comment
    //Check if the user is the owner of the comment
    $scope.updateComment = function(messageToUpdate) {
        var request = {
            method : 'PUT',
            url : '/comment/' + $scope.comment["idComment"],
            data : $.param({newMessage: messageToUpdate}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(request).success(function(data){
            $scope.getDiaryComments();
            $scope.updateForm = false;
            $scope.hideSuccess = false;
            $scope.titleSuccess = "Diary updated successfully";
        });

    // Display the prefilled form with former data
            $scope.updateFormDiary = function(diary) {
            $scope.updateForm = true;
            $scope.diary = diary;
            $scope.messageToUpdate = comment["msg"];
            }

    // Hide diary updating form if the user cancels
            $scope.cancelFormUpdate = function() {$scope.updateForm = false;}
}
});


    // Delete the comment and show success message
    ////Also Check if the user is the owner of the comment
    $scope.deleteComment = function(comment) {
        var request = {
                method : 'DELETE',
                url : '/comment/' + Comment.idComment,
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(request).success(function(data){
            $scope.getDiaryComments();
            $scope.hideSuccess = false;
            $scope.titleSuccess = data;
        });
    }
