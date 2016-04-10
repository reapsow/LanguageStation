var indexApp = angular.module('indexApp', ['ngMaterial']);
indexApp.controller('IndexCtrl', ['$scope', '$http', '$mdDialog', '$mdMedia', function ($scope, $http, $mdDialog, $mdMedia) {

	$scope.isLoading = false;
	
	$scope.customFullscreen = $mdMedia('xs') || $mdMedia('sm');

	$scope.goPopup = function(ev, lang, title, inputTitle, reqType) { 

		var reqUrl = '';
		var resUrl = '';

		if (reqType == 'japextr')        { reqUrl = '/reqExtractJap';       resUrl  = 'words_result.html';}
		else if (reqType == 'japtrs')    { reqUrl = '/reqTrasnlateJap';     resUrl  = 'trans_result.html';}
		else if (reqType == 'engextr')   { reqUrl = '/reqExtractEnglish';   resUrl  = 'words_result.html';}
		else if (reqType == 'engtrs')    { reqUrl = '/reqTrasnlateEnglish'; resUrl  = 'trans_result.html';}
		else if (reqType == 'furi')      { reqUrl = '/reqFurigana';         resUrl  = 'furigana_result.html';}
		else if (reqType == 'kortrs')    { reqUrl = '/reqTrasnlateKor';     resUrl  = 'trans_result.html';}

		var vars = [];
		vars.push(title);
		vars.push(inputTitle);

		var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))  && $scope.customFullscreen;

		$mdDialog.show({
			controller: ReqDialogController,
			templateUrl: 'req_dialog.html',
			parent: angular.element(document.body),
			targetEvent: ev,
			locals : {
				vars : vars 
			}, 
			clickOutsideToClose:true,
			fullscreen: useFullScreen
		})
		.then(function(sentence) {
			var dataObj = {
					sentence : sentence
			};	
			
			$scope.isLoading = true;

			var res = $http.post(reqUrl, dataObj);
			res.success(function(data, status, headers, config) {
				$scope.isLoading = false;
				var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))  && $scope.customFullscreen;
				$mdDialog.show({
					controller: ResDialogController,
					templateUrl: resUrl,
					parent: angular.element(document.body),
					targetEvent: ev,
					clickOutsideToClose:true,
					locals : {
						resultData : data
					}, 
					fullscreen: useFullScreen
				})
				.then(function(arr) {

				}, function() {
				});

				$scope.$watch(function() {
					return $mdMedia('xs') || $mdMedia('sm');
				}, function(wantsFullScreen) {
					$scope.customFullscreen = (wantsFullScreen === true);
				});
			});
			res.error(function(data, status, headers, config) {
				//alert( "failure message: " + JSON.stringify({data: data}));
			});		
		}, 
		function errorCallback(response) {
			//alert(response);
		});

		$scope.$watch(function() {
			return $mdMedia('xs') || $mdMedia('sm');
		}, function(wantsFullScreen) {
			$scope.customFullscreen = (wantsFullScreen === true);
		});
	}

	$scope.showRequestExtractWordsJap = function(ev) {
		$scope.goPopup(ev, 'jap', 'Extract Words', 'Input Japanese Sentence', 'japextr'); 
	}

	$scope.showRequestTranslateJap = function(ev) {
		$scope.goPopup(ev, 'jap', 'Translate Sentences　翻訳', 'Input Japanese Sentence','japtrs');
	}

	$scope.showRequestExtractWordsEng = function(ev) {
		$scope.goPopup(ev, 'eng', 'Extract Words', 'Input English Sentence','engextr');
	}

	$scope.showRequestTranslateEng = function(ev) {
		$scope.goPopup(ev, 'eng', 'Trasnlate Sentences', 'Input English Sentence','engtrs');
	}

	$scope.showFuriganaRequestPopup = function(ev) {
		$scope.goPopup(ev, 'jap', 'Furigana(フリガナ) Service ', 'Input Japanese Sentence','furi');
	};
	
	$scope.showRequestTranslateKor = function(ev) {
		$scope.goPopup(ev, 'kor', 'Translate Sentences　번역', 'Input Korean Sentence','kortrs');
	};
}]);

function ReqDialogController($scope, $mdDialog, vars) {
	$scope.title = vars[0];
	$scope.inputTitle = vars[1];

	$scope.hide = function() {
		$mdDialog.hide();
	};
	$scope.cancel = function() {
		$mdDialog.cancel();
	};
	$scope.submit = function(answer) {
		$mdDialog.hide($scope.sentence);
	};
}

function ResDialogController($scope, $mdDialog, resultData) {
	$scope.resultData = resultData;
	
	console.log(JSON.stringify(resultData));

	$scope.hide = function() {
		$mdDialog.hide();
	};
	$scope.cancel = function() {
		$mdDialog.cancel();
	};
	$scope.answer = function(answer) {
		$mdDialog.hide();
	};
}

