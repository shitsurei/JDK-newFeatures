package stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 张国荣
 * 	流的转换：转换为数组或集合
 */
public class Transform {
	public static void main(String[] args) {
		Stream<String> stream = Stream.of("hello", "world", "jdk8");
		//toArray()方法可以将流生成数组，传入参数为IntFunction构造数组类型
		//lambda表达式
		//String[] strs = stream.toArray(length -> new String[length]);
		//构造方法引用
		String[] array = stream.toArray(String[]::new);
		Arrays.asList(array).forEach(System.out::println);
		
		stream = Stream.of("hello", "world", "jdk8");
		/**
		 * collect()方法对流当中的元素执行一个可变（被汇聚的值是一个可变的结果容器）的汇聚操作
		 * 	方法包含三个操作，对应三个函数式参数:
		 * Suppplier（不接收参数返回一个值）：接收器，用于最终返回封装好的结果
		 * BiConsumer（接受两个参数不返回值）：累加器，遍历流中的每一个元素，将其添加到传入的集合中
		 * BiConsumer（接受两个同类型的参数不返回值）：合并器，将第二次遍历时传入的每一个有值的list都添加到最终的list当中
		 */
		//以下可以用ArrayList也可以用LinkedList
		//lambda表达式
		List<String> list = stream.collect(() -> new ArrayList<>(), (theList, item) -> theList.add(item), (theList1, theList2) -> theList1.addAll(theList2));
		list.forEach(System.out::println);
		//方法引用（构造方法引用；类名：：实例方法；类名：：实例方法）
		//list = stream.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		
		//Collectors工具类提供了较多封装好的方法
		//toList方法是通过ArrayList对流中的数据进行了封装
		//list = stream.collect(Collectors.toList());
		//toCollection方法是通过向Collection接口中传入容器的构造方法引用对流中的数据进行了封装，可以自定义容器的返回类型
		list = stream.collect(Collectors.toCollection(LinkedList::new));
		
		//应用：流中的字符串拼接
		stream = Stream.of("hello", "world", "jdk8");
		String content = stream.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
		//joining()方法提供拼串的功能
		//String content = stream.collect(Collectors.joining());
		System.out.println(content);//helloworldjdk8
	}
}
