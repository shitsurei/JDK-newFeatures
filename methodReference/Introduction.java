package methodReference;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author 张国荣 
 * 	方法引用： 
 * 1.lambda表达式的一种语法糖（未提供新的功能，只是一种简洁的表达方式） 
 * 2.本质上是一个函数指针 
 * 3.分类：四类
 * 3.1：类名：：静态方法 
 * 3.2：引用名（对象名）：：实例方法名
 * 3.3：类名：：实例方法名
 * 3.4：类名：：new（也称为构造方法引用）
 * 4.只有当lambda表达式所执行的操作对应着一个相应的方法时才能使用
 */
public class Introduction {
	public static class Student {
		String name;
		int score;

		public Student() {
			this.name = "all";
			this.score = 100;
		}
		
		public Student(String name, int score) {
			super();
			this.name = name;
			this.score = score;
		}

		@Override
		public String toString() {
			return "Student [name=" + name + ", score=" + score + "]";
		}
		//静态方法(错误的设计，方法没有用到当前类的字段)
		public static int compareByScore1(Student s1, Student s2) {
			return s1.score - s2.score;
		}
		//实例方法(正确的设计)
		public int compareByScore2(Student stu) {
			return this.score - stu.score;
		}
	}
	public static class StudentComparator{
		//实例方法
		public int compareByName(Student s1, Student s2) {
			return s1.name.compareTo(s2.name);
		}
	}

	public static void main(String[] args) {
		List<String> list = Arrays.asList("hello", "world", "jdk8");
		list.forEach(System.out::print);// helloworldjdk8
		System.out.println("--------------");

		List<Student> stus = Arrays.asList(new Student("ann", 50), new Student("john", 30), new Student("sam", 80));
		/**
		 *	第一种 ：类名：：静态方法 
		 *	完全等价于stus.sort((s1, s2) -> Student.compareByScore(s1, s2));
		 */
		stus.sort(Student::compareByScore1);
		stus.forEach(System.out::println);
		System.out.println("--------------");
		
		/**
		 * 	第二种：引用名（对象名）：：实例方法名
		 * 	完全等价于stus.sort((s1, s2) -> sc.compareByName(s1, s2));
		 */
		StudentComparator sc = new StudentComparator();
		stus.sort(sc::compareByName);
		stus.forEach(System.out::println);
		System.out.println("--------------");
		
		/**
		 * 	第三种：类名：：实例方法名
		 * 	compareByScore2是一个实例方法，必须由对象调用，实际上是由lambda表达式的第一个参数调用的
		 * 	完全等价于stus.sort((s1, s2) -> s1.compareByScore2(s2));
		 */
		stus.sort(Student::compareByScore2);
		stus.forEach(System.out::println);
		System.out.println("--------------");
		
		/**
		 * 	第四种：构造方法引用
		 * 	完全等价于generateStudent(()-> new Student())
		 */
		System.out.println(generateStudent(Student::new));//Student [name=all, score=100]
	}
	/**
	 * 通过Supplier接口生成一个Student对象
	 * @param supplier
	 * @return
	 */
	public static Student generateStudent(Supplier<Student> supplier) {
		return supplier.get();
	}
}
