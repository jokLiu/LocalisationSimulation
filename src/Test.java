
//Test class for testing localisation

//NOTE!!!
//THE CODE SHOULD RUN WITH THE ASSERTIONS ENABLED!!
//IF USING ECLIPSE YOU NEED TO PRESS THE BUTTON NEAR THE GREEN RUN BUTTON 
//THEN SELECT "RUN CONFIGURATIONS"
//THEN GO TO "ARGUMENTS" BAR AND ADD "-ea" in the "VM arguments" area
//IF USING COMMAND LINE IT SHOULD BE RAN AS FOLLOW:
	// java -Xlint *java
	// java -ea Test

//Currently tests are running without visualising it, if
//you want to enable the visualisation of the tests
// you have to go to the "BeginLocalise" class
//and uncomment 36 line (code; "ml.visualise(sim);")


//If you want to add some tests it should be written as follows:
//
//	//The class with the method to start localisation
//	BeginLocalise begin = new BeginLocalise(); 
//
//	//Create the Pair object and call the begin method with the starting coordinates
//	Pair p1 = begin.begin(5, 2);
//
//	//assert the p1 coordinates with the starting coordinates
//	//the p1 begin method returns the starting coordinates
//	assert (p1.getX() == 5 && p1.getY() == 2);


public class Test {

	public static void main(String[] args) {

		System.out.println("----Starting the Tests----");
		BeginLocalise begin = new BeginLocalise();

		System.out.println("----Running first test----");
		Pair p1 = begin.begin(5, 2);
		assert (p1.getX() == 5 && p1.getY() == 2);
		System.out.println("----First test passed----");

		System.out.println("----Running second test----");
		Pair p2 = begin.begin(6, 2);
		assert (p2.getX() == 6 && p2.getY() == 2);
		System.out.println("----Second test passed----");

		System.out.println("----Running third test----");
		Pair p3 = begin.begin(0, 0);
		assert (p3.getX() == 0 && p3.getY() == 0);
		System.out.println("----Third  test passed----");

		System.out.println("----Running fourth test----");
		Pair p4 = begin.begin(1, 1);
		assert (p4.getX() == 1 && p4.getY() == 1);
		System.out.println("----Fourth test passed----");

		System.out.println("----Running fifth test----");
		Pair p5 = begin.begin(0, 7);
		assert (p5.getX() == 0 && p5.getY() == 7);
		System.out.println("----Fifth test passed----");

		System.out.println("----Running sixth test----");
		Pair p6 = begin.begin(10, 1);
		assert (p6.getX() == 10 && p6.getY() == 1);
		System.out.println("----Sixth test passed----");

		System.out.println("----Running seventh test----");
		Pair p7 = begin.begin(11, 0);
		assert (p7.getX() == 11 && p7.getY() == 0);
		System.out.println("----Seventh test passed----");

		System.out.println("----Running eigth test----");
		Pair p8 = begin.begin(4, 3);
		assert (p8.getX() == 4 && p8.getY() == 3);
		System.out.println("----Eigth test passed----");

		System.out.println("----Running nineth test----");
		Pair p9 = begin.begin(8, 4);
		assert (p9.getX() == 8 && p9.getY() == 4);
		System.out.println("----Nineth test passed----");
		System.out.println("---------------------------");
		System.out.println("----ALL TESTS PASSED!----");

	}

}
