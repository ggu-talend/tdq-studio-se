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
package org.talend.dataquality.record.linkage.record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.talend.dataquality.matchmerge.Record;
import org.talend.dataquality.record.linkage.Messages;
import org.talend.dataquality.record.linkage.attribute.DummyMatcher;
import org.talend.dataquality.record.linkage.attribute.IAttributeMatcher;

/**
 * @author scorreia Abstract class for matching records.
 */
public abstract class AbstractRecordMatcher implements IRecordMatcher {

    private static Logger log = Logger.getLogger(AbstractRecordMatcher.class);

    protected int recordSize = 0;

    protected int[][] attributeGroups;

    /**
     * The importance weights of each attribute.
     */
    protected double[] attributeWeights;

    /**
     * Indices of records to be compared.
     */
    protected int[] usedIndices;

    protected IAttributeMatcher[] attributeMatchers;

    /**
     * Indices of the Matchers to be used as blocking variables.
     */
    protected int[] blockedIndices;

    /**
     * Indices of records to be compared (without the blocked indices).
     */
    protected int[] usedIndicesNotblocked = null; // TODO to be reset

    /**
     * The attribute matching weigths computed by each attribute matcher.
     */
    protected double[] attributeMatchingWeights;

    /**
     * Tells whether attribute group are used.
     */
    protected boolean useGroups = false;

    /**
     * Threshold below which variables are blocked. Default value is 1.
     */
    protected double blockingThreshold = 1;

    /**
     * Threshold below which a record will not match
     */
    protected Double recordMatchThreshold = Double.POSITIVE_INFINITY;

    /**
     * hide the label when there is only one matcher.
     */
    protected boolean displayLabels = false;

