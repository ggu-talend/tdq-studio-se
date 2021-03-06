// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.wizard.indicator.forms.impl;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataprofiler.core.ui.wizard.indicator.forms.AbstractIndicatorForm;
import org.talend.dataprofiler.core.ui.wizard.indicator.forms.FormEnum;
import org.talend.dataquality.indicators.IndicatorParameters;
import org.talend.dataquality.indicators.IndicatorsFactory;
import org.talend.dataquality.indicators.TextParameters;

/**
 * DOC zqin class global comment. Detailled comment
 */
public class TextLengthForm extends AbstractIndicatorForm {

    protected Button nullBtn, blankBtn;

    public TextLengthForm(Composite parent, int style, IndicatorParameters parameters) {
        super(parent, style, parameters);
        setupForm();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataprofiler.core.ui.utils.AbstractForm#addFields()
     */
    @Override
    protected void addFields() {
        this.setLayout(new GridLayout());

        Group group = new Group(this, SWT.NONE);
        group.setLayout(new GridLayout());
        group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        group.setText(DefaultMessagesImpl.getString("TextLengthForm.options")); //$NON-NLS-1$

        nullBtn = new Button(group, SWT.CHECK);
        nullBtn.setText(DefaultMessagesImpl.getString("TextLengthForm.countNulls")); //$NON-NLS-1$

        blankBtn = new Button(group, SWT.CHECK);
        blankBtn.setText(DefaultMessagesImpl.getString("TextLengthForm.countBlanks")); //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataprofiler.core.ui.utils.AbstractForm#addFieldsListeners()
     */
    @Override
    protected void addFieldsListeners() {
        nullBtn.addSelectionListener(new SelectionAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                updateStatus(IStatus.OK, MSG_OK);
            }

        });

        blankBtn.addSelectionListener(new SelectionAdapter() {

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
             */
            @Override
            public void widgetSelected(SelectionEvent e) {
                updateStatus(IStatus.OK, MSG_OK);
            }

        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataprofiler.core.ui.utils.AbstractForm#initialize()
     */
    @Override
    protected void initialize() {
        TextParameters textParameter = parameters.getTextParameter();
        if (textParameter != null) {
            nullBtn.setSelection(textParameter.isUseNulls());
            blankBtn.setSelection(textParameter.isUseBlank());
        }
    }

    @Override
    public FormEnum getFormEnum() {
        return FormEnum.TextLengthForm;
    }

    @Override
    public boolean performFinish() {
        TextParameters textParameter = parameters.getTextParameter();
        if (textParameter == null) {
            textParameter = IndicatorsFactory.eINSTANCE.createTextParameters();
        }
        textParameter.setUseNulls(nullBtn.getSelection());
        textParameter.setUseBlank(blankBtn.getSelection());
        parameters.setTextParameter(textParameter);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataprofiler.core.ui.utils.AbstractForm#adaptFormToReadOnly()
     */
    @Override
    protected void adaptFormToReadOnly() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataprofiler.core.ui.utils.AbstractForm#addUtilsButtonListeners()
     */
    @Override
    protected void addUtilsButtonListeners() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataprofiler.core.ui.utils.AbstractForm#checkFieldsValue()
     */
    @Override
    protected boolean checkFieldsValue() {
        return false;
    }
}
