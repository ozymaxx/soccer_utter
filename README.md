# SoccerUtter
This is a multimodal Java application that generates database queries from sketch and speech inputs.

## Requirements
* [Java](http://www.oracle.com/technetwork/java/javase/overview/index.html) with [JavaFX](http://www.oracle.com/technetwork/java/javafx2-archive-download-1939373.html) SDK
* A drawing surface
* A microphone
* [IrisTK](iristk.net)
* Google Cloud Speech API Account
* Windows 7 or higher

## How to use
* Install Java SDK and JavaFX.
* Install IrisTK by following the steps outlined [here](http://www.iristk.net/installation.html).
* Follow the steps given under **Open vocabulary recognizers** title in [this tutorial](http://www.iristk.net/tutorial_semantics.html).
* On command line (cmd.exe), browse to the main IrisTK directory (probably <your_home_directory>/IrisTK) by `cmd <path_to_iristk>`
* Type `iristk create simple_dialog soccer_utter` and then `iristk eclipse`.
* Import the main IrisTK folder (which is where you are on terminal now) to Eclipse.
* Copy all the files in this repository to `app/soccer_utter` folder in your project.
* Click **Run**.

## Credits
* IrisTK - [iristk.net](iristk.net)
* Ozan Can Altıok - oaltiok15 at ku dot edu dot tr - [Koç University Intelligent User Interfaces Laboratory](http://iui.ku.edu.tr)
