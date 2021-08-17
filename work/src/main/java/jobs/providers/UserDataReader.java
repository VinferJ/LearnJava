package jobs.providers;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author vinfer
 * @date 2021-08-15 1:53
 */
public class UserDataReader extends AbstractDataReader{

    private final AtomicInteger pageCounter = new AtomicInteger(1);

    private static final int PAGE_SIZE = 100;

    @Override
    public long getId() {
        return 10000000L;
    }

    @Override
    protected <T> List<T> produceData() {
        // sql分页查询
        final int page = pageCounter.get();
        if (page == -1){
            // 重置为第一页
            pageCounter.set(1);
            return null;
        }
        // 示例，查询第一页：select * from table limit 0,100
        int limitLeft = (page - 1) * PAGE_SIZE;
        int limitRight = page * PAGE_SIZE;
        List<T> list = pageQuery(limitLeft, limitLeft);
        if (list.size() < PAGE_SIZE){
            // 最后一页时将counter置为-1，下一次查询时将向上层调用返回null，并且重置counter
            pageCounter.set(-1);
        }
        return list;
    }

    private <T> List<T> pageQuery(int left,int right){
        return new ArrayList<>();
    }
}
