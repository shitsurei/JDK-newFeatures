package sourceCode;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectorsAnalysis {
	public static class Person {
		String name;
		int age;

		public Person(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age + "]";
		}
	}

	public static void main(String[] args) {
		Person p1 = new Person("zhangsan", 20);
		Person p2 = new Person("lisi", 25);
		Person p3 = new Person("wangwu", 30);
		Person p4 = new Person("zhaoliu", 50);
		Person p5 = new Person("liuqi", 70);
		List<Person> ps = Arrays.asList(p1, p2, p3, p4, p5);
		/**
		 * Collectors本身是一个工厂，为开发者提供了一些常用的汇聚操作 CollectorImpl是一个Collectors中的静态内部类
		 * 	注意每一个collect的返回值
		 */
		// 找出年龄最小的元素(最大最小值)
		ps.stream().collect(Collectors.minBy((theP1, theP2) -> theP1.age - theP2.age)).ifPresent(System.out::println);
		// 年龄平均值
		System.out.println(ps.stream().collect(Collectors.averagingInt(theP -> theP.age)).toString());
		// 统计摘要信息
		IntSummaryStatistics iss = ps.stream().collect(Collectors.summarizingInt(theP -> theP.age));
		System.out.println(iss);
		// 字符串拼接（前缀，分隔符，后缀）
		System.out.println(ps.stream().map(theP -> theP.name).collect(Collectors.joining(",", "[", "]")));
		// 多级分组（先按首字母分组，分组的结果再对年龄进行分组）
		// groupingBy的参数为分类器classifier，分类器的类型决定了分组返回值map的key类型
		Map<Object, Map<Object, List<Person>>> map = ps.stream().collect(Collectors.groupingBy(theP -> theP.name.charAt(0), Collectors.groupingBy(theP -> theP.age)));
		System.out.println(map);
		//多级分区（先按年龄是否大于30，再按年龄是否大于50）
		Map<Boolean, Map<Boolean, List<Person>>> map2 = ps.stream().collect(Collectors.partitioningBy(theP -> theP.age > 30, Collectors.partitioningBy(theP -> theP.age > 50)));
		System.out.println(map2);
	}
}
