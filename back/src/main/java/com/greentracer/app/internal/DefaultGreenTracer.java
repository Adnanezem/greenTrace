package com.greentracer.app.internal;
import com.greentracer.app.records.testRecord;

public class DefaultGreenTracer {
    public testRecord greeting(String name) {
        return new testRecord(name);
    }
}
