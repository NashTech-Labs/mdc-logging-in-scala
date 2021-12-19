# MDC Logging in Scala

- This template shows how to integrate MDC Logging in any Scala project.
- Mapped Diagnostic Context, in short MDC, allows us to put context to all the log statements for a single thread.
- We can also propagate MDC across multiple threads or futures, for that we have to create our own ExecutionContext where we can copy the MDC to any new thread that is being used giving us multi-thread MDC logging.

In this template, I have demonstrated both the cases i.e. MdcAcrossSingleThread and MdcAcrossMultipleThreadsOrFutures. Run these example apps and look at the logs to get a better idea about MDC logging.

MdcExecutionContext is the custom ExecutionContext that we have created to propagate MDC across any new thread.

## Prerequisites

- Scala Build Tool(SBT), version 1.5.7
- Scala, version 2.12.12
- Logback Classic Module, version 1.2.3
