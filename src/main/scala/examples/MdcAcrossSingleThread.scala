package examples

import org.slf4j.{LoggerFactory, MDC}

import java.util.UUID

object MdcAcrossSingleThread extends App {
  val contextID = UUID.randomUUID().toString
  val logger = LoggerFactory.getLogger(getClass.getSimpleName)

  MDC.put("contextID", s"contextID-$contextID") // putting the context for all log statements

  logger.info("This is the main thread")
  logger.debug("Debug log")
  logger.warn("Warning log")
  logger.error("Error log")
}
