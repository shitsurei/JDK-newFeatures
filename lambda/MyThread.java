package lambda;

public class MyThread {
	public static void main(String[] args) {
		//匿名内部类方式
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("hello world");
			}
		}).start();
		
		//lambda表达式方式
		new Thread(()->System.out.println("hello lambda")).start();
	}
}
