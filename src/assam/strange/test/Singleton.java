package assam.strange.test;

class Singleton {
	private static Singleton obj = new Singleton();
	public static int counter1;
	public static int counter2 = 0; // <-- 有沒有初始化差很多

	private Singleton() {
		counter1++;
		counter2++;
	}

	public static Singleton getInstance() {
		return obj;
	}
}
