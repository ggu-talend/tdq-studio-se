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

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.cwm.helper.SwitchHelpers;
import org.talend.cwm.relational.TdColumn;
import org.talend.dataprofiler.core.CorePlugin;
import org.talend.dataprofiler.core.ImageLib;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataprofiler.core.ui.editor.AbstractMetadataFormPage;
import org.talend.dataprofiler.core.ui.editor.analysis.AnalysisEditor;
import org.talend.dataprofiler.core.ui.editor.analysis.CorrelationAnalysisDetailsPage;
import org.talend.dataprofiler.core.ui.editor.analysis.ColumnAnalysisDetailsPage;
import org.talend.dataprofiler.core.ui.editor.analysis.ColumnSetAnalysisDetailsPage;
import org.talend.dataprofiler.core.ui.editor.analysis.BusinessRuleAnalysisDetailsPage;
import org.talend.dataprofiler.core.ui.utils.CheckValueUtils;
import org.talend.dataprofiler.core.ui.utils.DateTimeDialog;
import org.talend.dataprofiler.core.ui.utils.UIMessages;
import org.talend.dataprofiler.core.ui.wizard.indicator.forms.AbstractIndicatorForm;
import org.talend.dataprofiler.core.ui.wizard.indicator.forms.FormEnum;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.domain.Domain;
import org.talend.dataquality.domain.RangeRestriction;
import org.talend.dataquality.helpers.AnalysisHelper;
import org.talend.dataquality.helpers.IndicatorHelper;
import org.talend.dataquality.indicators.Indicator;
import org.talend.dataquality.indicators.IndicatorParameters;
import org.talend.dq.helper.UDIHelper;
import org.talend.dq.nodes.indicator.type.IndicatorEnum;
import org.talend.utils.format.StringFormatUtil;
import org.talend.utils.sql.Java2SqlType;
import org.talend.utils.sql.TalendTypeConvert;
import org.talend.utils.sugars.ReturnCode;
import orgomg.cwm.objectmodel.core.ModelElement;

/**
 * DOC zqin class global comment. Detailled comment
 */
public class IndicatorThresholdsForm extends AbstractIndicatorForm {

    protected Text pLowerText, pHigherText;

    protected Text lowerText, higherText;

    private Button lowerBTN, higherBTN;

    private Button lowerDelBTN, higherDelBTN;

    private static final double MIN = 0;

    private static final double MAX = 100;

    private static final String VALUE_THRESHOLD = "Value Threshold"; //$NON-NLS-1$

    private static final String PERCENTAGE_THRESHOLD = "Percentage Threshold"; //$NON-NLS-1$

    private boolean isContainRowCount;

    private boolean isRangeForDate;

    private boolean isDatetime;

    private boolean isOptionForRowCount;

    public IndicatorThresholdsForm(Composite parent, int style, IndicatorParameters parameters) {
        super(parent, style, parameters);

        Indicator currentIndicator = (Indicator) parameters.eContainer();
        IndicatorEnum currentIndicatorType = IndicatorEnum.findIndicatorEnum(currentIndicator.eClass());
        ModelElement analyzedElement = currentIndicator.getAnalyzedElement();
        if (null != analyzedElement) {

            if (SwitchHelpers.NAMED_COLUMN_SET_SWITCH.doSwitch(analyzedElement) != null) {
                isRangeForDate = false;
                isDatetime = false;
            } else if (SwitchHelpers.COLUMN_SWITCH.doSwitch(analyzedElement) == null
                    && SwitchHelpers.METADATA_COLUMN_SWITCH.doSwitch(analyzedElement) != null) {
                // MOD qiongli 2010-12-8,feature 16796.
                MetadataColumn mColumn = SwitchHelpers.METADATA_COLUMN_SWITCH.doSwitch(analyzedElement);
                int sqltype = TalendTypeConvert.convertToJDBCType(mColumn.getTalendType());
                isRangeForDate = Java2SqlType.isDateInSQL(sqltype)
                        && currentIndicatorType.isAChildOf(IndicatorEnum.RangeIndicatorEnum);
                if (isRangeForDate) {
                    isDatetime = Java2SqlType.isDateTimeSQL(sqltype);
                }
            } else {
                // int sqltype = ((TdColumn) analyzedElement).getJavaType();
                int sqltype = ((TdColumn) analyzedElement).getSqlDataType().getJavaDataType();
                isRangeForDate = Java2SqlType.isDateInSQL(sqltype)
                        && currentIndicatorType.isAChildOf(IndicatorEnum.RangeIndicatorEnum);

                if (isRangeForDate) {
                    isDatetime = Java2SqlType.isDateTimeSQL(sqltype);
                }
            }
        }

        isOptionForRowCount = (currentIndicatorType == IndicatorEnum.RowCountIndicatorEnum)
                || UDIHelper.isCount(currentIndicator);

        setupForm();
    }

