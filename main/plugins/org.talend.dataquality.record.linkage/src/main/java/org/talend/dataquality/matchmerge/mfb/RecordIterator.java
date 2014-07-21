// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.matchmerge.mfb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.talend.dataquality.matchmerge.Attribute;
import org.talend.dataquality.matchmerge.Record;

public class RecordIterator implements Iterator<Record> {

    private final int size;

    private List<RecordGenerator> rcdGenerators = new ArrayList<RecordGenerator>();

    protected int currentIndex = 0;

    protected long timestamp = 0;

    public RecordIterator(int size, RecordGenerator generators) {
        this.size = size;
        rcdGenerators.add(generators);
    }

    /**
     * 
     * Record iterators
     * 
     * @param size the record count.
     * @param generators record generator.
     */
    public RecordIterator(int size, List<RecordGenerator> generators) {
        this.size = size;
        this.rcdGenerators = generators;
    }

    public interface ValueGenerator {

        int getColumnIndex();

        String newValue();
    }

    @Override
    public boolean hasNext() {
        return currentIndex < size;
    }

    @Override
    public Record next() {
        Vector<Attribute> record = new Vector<Attribute>();
        // Records
        int rcdIdx = currentIndex;
        if (currentIndex >= rcdGenerators.size()) {
            // Keep the compatibility to old behavior (MDM Junit testing only take one record but can run several times
            // , see MFBTest .)
            rcdIdx = 0;

        }
        Map<String, ValueGenerator> matchKeyMap = rcdGenerators.get(rcdIdx).getMatchKeyMap();
        // Attributes
        for (Map.Entry<String, ValueGenerator> generator : matchKeyMap.entrySet()) {
            Attribute attribute = new Attribute(generator.getKey(), generator.getValue().getColumnIndex());
            attribute.setValue(generator.getValue().newValue());
            record.add(attribute);
        }
        currentIndex++;
        return createRecord(record, rcdGenerators.get(rcdIdx).getOriginalRow());
    }

    /**
     * Creaet a new record.
     * 
     * @param record
     * @return
     */
    protected Record createRecord(Vector<Attribute> record, String[] originalRow) {
        return new Record(record, String.valueOf(currentIndex - 1), timestamp++, "MFB");
    }

    @Override
    public void remove() {
        throw new RuntimeException("Not supported");
    }
}
