// Asgn 2: A simple HTTP server.
// By: Eugene Chou
//     Andrew Quinn
//     Brian Zhao

// Assignment completed by Nihal Ernest

#include "asgn2_helper_funcs.h"
#include "connection.h"
#include "debug.h"
#include "response.h"
#include "request.h"
#include "queue.h"

#include <err.h>
#include <errno.h>
#include <fcntl.h>
#include <signal.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

#include <sys/stat.h>
#include <sys/file.h>

#include <pthread.h>

#define OPTIONS "t: "

void handle_connection(int);

void handle_get(conn_t *);
void handle_put(conn_t *);
void handle_unsupported(conn_t *);

int worker_threads();

queue_t *q = NULL;

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

/* audit log function prints the log to stderr
   format: req, uri, status, rid

*/
void audit_log(char *req, char *uri, int status, char *id) {
    fprintf(stderr, "%s,/%s,%d,%s\n", req, uri, status, id); //Printing the audit log out to console
}

int main(int argc, char **argv) {
    if (argc < 2) { // cant be missing that many args
        warnx("wrong arguments: %s port_num", argv[0]);
        fprintf(stderr, "usage: %s <port>\n", argv[0]);
        return EXIT_FAILURE;
    }

    int opt = 0;
    int threads = 0;

    while ((opt = getopt(argc, argv, OPTIONS)) != -1) { // checks for the -t flag
        switch (opt) {
        case 't': threads = atoi(optarg); break;
        default: threads = 4; break;
        }
    }

    //	printf("Threads: %d\n", threads);

    char *endptr = NULL;
    size_t port = (size_t) strtoull(argv[argc - 1], &endptr, 10);
    if (endptr && *endptr != '\0') {
        warnx("invalid port number: %s", argv[argc - 1]);
        return EXIT_FAILURE;
    }

    signal(SIGPIPE, SIG_IGN);
    Listener_Socket sock;
    listener_init(&sock, port);

    pthread_t wt[threads];

    q = queue_new(threads);

    for (int i = 0; i < threads; i++) { //creates all the worker threads
        pthread_create(&wt[i], NULL, (void *(*) (void *) ) worker_threads, NULL);
        //	    printf("Thread %d created\n", i);
    }

    while (1) {
        //		printf("donke\n");
        uintptr_t connfd = listener_accept(&sock);
        //      printf("monke\n");

        if (!queue_push(q, (void *) connfd)) { // pushes the connection to the queue
            return EXIT_FAILURE;
        }
    }

    pthread_mutex_destroy(&mutex);

    return EXIT_SUCCESS;
}

/*
worker threads pop connections from the queue and handle them
*/
int worker_threads() {
    uintptr_t rc;

    while (1) {
        if (!queue_pop(q, (void **) &rc)) {
            // Print error message (internal server)
            return EXIT_FAILURE;
        }

        //		fprintf(stderr, "after: %lu\n", rc);
        handle_connection(rc);

        //		fprintf(stderr, "handle connection works\n");
        close(rc);
    }

    return 0;
}

void handle_connection(int connfd) {

    conn_t *conn = conn_new(connfd);

    const Response_t *res = conn_parse(conn);

    if (res != NULL) {
        conn_send_response(conn, res);
    } else {
        //        debug("%s", conn_str(conn));
        const Request_t *req = conn_get_request(conn);
        if (req == &REQUEST_GET) {
            handle_get(conn);
        } else if (req == &REQUEST_PUT) {
            handle_put(conn);
        } else {
            handle_unsupported(conn);
        }
    }

    conn_delete(&conn);
}