    @Override
    protected void addFields() {
        int colsForLayout = 2;

        if (isRangeForDate) {
            colsForLayout = 4;
        }

        Group group = new Group(this, SWT.NONE);
        group.setLayout(new GridLayout(colsForLayout, false));
        group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        group.setText(DefaultMessagesImpl.getString("IndicatorThresholdsForm.setThresholds")); //$NON-NLS-1$

        GridData gdText = new GridData(GridData.FILL_HORIZONTAL);

        Label lowerLabel = new Label(group, SWT.NONE);
        lowerLabel.setText(DefaultMessagesImpl.getString("DataThresholdsForm.lowerThreshold")); //$NON-NLS-1$
        lowerText = new Text(group, SWT.BORDER);
        lowerText.setLayoutData(gdText);
        if (isRangeForDate) {
            lowerBTN = new Button(group, SWT.PUSH);
            lowerBTN.setText("..."); //$NON-NLS-1$
            lowerBTN.setToolTipText(DefaultMessagesImpl.getString("IndicatorThresholdsForm.SelectLowerThreshold")); //$NON-NLS-1$
            lowerDelBTN = new Button(group, SWT.PUSH);
            lowerDelBTN.setImage(ImageLib.getImage(ImageLib.DELETE_ACTION));
            lowerDelBTN.setToolTipText(DefaultMessagesImpl.getString("IndicatorThresholdsForm.RemoveLowerThreshold")); //$NON-NLS-1$

            lowerText.setEditable(false);
            lowerText.setEnabled(false);
        }

        Label higherLabel = new Label(group, SWT.NONE);
        higherLabel.setText(DefaultMessagesImpl.getString("DataThresholdsForm.higherThreshold")); //$NON-NLS-1$
        higherText = new Text(group, SWT.BORDER);
        higherText.setLayoutData(gdText);
        if (isRangeForDate) {
            higherBTN = new Button(group, SWT.PUSH);
            higherBTN.setText("..."); //$NON-NLS-1$
            higherBTN.setToolTipText(DefaultMessagesImpl.getString("IndicatorThresholdsForm.SelectHigherThreshold")); //$NON-NLS-1$
            higherDelBTN = new Button(group, SWT.PUSH);
            higherDelBTN.setImage(ImageLib.getImage(ImageLib.DELETE_ACTION));
            higherDelBTN.setToolTipText(DefaultMessagesImpl.getString("IndicatorThresholdsForm.RemoveHigherThreshold")); //$NON-NLS-1$

            higherText.setEditable(false);
            higherText.setEnabled(false);
        }

        if (!isOptionForRowCount && !isRangeForDate) {
            Group pGroup = new Group(this, SWT.NONE);
            pGroup.setLayout(new GridLayout(2, false));
            pGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            pGroup.setText(DefaultMessagesImpl.getString("IndicatorThresholdsForm.setPersentThresholds")); //$NON-NLS-1$

            Label pLower = new Label(pGroup, SWT.NONE);
            pLower.setText(DefaultMessagesImpl.getString("IndicatorThresholdsForm.lowerThreshold")); //$NON-NLS-1$
            pLowerText = new Text(pGroup, SWT.BORDER);
            pLowerText.setLayoutData(gdText);

            Label pHigher = new Label(pGroup, SWT.NONE);
            pHigher.setText(DefaultMessagesImpl.getString("IndicatorThresholdsForm.higherThreshold")); //$NON-NLS-1$
            pHigherText = new Text(pGroup, SWT.BORDER);
            pHigherText.setLayoutData(gdText);

            setPercentUIEnable();
        }
    }

