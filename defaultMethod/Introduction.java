package defaultMethod;

/**
 * @author 张国荣
 * 	默认方法：接口中实现的方法
 * 	JDK8引入默认方法是为了向前兼容，避免对既有代码的破坏
 * 	以往代码中实现的接口不需要再对新增加的默认方法重新实现（透明性）
 *
 */
public class Introduction implements MyInterface, MyInterface2 {
	public static void main(String[] args) {
		//单一实现接口时可以直接在子类中调用接口的默认方法
		new Introduction().myMethod();//hello world2
		//当子类同时继承父类和实现接口后都间接继承了同名默认方法时编译器优先执行继承父类中的默认方法（java设计者认为继承关系更加直接）
	}

	@Override
	public void myMethod() {
		//多继承且继承了多个同名默认方法时需要在子类中重写并声明要继承哪个接口的默认方法
		MyInterface2.super.myMethod();
	}
}

interface MyInterface {
	//默认方法（接口中可实现的方法）
	default void myMethod() {
		System.out.println("hello world");
	}
}

interface MyInterface2 {
	default void myMethod() {
		System.out.println("hello world2");
	}
}
