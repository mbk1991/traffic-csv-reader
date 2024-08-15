package FlowCollect;

import FlowCollect.reader.FlowCsvReader;
import FlowCollect.writer.JdbcWriter;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class FlowCsvCollectorJob implements Job
{
	private Logger logger = Logger.getLogger(getClass());
	private FlowCsvReader flowCsvReader = new FlowCsvReader(new JdbcWriter());

	public void execute(JobExecutionContext context) throws JobExecutionException {
        org.quartz.JobKey jobKey = context.getJobDetail().getKey();
        logger.info("SystemStatusJob says: " + jobKey + " executing at " + new Date());

        flowCsvReader.exec();
	}
}
