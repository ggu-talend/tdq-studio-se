/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.dataquality.indicators.schema.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.talend.dataquality.indicators.schema.util.SchemaAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SchemaItemProviderAdapterFactory extends SchemaAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
    /**
     * This keeps track of the root adapter factory that delegates to this adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ComposedAdapterFactory parentAdapterFactory;

    /**
     * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IChangeNotifier changeNotifier = new ChangeNotifier();

    /**
     * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Collection<Object> supportedTypes = new ArrayList<Object>();

    /**
     * This constructs an instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SchemaItemProviderAdapterFactory() {
        supportedTypes.add(IEditingDomainItemProvider.class);
        supportedTypes.add(IStructuredItemContentProvider.class);
        supportedTypes.add(ITreeItemContentProvider.class);
        supportedTypes.add(IItemLabelProvider.class);
        supportedTypes.add(IItemPropertySource.class);
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.indicators.schema.SchemaIndicator} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SchemaIndicatorItemProvider schemaIndicatorItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.indicators.schema.SchemaIndicator}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createSchemaIndicatorAdapter() {
        if (schemaIndicatorItemProvider == null) {
            schemaIndicatorItemProvider = new SchemaIndicatorItemProvider(this);
        }

        return schemaIndicatorItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.indicators.schema.TableIndicator} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TableIndicatorItemProvider tableIndicatorItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.indicators.schema.TableIndicator}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createTableIndicatorAdapter() {
        if (tableIndicatorItemProvider == null) {
            tableIndicatorItemProvider = new TableIndicatorItemProvider(this);
        }

        return tableIndicatorItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.indicators.schema.ConnectionIndicator} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ConnectionIndicatorItemProvider connectionIndicatorItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.indicators.schema.ConnectionIndicator}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createConnectionIndicatorAdapter() {
        if (connectionIndicatorItemProvider == null) {
            connectionIndicatorItemProvider = new ConnectionIndicatorItemProvider(this);
        }

        return connectionIndicatorItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.indicators.schema.CatalogIndicator} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected CatalogIndicatorItemProvider catalogIndicatorItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.indicators.schema.CatalogIndicator}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createCatalogIndicatorAdapter() {
        if (catalogIndicatorItemProvider == null) {
            catalogIndicatorItemProvider = new CatalogIndicatorItemProvider(this);
        }

        return catalogIndicatorItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.indicators.schema.ViewIndicator} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ViewIndicatorItemProvider viewIndicatorItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.indicators.schema.ViewIndicator}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createViewIndicatorAdapter() {
        if (viewIndicatorItemProvider == null) {
            viewIndicatorItemProvider = new ViewIndicatorItemProvider(this);
        }

        return viewIndicatorItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.indicators.schema.AbstractTableIndicator} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AbstractTableIndicatorItemProvider abstractTableIndicatorItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.indicators.schema.AbstractTableIndicator}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createAbstractTableIndicatorAdapter() {
        if (abstractTableIndicatorItemProvider == null) {
            abstractTableIndicatorItemProvider = new AbstractTableIndicatorItemProvider(this);
        }

        return abstractTableIndicatorItemProvider;
    }

    /**
     * This returns the root adapter factory that contains this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ComposeableAdapterFactory getRootAdapterFactory() {
        return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
    }

    /**
     * This sets the composed adapter factory that contains this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
        this.parentAdapterFactory = parentAdapterFactory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object type) {
        return supportedTypes.contains(type) || super.isFactoryForType(type);
    }

    /**
     * This implementation substitutes the factory itself as the key for the adapter.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter adapt(Notifier notifier, Object type) {
        return super.adapt(notifier, this);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object adapt(Object object, Object type) {
        if (isFactoryForType(type)) {
            Object adapter = super.adapt(object, type);
            if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
                return adapter;
            }
        }

        return null;
    }

    /**
     * This adds a listener.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void addListener(INotifyChangedListener notifyChangedListener) {
        changeNotifier.addListener(notifyChangedListener);
    }

    /**
     * This removes a listener.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void removeListener(INotifyChangedListener notifyChangedListener) {
        changeNotifier.removeListener(notifyChangedListener);
    }

    /**
     * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void fireNotifyChanged(Notification notification) {
        changeNotifier.fireNotifyChanged(notification);

        if (parentAdapterFactory != null) {
            parentAdapterFactory.fireNotifyChanged(notification);
        }
    }

    /**
     * This disposes all of the item providers created by this factory. 
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void dispose() {
        if (schemaIndicatorItemProvider != null) schemaIndicatorItemProvider.dispose();
        if (tableIndicatorItemProvider != null) tableIndicatorItemProvider.dispose();
        if (connectionIndicatorItemProvider != null) connectionIndicatorItemProvider.dispose();
        if (catalogIndicatorItemProvider != null) catalogIndicatorItemProvider.dispose();
        if (viewIndicatorItemProvider != null) viewIndicatorItemProvider.dispose();
        if (abstractTableIndicatorItemProvider != null) abstractTableIndicatorItemProvider.dispose();
    }

}
