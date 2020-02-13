package lambda;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorInterface {
	public static void main(String[] args) {
		/**
		 * BinaryOperator接口是BiFunction的一种特例，要求参数和返回值必须是同一种类型
		 * BinaryOperator直接继承于BiFunction，只用声明一种泛型类型即可 
		 * 	这个接口用于计算相同数据类型的计算更加便捷
		 */
		BinaryOperator<Integer> add = (value1, value2) -> value1 + value2;
		System.out.println(add.apply(2, 3));
		//根据字典序进行排序
		System.out.println(getShort("abcd", "bcd", (str1, str2) -> str1.compareTo(str2)));
		//根据字符串长度进行排序
		System.out.println(getShort("abcd", "bcd", (str1, str2) -> str1.length() - str2.length()));
	}
	//接口还提供了两个静态方法minBy(Comparator)和maxBy(Comparator)
	/**
	 * 传入比较器，返回两个字符串中较小的那个
	 * @param str1
	 * @param str2
	 * @param comparator 函数式接口 比较器
	 * @return
	 */
	public static String getShort(String str1,String str2,Comparator<String> comparator) {
		return BinaryOperator.minBy(comparator).apply(str1, str2);
	}
}
