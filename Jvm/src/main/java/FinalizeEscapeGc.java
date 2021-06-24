/**
 * @author vinfer
 * @date 2021-03-03 18:25
 */
public class FinalizeEscapeGc {

    public static FinalizeEscapeGc SAVE_HOOK = null;

    public void isAlive(){
        System.out.println("Yeah, I am still alive!");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method has been executed!");
        // 将save_hook再次与自身产生引用，让GCRoots可达，实现自救
        FinalizeEscapeGc.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws Throwable {
        SAVE_HOOK = new FinalizeEscapeGc();
        // 对象第一次自救，并且自救成功
        // 将对象置为null时，此时GC-Roots不可达，对象被标记1次
        SAVE_HOOK = null;
        System.gc();
        // 对象重写了finalize方法会被保存到一个叫F-Queue的队列中，由一个叫Finalizer的低调度优先级线程去执行
        // 这里的执行只是触发去执行该方法，并不承诺等待该方法执行结束，如果没有等待该方法执行结束，
        // 有可能会因为没有执行到对象重新建立引用的语句而导致对象自救失败，
        // 因此finalize方法的优先级很低，这里停顿0.5s，等待线程执行完该方法
        Thread.sleep(500);
        if (SAVE_HOOK != null){
            // 由于对象重写了finalize方法，因此被标记1次后不会马上回收，会去执行其finalize方法，但不保证一定成功执行
            // 当在finalize时重新产生引用让GC-Roots可达，那么就可以实现1次自救
            // 对象的finalize方法执行完后，会在F-Queue出队，也因此同一个对象的finalize方法不可能被调用两次
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("Oh shit! I dead!");
        }
        // 下面的代码和上面的一样，但是由于finalize方法只能调用一次，因此下面会自救失败
        SAVE_HOOK = null;
        System.gc();
        // finalize方法的优先级很低，这里停顿0.5s
        Thread.sleep(500);
        if (SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("Oh shit! I dead!");
        }
    }

}
