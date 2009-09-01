/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.dataquality.indicators.columnset.impl;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.talend.dataquality.indicators.columnset.ColumnDependencyIndicator;
import org.talend.dataquality.indicators.columnset.ColumnsetPackage;
import org.talend.dataquality.indicators.impl.IndicatorImpl;
import orgomg.cwm.objectmodel.core.Expression;
import orgomg.cwm.resource.relational.Column;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Column Dependency Indicator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnDependencyIndicatorImpl#getColumnA <em>Column A</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnDependencyIndicatorImpl#getColumnB <em>Column B</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnDependencyIndicatorImpl#getACount <em>ACount</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnDependencyIndicatorImpl#getDistinctACount <em>Distinct ACount</em>}</li>
 *   <li>{@link org.talend.dataquality.indicators.columnset.impl.ColumnDependencyIndicatorImpl#getDependencyFactor <em>Dependency Factor</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ColumnDependencyIndicatorImpl extends IndicatorImpl implements ColumnDependencyIndicator {
    /**
     * The cached value of the '{@link #getColumnA() <em>Column A</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getColumnA()
     * @generated
     * @ordered
     */
    protected Column columnA;

    /**
     * The cached value of the '{@link #getColumnB() <em>Column B</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getColumnB()
     * @generated
     * @ordered
     */
    protected Column columnB;

    /**
     * The default value of the '{@link #getACount() <em>ACount</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getACount()
     * @generated
     * @ordered
     */
    protected static final Long ACOUNT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getACount() <em>ACount</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getACount()
     * @generated
     * @ordered
     */
    protected Long aCount = ACOUNT_EDEFAULT;

    /**
     * The default value of the '{@link #getDistinctACount() <em>Distinct ACount</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistinctACount()
     * @generated
     * @ordered
     */
    protected static final Long DISTINCT_ACOUNT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDistinctACount() <em>Distinct ACount</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistinctACount()
     * @generated
     * @ordered
     */
    protected Long distinctACount = DISTINCT_ACOUNT_EDEFAULT;

    /**
     * The default value of the '{@link #getDependencyFactor() <em>Dependency Factor</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDependencyFactor()
     * @generated
     * @ordered
     */
    protected static final Double DEPENDENCY_FACTOR_EDEFAULT = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ColumnDependencyIndicatorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ColumnsetPackage.Literals.COLUMN_DEPENDENCY_INDICATOR;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Column getColumnA() {
        if (columnA != null && columnA.eIsProxy()) {
            InternalEObject oldColumnA = (InternalEObject)columnA;
            columnA = (Column)eResolveProxy(oldColumnA);
            if (columnA != oldColumnA) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__COLUMN_A, oldColumnA, columnA));
            }
        }
        return columnA;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Column basicGetColumnA() {
        return columnA;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setColumnA(Column newColumnA) {
        Column oldColumnA = columnA;
        columnA = newColumnA;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__COLUMN_A, oldColumnA, columnA));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Column getColumnB() {
        if (columnB != null && columnB.eIsProxy()) {
            InternalEObject oldColumnB = (InternalEObject)columnB;
            columnB = (Column)eResolveProxy(oldColumnB);
            if (columnB != oldColumnB) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__COLUMN_B, oldColumnB, columnB));
            }
        }
        return columnB;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Column basicGetColumnB() {
        return columnB;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setColumnB(Column newColumnB) {
        Column oldColumnB = columnB;
        columnB = newColumnB;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__COLUMN_B, oldColumnB, columnB));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Long getACount() {
        return aCount;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setACount(Long newACount) {
        Long oldACount = aCount;
        aCount = newACount;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__ACOUNT, oldACount, aCount));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Long getDistinctACount() {
        return distinctACount;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDistinctACount(Long newDistinctACount) {
        Long oldDistinctACount = distinctACount;
        distinctACount = newDistinctACount;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__DISTINCT_ACOUNT, oldDistinctACount, distinctACount));
    }

    @Override
    public boolean storeSqlResults(List<Object[]> objects) {
        
        return true;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated not
     */
    public Double getDependencyFactor() {
    
        if(getACount() != null && getDistinctACount() != null){
            return (getACount() == 0L) ? Double.NaN : getDistinctACount().doubleValue() / getACount().doubleValue();
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__COLUMN_A:
                if (resolve) return getColumnA();
                return basicGetColumnA();
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__COLUMN_B:
                if (resolve) return getColumnB();
                return basicGetColumnB();
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__ACOUNT:
                return getACount();
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__DISTINCT_ACOUNT:
                return getDistinctACount();
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__DEPENDENCY_FACTOR:
                return getDependencyFactor();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__COLUMN_A:
                setColumnA((Column)newValue);
                return;
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__COLUMN_B:
                setColumnB((Column)newValue);
                return;
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__ACOUNT:
                setACount((Long)newValue);
                return;
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__DISTINCT_ACOUNT:
                setDistinctACount((Long)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__COLUMN_A:
                setColumnA((Column)null);
                return;
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__COLUMN_B:
                setColumnB((Column)null);
                return;
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__ACOUNT:
                setACount(ACOUNT_EDEFAULT);
                return;
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__DISTINCT_ACOUNT:
                setDistinctACount(DISTINCT_ACOUNT_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__COLUMN_A:
                return columnA != null;
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__COLUMN_B:
                return columnB != null;
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__ACOUNT:
                return ACOUNT_EDEFAULT == null ? aCount != null : !ACOUNT_EDEFAULT.equals(aCount);
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__DISTINCT_ACOUNT:
                return DISTINCT_ACOUNT_EDEFAULT == null ? distinctACount != null : !DISTINCT_ACOUNT_EDEFAULT.equals(distinctACount);
            case ColumnsetPackage.COLUMN_DEPENDENCY_INDICATOR__DEPENDENCY_FACTOR:
                return DEPENDENCY_FACTOR_EDEFAULT == null ? getDependencyFactor() != null : !DEPENDENCY_FACTOR_EDEFAULT.equals(getDependencyFactor());
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (aCount: ");
        result.append(aCount);
        result.append(", distinctACount: ");
        result.append(distinctACount);
        result.append(')');
        return result.toString();
    }

    @Override
    public Expression getInstantiatedExpressions(String language) {

        //i think this place is good place to create expression
        
        return super.getInstantiatedExpressions(language);
    }

   
    
    

} //ColumnDependencyIndicatorImpl
