package com.insigma.common.dwr;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSessions;


public class DwrDragView {

    public static void sendMsg(final String msg) throws Exception {
        Browser.withAllSessions(new Runnable() {
            @Override
            public void run() {
                ScriptSessions.addFunctionCall("show", msg);
            }
        });
    }
}