void handle_get(conn_t *conn) {

    //debug("GET request not implemented. But, we want to get %s", uri);

    // What are the steps in here?

    // 1. Open the file.
    // If  open it returns < 0, then use the result appropriately
    //   a. Cannot access -- use RESPONSE_FORBIDDEN
    //   b. Cannot find the file -- use RESPONSE_NOT_FOUND
    //   c. other error? -- use RESPONSE_INTERNAL_SERVER_ERROR
    // (hint: check errno for these cases)!

    // 2. Get the size of the file.
    // (hint: checkout the function fstat)!

    // Get the size of the file.

    // 3. Check if the file is a directory, because directories *will*
    // open, but are not valid.
    // (hint: checkout the macro "S_IFDIR", which you can use after you call fstat!)

    // 4. Send the file
    // (hint: checkout the conn_send_file function!)
    char *uri = conn_get_uri(conn);

    // Needed for the audit log
    char *reqId = NULL;
    reqId = conn_get_header(conn, "Request-Id");

    const Response_t *res = NULL;
    //  debug("handling put request for %s", uri);

    // Check if file already exists before opening it.
    //    bool existed = access(uri, F_OK) == 0;
    //  debug("%s existed? %d", uri, existed);

    // Open the file..

    pthread_mutex_lock(&mutex);
    int fd = open(uri, O_RDONLY, 0666);

    if (fd < 0) {
        if (errno == EACCES) {
            res = &RESPONSE_FORBIDDEN;
            conn_send_response(conn, res);
            audit_log("GET", uri, 403, reqId);
        } else if (errno == ENOENT) {
            res = &RESPONSE_NOT_FOUND;
            conn_send_response(conn, res);
            audit_log("GET", uri, 404, reqId);
        } else {
            res = &RESPONSE_INTERNAL_SERVER_ERROR;
            conn_send_response(conn, res);
            audit_log("GET", uri, 500, reqId);
        }

        pthread_mutex_unlock(&mutex);
        close(fd);
        return;
    }

    struct stat dir;
    stat(uri, &dir);

    if (S_ISDIR(dir.st_mode)) {
        conn_send_response(conn, &RESPONSE_FORBIDDEN);
        audit_log("GET", uri, 403, reqId);
        pthread_mutex_unlock(&mutex);
        close(fd);
        return;
    }

    pthread_mutex_unlock(&mutex);
    flock(fd, LOCK_SH);

    struct stat sb;
    fstat(fd, &sb);

    int content_length = sb.st_size;

    conn_send_file(conn, fd, content_length);

    audit_log("GET", uri, 200, reqId);

    //	flock(fd, LOCK_UN);

    // heard that flock(unlock) is not needed if u have close(fd) right after

    close(fd);
}

void handle_unsupported(conn_t *conn) {
    debug("handling unsupported request");

    // send responses
    conn_send_response(conn, &RESPONSE_NOT_IMPLEMENTED);
}

void handle_put(conn_t *conn) {

    char *uri = conn_get_uri(conn);
    const Response_t *res = NULL;
    //debug("handling put request for %s", uri);

    char *reqId = NULL;
    reqId = conn_get_header(conn, "Request-Id");

    // Check if file already exists before opening it.
    //bool existed = access(uri, F_OK) == 0;
    //debug("%s existed? %d", uri, existed);

    pthread_mutex_lock(&mutex);
    bool existed = access(uri, F_OK) == 0;
    //    int turn = 0;

    // Open the file..
    int fd = open(uri, O_CREAT | O_WRONLY, 0600);

    flock(fd, LOCK_EX);

    // Using this instead of O_TRUNC helped pass more cases in stress_mix
    ftruncate(fd, 1);

    //    pthread_mutex_unlock(&mutex);

    int status = 0;
    if (fd < 0) {
        //debug("%s: %d", uri, errno);
        if (errno == EACCES || errno == EISDIR || errno == ENOENT) {
            res = &RESPONSE_FORBIDDEN;
            status = 403;
            //       goto out;
        } else {
            res = &RESPONSE_INTERNAL_SERVER_ERROR;
            status = 500;
            //         goto out;
        }
        pthread_mutex_unlock(&mutex);
        goto out;
    }

    pthread_mutex_unlock(&mutex);

    //    ftruncate(fd, 1); // this one works well

    //  flock(fd, LOCK_EX);

    res = conn_recv_file(conn, fd);

    // check to see if the file existed
    if (res == NULL && existed) {
        res = &RESPONSE_OK;
        status = 200;
        goto out;
    } else if (res == NULL && !existed) {
        res = &RESPONSE_CREATED;
        status = 201;
        goto out;
    }

    //	flock(fd, LOCK_UN);

out:
    audit_log("PUT", uri, status, reqId);

    conn_send_response(conn, res);
    close(fd);
}
