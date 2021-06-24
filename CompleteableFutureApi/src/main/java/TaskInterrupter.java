import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 异步任务中断器
 * @author vinfer
 * @date 2021-06-19 0:22
 */
public class TaskInterrupter {

    static AtomicBoolean shutdown = new AtomicBoolean(false);

    static class Interrupter implements Runnable{

        private final List<CompletableFuture<Boolean>> futureList;

        public Interrupter(List<CompletableFuture<Boolean>> futureList){
            this.futureList = futureList;
        }

        @Override
        public void run() {
            // 中断其他任务的大致思路：
            // 首先cf.get之前将所有的任务都保存到该中断器类中，然后启动中断器监视
            // 关闭cf中的任务必须要进行异步关闭，因为调用cf.get的线程会被阻塞
            // 中断逻辑就是通过不断去检查任务的执行状态，只要出现isCompletedExceptionally的任务，那么马上遍历取消所有任务
            // 然后自动退出中断器监视
            while (true){
                if (!shutdown.get()){
                    for (CompletableFuture<Boolean> f : futureList) {
                        if (f.isCompletedExceptionally()){
                            System.out.println("detected exceptional task aborting the rest....");
                            futureList.forEach(task -> {
                                //有一个会取消失败，这个任务就是异常退出的任务，剩下的9个都能中断成功
                                System.out.println("task-"+task.hashCode()+" exits: " + task.cancel(true));
                            });
                            shutdownMonitor();
                            break;
                        }
                    }
                }else {
                    break;
                }
            }
        }
    }

    private static void shutdownMonitor(){
        shutdown.compareAndSet(false,true);
    }

    public static void startMonitor(List<CompletableFuture<Boolean>> futureList){
        new Thread(new Interrupter(futureList)).start();
    }





}
