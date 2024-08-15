package FlowCollect;

import Prop.PropData;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class FlowCsvCollectorMain {
	Logger log = Logger.getLogger(getClass());
	private Scheduler sched = null;

	public void run() {
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			this.sched = sf.getScheduler();
			log.info("------- Initialization Complete --------");
			JobDetail job = JobBuilder.newJob(FlowCsvCollectorJob.class)
					.withIdentity("FlowCsvCollectorJob", "group1").build();
			CronTrigger trigger = (CronTrigger) TriggerBuilder
					.newTrigger()
					.withIdentity("FlowCsvCollectorMain", "group1")
					.withSchedule(
							CronScheduleBuilder.cronSchedule(PropData.getFlowCollectCron()))
					.build();
			Date ft = sched.scheduleJob(job, trigger);
			log.info((new StringBuilder()).append(job.getKey())
					.append(" has been scheduled to run at: ").append(ft)
					.append(" and repeat based on expression: ")
					.append(trigger.getCronExpression()).toString());
			this.sched.start();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			log.error("Cannot start System Status Job Trigger. This system will be shutdown.");
			System.exit(0);
		}
	}

	public void stopSchedule() {
		if (this.sched != null) {
			try {
				this.sched.shutdown(true);
			} catch (SchedulerException e) {
				log.error(e.getMessage(), e);
			}
			this.sched = null;
		}
	}

	public static void main(String[] args) throws Exception {

		new FlowCsvCollectorMain().run();
	}
}