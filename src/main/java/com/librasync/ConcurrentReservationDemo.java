package com.librasync;

import java.util.ArrayList;
import java.util.List;

public class ConcurrentReservationDemo {

    public void run(LibraryService service, int bookId) throws InterruptedException {
        // These demo IDs must exist in the seeded database.
        int[] userIds = {2, 3, 4, 5, 6};
        List<Thread> threads = new ArrayList<>();

        System.out.println("\nStarting 5 threads that simultaneously request Book ID " + bookId + "...");

        for (int userId : userIds) {
            Thread t = new Thread(() -> {
                String result = service.reserveBook(userId, bookId);
                System.out.println(Thread.currentThread().getName() + " -> " + result);
            }, "Reservation-User-" + userId);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) t.join();
        System.out.println("Concurrent reservation test completed.");
    }
}
