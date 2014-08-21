import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import redis.clients.jedis.HostAndPort;

import java.sql.Connection;
import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by kartik.k on 8/20/2014.
 */
public class RedisClusterHelperTest {
    RedisClusterForRedisAdmin redisCluster;
    @Before
    void setup(){
        Set<HostAndPort> instanceSet = new HashSet<HostAndPort>();
        for(int portNo = 700;portNo<=7005;portNo++)
            instanceSet.add(new HostAndPort("172.16.137.228",portNo));
        redisCluster = new RedisClusterForRedisAdmin(instanceSet);
        redisCluster.pingAll();
    }

    @Test
    void clusterShouldBeUp(){
        assertTrue("Cluster should be up and running",redisCluster.isClusterAlive());
    }

    @Test
    public void shouldAllowAddingIntegersToRedis(){
        int noOfValues = 1000;
        int offset = 109;
        for(int i = 0;i<noOfValues;i++){
            redisCluster.set(Integer.toString(i), Integer.toString(i + offset));
            if(i%1000 == 0){
                System.out.println(Integer.toString(i/1000) + "k writes completed..");
            }
        }

    }
}
