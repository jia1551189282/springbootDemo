package springbootdemo.demo.demo;
/*
  单例模式（饿汉）
 */
public class Singleton {
    private Singleton(){};

    private static Singleton singleton = new Singleton();

    public static Singleton  getInstance(){
        return singleton;
    }


}
/**
 * 单例模式 （懒汉)  实例化对象的时候要先判断一下对象是否已经存在  比饿汉式节约资源
 * 但是会存在线程安全问题(多个线程同时访问的时候   一个线程进入了if  但是还没有实例化的时候另外一个线程也进入  就会创建两个  从而出现安全问题)
 */

class Singleton2{

    private Singleton2(){

    };
    private static Singleton2 singleton2 = null;

    public Singleton2 getInstance(){
        if(singleton2 == null){
            singleton2 = new Singleton2();
        }
        return singleton2;
    }
}

/**
 * 单例模式 （懒汉2） 解决线程安全问题  实例化的方法加上锁
 * 加上了同步锁   虽然解决了安全问题 但是效率低  一个线程进入了  另外一个线程就要等待当前线程释放锁之后才能进入
 */

class Singleton3{
    private Singleton3(){

        };
    private static Singleton3  singleton3= null;
    synchronized public Singleton3 getInstance(){
        if(singleton3 == null){
            singleton3 = new Singleton3();
        }
        return singleton3;
    }


        }
