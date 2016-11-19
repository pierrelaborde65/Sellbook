ModuleSellbook.controller('myDiaryObjectives', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("diaryObjectives");
    // Test if the person can stay on the page
    var idUser = $cookies.get('id');
    var token_person = $cookies.get('token');


    // Hide the error message at the beginning
    $scope.hideError = true;
    // Hide the success message at the beginning
    $scope.hideSuccess = true;

    $scope.objective;

    //Get all objectives
    $scope.getDiaryObjectives = function() {
        console.log("Objectives");
        var request = {
                method: 'GET',
                url : '/user/' + idUser +'/diary/' + idDiary +'/objectives',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(request).success(function(data){
            $scope.diaryObjectives = data;
        });
    };


    // Delete the diary and show success message
    $scope.deleteDiary = function(diary) {
        var request = {
                method : 'DELETE',
                url : '/objective/' + Objective.idObjective,
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(request).success(function(data){
            $scope.getDiaryObjectives();
            $scope.hideSuccess = false;
            $scope.titleSuccess = data;
        });
    }

    //Redirect to newObjective
    document.getElementById("addObjectiveBtn").onclick = function () {
            $window.location.href = '/newObjective';
        };