    private void setPercentUIEnable() {
        IEditorPart editor = CorePlugin.getDefault().getCurrentActiveEditor();
        AbstractMetadataFormPage masterPage = null;
        boolean tableMasterPage = false;
        boolean columnSetMasterPage = false;
        boolean columnCorrelationMasterPage = false;
        AnalysisEditor anaEditor = null;
        if (editor != null) {
            anaEditor = (AnalysisEditor) editor;
            Object temp = anaEditor.getMasterPage();
            if (temp != null) {
                tableMasterPage = temp instanceof BusinessRuleAnalysisDetailsPage;
                columnSetMasterPage = temp instanceof ColumnSetAnalysisDetailsPage;
                columnCorrelationMasterPage = temp instanceof CorrelationAnalysisDetailsPage;
                if (tableMasterPage) {
                    masterPage = (BusinessRuleAnalysisDetailsPage) temp;
                } else if (columnSetMasterPage) {
                    masterPage = (ColumnSetAnalysisDetailsPage) temp;
                } else if (columnCorrelationMasterPage) {
                    masterPage = (CorrelationAnalysisDetailsPage) temp;
                } else {
                    masterPage = (ColumnAnalysisDetailsPage) temp;
                }
            }
        }

        if (masterPage != null) {
            Analysis ana;
            if (tableMasterPage) {
                ana = ((BusinessRuleAnalysisDetailsPage) masterPage).getAnalysisHandler().getAnalysis();
            } else if (columnSetMasterPage) {
                ana = ((ColumnSetAnalysisDetailsPage) masterPage).getColumnSetAnalysisHandler().getAnalysis();
            } else if (columnCorrelationMasterPage) {
                ana = ((CorrelationAnalysisDetailsPage) masterPage).getColumnCorrelationAnalysisHandler()
                        .getAnalysis();
            } else {
                ana = ((ColumnAnalysisDetailsPage) masterPage).getAnalysisHandler().getAnalysis();
            }
            isContainRowCount = AnalysisHelper.containsRowCount(ana);
        }

        pLowerText.setEnabled(isContainRowCount);
        pHigherText.setEnabled(isContainRowCount);
    }

    @Override
    public FormEnum getFormEnum() {
        return FormEnum.IndicatorThresholdsForm;
    }

    @Override
    public boolean performFinish() {
        boolean isMinEmpty = CheckValueUtils.isEmpty(lowerText.getText());
        boolean isMaxEmpty = CheckValueUtils.isEmpty(higherText.getText());
        if (isContainRowCount) {
            String plower = pLowerText.getText();
            String phigher = pHigherText.getText();
            boolean isPerMinEmpty = CheckValueUtils.isEmpty(plower);
            boolean isPerMaxEmpty = CheckValueUtils.isEmpty(phigher);

            if (isMinEmpty && isMaxEmpty && isPerMinEmpty && isPerMaxEmpty) {
                parameters.setIndicatorValidDomain(null);
            } else {
                if (isMinEmpty && isMaxEmpty) {
                    removeRange(VALUE_THRESHOLD);
                } else {
                    IndicatorHelper.setIndicatorThreshold(parameters, lowerText.getText(), higherText.getText());
                }

                if (isPerMinEmpty && isPerMaxEmpty) {
                    removeRange(PERCENTAGE_THRESHOLD);
                } else {
                    String lower = "", higher = ""; //$NON-NLS-1$ //$NON-NLS-2$
                    if (StringUtils.isNotEmpty(plower)) {
                        lower = String.valueOf(Double.valueOf(plower) / 100);
                    }

                    if (StringUtils.isNotEmpty(phigher)) {
                        higher = String.valueOf(Double.valueOf(phigher) / 100);
                    }

                    IndicatorHelper.setIndicatorThresholdInPercent(parameters, lower, higher);
                }
            }

        } else {
            if (isMinEmpty && isMaxEmpty) {
                parameters.setIndicatorValidDomain(null);
            } else {
                IndicatorHelper.setIndicatorThreshold(parameters, lowerText.getText(), higherText.getText());
            }
        }

        return true;
    }

