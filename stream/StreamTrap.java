package stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamTrap {
	public static void main(String[] args) {
		//一个流不能重复操作或关闭后再去使用
		Stream<String> stream = Stream.empty();
		System.out.println(stream.distinct());
		//System.out.println(stream.distinct());//报错：stream has already been operated upon or closed
		
		//当没有遇到终止操作(及早求值)时所有的中间操作(惰性求值)都会延迟执行
		List<String> list = Arrays.asList("hello","world","jdk8");
		list.stream().map(item -> {
			System.out.println("test");//不输出
			return item.toUpperCase();
		});
		list.stream().map(item -> {
			System.out.println("test");//输出
			return item.toUpperCase();
		}).forEach(System.out::println);
		/**
		 * 	迭代器不断输出0,1,0,1
		 * 	由于没有limit方法对迭代次数进行限制，去重方法会无限等待迭代结果的去重
		 * 	本行代码会无限等待下去
		 */
		Stream.iterate(0, i -> (i + 1) % 2).distinct().limit(6).forEach(System.out::println);
		//这才是正确的拼接顺序
		Stream.iterate(0, i -> (i + 1) % 2).limit(6).distinct().forEach(System.out::println);
	}
}
