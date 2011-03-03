/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package org.talend.dataquality.indicators.columnset.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.talend.algorithms.AlgoUtils;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.cwm.helper.SwitchHelpers;
import org.talend.cwm.relational.TdColumn;
import org.talend.dataquality.helpers.MetadataHelper;
import org.talend.dataquality.indicators.DataminingType;
import org.talend.dataquality.indicators.DistinctCountIndicator;
import org.talend.dataquality.indicators.DuplicateCountIndicator;
import org.talend.dataquality.indicators.Indicator;
import org.talend.dataquality.indicators.RowCountIndicator;
import org.talend.dataquality.indicators.UniqueCountIndicator;
import org.talend.dataquality.indicators.columnset.ColumnSetMultiValueIndicator;
import org.talend.dataquality.indicators.columnset.ColumnsetPackage;
import org.talend.dataquality.indicators.impl.CompositeIndicatorImpl;
import org.talend.utils.collections.Tuple;
import org.talend.utils.sql.Java2SqlType;
import orgomg.cwm.objectmodel.core.ModelElement;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Column Set Multi Value Indicator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getAnalyzedColumns <em>Analyzed Columns</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getListRows <em>List Rows</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getNumericFunctions <em>Numeric Functions</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getNominalColumns <em>Nominal Columns</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getNumericColumns <em>Numeric Columns</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getColumnHeaders <em>Column Headers</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getDateFunctions <em>Date Functions</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getDateColumns <em>Date Columns</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getUniqueCount <em>Unique Count</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getDistinctCount <em>Distinct Count</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getDuplicateCount <em>Duplicate Count</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getRowCountIndicator <em>Row Count Indicator</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getUniqueCountIndicator <em>Unique Count Indicator</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getDistinctCountIndicator <em>Distinct Count Indicator</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnSetMultiValueIndicatorImpl#getDuplicateCountIndicator <em>Duplicate Count Indicator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ColumnSetMultiValueIndicatorImpl extends CompositeIndicatorImpl implements ColumnSetMultiValueIndicator {

    private static Logger log = Logger.getLogger(ColumnSetMultiValueIndicatorImpl.class);

    /**
     * The cached value of the '{@link #getAnalyzedColumns() <em>Analyzed Columns</em>}' reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getAnalyzedColumns()
     * @generated
     * @ordered
     */
    protected EList<MetadataColumn> analyzedColumns;

    /**
     * The default value of the '{@link #getListRows() <em>List Rows</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getListRows()
     * @generated
     * @ordered
     */
    protected static final List<Object[]> LIST_ROWS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getListRows() <em>List Rows</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getListRows()
     * @generated
     * @ordered
     */
    protected List<Object[]> listRows = LIST_ROWS_EDEFAULT;

    /**
     * The cached value of the '{@link #getNumericFunctions() <em>Numeric Functions</em>}' attribute list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getNumericFunctions()
     * @generated
     * @ordered
     */
    protected EList<String> numericFunctions;

    /**
     * The cached value of the '{@link #getDateFunctions() <em>Date Functions</em>}' attribute list.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getDateFunctions()
     * @generated
     * @ordered
     */
    protected EList<String> dateFunctions;

    /**
     * The cached value of the '{@link #getDateColumns() <em>Date Columns</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDateColumns()
     * @generated
     * @ordered
     */
    protected EList<MetadataColumn> dateColumns;

    /**
     * The default value of the '{@link #getUniqueCount() <em>Unique Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUniqueCount()
     * @generated
     * @ordered
     */
    protected static final Long UNIQUE_COUNT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getUniqueCount() <em>Unique Count</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getUniqueCount()
     * @generated
     * @ordered
     */
    protected Long uniqueCount = UNIQUE_COUNT_EDEFAULT;

    /**
     * The default value of the '{@link #getDistinctCount() <em>Distinct Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistinctCount()
     * @generated
     * @ordered
     */
    protected static final Long DISTINCT_COUNT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDistinctCount() <em>Distinct Count</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistinctCount()
     * @generated
     * @ordered
     */
    protected Long distinctCount = DISTINCT_COUNT_EDEFAULT;

    /**
     * The default value of the '{@link #getDuplicateCount() <em>Duplicate Count</em>}' attribute.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getDuplicateCount()
     * @generated
     * @ordered
     */
    protected static final Long DUPLICATE_COUNT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getRowCountIndicator() <em>Row Count Indicator</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getRowCountIndicator()
     * @generated
     * @ordered
     */
    protected RowCountIndicator rowCountIndicator;

    /**
     * The cached value of the '{@link #getUniqueCountIndicator() <em>Unique Count Indicator</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getUniqueCountIndicator()
     * @generated
     * @ordered
     */
    protected UniqueCountIndicator uniqueCountIndicator;

    /**
     * The cached value of the '{@link #getDistinctCountIndicator() <em>Distinct Count Indicator</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDistinctCountIndicator()
     * @generated
     * @ordered
     */
    protected DistinctCountIndicator distinctCountIndicator;

    /**
     * The cached value of the '{@link #getDuplicateCountIndicator() <em>Duplicate Count Indicator</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDuplicateCountIndicator()
     * @generated
     * @ordered
     */
    protected DuplicateCountIndicator duplicateCountIndicator;

    /**
     * store the value of group like use sql 'select a ,b from table group by a,b'.
     */
    protected HashMap<Object, Long> valueByGroupMap = new HashMap<Object, Long>();

    /**
     * make 'valueByGroupMap' convert to a List<Object[]>
     */
    protected List<Object[]> valueByGroupList = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ColumnSetMultiValueIndicatorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ColumnsetPackage.Literals.COLUMN_SET_MULTI_VALUE_INDICATOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<MetadataColumn> getAnalyzedColumns() {
        if (analyzedColumns == null) {
            analyzedColumns = new EObjectResolvingEList<MetadataColumn>(MetadataColumn.class, this, ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__ANALYZED_COLUMNS);
        }
        return analyzedColumns;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public List<Object[]> getListRows() {
        return listRows;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setListRows(List<Object[]> newListRows) {
        List<Object[]> oldListRows = listRows;
        listRows = newListRows;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__LIST_ROWS, oldListRows, listRows));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getNumericFunctions() {
        if (numericFunctions == null) {
            numericFunctions = new EDataTypeUniqueEList<String>(String.class, this, ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__NUMERIC_FUNCTIONS);
        }
        return numericFunctions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EList<MetadataColumn> getNominalColumns() {
        EList<MetadataColumn> nominalColumns = new BasicElistExtend<MetadataColumn>();// bug 10578 by zshen,fix the
                                                                                  // exception when
        // correlation analysis to be move
        if (analyzedColumns != null) {
            for (MetadataColumn column : analyzedColumns) {
                // MOD yyi 2011-02-25 16660: edit connection, save it will get error
                final MetadataColumn mdColumn = SwitchHelpers.METADATA_COLUMN_SWITCH.doSwitch(column);
                final TdColumn tdColumn = SwitchHelpers.COLUMN_SWITCH.doSwitch(column);

                if (tdColumn == null && mdColumn == null) {
                    if (column == null) {
                        log.error("The list of analyzed column contains a null column");
                    } else {
                        log.error("Analyzed element should be a TdColumn instead of a Column. Analyzed element is "
                                + column.getName());
                    }
                    continue;
                }
                DataminingType dataminingType = MetadataHelper.getDataminingType(column);
                if (DataminingType.NOMINAL.equals(dataminingType)) {
                    nominalColumns.add(column);
                } else if (null != mdColumn) {
                    nominalColumns.add(column);
                }
            }
        }
        return nominalColumns;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EList<MetadataColumn> getNumericColumns() {
        EList<MetadataColumn> computedColumns = new BasicElistExtend<MetadataColumn>();// bug 10578 by zshen,fix the
                                                                                   // exception when
        // correlation analysis to be move
        if (analyzedColumns != null) {
            for (MetadataColumn column : analyzedColumns) {
                // MOD yyi 2011-02-25 16660: edit connection, save it will get error
                final MetadataColumn mdColumn = SwitchHelpers.METADATA_COLUMN_SWITCH.doSwitch(column);
                final TdColumn tdColumn = SwitchHelpers.COLUMN_SWITCH.doSwitch(column);

                if (tdColumn == null && mdColumn == null) {
                    if (column == null) {
                        log.error("The list of analyzed column contains a null column");
                    } else {
                        log.error("Analyzed element should be a TdColumn instead of a Column. Analyzed element is "
                                + column.getName());
                    }
                    continue;
                }
                DataminingType dataminingType = MetadataHelper.getDataminingType(column);
                if (DataminingType.INTERVAL.equals(dataminingType) && tdColumn != null
                        && Java2SqlType.isNumbericInSQL(tdColumn.getSqlDataType().getJavaDataType())) {
                    computedColumns.add(column);
                }
            }
        }
        return computedColumns;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EList<String> getColumnHeaders() {
        EList<String> headers = new BasicEList<String>();
        for (ModelElement column : this.getNominalColumns()) {
            headers.add(getColumnName(column));
        }
        for (ModelElement column : this.getNumericColumns()) {
            // call functions for each column
            for (String f : this.getNumericFunctions()) {
                headers.add(MessageFormat.format(f, getColumnName(column)));
            }
        }
        for (ModelElement column : this.getDateColumns()) {
            // call functions for each column
            for (String f : this.getDateFunctions()) {
                headers.add(MessageFormat.format(f, getColumnName(column)));
            }
        }
        headers.add(this.getCountAll());

        return headers;
    }

    /**
     * ADD yyi 2011-03-03 17871:filter data
     * 
     * @param column
     * @return
     */
    private String getColumnName(ModelElement column) {

        MetadataColumn mdColumn = SwitchHelpers.METADATA_COLUMN_SWITCH.doSwitch(column);
        TdColumn tdColumn = SwitchHelpers.COLUMN_SWITCH.doSwitch(column);

        if (tdColumn != null) {
            return tdColumn.getName();
        } else if (mdColumn != null) {
            return mdColumn.getId();
        }
        return "";
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EList<String> getDateFunctions() {
        if (dateFunctions == null) {
            dateFunctions = new EDataTypeUniqueEList<String>(String.class, this, ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DATE_FUNCTIONS);
        }
        return dateFunctions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EList<MetadataColumn> getDateColumns() {
        EList<MetadataColumn> dateColumns = new BasicEList<MetadataColumn>();
        if (analyzedColumns != null) {
            for (MetadataColumn column : analyzedColumns) {
                final TdColumn tdColumn = SwitchHelpers.COLUMN_SWITCH.doSwitch(column);
                final MetadataColumn mdColumn = SwitchHelpers.METADATA_COLUMN_SWITCH.doSwitch(column);
                if (tdColumn != null) {
                    final DataminingType dmType = MetadataHelper.getDataminingType(tdColumn);
                    if (DataminingType.INTERVAL.equals(dmType)
                            && Java2SqlType.isDateInSQL(tdColumn.getSqlDataType().getJavaDataType())) {
                        dateColumns.add(tdColumn);
                    }
                } else if (mdColumn != null) {
                    // TODO add flat file date type
                }
            }
        }
        return dateColumns;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Long getUniqueCount() {
        return uniqueCount;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setUniqueCount(Long newUniqueCount) {
        Long oldUniqueCount = uniqueCount;
        uniqueCount = newUniqueCount;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__UNIQUE_COUNT, oldUniqueCount, uniqueCount));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Long getDistinctCount() {
        return distinctCount;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setDistinctCount(Long newDistinctCount) {
        Long oldDistinctCount = distinctCount;
        distinctCount = newDistinctCount;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DISTINCT_COUNT, oldDistinctCount, distinctCount));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Long getDuplicateCount() {
        return (uniqueCount != null && distinctCount != null) ? distinctCount - uniqueCount : null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public RowCountIndicator getRowCountIndicator() {
        return rowCountIndicator;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRowCountIndicator(RowCountIndicator newRowCountIndicator, NotificationChain msgs) {
        RowCountIndicator oldRowCountIndicator = rowCountIndicator;
        rowCountIndicator = newRowCountIndicator;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__ROW_COUNT_INDICATOR, oldRowCountIndicator, newRowCountIndicator);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setRowCountIndicator(RowCountIndicator newRowCountIndicator) {
        if (newRowCountIndicator != rowCountIndicator) {
            NotificationChain msgs = null;
            if (rowCountIndicator != null)
                msgs = ((InternalEObject)rowCountIndicator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__ROW_COUNT_INDICATOR, null, msgs);
            if (newRowCountIndicator != null)
                msgs = ((InternalEObject)newRowCountIndicator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__ROW_COUNT_INDICATOR, null, msgs);
            msgs = basicSetRowCountIndicator(newRowCountIndicator, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__ROW_COUNT_INDICATOR, newRowCountIndicator, newRowCountIndicator));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public UniqueCountIndicator getUniqueCountIndicator() {
        return uniqueCountIndicator;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetUniqueCountIndicator(UniqueCountIndicator newUniqueCountIndicator, NotificationChain msgs) {
        UniqueCountIndicator oldUniqueCountIndicator = uniqueCountIndicator;
        uniqueCountIndicator = newUniqueCountIndicator;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__UNIQUE_COUNT_INDICATOR, oldUniqueCountIndicator, newUniqueCountIndicator);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setUniqueCountIndicator(UniqueCountIndicator newUniqueCountIndicator) {
        if (newUniqueCountIndicator != uniqueCountIndicator) {
            NotificationChain msgs = null;
            if (uniqueCountIndicator != null)
                msgs = ((InternalEObject)uniqueCountIndicator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__UNIQUE_COUNT_INDICATOR, null, msgs);
            if (newUniqueCountIndicator != null)
                msgs = ((InternalEObject)newUniqueCountIndicator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__UNIQUE_COUNT_INDICATOR, null, msgs);
            msgs = basicSetUniqueCountIndicator(newUniqueCountIndicator, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__UNIQUE_COUNT_INDICATOR, newUniqueCountIndicator, newUniqueCountIndicator));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DistinctCountIndicator getDistinctCountIndicator() {
        return distinctCountIndicator;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDistinctCountIndicator(DistinctCountIndicator newDistinctCountIndicator,
            NotificationChain msgs) {
        DistinctCountIndicator oldDistinctCountIndicator = distinctCountIndicator;
        distinctCountIndicator = newDistinctCountIndicator;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DISTINCT_COUNT_INDICATOR, oldDistinctCountIndicator, newDistinctCountIndicator);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setDistinctCountIndicator(DistinctCountIndicator newDistinctCountIndicator) {
        if (newDistinctCountIndicator != distinctCountIndicator) {
            NotificationChain msgs = null;
            if (distinctCountIndicator != null)
                msgs = ((InternalEObject)distinctCountIndicator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DISTINCT_COUNT_INDICATOR, null, msgs);
            if (newDistinctCountIndicator != null)
                msgs = ((InternalEObject)newDistinctCountIndicator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DISTINCT_COUNT_INDICATOR, null, msgs);
            msgs = basicSetDistinctCountIndicator(newDistinctCountIndicator, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DISTINCT_COUNT_INDICATOR, newDistinctCountIndicator, newDistinctCountIndicator));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DuplicateCountIndicator getDuplicateCountIndicator() {
        return duplicateCountIndicator;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDuplicateCountIndicator(DuplicateCountIndicator newDuplicateCountIndicator,
            NotificationChain msgs) {
        DuplicateCountIndicator oldDuplicateCountIndicator = duplicateCountIndicator;
        duplicateCountIndicator = newDuplicateCountIndicator;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DUPLICATE_COUNT_INDICATOR, oldDuplicateCountIndicator, newDuplicateCountIndicator);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setDuplicateCountIndicator(DuplicateCountIndicator newDuplicateCountIndicator) {
        if (newDuplicateCountIndicator != duplicateCountIndicator) {
            NotificationChain msgs = null;
            if (duplicateCountIndicator != null)
                msgs = ((InternalEObject)duplicateCountIndicator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DUPLICATE_COUNT_INDICATOR, null, msgs);
            if (newDuplicateCountIndicator != null)
                msgs = ((InternalEObject)newDuplicateCountIndicator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DUPLICATE_COUNT_INDICATOR, null, msgs);
            msgs = basicSetDuplicateCountIndicator(newDuplicateCountIndicator, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DUPLICATE_COUNT_INDICATOR, newDuplicateCountIndicator, newDuplicateCountIndicator));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__ROW_COUNT_INDICATOR:
                return basicSetRowCountIndicator(null, msgs);
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__UNIQUE_COUNT_INDICATOR:
                return basicSetUniqueCountIndicator(null, msgs);
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DISTINCT_COUNT_INDICATOR:
                return basicSetDistinctCountIndicator(null, msgs);
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DUPLICATE_COUNT_INDICATOR:
                return basicSetDuplicateCountIndicator(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.indicators.impl.IndicatorImpl#storeSqlResults(java.util.List)
     * 
     * @generated NOT
     */
    @Override
    public boolean storeSqlResults(List<Object[]> objects) {
        if (this.isStoreData()) {
            this.setListRows(objects);
        } else {
            this.setListRows(new ArrayList<Object[]>());
        }
        if (log.isDebugEnabled()) {
            StringBuilder builder = new StringBuilder();
            builder.append("Column set multivalue indicator\n");
            for (Object[] obj : objects) {
                for (int i = 0; i < obj.length; i++) {
                    builder.append(obj[i]).append("\t");
                }
                builder.append('\n');
            }
            log.debug(builder);
        }

        computeCounts(objects);
        // MOD xqliu 2010-03-30 bug 12161
        if (getRowCountIndicator() != null) {
            getRowCountIndicator().setCount(getCount());
        }
        if (getUniqueCountIndicator() != null) {
            getUniqueCountIndicator().setUniqueValueCount(getUniqueCount());
        }
        if (getDistinctCountIndicator() != null) {
            getDistinctCountIndicator().setDistinctValueCount(getDistinctCount());
        }
        if (getDuplicateCountIndicator() != null) {
            getDuplicateCountIndicator().setDuplicateValueCount(getDuplicateCount());
        }
        // ~12161
        return true;
    }

    /**
     * DOC scorreia Comment method "computeUniqueCount".
     * 
     * @param objects
     * @return
     */
    private boolean computeCounts(List<Object[]> objects) {
        boolean ok = true;
        this.setDistinctCount(Long.valueOf(objects.size()));
        Long uniq = 0L;
        Long rowcount = 0L;
        for (Object[] row : objects) {
            Object c = row[row.length - 1];
            if (c != null) {
                Long val = Long.valueOf(String.valueOf(c));
                rowcount += val;
                if (val == 1) {
                    uniq++;
                }
            } else {
                ok = false;
            }
        }
        this.setCount(rowcount);
        this.setUniqueCount(uniq);
        return ok;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__ANALYZED_COLUMNS:
                return getAnalyzedColumns();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__LIST_ROWS:
                return getListRows();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__NUMERIC_FUNCTIONS:
                return getNumericFunctions();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__NOMINAL_COLUMNS:
                return getNominalColumns();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__NUMERIC_COLUMNS:
                return getNumericColumns();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__COLUMN_HEADERS:
                return getColumnHeaders();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DATE_FUNCTIONS:
                return getDateFunctions();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DATE_COLUMNS:
                return getDateColumns();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__UNIQUE_COUNT:
                return getUniqueCount();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DISTINCT_COUNT:
                return getDistinctCount();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DUPLICATE_COUNT:
                return getDuplicateCount();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__ROW_COUNT_INDICATOR:
                return getRowCountIndicator();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__UNIQUE_COUNT_INDICATOR:
                return getUniqueCountIndicator();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DISTINCT_COUNT_INDICATOR:
                return getDistinctCountIndicator();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DUPLICATE_COUNT_INDICATOR:
                return getDuplicateCountIndicator();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__ANALYZED_COLUMNS:
                getAnalyzedColumns().clear();
                getAnalyzedColumns().addAll((Collection<? extends MetadataColumn>)newValue);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__LIST_ROWS:
                setListRows((List<Object[]>)newValue);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__NUMERIC_FUNCTIONS:
                getNumericFunctions().clear();
                getNumericFunctions().addAll((Collection<? extends String>)newValue);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DATE_FUNCTIONS:
                getDateFunctions().clear();
                getDateFunctions().addAll((Collection<? extends String>)newValue);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DATE_COLUMNS:
                getDateColumns().clear();
                getDateColumns().addAll((Collection<? extends MetadataColumn>)newValue);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__UNIQUE_COUNT:
                setUniqueCount((Long)newValue);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DISTINCT_COUNT:
                setDistinctCount((Long)newValue);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__ROW_COUNT_INDICATOR:
                setRowCountIndicator((RowCountIndicator)newValue);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__UNIQUE_COUNT_INDICATOR:
                setUniqueCountIndicator((UniqueCountIndicator)newValue);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DISTINCT_COUNT_INDICATOR:
                setDistinctCountIndicator((DistinctCountIndicator)newValue);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DUPLICATE_COUNT_INDICATOR:
                setDuplicateCountIndicator((DuplicateCountIndicator)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__ANALYZED_COLUMNS:
                getAnalyzedColumns().clear();
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__LIST_ROWS:
                setListRows(LIST_ROWS_EDEFAULT);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__NUMERIC_FUNCTIONS:
                getNumericFunctions().clear();
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DATE_FUNCTIONS:
                getDateFunctions().clear();
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DATE_COLUMNS:
                getDateColumns().clear();
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__UNIQUE_COUNT:
                setUniqueCount(UNIQUE_COUNT_EDEFAULT);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DISTINCT_COUNT:
                setDistinctCount(DISTINCT_COUNT_EDEFAULT);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__ROW_COUNT_INDICATOR:
                setRowCountIndicator((RowCountIndicator)null);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__UNIQUE_COUNT_INDICATOR:
                setUniqueCountIndicator((UniqueCountIndicator)null);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DISTINCT_COUNT_INDICATOR:
                setDistinctCountIndicator((DistinctCountIndicator)null);
                return;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DUPLICATE_COUNT_INDICATOR:
                setDuplicateCountIndicator((DuplicateCountIndicator)null);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__ANALYZED_COLUMNS:
                return analyzedColumns != null && !analyzedColumns.isEmpty();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__LIST_ROWS:
                return LIST_ROWS_EDEFAULT == null ? listRows != null : !LIST_ROWS_EDEFAULT.equals(listRows);
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__NUMERIC_FUNCTIONS:
                return numericFunctions != null && !numericFunctions.isEmpty();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__NOMINAL_COLUMNS:
                return !getNominalColumns().isEmpty();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__NUMERIC_COLUMNS:
                return !getNumericColumns().isEmpty();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__COLUMN_HEADERS:
                return !getColumnHeaders().isEmpty();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DATE_FUNCTIONS:
                return dateFunctions != null && !dateFunctions.isEmpty();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DATE_COLUMNS:
                return dateColumns != null && !dateColumns.isEmpty();
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__UNIQUE_COUNT:
                return UNIQUE_COUNT_EDEFAULT == null ? uniqueCount != null : !UNIQUE_COUNT_EDEFAULT.equals(uniqueCount);
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DISTINCT_COUNT:
                return DISTINCT_COUNT_EDEFAULT == null ? distinctCount != null : !DISTINCT_COUNT_EDEFAULT.equals(distinctCount);
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DUPLICATE_COUNT:
                return DUPLICATE_COUNT_EDEFAULT == null ? getDuplicateCount() != null : !DUPLICATE_COUNT_EDEFAULT.equals(getDuplicateCount());
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__ROW_COUNT_INDICATOR:
                return rowCountIndicator != null;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__UNIQUE_COUNT_INDICATOR:
                return uniqueCountIndicator != null;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DISTINCT_COUNT_INDICATOR:
                return distinctCountIndicator != null;
            case ColumnsetPackage.COLUMN_SET_MULTI_VALUE_INDICATOR__DUPLICATE_COUNT_INDICATOR:
                return duplicateCountIndicator != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (listRows: ");
        result.append(listRows);
        result.append(", numericFunctions: ");
        result.append(numericFunctions);
        result.append(", dateFunctions: ");
        result.append(dateFunctions);
        result.append(", uniqueCount: ");
        result.append(uniqueCount);
        result.append(", distinctCount: ");
        result.append(distinctCount);
        result.append(')');
        return result.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.indicators.columnset.ColumnSetMultiValueIndicator#getCountAll()
     * 
     * @generated NOT getCountAll()
     */
    public String getCountAll() {
        return "COUNT(*)";
    }

    // bug 10578 by zshen,fix the exception when correlation analysis to be move
    private class BasicElistExtend<E> extends BasicEList<E> implements EStructuralFeature.Setting {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#get(boolean)
         */
        public Object get(boolean resolve) {

            return this;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#getEObject()
         */
        public EObject getEObject() {

            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#getEStructuralFeature()
         */
        public EStructuralFeature getEStructuralFeature() {
            // TODO Auto-generated method stub
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#isSet()
         */
        public boolean isSet() {
            // TODO Auto-generated method stub
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#set(java.lang.Object)
         */
        public void set(Object newValue) {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#unset()
         */
        public void unset() {
            // TODO Auto-generated method stub

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.indicators.impl.CompositeIndicatorImpl#getChildIndicators()
     * 
     * ADDED yyi 2009-12-28 getChildIndicators()
     */
    @Override
    public EList<Indicator> getChildIndicators() {
        EList<Indicator> children = new BasicEList<Indicator>();
        addChildToList(this.getRowCountIndicator(), children);
        addChildToList(this.getDistinctCountIndicator(), children);
        addChildToList(this.getDuplicateCountIndicator(), children);
        addChildToList(this.getUniqueCountIndicator(), children);
        return children;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.indicators.impl.IndicatorImpl#setComputed(boolean)
     */
    @Override
    public void setComputed(boolean newComputed) {
        super.setComputed(newComputed);
        for (Indicator indicator : getChildIndicators()) {
            indicator.setComputed(newComputed);
        }
    }

    /*
     * Add yyi 2010-02-08 10703. Set row count for each child indicator
     * 
     * @see org.talend.dataquality.indicators.impl.IndicatorImpl#setCount(java.lang.Long)
     */
    @Override
    public void setCount(Long newCount) {
        super.setCount(newCount);
        for (Indicator indicator : getChildIndicators()) {
            indicator.setCount(newCount);
        }
    }

    @Override
    public boolean finalizeComputation() {
        convertValueByGroupMapToList();
        storeSqlResults(valueByGroupList);
        return super.finalizeComputation();
    }

    @Override
    public boolean reset() {
        this.valueByGroupMap.clear();
        return super.reset();
    }

    /**
     * 
     * convert Map 'valueByGroupMap' to a List<Object[]>
     * 
     * @return
     */
    protected List<Object[]> convertValueByGroupMapToList() {
        Iterator<Object> it = valueByGroupMap.keySet().iterator();
        valueByGroupList = new ArrayList<Object[]>();
        while (it.hasNext()) {
            Tuple tuple = (Tuple) it.next();
            List<Object> tempLs = new ArrayList<Object>();
            tempLs.addAll(Arrays.asList(tuple.getTuple()));
            tempLs.add(valueByGroupMap.get(tuple));
            valueByGroupList.add(tempLs.toArray());
        }
        return valueByGroupList;
    }

    @Override
    public boolean handle(EList<Object> datas) {
        Object objects[] = datas.toArray();
        Tuple tuple = new Tuple(objects);
        Iterator<Object> it = valueByGroupMap.keySet().iterator();
        boolean isFound = false;
        while (it.hasNext()) {
            Tuple oldTuple = (Tuple) it.next();
            if (tuple.equals(oldTuple)) {
                AlgoUtils.incrementValueCounts(oldTuple, this.valueByGroupMap);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            AlgoUtils.incrementValueCounts(tuple, this.valueByGroupMap);
        }
        return true;
    }
} // ColumnSetMultiValueIndicatorImpl
