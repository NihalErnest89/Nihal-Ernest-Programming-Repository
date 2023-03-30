# Multi Threaded HTTP Server


### Overview
- Build a multi-threaded HTTP server using a thread-safe queue. This would allow the server to serve multiple clients simultaneously.
- Requires usage of pthread library for mutex locks in the HTTP Server. Also uses mutex locks and condition variables for the bounded buffer queue.
- Gain experience managing concurrency through synchronization.