    @Override
    public org.talend.dataquality.matchmerge.mfb.MatchResult getMatchingWeight(Record record1, Record record2) {
        // Sub classes may override this default implementation.
        throw new NotImplementedException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.record.linkage.record.IRecordMatcher#setDisplayLabels(boolean)
     */
    @Override
    public void setDisplayLabels(boolean displayLabels) {
        this.displayLabels = displayLabels;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.matching.record.IRecordMatcher#setAttributeGroups(int[][])
     */
    @Override
    public boolean setAttributeGroups(int[][] groups) {
        if (groups == null) {
            this.useGroups = true;
            return true;
        }
        if (groups.length != recordSize) {
            return false;
        }
        boolean atLeastOneGroup = false;
        for (int[] g : groups) {
            if (g == null || g.length == 0) {
                continue;
            }
            // else
            atLeastOneGroup = true;
            break;
        }
        if (!atLeastOneGroup) {
            return false;
        }
        this.attributeGroups = groups;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.dataquality.matching.record.IRecordMatcher#setAttributeMatchers(org.talend.dataquality.matching.attribute
     * .IAttributeMatcher[])
     */
    @Override
    public boolean setAttributeMatchers(IAttributeMatcher[] attrMatchers) {
        if (attrMatchers == null || attrMatchers.length != recordSize) {
            return false;
        }
        // else
        this.attributeMatchers = attrMatchers;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.record.linkage.record.IRecordMatcher#getAttributeMatchers()
     */
    @Override
    public IAttributeMatcher[] getAttributeMatchers() {
        return attributeMatchers;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.matching.record.IRecordMatcher#setAttributeWeights(double[])
     */
    @Override
    public boolean setAttributeWeights(double[] weights) {
        if (weights == null || recordSize != weights.length) {
            return false;
        }
        attributeWeights = normalize(weights);
        return attributeWeights != null;
    }

    private double[] normalize(double[] weights) {
        List<Integer> indices = new ArrayList<Integer>();
        double total = 0;
        for (int i = 0; i < recordSize; i++) {
            final double w = weights[i];
            if (w < 0) {
                throw new IllegalArgumentException(Messages.getString("AbstractRecordMatcher.InvalideAttributeWeight", w)); //$NON-NLS-1$
            }
            total += w;
            if (w != 0) {
                indices.add(i);
            }
        }
        // at least one weight must be non zero
        if (total == 0.0d) {
            throw new IllegalArgumentException(Messages.getString("AbstractRecordMatcher.0")); //$NON-NLS-1$
        }

        this.usedIndices = new int[indices.size()];
        int j = 0;
        for (Integer idx : indices) {
            usedIndices[j++] = idx;
        }

        double[] normalized = new double[recordSize];
        for (int i = 0; i < recordSize; i++) {
            final double w = weights[i];
            // total = 0 already handled before
            normalized[i] = w / total;
        }
        return normalized;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.matching.record.IRecordMatcher#setRecordSize(int)
     */
    @Override
    public void setRecordSize(int numberOfAttributes) {
        this.recordSize = numberOfAttributes;
        // initialize weights with 1 for every attribute
        double[] weights = new double[recordSize];
        Arrays.fill(weights, 1.0d);
        this.attributeWeights = normalize(weights);
        // initialize array of attribute MATCHING weights
        this.attributeMatchingWeights = new double[recordSize];
    }

    /**
     * Method "internalScalarProduct" computes the scalar product of elements listed in usedIndices array.
     * 
     * @param v1
     * @param v2
     * @return
     */
    protected double internalScalarProduct(double[] v1, double[] v2) {
        assert usedIndices != null;
        double result = 0;
        for (int idx : usedIndices) {
            result += v1[idx] * v2[idx];
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.record.linkage.record.IRecordMatcher#setBlockingAttributeMatchers(int[])
     */
    @Override
    public boolean setBlockingAttributeMatchers(int[] attrMatcherIndices) {
        for (int idx : attrMatcherIndices) {
            if (idx < 0 || idx >= recordSize) {
                log.error("the index must be between 0 and the size of the records"); //$NON-NLS-1$
                return false;
            }
        }
        this.blockedIndices = attrMatcherIndices;
        return true;
    }

    protected int[] getUsedIndicesNotblocked() {

        if (usedIndicesNotblocked == null && usedIndices != null) {
            List<Integer> indices = new ArrayList<Integer>();
            for (int usedIdx : usedIndices) {
                boolean isBlocked = false;
                if (blockedIndices != null) {
                    for (int blockedIdx : blockedIndices) {
                        if (blockedIdx == usedIdx) {
                            isBlocked = true;
                            break;
                        }
                    }
                }
                if (!isBlocked) {
                    indices.add(usedIdx);
                }
            }
            usedIndicesNotblocked = new int[indices.size()];
            for (int i = 0; i < indices.size(); i++) {
                usedIndicesNotblocked[i] = indices.get(i);
            }
        }
        return usedIndicesNotblocked;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.record.linkage.record.IRecordMatcher#setblockingThreshold(double)
     */
    @Override
    public boolean setblockingThreshold(double threshold) {
        this.blockingThreshold = threshold;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.record.linkage.record.IRecordMatcher#getCurrentAttributeMatchingWeights()
     */
    @Override
    public double[] getCurrentAttributeMatchingWeights() {
        return this.attributeMatchingWeights;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.record.linkage.record.IRecordMatcher#getLabeledAttributeMatchWeights()
     */
    @Override
    public String getLabeledAttributeMatchWeights() {
        final String separator = " | "; //$NON-NLS-1$
        StringBuffer buffer = new StringBuffer();
        double[] currentAttributeMatchingWeights = this.getCurrentAttributeMatchingWeights();
        for (int i = 0; i < currentAttributeMatchingWeights.length; i++) {
            IAttributeMatcher attributeMatcher = this.attributeMatchers[i];
            if (attributeMatcher instanceof DummyMatcher) {
                continue; // Don't take dummy matcher into account.
            }
            if (buffer.length() > 0) {
                buffer.append(separator);
            }
            if (displayLabels) {
                String attributeName = attributeMatcher.getAttributeName();
                if (attributeName != null) {
                    buffer.append(attributeName).append(": "); //$NON-NLS-1$
                }
            }
            buffer.append(currentAttributeMatchingWeights[i]);
        }

        return buffer.toString();
    }

    /**
     * Getter for recordMatchThreshold.
     * 
     * @return the recordMatchThreshold
     */
    @Override
    public double getRecordMatchThreshold() {
        return this.recordMatchThreshold;
    }

    /**
     * Sets the recordMatchThreshold.
     * 
     * @param recordMatchThreshold the recordMatchThreshold to set
     */
    @Override
    public void setRecordMatchThreshold(double recordMatchThreshold) {
        this.recordMatchThreshold = recordMatchThreshold;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.record.linkage.record.IRecordMatcher#getRecordSize()
     */
    @Override
    public int getRecordSize() {
        return recordSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.getClass().getSimpleName()).append(" Record size:"); //$NON-NLS-1$
        buf.append(this.recordSize).append("\n"); //$NON-NLS-1$
        for (int usedIndice : usedIndices) {
            buf.append(this.attributeMatchers[usedIndice].getMatchType()).append("/"); //$NON-NLS-1$
        }
        return buf.toString();
    }

}
