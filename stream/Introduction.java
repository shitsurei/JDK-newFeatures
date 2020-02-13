package stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author 张国荣
 * 1.概念：流，一个支持串行和并行聚合操作的元素序列，位于java.util.stream包中
 * 2.构成：一个源，一个或多个中间操作（将零个流转换成另一个流），一个终止操作（得到结果集）
 * 	注意：每一次中间操作都会形成新的流，即所有操作都不会对源数据产生影响
 * 3.操作分类：
 * 3.1惰性求值：对集合中的元素进行筛选计算（只有触发终止操作时，中间操作才会被执行，否则不执行）；返回值都是stream对象
 * 3.2及早求值：对流中的元素进行组合（终止操作时立刻求值）
 * 4.语言风格：链式函数表达式，类似管道机制，通过与lambda表达式结合流可以更好的操作集合，接近自然语言
 * 5.支持并行处理（对集合的遍历速度优于传统的迭代器）
 */
public class Introduction {
	public static void main(String[] args) {
		String[] strs = {"hello","world"};
		//创建方式1
		Stream stream = Stream.of("hello","world");
		//创建方式2：通过数组创建
		stream = Stream.of(strs);
		stream = Arrays.stream(strs);
		//创建方式3：通过集合创建
		List<String> list = Arrays.asList(strs);
		stream = list.stream();
		
		//整型流
		IntStream is = IntStream.of(new int[] {2,4,7,9});
		is.forEach(System.out::print);//2479
		//静态方法，生成一个左闭右开的整型序列
		IntStream.range(4, 7).forEach(System.out::print);//456
		//静态方法，生成一个左闭右闭的整型序列
		IntStream.rangeClosed(4, 7).forEach(System.out::print);//4567
		
		List<Integer> nums = Arrays.asList(1,3,5,6);
		/**
		 * 	源：nums
		 * 	中间操作（惰性求值）：map（映射，乘2）
		 * 	终止操作（及早求值）：reduce（累加）
		 */
		System.out.println(nums.stream().map(i -> 2 * i).reduce(0, Integer::sum));
	}
}
