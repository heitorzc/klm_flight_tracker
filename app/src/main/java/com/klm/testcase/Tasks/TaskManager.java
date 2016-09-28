package com.klm.testcase.Tasks;

import android.content.Context;


public class TaskManager {

    public interface TaskListener {
        void performTask(final Object responseObject);
    }

    protected Context context;
    private TaskListener taskListener;

    public TaskManager(TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    protected void doRequest(final String url, final Object responseObjectClass) {

        try {

            new HttpRequestManager(responseObjectClass, taskListener).get(url);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
