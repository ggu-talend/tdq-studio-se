// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.editor.composite;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.talend.commons.emf.EMFUtil;
import org.talend.cwm.dependencies.DependenciesHandler;
import org.talend.dataprofiler.core.model.ModelElementIndicator;
import org.talend.dataprofiler.core.ui.editor.preview.TableIndicatorUnit;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.indicators.Indicator;
import org.talend.dataquality.indicators.sql.WhereRuleIndicator;
import org.talend.repository.model.IRepositoryNode;
import orgomg.cwm.objectmodel.core.ModelElement;
import orgomg.cwm.resource.relational.NamedColumnSet;

/**
 * The interface class to handle the change when drop tables.
 */
public abstract class AbstractTableDropTree extends AbstractColumnDropTree {

    public static final String TABLEVIEWER_KEY = "TABLEVIEWER_KEY"; //$NON-NLS-1$

    public abstract void dropTables(List<NamedColumnSet> sets, int index);

    public abstract void dropWhereRules(Object data, List<IFile> files, int index);

    public abstract boolean canDrop(NamedColumnSet set);

    public abstract boolean canDrop(Object data, List<IFile> files);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.dataprofiler.core.ui.editor.composite.AbstractColumnDropTree#addElements(org.talend.dataprofiler.core
     * .model.ModelElementIndicator[])
     */
    @Override
    public void addElements(ModelElementIndicator[] elements) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataprofiler.core.ui.editor.composite.AbstractColumnDropTree#canDrop(org.talend.repository.model.
     * IRepositoryNode)
     */
    @Override
    public boolean canDrop(IRepositoryNode reposNode) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.dataprofiler.core.ui.editor.composite.AbstractColumnDropTree#setElements(org.talend.dataprofiler.core
     * .model.ModelElementIndicator[])
     */
    @Override
    protected void setElements(ModelElementIndicator[] modelElementIndicator) {
        // TODO Auto-generated method stub

    }

    /**
     * DOC zshen Comment method "removeDependency".
     * 
     * @param analysis
     * @param unit
     */
    protected void removeDependency(Analysis analysis, TableIndicatorUnit unit) {
        List<ModelElement> reomveElements = new ArrayList<ModelElement>();
        Indicator indicator = unit.getIndicator();
        if (indicator instanceof WhereRuleIndicator) {
            reomveElements.add(indicator.getIndicatorDefinition());
        }
        DependenciesHandler.getInstance().removeDependenciesBetweenModels(analysis, reomveElements);
        for (ModelElement me : reomveElements) {
            EMFUtil.saveSingleResource(me.eResource());
        }
    }
}
