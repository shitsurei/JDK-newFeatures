package lambda;

import lambda.FunctionApplication.Person;

import java.util.function.Supplier;

public class SupplierInterface {
	public static void main(String[] args) {
		/**
		 * Supplier也是函数接口，抽象方法为get
		 * 	该方法不接收参数，同时返回一个结果
		 * 	可以用于工厂类的创建
		 */
		Supplier<String> supplier = () -> "hello world";
		System.out.println(supplier.get());//hello world
		/**
		 * 通过Supplier创建对象实例
		 */
		Supplier<Person> supplier2 = () -> new Person("tom", 22);
		System.out.println(supplier2.get().getUsername());
		//构造方法引用只会执行不带参数的构造方法
		supplier2 = Person::new;
	}
}
