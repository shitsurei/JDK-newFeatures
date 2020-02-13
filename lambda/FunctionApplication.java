package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author 张国荣
 * Function接口的使用案例
 *
 */
public class FunctionApplication {

	public static class Person{
		private String username;
		private int age;
		public Person() {
			
		}
		public Person(String username, int age) {
			super();
			this.username = username;
			this.age = age;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		@Override
		public String toString() {
			return "Person [username=" + username + ", age=" + age + "]";
		}
	}
	
	public static void main(String[] args) {
		Person p1 = new Person("tom", 12);
		Person p2 = new Person("lily", 23);
		Person p3 = new Person("john", 34);
		
		List<Person> list = Arrays.asList(p1,p2,p3);
		List<Person> resultByUsername = getPersonByUsername("tom", list);
		resultByUsername.forEach(System.out::println);
		System.out.println("----------");
		List<Person> resultByAge = getPersonByAge(10, list);
		resultByAge.forEach(System.out::println);
		System.out.println("----------");
		//方法调用时传入筛选规则
		List<Person> resultByData = getPersonByData(22, list, (data,databse) -> databse.stream().filter(p -> p.getAge() <= data).collect(Collectors.toList()));
		resultByData.forEach(System.out::println);
	}
	/**
	 * 根据userName筛选集合中的person，返回与给定值相等的person集合
	 * @param username
	 * @param database
	 * @return
	 */
	public static List<Person> getPersonByUsername(String username, List<Person> database){
		//stream()	生成流
		//filter(Predicate)	根据给定谓词对集合中的数据进行过滤
		//Predicate	谓词接口（函数式接口）	抽象方法为test，根据给定条件返回true或false
		//collect()	将流数据重新生成集合
		return database.stream().filter(p -> p.getUsername().equals(username)).collect(Collectors.toList());
	}
	/**
	 * 根据age筛选集合中的person，返回大于等于给定值的person集合
	 * 由于输入参数是2个，返回值唯一，可以通过BiFunction这一函数接口实现
	 * @param age
	 * @param database
	 * @return
	 */
	public static List<Person> getPersonByAge(int age, List<Person> database){
		//定义行为
		//statement lambda
		BiFunction<Integer, List<Person>, List<Person>> biFunction = (ages, databses) -> {
			return databses.stream().filter(p -> p.getAge() >= ages).collect(Collectors.toList());
		};
		//expression lambda
		//biFunction = (ages, databses) -> databses.stream().filter(p -> p.getAge() >= ages).collect(Collectors.toList());
		//返回这一行为对给定参数的应用结果
		return biFunction.apply(age, database);
	}
	
	/**
	 * 对于以上两个方法的灵活拓展，筛选规则可以在调用该方法时传入
	 * @param anyData
	 * @param database
	 * @param rule
	 * @return
	 */
	public static List<Person> getPersonByData(int anyData, List<Person> database, BiFunction<Integer, List<Person>, List<Person>> rule){
		return rule.apply(anyData, database);
	}
}
