package lambda;

/**
 * @author 张国荣
 * 函数式接口声明
 *
 */
@FunctionalInterface
interface MyInterface{
	void test();
	//toString方法继承自Object类，故不计入函数式接口的抽象方法数量中
	String toString();
}


public class Substance {

	public void myTest(MyInterface myInterface) {
		System.out.println(1);
		myInterface.test();
		System.out.println(2);
	}
	
	public static void main(String[] args) {
		Substance sub = new Substance();
		//参数传入接口的匿名内部类实现
		sub.myTest(new MyInterface() {
			@Override
			public void test() {
				System.out.println("myInterface");
			}
		});
		//参数传入lambda表达式，虽然抽象方法test的参数为空，()也不能省略
		sub.myTest(()->System.out.println("myInterface2"));
		//函数式编程实质上是函数式接口的实现对象
		MyInterface myInterface = () -> {
			System.out.println("hh");
		};
		System.out.println(myInterface.getClass());//class lambda.Substance$$Lambda$2/135721597
		System.out.println(myInterface.getClass().getSuperclass());//class java.lang.Object
		System.out.println(myInterface.getClass().getInterfaces().length);//1
		System.out.println(myInterface.getClass().getInterfaces()[0]);//interface lambda.MyInterface
	}
	/**
	 * 总结：
	 * 在将函数作为一等公民的语言（JavaScript，Python）中，lambda表达式的类型是函数；
	 * 但在java中lambda表达式是对象，他们必须依附于一类特别的对象类型->函数式接口；（该对象的类型必须通过上下文信息断定，因为lambda表达式不关心方法的名字，只关心方法的实现）
	 * 这样可以保证语言的向下兼容
	 */
}
