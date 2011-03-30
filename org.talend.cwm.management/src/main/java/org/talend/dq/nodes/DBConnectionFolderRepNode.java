// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dq.nodes;

import java.util.List;

import org.apache.log4j.Logger;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.utils.data.container.Container;
import org.talend.commons.utils.data.container.RootContainer;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.Folder;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.dq.helper.RepositoryNodeHelper;
import org.talend.repository.model.IRepositoryNode;
import org.talend.repository.model.RepositoryNode;

/**
 * DOC klliu class global comment. Detailled comment
 */
public class DBConnectionFolderRepNode extends RepositoryNode {

    private static Logger log = Logger.getLogger(DBConnectionFolderRepNode.class);

    /**
     * DOC klliu DBConnectionFolderRepNode constructor comment.
     * 
     * @param object
     * @param parent
     * @param type
     */
    public DBConnectionFolderRepNode(IRepositoryViewObject object, RepositoryNode parent, ENodeType type) {
        super(object, parent, type);
    }

    @Override
    public List<IRepositoryNode> getChildren() {
        // MOD qiongli 2011-2-22,bug 17588.override 'getChildren(boolean withDeleted)'. in this case,withDeleted is
        // false.
        return getChildren(false);
    }

    @Override
    public List<IRepositoryNode> getChildren(boolean withDeleted) {
        try {
            super.getChildren().clear();
            RootContainer<String, IRepositoryViewObject> tdqViewObjects = ProxyRepositoryFactory.getInstance()
                    .getTdqRepositoryViewObjects(getContentType(), RepositoryNodeHelper.getPath(this).toString());
            // sub folders
            // MOD qiongli 2011-1-18.setProperties for every node
            for (Container<String, IRepositoryViewObject> container : tdqViewObjects.getSubContainer()) {
                Folder folder = new Folder((Property) container.getProperty(), ERepositoryObjectType.METADATA_CONNECTIONS);
                if (!withDeleted && folder.isDeleted()) {
                    continue;
                }
                DBConnectionSubFolderRepNode childNodeFolder = new DBConnectionSubFolderRepNode(folder, this,
                        ENodeType.SIMPLE_FOLDER);
                childNodeFolder.setProperties(EProperties.LABEL, ERepositoryObjectType.METADATA_CONNECTIONS);
                childNodeFolder.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.METADATA_CONNECTIONS);
                folder.setRepositoryNode(childNodeFolder);
                super.getChildren().add(childNodeFolder);
            }
            // connection files
            for (IRepositoryViewObject viewObject : tdqViewObjects.getMembers()) {
                if (!withDeleted && viewObject.isDeleted()) {
                    continue;
                }

                DBConnectionRepNode repNode = new DBConnectionRepNode(viewObject, this, ENodeType.REPOSITORY_ELEMENT);
                repNode.setProperties(EProperties.LABEL, ERepositoryObjectType.METADATA_CONNECTIONS);
                repNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.METADATA_CONNECTIONS);
                viewObject.setRepositoryNode(repNode);
                super.getChildren().add(repNode);
            }
        } catch (PersistenceException e) {
            log.error(e, e);
        }
        return super.getChildren();

    }
}
