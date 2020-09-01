import mockit.*;
import org.junit.Assert;
import org.junit.Test;

public class ServiceATest {
    @Tested  @Injectable
    ServiceA target;

    @Injectable
    ServiceB b;

    @Test
    public void testDynamic() {
        new Expectations(){
            {
                b.doB();
                result = 1;
            }
        };
        Assert.assertEquals(false, target.doDynamic());
    }

    @Test
    public void testStatic() {
        new Expectations(CommonUtil.class) {
            {
                CommonUtil.doC();
                result = 1;
            }
        };
        Assert.assertEquals(false, target.doDynamic());
    }

    @Test
    public void testSelf() {
        new Expectations() {
            {
                target.doA();
                result = 1;
            }
        };
        Assert.assertEquals(false, target.doSelf());
    }


    @Test
    public void testConstruct() {
        ModleC c = new ModleC(2);
        System.out.println(c.getFieldC());
        new Expectations(c) {
            {
                new ModleC(1);
                result = c;
            }
        };
        Assert.assertEquals(2, target.doConstruct());
    }
}
