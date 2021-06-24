import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author vinfer
 * @date 2021-06-19 0:50
 */
public class TaskExecutor {

    public static void main(String[] args) {
        exec();
    }

    static void exec(){
        List<CompletableFuture<Boolean>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futureList.add(getTask(i==0));
        }
        CompletableFuture<Void> tasks = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
        TaskInterrupter.startMonitor(futureList);
        long s = Calendar.getInstance().getTimeInMillis();
        try {
            tasks.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("done!");
        System.out.println(Calendar.getInstance().getTimeInMillis() - s);
    }

    static CompletableFuture<Boolean> getTask(boolean f){
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (f) {
                throw new RuntimeException("error caused");
            }else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return f;
        });
    }

}
