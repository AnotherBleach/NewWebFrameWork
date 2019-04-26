import cn.edu.bupt.annotation.Aspect;
import cn.edu.bupt.annotation.Controller;
import cn.edu.bupt.annotation.RpcService;
import cn.edu.bupt.aop.Proxy;
import cn.edu.bupt.bean.Args;
import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;


@RpcService(interfaceName = Args.class)
public class MyAspect implements Proxy {
    @Override
    public void before() {

    }

    @Override
    public void after() {

    }

    @Test
    public void test() {

        PriorityQueue<Integer>pt = new PriorityQueue<>(100, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });
        pt.add(1);
        pt.add(2);
        pt.add(5);
        pt.add(3);
        while(!pt.isEmpty())
        {
            System.out.println(pt.poll());

        }

    }

}
