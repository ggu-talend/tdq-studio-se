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
package org.talend.dataprofiler.core.ui.action.provider;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.talend.dataprofiler.core.ImageLib;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataprofiler.core.ui.wizard.folder.FolderWizard;
import org.talend.resource.ResourceManager;
import org.talend.top.repository.ProxyRepositoryManager;

/**
 * This provider for creating a user folder.
 */
public class CreateUserFolderProvider extends CommonActionProvider {

    public static final String ACTION_ID = "Create_User_Folder_Action"; //$NON-NLS-1$

    /**
     * DOC rli CreateSubFolderProvider constructor comment.
     */
    public CreateUserFolderProvider() {
    }

    private IFolder currentSelection;

    /**
     * Adds a submenu to the given menu with the name "New Component".
     */
    public void fillContextMenu(IMenuManager menu) {
        Object obj = ((TreeSelection) this.getContext().getSelection()).getFirstElement();
        if (obj instanceof IFolder) {

            currentSelection = (IFolder) obj;

            if (!ResourceManager.isNoSubFolder(currentSelection)) {
                CreateUserFolderAction createSubFolderAction = new CreateUserFolderAction();
                menu.add(createSubFolderAction);
            }
        }

    }

    /**
     * @author rli
     * 
     */
    private class CreateUserFolderAction extends Action {

        public CreateUserFolderAction() {
            super(DefaultMessagesImpl.getString("CreateUserFolderProvider.createFolder")); //$NON-NLS-1$
            setImageDescriptor(ImageLib.getImageDescriptor(ImageLib.FOLDER_NEW_IMAGE));
            setId(ACTION_ID); //$NON-NLS-1$
        }

        /*
         * (non-Javadoc) Method declared on IAction.
         */
        public void run() {
            FolderWizard processWizard = new FolderWizard(currentSelection.getFullPath(), null);
            Shell activeShell = Display.getCurrent().getActiveShell();
            WizardDialog dialog = new WizardDialog(activeShell, processWizard);
            dialog.setPageSize(400, 60);
            dialog.create();
            if (WizardDialog.OK == dialog.open())
                ProxyRepositoryManager.getInstance().save();
            // if (dialog.open() == Window.OK)
            // {
            // try {
            // currentSelection.refreshLocal(IResource.DEPTH_INFINITE, null);
            // } catch (CoreException e) {
            // ExceptionHandler.process(e);
            // }
            // DQRespositoryView findView = (DQRespositoryView) CorePlugin.getDefault().findView(DQRespositoryView.ID);
            // findView.getCommonViewer().setExpandedState(\, true);
            // }
        }
    }

}
