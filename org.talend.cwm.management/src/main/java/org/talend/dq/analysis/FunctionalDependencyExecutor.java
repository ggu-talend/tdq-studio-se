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
package org.talend.dq.analysis;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.talend.cwm.db.connection.ConnectionUtils;
import org.talend.cwm.exception.AnalysisExecutionException;
import org.talend.cwm.helper.ColumnHelper;
import org.talend.cwm.helper.ColumnSetHelper;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.indicators.Indicator;
import org.talend.dataquality.indicators.columnset.ColumnDependencyIndicator;
import org.talend.dataquality.indicators.columnset.ColumnsetPackage;
import org.talend.dataquality.indicators.definition.IndicatorDefinition;
import org.talend.dq.dbms.GenericSQLHandler;
import org.talend.utils.sugars.TypedReturnCode;
import orgomg.cwm.objectmodel.core.CoreFactory;
import orgomg.cwm.objectmodel.core.Expression;
import orgomg.cwm.objectmodel.core.Package;
import orgomg.cwm.resource.relational.Column;
import orgomg.cwm.resource.relational.ColumnSet;

/**
 * DOC jet class global comment. Detailled comment
 */
public class FunctionalDependencyExecutor extends ColumnAnalysisSqlExecutor {

    private static Logger log = Logger.getLogger(FunctionalDependencyExecutor.class);

    private String catalogOrSchema = null;

    @Override
    protected boolean check(Analysis analysis) {

        return true;

    }

    protected boolean runAnalysis(Analysis analysis, String sqlStatement) {
        boolean ok = true;
        TypedReturnCode<Connection> trc = this.getConnection(analysis);
        if (!trc.isOk()) {
            return traceError("Cannot execute Analysis " + analysis.getName() + ". Error: " + trc.getMessage());
        }

        Connection connection = trc.getObject();
        try {

            // execute the sql statement for each indicator
            EList<Indicator> indicators = analysis.getResults().getIndicators();
            EList<Indicator> deactivatedIndicators = analysis.getParameters().getDeactivatedIndicators();
            for (Indicator indicator : indicators) {
                if (deactivatedIndicators.contains(indicator)) {
                    continue; // do not evaluate this indicator
                }

                Expression query = dbms().getInstantiatedExpression(indicator);

                if (query == null || !executeQuery(indicator, connection, query)) {
                    ok = traceError("Query not executed for indicator: \"" + indicator.getName() + "\" "
                            + ((query == null) ? "query is null" : "SQL query: " + query.getBody()));
                } else {
                    indicator.setComputed(true);
                }

            }

            connection.close();

        } catch (SQLException e) {
            log.error(e, e);
            this.errorMessage = e.getMessage();
            ok = false;
        } catch (AnalysisExecutionException e) {
            ok = traceError(e.getMessage());
        } finally {
            ConnectionUtils.closeConnection(connection);
        }
        return ok;
    }

    private boolean executeQuery(Indicator indicator, Connection connection, Expression query) throws AnalysisExecutionException {
        try {

            List<Object[]> myResultSet = executeQuery(catalogOrSchema, connection, query.getBody());
            if (indicator instanceof ColumnDependencyIndicator) {
                ((ColumnDependencyIndicator) indicator).setACount(getNB(myResultSet));
                ((ColumnDependencyIndicator) indicator).setDistinctACount(getNBDistinct(myResultSet));

            }
            // computer result

        } catch (SQLException e) {
            log.error(e, e);
            return false;
        }

        return true;
    }

    /**
     * DOC jet Comment method "getNBDistinct".
     * 
     * @param myResultSet
     * @return
     */
    private Long getNBDistinct(List<Object[]> myResultSet) {

        if (myResultSet.size() == 1 && myResultSet.get(0).length == 2) {
            return (Long) myResultSet.get(0)[1];
        }

        return null;
    }

    /**
     * DOC jet Comment method "getNB".
     * 
     * @param myResultSet
     * @return
     */
    private Long getNB(List<Object[]> myResultSet) {
        if (myResultSet.size() == 1 && myResultSet.get(0).length == 2) {
            return (Long) myResultSet.get(0)[0];
        }

        return null;
    }

    @Override
    protected String createSqlStatement(Analysis analysis) {
        this.cachedAnalysis = analysis;

        EList<Indicator> indicators = analysis.getResults().getIndicators();
        for (Indicator indicator : indicators) {
            instantiateQuery(indicator);
        }

        // no query to return, here we only instantiate several SQL queries
        return ""; //$NON-NLS-1$
    }

    private boolean instantiateQuery(Indicator indicator) {
        // (but is not need, hence we keep it commented)

        if (ColumnsetPackage.eINSTANCE.getColumnDependencyIndicator().equals(indicator.eClass())) {
            ColumnDependencyIndicator rowMatchingIndicator = (ColumnDependencyIndicator) indicator;
            Column columnA = rowMatchingIndicator.getColumnA();
            Column columnB = rowMatchingIndicator.getColumnB();

            IndicatorDefinition indicatorDefinition = indicator.getIndicatorDefinition();
            Expression sqlGenericExpression = dbms().getSqlExpression(indicatorDefinition);

            boolean useNulls = false; // TODO scorreia create an indicator for each option

            Expression instantiatedSqlExpression = createInstantiatedSqlExpression(sqlGenericExpression, columnA, columnB);
            indicator.setInstantiatedExpression(instantiatedSqlExpression);
            return true;
        }
        return traceError("Unhandled given indicator: " + indicator.getName());
    }

    /**
     * DOC jet Comment method "createInstantiatedSqlExpression".
     * 
     * @param sqlGenericExpression
     * @param columnA
     * @param columnB
     * @param useNulls
     * @return
     */
    private Expression createInstantiatedSqlExpression(Expression sqlGenericExpression, Column columnA, Column columnB) {
        assert columnA != null;
        assert columnB != null;

        String genericSQL = sqlGenericExpression.getBody();
        GenericSQLHandler sqlHandler = new GenericSQLHandler(genericSQL);
        sqlHandler.replaceColumnA(columnA.getName()).replaceColumnB(columnB.getName()).replaceTable(
                getTableNameFromColumn(columnA));
        Expression instantiatedExpression = CoreFactory.eINSTANCE.createExpression();
        instantiatedExpression.setLanguage(sqlGenericExpression.getLanguage());
        String instantiatedSQL = sqlHandler.getSqlString();
        instantiatedExpression.setBody(instantiatedSQL);
        return instantiatedExpression;
    }

    private String getTableNameFromColumn(Column column) {

        ColumnSet columnSetOwner = ColumnHelper.getColumnSetOwner(column);
        if (columnSetOwner == null) {
            log.error("ColumnSet Owner of column " + column.getName() + " is null");
        } else {
            // this is so bad code
            Package schema = ColumnSetHelper.getParentCatalogOrSchema(columnSetOwner);
            catalogOrSchema = schema.getName();
            return columnSetOwner.getName();

        }
        return null;
    }

}
