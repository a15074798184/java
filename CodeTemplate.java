package test;

public class CodeTemplate {

	public static void main(String[] args) {
		int i = 0;
		outer: for (; true;) {
			inner: for (; i < 10; i++) {
				System.out.println("i = " + i);
				if (i == 2) {
					System.out.println("continue");
					continue;
				}
				if (i == 3) {
					System.out.println("break");
					i++; 
					break;
				}
				if (i == 7) {
					System.out.println("continue outer");
					i++; 
					continue outer;
				}
				if (i == 8) {
					System.out.println("break outer");
					break outer;
				}
				for (int k = 0; k < 5; k++) {
					if (k == 3) {
						System.out.println("continue inner");
						continue inner;
					}
				}
			}
		}
	}
}
/*
 * Output: 
 * i = 0 
 * continue inner 
 * i = 1 
 * continue inner 
 * i = 2 
 * continue 
 * i = 3 
 * break
 * i = 4 
 * continue inner 
 * i = 5 
 * continue inner 
 * i = 6 
 * continue inner 
 * i = 7 
 * continue outer 
 * i = 8 
 * break outer
 */// :~

class CodeTemplate1 {
	public static void main(String[] args) {
		int i = 0;
		outer: while (true) {
			System.out.println("Outer while loop");
			while (true) {
				i++;
				System.out.println("i = " + i);
				if (i == 1) {
					System.out.println("continue");
					continue;
				}
				if (i == 3) {
					System.out.println("continue outer");
					continue outer;
				}
				if (i == 5) {
					System.out.println("break");
					break;
				}
				if (i == 7) {
					System.out.println("break outer");
					break outer;
				}
			}
		}
	}
	/*
	 * Output: 
	 * Outer while loop
	 * i = 1
	 * continue 
	 * i = 2 
	 * i = 3 
	 * continue outer 
	 * Outer while loop 
	 * i = 4 
	 * i = 5 
	 * break 
	 * Outer while loop 
	 * i = 6 
	 * i = 7 
	 * break outer
	 */// :~
}
