package jobs;

/**
 * @author vinfer
 * @date 2021-08-11 8:45
 */
public abstract class AbstractJobExecutor implements JobExecutor{

    private static class Worker{
        private Job job;
        private final Thread t;
        public Worker(Job job){
            this.job = job;
            this.t = new Thread(() -> job.start());
        }

        public void setJob(Job job) {
            this.job = job;
        }

        public Job getJob() {
            return job;
        }

        public Thread getThread() {
            return t;
        }

        public void run(){
            t.start();
        }
    }

}
