
public class Test {

	public static void main(String[] args) {
		BeginLocalise begin = new BeginLocalise();
		Pair p1 = begin.begin(5,2);
		assert(p1.getX() == 5 && p1.getY() == 2);
		
		Pair p2 = begin.begin(6,2);
		assert(p2.getX() == 6 && p2.getY() == 2);
		
		Pair p3 = begin.begin(0,0);
		assert(p3.getX() == 0 && p3.getY() == 0);
		
		Pair p6 = begin.begin(10,1);
		assert(p6.getX() == 10&& p6.getY() == 1);
		
		Pair p4 = begin.begin(1,1);
		assert(p4.getX() == 1 && p4.getY() == 1);
		
		Pair p5 = begin.begin(0, 7);
		assert(p5.getX() == 0 && p5.getY() == 7);
		
		Pair p7 = begin.begin(11, 0);
		assert(p7.getX() == 11 && p7.getY() == 0);
		
		Pair p8 = begin.begin(4, 3);
		assert(p8.getX() == 4 && p8.getY() == 3);
		
		Pair p9 = begin.begin(8, 4);
		assert(p9.getX() == 8 && p9.getY() == 4);

	}

}
