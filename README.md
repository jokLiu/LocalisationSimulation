WareHouse Project 

Team: 1.3

Localisation 

Done by Jokubas Liutkus

This repo is the localisation simulation of the actual warehouse

The real code for the real world warehouse is in the team github repository

This repo is only for visual and automatic testing

To test this project you need to go to the "Test" class and run it as follows:


THE CODE SHOULD RUN WITH THE ASSERTIONS ENABLED!!
IF USING ECLIPSE YOU NEED TO PRESS THE BUTTON NEAR THE GREEN RUN BUTTON 
THEN SELECT "RUN CONFIGURATIONS"
THEN GO TO "ARGUMENTS" BAR AND ADD "-ea" in the "VM arguments" area
IF USING COMMAND LINE IT SHOULD BE RAN AS FOLLOW:
	$ java -Xlint *java
	$ java -ea Test

Currently tests are running without visualising it, if
you want to enable the visualisation of the tests
you have to go to the "BeginLocalise" class
and uncomment 36 line (code; "ml.visualise(sim);")

If you want to add some tests it should be written as follows:

	//The class with the method to start localisation
	BeginLocalise begin = new BeginLocalise(); 

	//Create the Pair object and call the begin method with the starting coordinates
	Pair p1 = begin.begin(5, 2);

	//assert the p1 coordinates with the starting coordinates
	//the p1 begin method returns the starting coordinates
	assert (p1.getX() == 5 && p1.getY() == 2);