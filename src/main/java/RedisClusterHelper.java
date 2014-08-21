import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.Map;

/**
 * Created by kartik.k on 8/20/2014.
 */
public class RedisClusterHelper {




    private static void addSomeKeyValuePairs(long noOfKeyValPairs, Jedis jedis) {
        for (long i = 0; i < noOfKeyValPairs; i += 1) {
            long square = i + 1;
            jedis.set(Long.toString(i), Long.toString(square));
        }
    }

    private static void testSingleInstance(String host, int port) {
        Jedis jedis = new Jedis(host, port);
        jedis.connect();
        addSomeKeyValuePairs(10000, jedis);
        Page page = Page.getFirstPage(jedis, 10);
        Page secondPage = page.nextPage();
        List<String> resultList = page.getResultKeyList();
        for (String result : resultList) {
            double perfectSquare = Double.parseDouble(jedis.get(result));
            double squareRoot = Math.sqrt(perfectSquare);
            System.out.print(result + "\t");
            System.out.print(Double.toString(perfectSquare) + "\t");
            System.out.print(Double.toString(squareRoot) + "\n");
        }
        System.out.println("-------------------------------------");
        resultList = secondPage.getResultKeyList();
        for (String result : resultList) {
            double perfectSquare = Double.parseDouble(jedis.get(result));
            double squareRoot = Math.sqrt(perfectSquare);
            System.out.print(result + "\t");
            System.out.print(Double.toString(perfectSquare) + "\t");
            System.out.print(Double.toString(squareRoot) + "\n");
        }
        jedis.close();
    }
}