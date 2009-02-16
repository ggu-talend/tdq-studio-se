// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dq;

import org.talend.commons.emf.FactoriesUtil;

/**
 * This class store all the constant of current plugin.
 * 
 */
public final class PluginConstant {

    private PluginConstant() {
    }

    public static final String EMPTY_STRING = ""; //$NON-NLS-1$

    public static final String PASSWORD_PROPERTY = "password"; //$NON-NLS-1$

    public static final String HOSTNAME_PROPERTY = "hostname"; //$NON-NLS-1$

    public static final String PORT_PROPERTY = "port"; //$NON-NLS-1$

    public static final String DBTYPE_PROPERTY = "dbtype"; //$NON-NLS-1$

    public static final String DEFAULT_PARAMETERS = "zeroDateTimeBehavior=convertToNull&noDatetimeStringSync=true"; //$NON-NLS-1$

    public static final String PRV_SUFFIX = "." + FactoriesUtil.PROV; //$NON-NLS-1$

    public static final String ANA_SUFFIX = "." + FactoriesUtil.ANA; //$NON-NLS-1$

    public static final String REP_SUFFIX = "." + FactoriesUtil.REP; //$NON-NLS-1$

    public static final String PATTERN_SUFFIX = "*.pattern"; //$NON-NLS-1$
}
