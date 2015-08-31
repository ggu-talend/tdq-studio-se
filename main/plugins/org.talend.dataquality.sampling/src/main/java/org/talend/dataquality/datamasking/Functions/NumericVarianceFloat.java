// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.datamasking.Functions;

import java.io.Serializable;

/**
 * created by jgonzalez on 18 juin 2015. See NumericVariance.
 *
 */
public class NumericVarianceFloat extends NumericVariance<Float> implements Serializable {

    private static final long serialVersionUID = -8029563336814263376L;

    @Override
    public Float generateMaskedRow(Float f) {
        if (f == null && keepNull) {
            return null;
        } else {
            if (f == null) {
                return 0.0f;
            } else {
                super.init();
                float value = f * ((float) rate + 100) / 100;
                return value;
            }
        }
    }
}