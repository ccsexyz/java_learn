/*************************************************************************
	> File Name: test.java
	> Author: 
	> Mail: 
	> Created Time: 2015年10月02日 星期五 11时13分09秒
 ************************************************************************/

import java.util.*;
import org.omg.CORBA.*;

public class test
{
    public static void main(String[] args)
    {
        Integer a = 1000;
        a++;
        System.out.println(a.intValue());
        double l = max(1.2, 12,3, 45,3, 3,3, 234,432);
        System.out.println(l);
    }

    public static double max(double... values)
    {
        double largest = Double.MIN_VALUE;
        for(double v : values)
            if(v > largest)
                largest = v;
        return largest;
    }
}
