import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author vinfer
 * @date 2021-05-26 21:58
 */
public class Client {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        test3();
    }

    static class F{
        static {
            System.out.println("F2");
            System.out.println("F4");
        }
        public F(){
            System.out.println("F1");
            System.out.println("F3");
        }
    }

    static class C extends F{
        static {
            System.out.println("C2");
            System.out.println("C4");
        }
        public C(){
            System.out.println("C1");
            System.out.println("C3");
        }
    }



    static void test1() throws InterruptedException, ExecutionException {

        CompletableFuture<String> cacheQueryTask = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return queryCache();
        });
        System.out.println("-----------");

        cacheQueryTask.thenAccept(s -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("accept");
        });

        System.out.println("============================");
        Thread.sleep(3000);

    }

    static void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future....");
        });
        // 通过调用get方法阻塞main线程
        future.get();
        System.out.println("=====================");
        Thread.sleep(2000);
    }

    static void test3() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future....");
            return null;
        });
        CompletableFuture<Void> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future....");
            return null;
        });
        CompletableFuture<Void> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future....");
            return null;
        });
        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2, future3);
        long start = Calendar.getInstance().getTimeInMillis();
        allOf.get();
        System.out.println(Calendar.getInstance().getTimeInMillis() - start);
    }

    static String queryCache(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return "cache";
    }

    static void obtain(String code){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(code);
    }


}
