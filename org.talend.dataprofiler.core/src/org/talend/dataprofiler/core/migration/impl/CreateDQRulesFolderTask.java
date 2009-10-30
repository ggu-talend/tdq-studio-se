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
package org.talend.dataprofiler.core.migration.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFolder;
import org.talend.dataprofiler.core.CorePlugin;
import org.talend.dataprofiler.core.exception.ExceptionHandler;
import org.talend.dataprofiler.core.manager.DQStructureManager;
import org.talend.dataprofiler.core.migration.AbstractMigrationTask;
import org.talend.resource.ResourceManager;

/**
 * DOC xqliu class global comment. Detailled comment
 */
public class CreateDQRulesFolderTask extends AbstractMigrationTask {

    protected static Logger log = Logger.getLogger(CreateDQRulesFolderTask.class);

    private static final String DQ_RULES = "DQ Rules"; //$NON-NLS-1$

    private static final String RULES_PATH = "/dqrules"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataprofiler.core.migration.IWorkspaceMigrationTask#execute()
     */
    public boolean execute() {
        try {
            DQStructureManager manager = DQStructureManager.getInstance();
            IFolder createNewFoler = manager.createNewReadOnlyFolder(ResourceManager.getLibrariesFolder(), DQ_RULES);
            manager.copyFilesToFolder(CorePlugin.getDefault(), RULES_PATH, true, createNewFoler, null);
        } catch (Exception e) {
            ExceptionHandler.process(e);
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataprofiler.core.migration.IWorkspaceMigrationTask#getOrder()
     */
    public Date getOrder() {
        Calendar calender = Calendar.getInstance();
        calender.set(2009, 2, 13);
        return calender.getTime();
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.talend.dataprofiler.core.migration.IWorkspaceMigrationTask# getMigrationTaskType()
     */
    public MigrationTaskType getMigrationTaskType() {
        return MigrationTaskType.STUCTRUE;
    }
}
