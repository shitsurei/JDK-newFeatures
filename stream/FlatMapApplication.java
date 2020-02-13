package stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlatMapApplication {
	public static void main(String[] args) {
		/**
		 * flatMap应用场景
		 */
		//案例3：字符串集合去重
		List<String> strss = Arrays.asList("hello world","world welcome","hello world welcome");
		strss.stream().map(item -> item.split(" ")).flatMap(Arrays::stream).distinct().forEach(System.out::println);
		
		//案例4：求两个集合的笛卡尔积
		List<String> list1 = Arrays.asList("你好","hi","hello");
		List<String> list2 = Arrays.asList("张三","lisi","wang5");
		//flatMap方法将Stream<Stream<……>>打平为一个Stream
		list1.stream().flatMap(item -> list2.stream().map(item2 -> item + " " + item2)).collect(Collectors.toList()).forEach(System.out::println);
	
		/**
		 * 流
		 * 分组：group by:Function接口
		 * 分区：partition by（分组的一种特例，按T/F分成两部分）:Predicate接口
		 */
		//案例5：根据姓名的首字母对集合中的元素进行分组，类似于SQL语句中的groupBy
		Person p1 = new Person("zhangsan", 20);
		Person p2 = new Person("lisi", 25);
		Person p3 = new Person("wangwu", 30);
		Person p4 = new Person("zhaoliu", 50);
		Person p5 = new Person("liuqi", 70);
		
		List<Person> ps = Arrays.asList(p1, p2, p3, p4, p5);
		//Collectors提供了相关辅助函数，注意函数的返回类型
		Map<Character, List<Person>> map = ps.stream().collect(Collectors.groupingBy(item -> item.name.charAt(0)));
		System.out.println(map);//{w=[……], z=[……], l=[……]}
		
		ps = Arrays.asList(p1, p2, p3, p4, p5);
		//分组统计功能：统计每组的个数，类似SQL中的count(*) groupBy
		Map<Object, Long> map2 = ps.stream().collect(Collectors.groupingBy(item -> item.name.charAt(0), Collectors.counting()));
		System.out.println(map2);//{w=1, z=2, l=2}
		
		ps = Arrays.asList(p1, p2, p3, p4, p5);
		//分组统计功能：统计每组某个字段的平均值，类似SQL中的average(age) groupBy
		Map<Object, Double> map3 = ps.stream().collect(Collectors.groupingBy(item -> item.name.charAt(0), Collectors.averagingDouble(item -> item.age)));
		System.out.println(map3);//{w=30.0, z=35.0, l=47.5}
		
		ps = Arrays.asList(p1, p2, p3, p4, p5);
		//分区统计功能：将集合中的所有元素按某个标准划分为两部分
		Map<Boolean, List<Person>> map4 = ps.stream().collect(Collectors.partitioningBy(item -> item.age > 30));
		System.out.println(map4);//{false=[……], true=[……]}
		
		
	}
	
	public static class Person{
		String name;
		int age;
		public Person(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}
	}
}
