package mdc

import org.slf4j.MDC
import scala.concurrent.ExecutionContext

object MdcExecutionContext {
  def apply(executionContext: ExecutionContext): MdcExecutionContext = {
    val mdcExecutionContext = new MdcExecutionContext(executionContext)
    mdcExecutionContext
  }
}

// Execution context proxy for propagating MDC from caller thread to execution thread.
class MdcExecutionContext(executionContext: ExecutionContext) extends ExecutionContext {
  override def execute(runnable: Runnable): Unit = {
    // getting a copy of the MDC data
    val callerMdc = MDC.getCopyOfContextMap
    executionContext.execute(() => {
      // setting caller thread MDC to the new execution thread
      if (callerMdc != null) MDC.setContextMap(callerMdc)
      try {
        runnable.run
      } finally {
        //clearing MDC so that we don't get misleading logs if this Thread is reused without having an MDC.
        MDC.clear
      }
    })
  }

  override def reportFailure(cause: Throwable): Unit = executionContext.reportFailure(cause)
}
