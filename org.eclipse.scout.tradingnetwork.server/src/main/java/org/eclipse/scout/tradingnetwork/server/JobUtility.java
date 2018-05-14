package org.eclipse.scout.tradingnetwork.server;

import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.job.FixedDelayScheduleBuilder;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.tradingnetwork.server.jobs.FindExectuedMatchesJob;
import org.eclipse.scout.tradingnetwork.server.jobs.ReloadExecutedOrdersFromOrderBook;
import org.eclipse.scout.tradingnetwork.server.jobs.ReloadOrdersFromOrderBookJob;
import org.eclipse.scout.tradingnetwork.server.jobs.UpdatePendingOrderStatusJob;
import org.eclipse.scout.tradingnetwork.server.sql.SuperUserRunContextProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobUtility {
  private static final Logger LOG = LoggerFactory.getLogger(JobUtility.class);

  public static void registerJobs() {
    registerReloadOrdersFromOrderBookJob();
    registerReloadExecutedOrdersFromOrderBookJob();
    registerUpdatePendingOrderStatusJob();
    registerFindExecutedMatchesJob();
  }

  private static void registerReloadOrdersFromOrderBookJob() {
    Jobs.schedule(new ReloadOrdersFromOrderBookJob(),
        Jobs.newInput()
            .withName(ReloadOrdersFromOrderBookJob.ID)
            .withRunContext(BEANS.get(SuperUserRunContextProducer.class).produce())
            .withExecutionTrigger(Jobs.newExecutionTrigger()
                .withSchedule(FixedDelayScheduleBuilder.repeatForever(10, TimeUnit.SECONDS)))
            .withExceptionHandling(new ExceptionHandler() {
              @Override
              public void handle(Throwable t) {
                LOG.error("Error on execution of job " + ReloadOrdersFromOrderBookJob.ID + ": ", t);
              }
            }, true));
  }

  private static void registerReloadExecutedOrdersFromOrderBookJob() {
    Jobs.schedule(new ReloadExecutedOrdersFromOrderBook(),
        Jobs.newInput()
            .withName(ReloadExecutedOrdersFromOrderBook.ID)
            .withRunContext(BEANS.get(SuperUserRunContextProducer.class).produce())
            .withExecutionTrigger(Jobs.newExecutionTrigger()
                .withSchedule(FixedDelayScheduleBuilder.repeatForever(10, TimeUnit.SECONDS)))
            .withExceptionHandling(new ExceptionHandler() {
              @Override
              public void handle(Throwable t) {
                LOG.error("Error on execution of job " + ReloadExecutedOrdersFromOrderBook.ID + ": ", t);
              }
            }, true));
  }

  private static void registerUpdatePendingOrderStatusJob() {
    Jobs.schedule(new UpdatePendingOrderStatusJob(),
        Jobs.newInput()
            .withName(UpdatePendingOrderStatusJob.ID)
            .withRunContext(BEANS.get(SuperUserRunContextProducer.class).produce())
            .withExecutionTrigger(Jobs.newExecutionTrigger()
                .withStartIn(2, TimeUnit.SECONDS)
                .withSchedule(FixedDelayScheduleBuilder.repeatForever(10, TimeUnit.SECONDS)))
            .withExceptionHandling(new ExceptionHandler() {
              @Override
              public void handle(Throwable t) {
                LOG.error("Error on execution of job " + UpdatePendingOrderStatusJob.ID + ": ", t);
              }
            }, true));
  }

  private static void registerFindExecutedMatchesJob() {
    Jobs.schedule(new FindExectuedMatchesJob(),
        Jobs.newInput()
            .withName(FindExectuedMatchesJob.ID)
            .withRunContext(BEANS.get(SuperUserRunContextProducer.class).produce())
            .withExecutionTrigger(Jobs.newExecutionTrigger()
                .withStartIn(2, TimeUnit.SECONDS)
                .withSchedule(FixedDelayScheduleBuilder.repeatForever(10, TimeUnit.SECONDS)))
            .withExceptionHandling(new ExceptionHandler() {
              @Override
              public void handle(Throwable t) {
                LOG.error("Error on execution of job " + FindExectuedMatchesJob.ID + ": ", t);
              }
            }, true));
  }

}
