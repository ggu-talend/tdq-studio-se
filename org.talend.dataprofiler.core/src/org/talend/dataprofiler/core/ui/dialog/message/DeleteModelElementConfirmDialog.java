// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.dialog.message;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.talend.cwm.dependencies.DependenciesHandler;
import org.talend.dataprofiler.core.PluginConstant;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.helpers.ReportHelper;
import org.talend.dataquality.reports.TdReport;
import org.talend.dataquality.reports.util.ReportsSwitch;
import orgomg.cwm.objectmodel.core.Dependency;
import orgomg.cwm.objectmodel.core.ModelElement;

/**
 * DOC rli class global comment. Detailled comment
 */
public class DeleteModelElementConfirmDialog {

    private static final String REQUIRES = "requires"; //$NON-NLS-1$

    private static LabelProvider fLabelProvider;

    private static List<ImpactNode> impactNodes = new ArrayList<ImpactNode>();

    /**
     * DOC rli DeleteModelElementConfirmDialog class global comment. Detailled comment
     */
    static class ImpactNode {

        List<ModelElement> children = new ArrayList<ModelElement>();

        private final ModelElement nodeElement;

        public ImpactNode(ModelElement modelElement) {
            this.nodeElement = modelElement;
        }

        public void addRequireModelElement(ModelElement modelElement) {
            if (!children.contains(modelElement)) {
                this.children.add(modelElement);
            }
        }

        public ModelElement[] getChildren() {
            return children.toArray(new ModelElement[children.size()]);
        }

        public String toString() {
            return nodeElement.getName();
        }

        /**
         * Getter for nodeElement.
         * 
         * @return the nodeElement
         */
        public ModelElement getNodeElement() {
            return nodeElement;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((nodeElement == null) ? 0 : nodeElement.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ImpactNode other = (ImpactNode) obj;
            if (nodeElement == null) {
                if (other.nodeElement != null) {
                    return false;
                }
            } else if (nodeElement.getName() == other.nodeElement.getName()) {
                return true;
            } else if (!nodeElement.getName().equals(other.nodeElement.getName())) {
                return false;
            }
            return true;
        }

    }

    static void addDenpendencyElements(ModelElement[] children) {
        for (int i = 0; i < children.length; i++) {
            EList<Dependency> supplierDependencies = children[i].getSupplierDependency();
            ImpactNode impactNode;
            for (Dependency dependency : supplierDependencies) {
                EList<ModelElement> clients = dependency.getClient();
                for (ModelElement client : clients) {
                    impactNode = new ImpactNode(client);
                    int index = impactNodes.indexOf(impactNode);
                    if (index == -1) {
                        impactNode.addRequireModelElement(children[i]);
                        impactNodes.add(impactNode);
                    } else {
                        ImpactNode existNode = impactNodes.get(index);
                        existNode.addRequireModelElement(children[i]);
                    }
                }
            }
        }
    }

    static ImpactNode[] getImpactNodes() {
        return impactNodes.toArray(new ImpactNode[impactNodes.size()]);
    }

    static void clear() {
        impactNodes.clear();
    }

    /**
     * DOC rli RequirsResourceConfirmDialog class global comment. Detailled comment
     */
    protected static class DialogContentProvider implements ITreeContentProvider {

        ImpactNode[] treeNode;

        DialogContentProvider(ImpactNode[] treeNode) {
            this.treeNode = treeNode;
        }

        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof ImpactNode) {
                return ((ImpactNode) parentElement).getChildren();
            }
            return null;
        }

        public Object getParent(Object element) {
            return null;
        }

        public boolean hasChildren(Object element) {
            return element instanceof ImpactNode;
        }

        public Object[] getElements(Object inputElement) {
            return treeNode;
        }

