package assam.strange.test;

/**
 * 參考該篇
 * http://www.oreilly.com.tw/column_sleepless2.php?id=part24 - 全世界所有程式員都會犯的錯誤
 */
public class MyMain {
	public static void main(String[] args) {
		Singleton obj = Singleton.getInstance();
		System.out.println("obj.counter1=="+obj.counter1);
		System.out.println("obj.counter2=="+obj.counter2);
	}
}
