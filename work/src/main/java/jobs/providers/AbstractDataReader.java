package jobs.providers;

import jobs.GenericDataReader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author vinfer
 * @date 2021-08-15 1:21
 */
public abstract class AbstractDataReader implements GenericDataReader {

    private final BlockingQueue<Object> dataQueue;

    private final ScheduledThreadPoolExecutor jobExecutor;

    public AbstractDataReader(){
        dataQueue = new ArrayBlockingQueue<>(1000);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        jobExecutor = new ScheduledThreadPoolExecutor(1, threadFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> readBatch(int limit) {
        List<T> list = new ArrayList<>(limit);
        for (int i = 0; i < limit; i++) {
            Object data = dataQueue.poll();
            if (null != data){
                list.add((T) data);
            }else {
                break;
            }
        }
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> readBatch(int limit, boolean strictToLimitNum) {
        if (!strictToLimitNum){
            return readBatch(limit);
        }
        List<T> list = new ArrayList<>(limit);
        for (int i = 0; i < limit; i++) {
            try {
                list.add((T) dataQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> read(int offset){
        List<T> list = new ArrayList<>(offset);
        for (int i = 0; i < offset; i++) {
            try {
                list.add((T) dataQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public boolean hasMoreData() {
        return dataQueue.size() > 0;
    }

    @Override
    public void startUpReader() {
        jobExecutor.execute(this::appendDataUntilEnd);
    }

    private void appendDataUntilEnd(){
        List<Object> dataList;
        while ((dataList = produceData()) != null){
            for (Object o : dataList) {
                try {
                    dataQueue.put(o);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        // reader停止读取数据，发布对应的事件通知监听者
    }

    /**
     * 数据生产抽象方法，当方法返回空时，reader自动停止读取数据
     * @param <T>       数据泛型
     * @return          数据对象
     */
    protected abstract <T> List<T> produceData();

}
