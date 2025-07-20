package lk.codebridge.app.ejb.interceptors;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.Interceptors;
import jakarta.interceptor.InvocationContext;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Interceptor
public class TransferLoggingInterceptor {

    private static final String LOG_FILE = "transfer_logs.txt";

    @AroundInvoke
    public Object logTransfer(InvocationContext ctx) throws Exception {
        LocalDateTime start = LocalDateTime.now();

        System.out.println("Start: " + start);

        Object result = null;
        Exception toThrow = null;
        try {
            result = ctx.proceed();
        } catch (Exception e) {
            toThrow = e;
        }

        LocalDateTime end = LocalDateTime.now();

        StringBuilder logEntry = new StringBuilder();
        logEntry.append("Timestamp: ").append(start).append("\n");
        logEntry.append("Method: ").append(ctx.getMethod().getName()).append("\n");

        Object[] params = ctx.getParameters();
        if (params != null) {
            logEntry.append("Parameters: ");
            for (Object p : params) {
                logEntry.append(p).append(", ");
            }
            logEntry.setLength(logEntry.length() - 2); // remove last comma and space
            logEntry.append("\n");
        }

        logEntry.append("Duration (ms): ").append(java.time.Duration.between(start, end).toMillis()).append("\n");
        if (toThrow != null) {
            logEntry.append("Exception: ").append(toThrow.getMessage()).append("\n");
        }
        logEntry.append("------\n");


        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(LOG_FILE, true)))) {
            out.print(logEntry.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (toThrow != null) {
            throw toThrow;
        }
        return result;
    }
}
