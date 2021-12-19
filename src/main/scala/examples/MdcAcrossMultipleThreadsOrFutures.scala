package examples

import mdc.MdcExecutionContext
import org.slf4j.{LoggerFactory, MDC}
import java.util.UUID
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.Success

object MdcAcrossMultipleThreadsOrFutures extends App {
//  Try out both the below ExecutionContext to see the difference between them through logs but use only one at a time. That means if you are using 'mdcExecutionContext', make sure to comment out 'defaultGlobalExecutionContext' and vice versa.

//  implicit val defaultGlobalExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  implicit val mdcExecutionContext: MdcExecutionContext = MdcExecutionContext(scala.concurrent.ExecutionContext.Implicits.global) // To propagate MDC across any new thread, we are using our own MdcExecutionContext
  val contextID = UUID.randomUUID().toString
  val logger = LoggerFactory.getLogger(getClass.getSimpleName)
  val future1FromMainThread = Future(50)

  MDC.put("contextID", s"contextID-$contextID") // putting the context for all log statements

  logger.info("This is the main thread")

  future1FromMainThread.onComplete {
    case Success(_) =>
      logger.info(s"MDC is propagated from main thread to future 1")
      val future2FromFuture1 = Future(80)
      future2FromFuture1.onComplete {
        case Success(_) =>
          logger.info(s"MDC is propagated from future1 to future2")
      }
      Await.result(future2FromFuture1, Duration.Inf)
  }

  Await.result(future1FromMainThread, Duration.Inf)
}