        public void dispose() {

        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }
    }

    /**
     * DOC rli Comment method "showDialog".
     * 
     * @param parentShell
     * @param modelElements
     * @param dialogMessage
     * @return
     */
    public static int showDialog(Shell parentShell, ModelElement[] modelElements, String dialogMessage) {
        addDenpendencyElements(modelElements);
        ImpactNode[] impactElements = getImpactNodes();
        if (impactElements.length > 0) {
            TreeMessageInfoDialog dialog = new TreeMessageInfoDialog(parentShell, DefaultMessagesImpl
                    .getString("DeleteModelElementConfirmDialog.confirmResourceDelete"), null, dialogMessage, //$NON-NLS-1$
                    MessageDialog.WARNING, new String[] { IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL }, 0);
            dialog.setContentProvider(new DialogContentProvider(impactElements));
            dialog.setLabelProvider(getLabelProvider());
            dialog.setInput(new Object());
            clear();
            int result = dialog.open();
            if (result == Window.OK) {
                removeReportComponent(impactElements);
            }
            return result;
        } else {
            return popConfirmDialog(modelElements);
        }
    }

    public static int showElementImpactDialog(Shell parentShell, ModelElement[] modelElements, String dialogMessage) {
        addDenpendencyElements(modelElements);
        ImpactNode[] impactElements = getImpactNodes();
        if (impactElements.length > 0) {
            TreeMessageInfoDialog dialog = new TreeMessageInfoDialog(parentShell, DefaultMessagesImpl
                    .getString("DeleteModelElementConfirmDialog.confirmElementDelete"), null, dialogMessage, //$NON-NLS-1$
                    MessageDialog.WARNING, new String[] { IDialogConstants.OK_LABEL }, 0);
            dialog.setContentProvider(new DialogContentProvider(impactElements));
            dialog.setLabelProvider(getLabelProvider());
            dialog.setInput(new Object());
            clear();
            int result = dialog.open();
            return result;
        } else {
            return Window.OK;
        }
    }

    private static int popConfirmDialog(ModelElement[] modelElements) {
        MessageDialog messageDialog;
        if (modelElements.length > 1) {
            messageDialog = new MessageDialog(
                    null,
                    DefaultMessagesImpl.getString("DeleteModelElementConfirmDialog.confirmResourceDelete"), null, //$NON-NLS-1$
                    DefaultMessagesImpl.getString("DeleteModelElementConfirmDialog.deleleTheseResources"), MessageDialog.WARNING, new String[] { //$NON-NLS-1$
                    IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL }, 0);

        } else {
            messageDialog = new MessageDialog(
                    null,
                    DefaultMessagesImpl.getString("DeleteModelElementConfirmDialog.confirmResourcesDelete"), null, DefaultMessagesImpl.getString("DeleteModelElementConfirmDialog.areYouDelele", modelElements[0].getName()) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    , MessageDialog.WARNING, new String[] { //$NON-NLS-1$
                    IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL }, 0);
        }
        return messageDialog.open();
    }

    private static void removeReportComponent(ImpactNode[] impactNodes) {
        ReportsSwitch<TdReport> mySwitch = new ReportsSwitch<TdReport>() {

            public TdReport caseTdReport(TdReport object) {
                return object;
            }
        };
        TdReport report = null;
        for (ImpactNode node : impactNodes) {
            report = mySwitch.doSwitch(node.getNodeElement());
            if (report != null && node.getChildren().length > 0) {
                List<Analysis> anaList = new ArrayList<Analysis>();
                for (ModelElement element : node.getChildren()) {
                    anaList.add((Analysis) element);
                }
                ReportHelper.removeAnalyses(report, anaList);
                // remove dependencies
                DependenciesHandler.getInstance().removeDependenciesBetweenModels(report, anaList);
            }
        }
    }

    protected static LabelProvider getLabelProvider() {
        if (fLabelProvider == null) {
            fLabelProvider = new LabelProvider() {

                public String getText(Object obj) {
                    if (obj instanceof ImpactNode) {
                        return ((ImpactNode) obj).toString();
                    }
                    return REQUIRES + PluginConstant.SPACE_STRING + "<<" + ((ModelElement) obj).getName() + ">>"; //$NON-NLS-1$ //$NON-NLS-2$
                }
            };
        }
        return fLabelProvider;
    }

}
