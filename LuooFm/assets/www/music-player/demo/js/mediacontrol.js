 function MediaControl () {}

MediaControl.prototype.getPlayList = function(params, win, fail) {
  //Make params hash optional.
    if (!fail) win = params;
      PhoneGap.exec(win, fail, "LuooMediaPlayer", "getVolPlayList", [params]);
};

MediaControl.prototype.play = function(params, win, fail) {
  //Make params hash optional.
    if (!fail) win = params;
      PhoneGap.exec(win, fail, "LuooMediaPlayer", "play", [params]);
};
MediaControl.prototype.resume = function(params, win, fail) {
  //Make params hash optional.
    if (!fail) win = params;
      PhoneGap.exec(win, fail, "LuooMediaPlayer", "resume", [params]);
};

MediaControl.prototype.pause = function(params, win, fail) {
  //Make params hash optional.
    if (!fail) win = params;
      PhoneGap.exec(win, fail, "LuooMediaPlayer", "pause", [params]);
};

MediaControl.prototype.getPlayingStatus = function(params, win, fail) {
  //Make params hash optional.
    if (!fail) win = params;
      PhoneGap.exec(win, fail, "LuooMediaPlayer", "getPlayingStatus", [params]);
};

MediaControl.prototype.stop = function(params, win, fail) {
  //Make params hash optional.
    if (!fail) win = params;
      PhoneGap.exec(win, fail, "LuooMediaPlayer", "stop", [params]);
};
PhoneGap.addConstructor(function() {
    PhoneGap.addPlugin("mediacontrol", new MediaControl());
});