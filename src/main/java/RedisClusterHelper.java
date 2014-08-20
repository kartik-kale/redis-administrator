import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kartik.k on 8/20/2014.
 */
public class RedisClusterHelper {
        public static void main(String[] args){

            listAllInstances(getJedisClusterAtPort7000("172.16.137.70"));
////        jedis.auth("foobared");
//        testSingleInstance("172.16.137.70",7000);
        }

        private static JedisCluster getJedisClusterAtPort7000(String ipAddress){
            Set<HostAndPort> instanceSet = new HashSet<HostAndPort>();
            instanceSet.add(new HostAndPort(ipAddress, 7005));
            JedisCluster jedisCluster = new JedisCluster(instanceSet);
            return jedisCluster;
        }

        private static void addSomeKeyValuePairs(long noOfKeyValPairs, Jedis jedis) {
            for (long i = 0; i < noOfKeyValPairs; i+=1) {
                long square = i+1;
                jedis.set(Long.toString(i), Long.toString(square));
            }
        }

        private static void testSingleInstance(String host,int port){
            Jedis jedis = new Jedis(host, port);
            jedis.connect();
            addSomeKeyValuePairs(10000,jedis);
            //String value = jedis.info();
            Page page = Page.getFirstPage(jedis,10);
            Page secondPage = page.nextPage();
            List<String> resultList = page.getResultKeyList();
            for (String result: resultList){
                double perfectSquare = Double.parseDouble(jedis.get(result));
                double squareRoot = Math.sqrt(perfectSquare);
                System.out.print(result + "\t");
                System.out.print(Double.toString(perfectSquare) + "\t");
                System.out.print(Double.toString(squareRoot) + "\n");
            }
            System.out.println("-------------------------------------");
            resultList = secondPage.getResultKeyList();
            for (String result: resultList){
                double perfectSquare = Double.parseDouble(jedis.get(result));
                double squareRoot = Math.sqrt(perfectSquare);
                System.out.print(result + "\t");
                System.out.print(Double.toString(perfectSquare) + "\t");
                System.out.print(Double.toString(squareRoot) + "\n");
            }
            jedis.close();
        }

        private static void listAllInstances(JedisCluster jedisCluster){
            for(String hostPort: jedisCluster.getClusterNodes().keySet()){
                System.out.println(hostPort);
            }
        }
        private void addIntsFrom0WithOffsetToCluster(int noOfValues, int offset, JedisCluster jedisCluster){
            for(int i = 0;i<noOfValues;i++){
                jedisCluster.set(Integer.toString(i),Integer.toString(i+offset));
                if(i%1000 == 0){
                    System.out.println(Integer.toString(i/1000) + "k writes completed..");
                }
            }
        }
    }