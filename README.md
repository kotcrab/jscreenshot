JScreenshot
===========

JScreenshot is a simple library for taking screenshots in Java applications, before capture user can select screen area that he want to capture.  
####[See demo](https://raw.githubusercontent.com/kotcrab/JScreenshot/master/usage-demo.gif)

### Usage

1. Add Maven depedency, or use [JAR files](https://github.com/kotcrab/JScreenshot/releases)

 ```
 <dependency>
	<groupId>pl.kotcrab</groupId>
	<artifactId>jscreenshot</artifactId>
 	<version>0.0.1</version>
  </dependency>
 ```
2. Take creenshot!

 ```java
	Screenshot.take(new ScreenshotAdapter() {
		@Override
		public void screenshotTaken (BufferedImage image) {
			//do whatever you want with your screenshot
		}
	});
 ```

3. Add custom capture dialog (optional)  
Default capture dialog is boring (and probably ugly depending on your current Look and Feel), chanigng them is prety easy, see wiki for that.
