![solver](solver.gif)

### Download the Windows 7 minesweeper here:
http://forum.xda-developers.com/showthread.php?t=2096449

### How to use:
**This only works with a 1920x1080 screen resolution on Windows.**

ComputerVision might be a little off depending on your graphics card, if that is the case you have to tweak some of the values.

The minesweeper board must have the same tile grid as the "Advanced" difficulty, i.e. 16x30.
You also need to uncheck the "Display animations" and "Allow question marks" boxes to get the optimal results.

The speed of the program is dependent on the MILLISECONDS_CLICK_DELAY variable in Bot.java, but
be aware that a race condition in the Robot class can cause the mouse to move before the click
is registered, but this is only an issue under 35 milliseconds.

###Possible improvements:
1. Creating smarter guessing by calculating probabilities.
2. Not flagging the mines (this could be implemented by removing one line of code, but I like the visuals.)
3. Finding a way to use JNA or JNI to simulate the mouse operations instead of using the built in java Robot class. 
	
###Dependencies:
jna.jar and jna-platform.jar, download here:
https://github.com/java-native-access/jna




