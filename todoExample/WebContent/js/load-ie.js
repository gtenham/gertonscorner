$LAB
.script("http://ajax.googleapis.com/ajax/libs/chrome-frame/1/CFInstall.min.js")
.wait(function() {
	CFInstall.check({
	     mode: "overlay",
	     destination: "http://todo.example.com/"
	   });
});