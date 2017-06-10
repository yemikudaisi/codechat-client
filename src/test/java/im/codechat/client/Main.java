package im.codechat.client;

import im.codechat.client.core.application.CodeChatManagerTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * The description for Main class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 6/9/2017
 */
public class Main {

    public static void main(String[] args){
        Result result = JUnitCore.runClasses(CodeChatManagerTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
