document.addEventListener("deviceready", function(){
	function checkConnection() { 
		var networkState = navigator.network.connection.type; 		 

		if (networkState == Connection.UNKNOWN || networkState == Connection.NONE){
			new Toast({context:$('body'),message:'神仙，你用的什么网络呀'}).show();
			setTimeout(window.close(), 2000);  
		}else if (networkState == Connection.CELL_2G){
			new Toast({context:$('body'),message:'测试版中，请谨慎使用2G网络XD'}).show(); 
		}		
	}
	checkConnection();

	function checkFileSystem() {
		window.requestFileSystem(LocalFileSystem.PERSISTENT, 0, function(){}, function(e){
			new Toast({context:$('body'),message:'sd卡不能用，' +  e.target.error.code}).show();
			setTimeout(window.close(), 2000);  
		});
	}
}, false);  