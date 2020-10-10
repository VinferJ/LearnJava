package stream.parallel;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

/**
 * @author Vinfer
 * @date 2020-10-10    10:52
 * @description
 **/
public class ParallelStreamTest {


    public static void main(String[] args) {
        /*
        * 单线程: 跟多线程相比，不会存在线程创建、上下文切换、任务拆分等额外性能的消耗，
        *        但是单线程性能存在瓶颈，当任务/计算量过大时，效率会降低
        *        因此当计算量或者是任务数量不是很庞大时，使用单线程的执行速度、效率反而更高
        *
        * 传统多线程：将任务不断放到cpu的线程中去，但是由于多线程之间存在上下文切换，因此
        *           任务会出现阻塞的情况，并且在多核cpu中，每个cpu会有自己的线程队列
        *           当分配到某个核的线程队列的一组任务存在一个任务阻塞后，后续的任务也需要进行阻塞等待，
        *           但可能会出现其他核心的全部任务都执行完，进入空闲状态，但是没执行完的任务却还需要在
        *           它当前的cpu核心中进行等待，从而造成了资源浪费
        *           手动多线程/异步、线程池等都存在这种问题，即使使用了多线程，仍然无法对cpu资源压榨得更极限
        *           因此传统多线程更加适用于io密集型的任务，而不是很适用与cpu(计算)密集型任务
        *           io密集型任务：如网络io(网络请求处理)、本地io、数据库查询等，
        *           io密集型任务的特点，cpu更多的时间是在阻塞等待任务的准备（io阻塞）
        *
        * fork-join: 在必要的情况下，将一个大的任务进行拆分(fork)成若干个小任务（拆到不可再拆分时），
        *            再将一个个小任务的运行/执行结果进行join汇总
        *            fork-join的核心是工作窃取模式(work-stealing),通过该模式可以更大程度上避免cpu资源浪费
        *            从而进一步压榨cpu的资源，因此fork-join框架中的多线程使用，比传统多线程的工作效率更高
        *            这也表明了fork-join更适用于cpu密集型的大型任务,例如大数据
        *            但是！！要想利用fork-join完成多任务拆分计算，底层的任务拆分以及执行逻辑都需要自己手动实现
        *            也就是任务拆分的算法需要自己实现，这就导致fork-join的使用会比较复杂，并且拆分算法的好坏
        *            也会在一定程度上影响最终的使用效果和性能
        * 工作窃取: fork-join会将拆分后的小任务放到多个不同的任务队列中，即每个任务队列都包含了多个不可再拆分的任务
        *          并且这个任务队列是一个双端队列(可以从头尾两端取出元素)当cpu的某个核心以及执行完了一个任务队列中全部任务
        *          要处于空闲状态时，fork-join框架会随机选取一个还未被执行完的任务队列，并且从该任务中窃取一个任务(从队尾取出元素)
        *          并放到这个空的任务队列中，从而让cpu核心继续处于忙碌状态，不浪费cpu资源
        *
        * 并行流: Stream可以通过声明的方式在sequential()和parallel()方法之间进行切换（默认是sequential-顺序/串行流）
        *       并行流就是把一个内容分成多个数据模块，并且用不同的线程分别处理每个数据块的流
        *       并行流比普通的多线程更能利用和压榨cpu资源
        *       jdk1.8中并行流的出现可以说是fork-join很好的替代品，因为并行流使用起来更加简单，底层的数据块拆分(对应的fork-join的任务拆分)
        *       是有jdk自己封装好的，拆分算法以及被封装好，因此使用起来很方便，可以比fork-join更进一步去压榨cpu资源，
        *       不仅使用更简单，性能也可以比fork-join更优秀
        *
        * */

        Instant start = Instant.now();
        long sum = LongStream.range(0, 100000000001L)
                .parallel()
                .reduce(0, Long::sum);
        Instant end = Instant.now();
        System.out.println(sum);
        //使用并行流大约花费18s
        System.out.println("共耗时： "+ Duration.between(start, end).toMillis()+"ms");

    }


}
