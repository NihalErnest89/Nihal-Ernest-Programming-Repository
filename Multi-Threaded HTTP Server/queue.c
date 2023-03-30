// Coded by Nihal Ernest

#include "queue.h"

#include <stdbool.h>
#include <stdint.h>
#include <stdlib.h>

#include <assert.h>

#include <pthread.h>
#include <sys/types.h>

/** @struct queue_t
 *
 *  @brief This typedef renames the struct queue.  Your `c` file
 *  should define the variables that you need for your queue.
 */
typedef struct queue {
    int len, count, in, out;
    void **buffer;

    pthread_mutex_t mutex;
    pthread_cond_t cv, cv2;

} queue;

/** @brief Dynamically allocates and initializes a new queue with a
 *         maximum size, size
 *
 *  @param size the maximum size of the queue
 *
 *  @return a pointer to a new queue_t
 */
queue_t *queue_new(int size) {
    queue_t *q = (queue *) malloc(sizeof(queue));

    // making pthread mutex and conditional variables
    pthread_mutex_init(&q->mutex, NULL);
    pthread_cond_init(&q->cv, NULL);
    pthread_cond_init(&q->cv2, NULL);

    // initialize the ints
    q->count = 0;
    q->in = 0;
    q->out = 0;

    // make the array of void pointers
    q->len = size;
    q->buffer = (void **) malloc(size * sizeof(void *));

    return q;
}

/** @brief Delete your queue and free all of its memory.
 *
 *  @param q the queue to be deleted.  Note, you should assign the
 *  passed in pointer to NULL when returning (i.e., you should set
 *  *q = NULL after deallocation).
 *
 */
void queue_delete(queue_t **q) {
    if (q != NULL && *q != NULL) {

        // destroy pthread mutexes and conditional variables
        int rc = 0;
        rc = pthread_mutex_destroy(&(*q)->mutex);
        assert(!rc);

        rc = pthread_cond_destroy(&(*q)->cv);
        assert(!rc);

        rc = pthread_cond_destroy(&(*q)->cv2);
        assert(!rc);

        // free the queue pointer
        free((*q)->buffer);
        free(*q);
        *q = NULL;
    }
}

/** @brief push an element onto a queue
 *
 *  @param q the queue to push an element into.
 *
 *  @param elem th element to add to the queue
 *
 *  @return A bool indicating success or failure.  Note, the function
 *          should succeed unless the q parameter is NULL.
 */
bool queue_push(queue_t *q, void *elem) {
    // if the queue is null or full
    if (q == NULL) {
        return 0;
    }

    pthread_mutex_lock(&q->mutex);

    while (q->count == q->len) {
        pthread_cond_wait(&q->cv, &q->mutex);
    }

    //    pthread_mutex_lock(&q->mutex);

    q->buffer[q->in] = elem;
    q->in = (q->in + 1) % q->len;

    q->count++;

    pthread_cond_signal(&q->cv2);
    pthread_mutex_unlock(&q->mutex);

    return 1;
}

/** @brief pop an element from a queue.
 *
 *  @param q the queue to pop an element from.
 *
 *  @param elem a place to assign the poped element.
 *
 *  @return A bool indicating success or failure.  Note, the function
 *          should succeed unless the q parameter is NULL.
 */
bool queue_pop(queue_t *q, void **elem) {
    // if the queue is null or empty
    if (q == NULL) {
        return 0;
    }

    pthread_mutex_lock(&q->mutex);

    while (q->count == 0) {
        pthread_cond_wait(&q->cv2, &q->mutex);
    }

    //    pthread_mutex_lock(&q->mutex);

    *elem = q->buffer[q->out];

    q->out = (q->out + 1) % q->len;
    q->count -= 1;

    pthread_cond_signal(&q->cv);
    pthread_mutex_unlock(&q->mutex);

    return 1;
}
