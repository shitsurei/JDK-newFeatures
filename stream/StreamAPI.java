package stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPI {
	public static void main(String[] args) {
		//将集合中的字符串转大写并输出
		List<String> strs = Arrays.asList("hello","world","jdk8");
		//不转换成流的方式
		strs.forEach(item -> System.out.println(item.toUpperCase()));
		//转换成流的方式
		strs.stream().map(String::toUpperCase).collect(Collectors.toList()).forEach(System.out::println);
		
		Stream<List<Integer>> stream = Stream.of(Arrays.asList(1),Arrays.asList(4,5),Arrays.asList(7,8,9));
		//flatMap方法将流中的每一个集合都转换成stream，打平之后再进行map就可以将所有元素汇集到一个流（一个集合）中
		stream.flatMap(theList -> theList.stream()).map(item -> item * item).collect(Collectors.toList()).forEach(System.out::println);
	
		//Stream.empty();生成一个空的流
		//generate方法输入一个supplier参数生成一个流
		Stream<String> stream2 = Stream.generate(UUID.randomUUID()::toString);
		//findFirst方法寻找流中第一个遇到的元素（短路方法），可能为空，故返回值为optional
		stream2.findFirst().ifPresent(System.out::println);
		/**
		 * 	一般以下两个方法组合使用
		 * iterate方法输入两个参数：初始种子，一元操作；从而产生一个无限流，不断对种子进行操作的迭代
		 * limit方法对迭代次数进行限制
		 */
		Stream.iterate(1, item -> item += 2).limit(20).forEach(System.out::println);
		/**
		 * 	使用原生类型方法mapToInt，减少性能损耗
		 * skip方法表示忽略掉流中前几个元素
		 * limit方法表示只保留流中的前几个元素
		 * sum方法对流中元素求和
		 * max，min方法找出流中的最大，小值，返回值为optional
		 * summaryStatistics方法获取一个IntSummaryStatistics对象，其中包含流中数据的相关统计内容（流中无内容时会显示默认值）
		 */
		System.out.println();
		IntSummaryStatistics iss = Stream.iterate(1, item -> item += 2).limit(6).filter(item -> item > 2).mapToInt(item -> item * 2).skip(2).limit(2).summaryStatistics();
		System.out.println(iss.getMax());
		System.out.println(iss.getMin());
		System.out.println(iss.getSum());
		System.out.println(iss.getCount());
		System.out.println(iss.getAverage());
	}
}
