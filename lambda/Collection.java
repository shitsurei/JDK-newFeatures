package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Collection {
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
		//JDK1.4（外部迭代：外部迭代器逐个遍历集合）
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		//JDK1.5（外部迭代）
		for(Integer e : list) {
			System.out.println(e);
		}
		//JDK1.8（内部迭代：从集合中逐个取出元素做操作）
		//consumer 新增的函数式接口之一
		//函数式接口 ：@FunctionalInterface 一个接口中有且只有一个抽象方法 eg：Runnable
		list.forEach(new Consumer<Integer>() {
			@Override
			public void accept(Integer t) {
				//System.out.println(t);
			}
		});
		/**
		 * 接口中的default方法：默认的实现方法
		 * 例如forEach
		 * 接口中的static方法：静态方法
		 */
		//lambda表达式
		//list.forEach(e -> System.out.println(e));
		/**
		 * 完整写法,编译器可推断
		 * list.forEach((Integer e) -> System.out.println(e));
		 */
		/**
		 * 关于函数式接口：
		 * 1.如果一个接口中有且只有一个抽象方法，那么该接口是一个函数式接口
		 * 2.如果某个接口声明了@FunctionalInterface注解，则编译器会按照函数式接口的定义来要求该接口（必须是接口类型且只有一个抽象方法）
		 * 3.如果某个接口只有一个抽象方法但没有声明@FunctionalInterface注解，编译器任然会将其看作一个函数式接口
		 * 总结：类似于子类重写父类方法时声明的@Override注解，编译器和IDE会辅助检查代码中的错误，因此符合函数式接口的定义时最好加上注解
		 * 4.函数式接口的实例可以通过	1.lambda表达式	2.方法引用		3.构造方法引用	来创建
		 * 5.如果某个接口中重写了Object类中的抽象方法，如toString，hashCode，以上方法不计入函数式接口的抽象方法数量限制（因为类一定会继承Object根类）
		 */
		/**
		 * 通过 方法引用 创建一个函数式接口的实例
		 */
		//list.forEach(System.out::println);
		
		/**
		 * 应用实例：将一个字符串集合中的所有元素变成大写，之后遍历输出
		 */
		//lambda表达式
		List<String> list2 = Arrays.asList("hello","world","lambda");
		list2.forEach(item->System.out.println(item.toUpperCase()));
		//方法引用(与流相结合,类似于Linux中的pipeline)
		//其中::	Function<T,R> 也是函数式接口,T为输入类型,R为输出类型
		list2.stream().map(String::toUpperCase).forEach(System.out::println);
	}
}
