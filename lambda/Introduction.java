package lambda;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * 01
 * Lisp：函数式编程语言鼻祖
 * Lambda：一种用于指定匿名函数或闭包的运算符
 * 为什么需要：
 * java中函数不能作为参数或返回值，相对的JavaScript中函数可以作为参数和返回值（回调函数）
 * 匿名内部类：图形化编程中常见
 *
 * @author 张国荣
 */
public class Introduction extends JFrame {
    private static final long serialVersionUID = 1L;
    /**
     * jdk8新特性
     * lambda表达式和函数式编程
     * 1.不用写大量的匿名内部类
     * 2.集合操作适应并发
     */
    public JButton jb;

    public Introduction() {
        this.setTitle("test");
        this.setBounds(200, 200, 400, 200);
        jb = new JButton("click");
        //匿名内部类方式添加响应事件
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clicked1");
            }
        });
        /**
         * lambda表达式：
         * 结构：	(param1,param2,param3,...) -> {.....}	（方法参数） -> {方法的实现}
         * 一个lambda表达式可以有一个或多个参数
         * 参数的类型可以声明也可以由编译器通过上下文推断
         * 空的圆括号表示参数为空，圆括号不能省略
         * 只有一个参数且参数类型可以推导时，圆括号可以省略
         * 表达式主体可以包含一条或多条语句
         * 语句只有一条时，花括号可以省略（有返回值时必须把return也省略掉）
         * 	//表达式风格	o1.compareTo(o2)
         * 	//语句风格		{return o1.compareTo(o2);}
         */
        //ActiconEvent event（java编译系统的推断功能可省略变量类型）
        jb.addActionListener(event -> System.out.println("clicked2"));
        this.add(jb);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        new Introduction();
    }
}
