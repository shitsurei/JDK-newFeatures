package optional;

import java.util.Optional;

/**
 * @author 张国荣
 * Optional
 *	用于解决NPE（空指针异常）问题，提高程序健壮性
 *	基于值的对象：final，不可变（可能会包含指向可变值的引用），私有的构造方法（通过几个静态方法构造包含值的实例）
 *	empty()		构造一个容器中的值为空的对象
 *	of(value)	构造一个容器中的值一定不能为空的对象
 *	ofNullable(value)	构造一个容器中的值可能为空的对象
 */
public class Introduction {
	public static void main(String[] args) {
		Optional<String> optional = Optional.of("hello");
		
		//不推荐采用这种面向对象的思维使用optional
		//判断容器中的值是否为空
		if(optional.isPresent())
			//不为空则获取容器中的值
			System.out.println(optional.get());//hello
		/**
		 * 	推荐使用函数式编程的风格使用
		 * 	ifPresent(Consumer)方法表示当optional中的值不为空时才执行consumer的accept方法
		 */
		optional.ifPresent(item -> System.out.println(item));//hello
		System.out.println("--------------");
		optional = Optional.empty();
		System.out.println(optional.orElse("world"));//world
		//orElseGet(Supplier)如果值为空则通过函数接口获取一个值
		System.out.println(optional.orElseGet(() -> "world"));//world
	}
}
