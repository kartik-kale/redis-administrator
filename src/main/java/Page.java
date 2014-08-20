import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;

/**
 * Created by kartik.k on 8/20/2014.
 */
public class Page {
        private Jedis jedis;
        private int pageSize;
        private ScanResult scanResult;

        public static Page getFirstPage(Jedis jedis, int pageSize){
            return new Page(jedis,pageSize,"0");
        }

        private Page(Jedis jedis,int pageSize,String pageCursor){
            this.jedis = jedis;
            this.pageSize = pageSize;
            this.scanResult = jedis.scan(pageCursor,new ScanParams().count(pageSize));
        }

        Page nextPage(){
            String nextPageCursor = scanResult.getStringCursor();
            Page nextPage = new Page(this.jedis, this.pageSize,nextPageCursor);
            return nextPage;
        }

        List<String> getResultKeyList(){
            return scanResult.getResult();
        }
        void display(){

        }

    }