    private void removeRange(String rangeName) {
        Domain validDomain = parameters.getIndicatorValidDomain();
        if (validDomain != null) {
            Iterator<RangeRestriction> it = validDomain.getRanges().iterator();
            while (it.hasNext()) {
                if (rangeName.equals(it.next().getName())) {
                    it.remove();
                }
            }
        }
    }

    @Override
    protected void adaptFormToReadOnly() {
    }

    /**
     * yyi 2009-10-29 validate lowerText higherText pLowerText pHigherText. feature:9340: Set indicator thresholds.
     */
    protected boolean checkFields() {
        ReturnCode rc0 = checkIndicatorFields();
        ReturnCode rc1 = checkIndicatorInPrecentFields();

        if (rc0.isOk() && rc1.isOk()) {
            updateStatus(IStatus.OK, MSG_OK);
            return true;
        } else {
            updateStatus(IStatus.ERROR, rc0.getMessage() + rc1.getMessage());
            return false;
        }
    }

    /**
     * DOC yyi Comment method "checkIndicatorFields".
     * 
     * @return
     */
    protected ReturnCode checkIndicatorFields() {
        String min = null != lowerText ? lowerText.getText().trim() : ""; //$NON-NLS-1$
        String max = null != higherText ? higherText.getText().trim() : ""; //$NON-NLS-1$

        ReturnCode rc = new ReturnCode(true);
        String statusLabelText = ""; //$NON-NLS-1$

        if (isRangeForDate) {
            if ((!CheckValueUtils.isDateValue(min) && !CheckValueUtils.isEmpty(min))
                    || (!CheckValueUtils.isDateValue(max) && !CheckValueUtils.isEmpty(max))) {

                rc.setOk(false);
                statusLabelText += MSG_ONLY_DATE + System.getProperty("line.separator"); //$NON-NLS-1$
            }
        } else {
            // bug 10550 by zshen,Cannot set a negative threshold on individual summary statistics indicators
            if ((!CheckValueUtils.isNumberWithNegativeValue(min) && !CheckValueUtils.isEmpty(min))
                    || (!CheckValueUtils.isNumberWithNegativeValue(max) && !CheckValueUtils.isEmpty(max))) {

                rc.setOk(false);
                statusLabelText += MSG_ONLY_NUMBER + System.getProperty("line.separator"); //$NON-NLS-1$
            } else {
                // MOD yyi 2010-04-15 bug 12483 : check the value is out of range
                try {
                    if (!CheckValueUtils.isEmpty(max)) {
                        Long.valueOf(max);
                    }
                    if (!CheckValueUtils.isEmpty(min)) {
                        Long.valueOf(min);
                    }
                } catch (NumberFormatException e) {
                    rc.setOk(false);
                    statusLabelText += UIMessages.MSG_INDICATOR_VALUE_OUT_OF_RANGE_LONG + System.getProperty("line.separator"); //$NON-NLS-1$
                }

            }
        }

        if (CheckValueUtils.isAoverB(min, max)) {
            rc.setOk(false);
            statusLabelText += UIMessages.MSG_LOWER_LESS_HIGHER + System.getProperty("line.separator"); //$NON-NLS-1$
        }

        rc.setMessage(statusLabelText);
        return rc;
    }

