package stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 内部迭代和外部迭代剖析
 * @author 张国荣
 *
 */
public class InternalRecursion {
	public static class Person {
		String name;
		int age;
		String address;
	}
	
	public static void main(String[] args) {
		List<Person> ps = new ArrayList<>();
		//流是一种描述性的语言
		ps.stream().filter(p -> p.age > 20).filter(p -> p.address.equals("beijing")).sorted((p1, p2) -> p2.age - p1.age).forEach(p -> System.out.println(p.name));
		//类似于SQL语句
		//select name from ps where age > 20 and address = 'beijing' order by age desc
		
		//流的迭代方式称为内部迭代，区别于传统的外部迭代
		List<Person> result = new ArrayList<>();
		for (int i = 0; i < ps.size(); i++) {
			//外部迭代中集合本身与遍历逻辑的代码是严格区分的
			Person p = ps.get(i);
			if(p.age > 20 && p.address.equals("beijing"))
				result.add(p);
		}
		Comparator<Person> ageDesc = new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				return o2.age - o1.age;
			}
		};
		result.sort(ageDesc);
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i).name);
		}
		/**
		 * 1.内部迭代操作的对象不再是集合而是流
		 * 2.对流所执行的惰性操作在流中已经有了框架，处理时所有操作会和整个流优化合并在一起
		 * 3.并且每一条数据在整个流中只被遍历一次（数据类似流水线上的产品一次被每一个操作处理或过滤），故性能不会受损
		 * 4.本质区别：集合关注的是数据与数据存储本身，流关注的是数据的计算
		 * 5.流与迭代器相似的一点是：无法重复使用或消费
		 */
	}
}
