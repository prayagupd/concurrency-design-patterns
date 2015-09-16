# Active Object

The goal is to decouple method execution from its invocation. Why? Well, to either achieve
a better throughput via asynchronous method invocation or work around system limitations or both, examples:
- booking system: instantaneous request confirmation vs time of when the order is actually processed
- Android programming - UI changes: a background service sending a message to the UI thread via a message handler

To avoid race conditions, incoming client requests are queued and handled by a scheduler.
The scheduler picks a queued object and makes it run its logic. It is object's responsibility
to know what to do when it gets invoked, hence the Active Object.

## Key Components
- __Proxy__: provides interface the clients can use to submit their requests
- __Activation List__: a queue of pending client requests
- __Scheduler__: decides which request to execute next
- __Active Object__: implements the core business logic
- __Callback__: contains execution result (i.e. a promise or a future)

## Pros and Cons
On the bright side:
- __Reduced code complexity__: Once pattern's mechanics are in place, the code can be treated as single-threaded.
- __No need for additional synchronization__: Concurrent requests are serialized and handled by a single internal thread

On the down side:
- __Performance overhead__: Sophisticated scheduling, spinning and request handling can be expensive in terms of memory and can lead to non-trivial context switching.
- __Programming overhead__: Active Object essentially requires you to create a small framework. It can definitely be kept self-contained enough, 
                            but it boils down to a simple the fact that you need to be aware of multiple components:
                            
                            Activation List - the queue of incoming requests                            
                            Callback - yields the results
                            Scheduler thread - watches for incoming requests
                            Scheduler implementation - enqueues requests
                            Proxy - client interface allowing to submit requests
                            Future - an asynchronous response


## Resources
- [Wikipedia](http://en.wikipedia.org/wiki/Active_object)
- [Prefer Using Active Objects instead of Naked Threads](http://www.drdobbs.com/parallel/prefer-using-active-objects-instead-of-n/225700095)
- [The Pragmatic Bookshelf: Java Active Objects](http://pragprog.com/magazines/2013-05/java-active-objects)
- [Android Concurrency: The Active Object Pattern](http://www.dre.vanderbilt.edu/~schmidt/cs282/PDFs/6-Concurrency-and-Synchronization-part9.pdf)









