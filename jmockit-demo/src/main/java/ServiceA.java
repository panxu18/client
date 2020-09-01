import java.util.Random;

public class ServiceA {

    ServiceB b;

    /**
     * 动态方法调用
     */
    public boolean doDynamic() {
        System.out.println("serviceA doDynamic()");
        return b.doB() == 0;
    }

    /**
     * 静态方法调用
     */
    public boolean doStatic() {
        System.out.println("serviceA doStatic()");
        return CommonUtil.doC() == 0;
    }

    public int doA() {

        System.out.println("serviceA doA()");
        Random random = new Random();
        return random.nextInt(100) / 2;
    }

    /**
     * 自身调用
     */
    public boolean doSelf() {
        System.out.println("serviceA doSelf()");
        return doA() == 0;
    }

    /**
     * 构造函数
     */
    public int doConstruct() {
        System.out.println("serviceA doConstruct()");
        ModleC c = new ModleC(1);
        return c.getFieldC();
    }




}
