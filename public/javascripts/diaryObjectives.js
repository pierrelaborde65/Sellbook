ModuleSellbook.controller('myDiaryObjectives', function($scope, $http, $window, $cookies, $cookieStore) {
    console.log("diaryObjectives");
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

