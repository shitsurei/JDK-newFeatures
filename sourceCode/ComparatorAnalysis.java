package sourceCode;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorAnalysis {
	public static void main(String[] args) {
		/**
		 * 比较器 1.2就有，1.8加强（函数式接口，默认方法） 
		 * 多使用类型方法而不是通用方法，避免拆箱和装箱的性能损耗
		 */
		List<String> strs = Arrays.asList("hello", "world", "jdk8");
		// 按字母降序排序
		Collections.sort(strs, (item1, item2) -> item2.compareTo(item1));
		// 按字符串长度排序（降序）
		Collections.sort(strs, Comparator.comparingInt(String::length).reversed());
		// lambda表达式的类型推断，reversed方法对类型推断起到干扰作用，编译器无法判断，需要显示指定item类型
		Collections.sort(strs, Comparator.comparingInt(item -> item.length()));
		// Collections.sort(strs, Comparator.comparingInt(item ->
		// item.length()).reversed());
		Collections.sort(strs, Comparator.comparingInt((String item) -> item.length()).reversed());
		/**
		 * 原因详解： 
		 * 1.Collections的sort方法实际上是使用了JDK8中List接口新增的默认方法sort
		 * 2.sort方法的参数比较器Comparator的类型被设计为要排序集合中的元素类型已经其父类（这样设计便于子类根据其任意一个父类的比较方法来进行比较）
		 * 3.reversed的返回值和comparingInt的返回值泛型都是item已经其父类类型，编译器难以判断，只能将item指定为Object类型
		 */

		// thenCompare方法可用于实现多级比较（先用比较器1比较，相等时再用比较器2比较，可串联）
		// 先按字符串长度排序，相等时再按字典序降序排列（否则不会调用，短路运算）
		Collections.sort(strs,
				Comparator.comparingInt(String::length).thenComparing(String.CASE_INSENSITIVE_ORDER.reversed()));
		// 长度逆序之后在字典序逆序
		Collections.sort(strs, Comparator.comparingInt(String::length).reversed().thenComparing(String::toLowerCase,
				Comparator.reverseOrder()));

		strs.forEach(System.out::println);
	}
}
