// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.talend.cwm.helper.TaggedValueHelper;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataprofiler.core.ui.editor.pattern.PatternEditor;
import org.talend.dataprofiler.core.ui.wizard.AbstractWizard;
import org.talend.dataquality.domain.pattern.ExpressionType;
import org.talend.dataquality.domain.pattern.Pattern;
import org.talend.dataquality.domain.pattern.RegularExpression;
import org.talend.dataquality.helpers.BooleanExpressionHelper;
import org.talend.dq.analysis.parameters.PatternParameter;
import org.talend.dq.helper.resourcehelper.ResourceFileMap;
import org.talend.dq.pattern.PatternBuilder;
import org.talend.dq.writer.impl.ElementWriterFactory;
import org.talend.utils.sugars.TypedReturnCode;
import orgomg.cwm.objectmodel.core.ModelElement;

/**
 * DOC qzhang class global comment. Detailled comment <br/>
 * 
 * $Id: talend.epf 1 2006-09-29 17:06:40Z qzhang $
 * 
 */
public class CreatePatternWizard extends AbstractWizard {

    private CreatePatternWizardPage1 mPage;

    private CreatePatternWizardPage2 mPage2;

    private IPath location;

    private ExpressionType type;

    private PatternParameter parameter;

    private String expression;

    private String language;

    private String purpose;

    private PatternBuilder patternBuilder;

    /**
     * DOC qzhang CreateSqlFileWizard constructor comment.
     * 
     * @param folder
     * @param type
     */
    public CreatePatternWizard(PatternParameter parameter, ExpressionType type) {
        this.type = type;
        this.parameter = parameter;
    }

    public CreatePatternWizard(PatternParameter parameter, ExpressionType type, String expression, String language) {
        this(parameter, type);

        this.expression = expression;
        this.language = language;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages() {
        String s = DefaultMessagesImpl.getString("CreatePatternWizard.regularExression"); //$NON-NLS-1$

        if (type == ExpressionType.SQL_LIKE) {
            s = DefaultMessagesImpl.getString("CreatePatternWizard.sqlExpression"); //$NON-NLS-1$
        }

        mPage = new CreatePatternWizardPage1();
        mPage.setTitle(s + DefaultMessagesImpl.getString("CreatePatternWizard.createPage1_2")); //$NON-NLS-1$
        mPage.setDescription(DefaultMessagesImpl.getString("CreatePatternWizard.defProp")); //$NON-NLS-1$
        mPage.setPageComplete(false);
        mPage.setPurpose(purpose);
        if (expression != null && language != null) {
            mPage2 = new CreatePatternWizardPage2(type, expression, language);
        } else {

            mPage2 = new CreatePatternWizardPage2(type);
        }
        mPage2.setTitle(s + DefaultMessagesImpl.getString("CreatePatternWizard.createPage2_2")); //$NON-NLS-1$
        mPage2.setDescription(DefaultMessagesImpl.getString("CreatePatternWizard.defineProp")); //$NON-NLS-1$
        addPage(mPage);
        addPage(mPage2);
    }

    public TypedReturnCode<IFile> createAndSaveCWMFile(ModelElement cwmElement) {
        Pattern pattern = (Pattern) cwmElement;
        IFolder folderResource = parameter.getFolderProvider().getFolderResource();

        return ElementWriterFactory.getInstance().createPatternWriter().create(pattern, folderResource);
    }

    public ModelElement initCWMResourceBuilder() {
        patternBuilder = new PatternBuilder();
        boolean patternInitialized = patternBuilder.initializePattern(parameter.getName());
        if (patternInitialized) {
            Pattern pattern = patternBuilder.getPattern();
            String lang = PatternLanguageType.findLanguageByName(parameter.getLanguage());
            String express = parameter.getExpression();
            RegularExpression regularExpr = BooleanExpressionHelper.createRegularExpression(lang, express);
            regularExpr.setExpressionType(type.getLiteral());
            pattern.getComponents().add(regularExpr);
            return pattern;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.talend.dataprofiler.core.ui.wizard.AbstractWizard#fillMetadataToCWMResource(orgomg.cwm.objectmodel.core.
     * ModelElement)
     */
    @Override
    public void fillMetadataToCWMResource(ModelElement cwmElement) {
        super.fillMetadataToCWMResource(cwmElement);
        TaggedValueHelper.setValidStatus(true, cwmElement);
    }

    /**
     * Getter for location.
     * 
     * @return the location
     */
    public IPath getLocation() {
        return this.location;
    }

    @Override
    protected PatternParameter getParameter() {
        return this.parameter;
    }

    @Override
    protected String getEditorName() {
        return PatternEditor.class.getName();
    }

    public void setPurpose(String purpose) {

        this.purpose = purpose;
    }

    @Override
    protected ResourceFileMap getResourceFileMap() {
        // TODO Auto-generated method stub
        return null;
    }

    public PatternBuilder getPatternBuilder() {
        return patternBuilder;
    }
}