    /**
     * DOC yyi Comment method "checkIndicatorInPrecentFields".
     * 
     * @return
     */
    protected ReturnCode checkIndicatorInPrecentFields() {
        String pmin = null != pLowerText ? pLowerText.getText().trim() : ""; //$NON-NLS-1$
        String pmax = null != pHigherText ? pHigherText.getText().trim() : ""; //$NON-NLS-1$

        ReturnCode rc = new ReturnCode(true);
        String statusLabelText = ""; //$NON-NLS-1$

        if ((!CheckValueUtils.isEmpty(pmin) && !CheckValueUtils.isRealNumberValue(pmin))
                || (!CheckValueUtils.isEmpty(pmax) && !CheckValueUtils.isRealNumberValue(pmax))) {

            rc.setOk(false);
            statusLabelText += MSG_ONLY_REAL_NUMBER + System.getProperty("line.separator"); //$NON-NLS-1$
        }
        if (CheckValueUtils.isOutRange(MIN, MAX, pmin) || CheckValueUtils.isOutRange(MIN, MAX, pmax)) {
            rc.setOk(false);
            statusLabelText += UIMessages.MSG_INDICATOR_VALUE_OUT_OF_RANGE + System.getProperty("line.separator"); //$NON-NLS-1$
        }
        if (CheckValueUtils.isAoverB(pmin, pmax)) {
            rc.setOk(false);
            statusLabelText += UIMessages.MSG_LOWER_LESS_HIGHER + System.getProperty("line.separator"); //$NON-NLS-1$
        }

        rc.setMessage(statusLabelText);
        return rc;
    }

    @Override
    protected void addFieldsListeners() {
        lowerText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                checkFields();
            }
        });

        higherText.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                checkFields();
            }

        });

        if (isContainRowCount) {
            pLowerText.addModifyListener(new ModifyListener() {

                public void modifyText(ModifyEvent e) {
                    checkFields();
                }
            });

            pHigherText.addModifyListener(new ModifyListener() {

                public void modifyText(ModifyEvent e) {
                    checkFields();
                }
            });
        }
    }

    @Override
    protected void addUtilsButtonListeners() {
        if (isRangeForDate) {
            lowerBTN.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    DateTimeDialog dialog = new DateTimeDialog(null, isDatetime);
                    if (Window.OK == dialog.open()) {
                        lowerText.setText(dialog.getSelectDate());
                    }
                }
            });

            higherBTN.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    DateTimeDialog dialog = new DateTimeDialog(null, isDatetime);
                    if (Window.OK == dialog.open()) {
                        higherText.setText(dialog.getSelectDate());
                    }
                }
            });

            lowerDelBTN.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    lowerText.setText(""); //$NON-NLS-1$
                }
            });

            higherDelBTN.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    higherText.setText(""); //$NON-NLS-1$
                }
            });
        }
    }

    @Override
    protected boolean checkFieldsValue() {
        return false;
    }

    @Override
    protected void initialize() {
        String[] indicatorThreshold = IndicatorHelper.getIndicatorThreshold(parameters);
        if (indicatorThreshold != null) {
            lowerText.setText(indicatorThreshold[0] == null ? "" : indicatorThreshold[0]); //$NON-NLS-1$
            higherText.setText(indicatorThreshold[1] == null ? "" : indicatorThreshold[1]); //$NON-NLS-1$
        }
        String[] indicatorPersentThreshold = IndicatorHelper.getIndicatorThresholdInPercent(parameters);
        if (indicatorPersentThreshold != null && isContainRowCount) {
            if (StringUtils.isNotEmpty(indicatorPersentThreshold[0])) {
                Double min = StringFormatUtil.parseDouble(indicatorPersentThreshold[0]);
                min = min > 1 ? min : StringFormatUtil.formatPercentDecimalDouble(min);
                pLowerText.setText(String.valueOf(min));
            }

            if (StringUtils.isNotEmpty(indicatorPersentThreshold[1])) {
                Double max = StringFormatUtil.parseDouble(indicatorPersentThreshold[1]);
                max = max > 1 ? max : StringFormatUtil.formatPercentDecimalDouble(max);
                pHigherText.setText(String.valueOf(max));
            }
        }
    }
}
