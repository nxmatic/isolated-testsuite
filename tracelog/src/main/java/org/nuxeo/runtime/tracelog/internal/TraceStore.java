package org.nuxeo.runtime.tracelog.internal;

import java.io.File;
import java.util.Map;

import org.mapdb.DB;
import org.mapdb.DB.HTreeMapMaker;
import org.mapdb.DBMaker;
import org.nuxeo.runtime.tracelog.spi.LogEvent;

public class TraceStore {

    public TraceStore() {
        this(new File("target/traces"));
    }

    public TraceStore(File location) {
      filestore =  location;
    }

    protected final File filestore;

    protected DB db;

    protected HTreeMapMaker maker;

    public Map<Long,LogEvent> events;

    public void release() {
        db.commit();
        db.close();
        db = null;
        maker = null;
    }

    public Map<Long,LogEvent> get(boolean readonly, boolean truncate) {
        return events = doLoad(readonly).doCreate(truncate).doMakeOrGet();
    }

    protected TraceStore doLoad(boolean readonly) {
        DBMaker maker = DBMaker.newFileDB(filestore).asyncWriteEnable().transactionDisable().compressionEnable();
        if (readonly) {
            maker = maker.readOnly();
        }
        db = maker.make();
        return this;
    }

    protected TraceStore doCreate(boolean truncate) {
         if (truncate) {
             db.delete("events");
         }
         maker = db.createHashMap("events");
         return this;
    }

    protected Map<Long, LogEvent> doMake() {
        return maker.make();
    }

    protected Map<Long, LogEvent> doMakeOrGet() {
        return maker.makeOrGet();
    }

}
