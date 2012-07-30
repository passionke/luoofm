 function GetPlayList () {}

GetPlayList.prototype.getPlayList = function(params, win, fail) {
  //Make params hash optional.
    if (!fail) win = params;
      PhoneGap.exec(win, fail, "LuooMediaPlayer", "getVolPlayList", [params]);
};

PhoneGap.addConstructor(function() {
    PhoneGap.addPlugin("luoogetplaylist", new GetPlayList());
});