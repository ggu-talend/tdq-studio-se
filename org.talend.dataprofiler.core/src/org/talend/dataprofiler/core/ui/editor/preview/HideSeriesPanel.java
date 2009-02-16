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
package org.talend.dataprofiler.core.ui.editor.preview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.widgets.Display;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.ui.TextAnchor;
import org.talend.cwm.relational.TdColumn;
import org.talend.dataprofiler.core.ui.utils.ChartDatasetUtils;
import org.talend.dataprofiler.core.ui.utils.ChartUtils;
import org.talend.dataprofiler.core.ui.utils.ChartDatasetUtils.DateValueAggregate;
import org.talend.dataprofiler.core.ui.utils.ChartDatasetUtils.ValueAggregator;
import org.talend.dataquality.indicators.columnset.ColumnSetMultiValueIndicator;
import org.talend.dataquality.indicators.columnset.CountAvgNullIndicator;
import orgomg.cwm.resource.relational.Column;

/**
 * 
 * DOC xZhao class global comment. Detailled comment
 */
public class HideSeriesPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private Map<String, RowColumPair> hightlightSeriesMap = new HashMap<String, RowColumPair>();

    private int columnCount;

    /**
     * 
     * DOC zhaoxinyi HideSeriesPanel class global comment. Detailled comment
     */
    @SuppressWarnings("serial")
    class CustomHideSeriesGantt extends HideSeriesGanttRenderer {

        /**
         * 
         * DOC zhaoxinyi CustomHideSeriesGantt constructor comment.
         * 
         * @param colors
         */
        public Paint getItemPaint(int row, int column) {
            Paint itemPaint = super.getItemPaint(row, column);
            String key = String.valueOf(row) + String.valueOf(column);
            if (hightlightSeriesMap.get(key) != null && hightlightSeriesMap.get(key).getRow() == row
                    && hightlightSeriesMap.get(key).getColumn() == column) {
                return ((Color) itemPaint).brighter().brighter();
            }
            return itemPaint;
        }
    }

    /**
     * 
     * DOC zhaoxinyi HideSeriesPanel class global comment. Detailled comment
     */
    class HideChartPanel extends ChartPanel {

        private static final long serialVersionUID = 1L;

        public HideChartPanel(JFreeChart chart) {
            super(chart, false, false, false, false, true);
        }

        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            grabFocus();
        }
    }

    private ColumnSetMultiValueIndicator countIndicator;

    private TdColumn tdColumn;

    private XYItemRenderer xyRenderer;

    private CategoryItemRenderer ganttRenderer;

    public void actionPerformed(ActionEvent actionevent) {
        Iterator<String> iterator = null;
        if (countIndicator instanceof CountAvgNullIndicator) {
            Map<String, ValueAggregator> createXYZDatasets = ChartDatasetUtils.createXYZDatasets(countIndicator, tdColumn);
            iterator = createXYZDatasets.keySet().iterator();
            int byte0 = -1;
            int i = 1;
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (actionevent.getActionCommand().equals(key)) {
                    byte0 = i - 1;
                }
                i++;
            }
            if (byte0 >= 0) {
                boolean flag = xyRenderer.getItemVisible(byte0, 0);
                xyRenderer.setSeriesVisible(byte0, new Boolean(!flag));
            }
        } else {
            Map<String, DateValueAggregate> createGanttDatasets = ChartDatasetUtils.createGanttDatasets(countIndicator, tdColumn);
            iterator = createGanttDatasets.keySet().iterator();
            int byte0 = -1;
            int i = 1;
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (actionevent.getActionCommand().equals(key)) {
                    byte0 = i - 1;
                }
                i++;
            }
            if (byte0 >= 0) {
                boolean flag = ganttRenderer.getItemVisible(byte0, 0);
                ganttRenderer.setSeriesVisible(byte0, new Boolean(!flag));
                // ganttRenderer.getPlot().clearAnnotations();
            }
        }

    }

    @SuppressWarnings("deprecation")
    public HideSeriesPanel(ColumnSetMultiValueIndicator columnMultiIndicator, TdColumn columnPara) {
        super(new BorderLayout());
        this.countIndicator = columnMultiIndicator;
        this.tdColumn = columnPara;
        JFreeChart chart = null;
        Iterator<String> iterator;
        if (columnMultiIndicator instanceof CountAvgNullIndicator) {
            Map<String, ValueAggregator> createXYZDatasets = ChartDatasetUtils.createXYZDatasets(columnMultiIndicator, tdColumn);
            iterator = createXYZDatasets.keySet().iterator();
            chart = TopChartFactory.createBubbleChart(columnMultiIndicator, columnPara);
            XYPlot plot = chart.getXYPlot();
            xyRenderer = plot.getRenderer();
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            int minYValue = (int) getMinYValue(plot, true);
            rangeAxis.setAutoRange(false);
            rangeAxis.setRange(0, minYValue * 100);
            rangeAxis.setTickUnit(new NumberTickUnit(minYValue * 5));
        } else {
            List<Object[]> rowList = columnMultiIndicator.getListRows();
            final EList<Column> nominalColumns = columnMultiIndicator.getNominalColumns();
            final EList<Column> dateColumns = columnMultiIndicator.getDateColumns();
            final EList<String> dateFunctions = columnMultiIndicator.getDateFunctions();
            final int indexOfDateCol = dateColumns.indexOf(columnPara);
            assert indexOfDateCol != -1;
            final int nbNominalColumns = nominalColumns.size();

            final int nbDateFunctions = dateFunctions.size();
            Map<String, DateValueAggregate> createGanttDatasets = ChartDatasetUtils.createGanttDatasets(columnMultiIndicator,
                    tdColumn);
            iterator = createGanttDatasets.keySet().iterator();
            chart = TopChartFactory.createGanttChart(columnMultiIndicator, columnPara);
            createAnnotOnGantt(chart, rowList, nbNominalColumns + nbDateFunctions * indexOfDateCol + 3, nbNominalColumns);
            final CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.setRenderer(new CustomHideSeriesGantt());
            plot.getDomainAxis().setMaximumCategoryLabelWidthRatio(10.0f);
            ganttRenderer = plot.getRenderer();
            CategoryToolTipGenerator toolTipGenerator = new CategoryToolTipGenerator() {

                public String generateToolTip(CategoryDataset dataset, int row, int column) {
                    TaskSeriesCollection taskSeriesColl = (TaskSeriesCollection) dataset;
                    List<Task> taskList = new ArrayList<Task>();
                    for (int i = 0; i < taskSeriesColl.getSeriesCount(); i++) {
                        for (int j = 0; j < taskSeriesColl.getSeries(i).getItemCount(); j++) {
                            taskList.add(taskSeriesColl.getSeries(i).get(j));
                        }
                    }
                    Task task = taskList.get(column);
                    // Task task = taskSeriesColl.getSeries(row).get(column);
                    String taskDescription = task.getDescription();
                    Date startDate = task.getDuration().getStart();
                    Date endDate = task.getDuration().getEnd();
                    return taskDescription + ",     " + startDate + "---->" + endDate; //$NON-NLS-1$ //$NON-NLS-2$
                    // return "this is a tooltip";
                }
            };
            ganttRenderer.setToolTipGenerator(toolTipGenerator);
        }
        final HideChartPanel chartpanel = new HideChartPanel(chart);
        chartpanel.addChartMouseListener(new ChartMouseListener() {

            public void chartMouseClicked(ChartMouseEvent event) {

                if (event.getTrigger().getClickCount() != 2) {
                    return;
                }

                Display.getDefault().asyncExec(new Runnable() {

                    public void run() {
                        ChartUtils.showChartInFillScreen(chartpanel.getChart());
                    }
                });
            }

            public void chartMouseMoved(ChartMouseEvent event) {
            }

        });

        JPanel jpanel = new JPanel();
        while (iterator.hasNext()) {
            String next = iterator.next();
            JCheckBox jcheckbox = new JCheckBox(next);
            jcheckbox.setActionCommand(next);
            jcheckbox.addActionListener(this);
            jcheckbox.setSelected(true);
            jpanel.add(jcheckbox);
        }
        add(chartpanel);
        add(jpanel, "South"); //$NON-NLS-1$
    }

    /**
     * 
     * DOC zhaoxinyi Comment method "getMinYValue".
     * 
     * @param plot
     * @param isY
     * @return
     */
    private double getMinYValue(XYPlot plot, boolean isY) {
        double minValue = 0d;
        int seriesCount = plot.getDataset().getSeriesCount();
        List<Double> yList = new ArrayList<Double>();
        for (int i = 0; i < seriesCount; i++) {
            for (int j = 0; j < plot.getDataset().getItemCount(i); j++) {
                double yValue = plot.getDataset().getYValue(i, j);
                yList.add(yValue);
            }
        }
        minValue = yList.get(0);
        for (double d : yList) {
            if (d > 0 && minValue > d) {
                minValue = d;
            }
        }
        if (minValue == 0) {
            minValue = 1d;
        }
        return minValue;
    }

    /**
     * 
     * DOC zhaoxinyi Comment method "createAnnotOnGantt".
     * 
     * @param chart
     * @param rowList
     * @param multiDateColumn
     */
    public void createAnnotOnGantt(JFreeChart chart, List<Object[]> rowList, int multiDateColumn, int nominal) {
        CategoryPlot xyplot = (CategoryPlot) chart.getPlot();
        CategoryTextAnnotation an;
        for (int seriesCount = 0; seriesCount < ((TaskSeriesCollection) xyplot.getDataset()).getSeriesCount(); seriesCount++) {
            int indexOfRow = 0;
            for (int itemCount = 0; itemCount < ((TaskSeriesCollection) xyplot.getDataset()).getSeries(seriesCount)
                    .getItemCount(); itemCount++, columnCount++) {
                Task task = ((TaskSeriesCollection) xyplot.getDataset()).getSeries(seriesCount).get(itemCount);
                String taskDescription = task.getDescription();
                String[] taskArray = taskDescription.split("\\|"); //$NON-NLS-1$
                boolean isSameTime = task.getDuration().getStart().getTime() == task.getDuration().getEnd().getTime();
                if (!isSameTime && (rowList.get(indexOfRow))[multiDateColumn - 3] != null
                        && (rowList.get(indexOfRow))[multiDateColumn - 2] != null
                        && !((rowList.get(indexOfRow))[multiDateColumn]).equals(new BigDecimal(0L))) {
                    RowColumPair pair = new RowColumPair();
                    pair.setRow(seriesCount);
                    pair.setColumn(columnCount);
                    hightlightSeriesMap.put(String.valueOf(seriesCount) + String.valueOf(columnCount), pair);
                    an = new CategoryTextAnnotation("#nulls = " + (rowList.get(indexOfRow))[multiDateColumn], //$NON-NLS-1$
                            (Comparable<String>) taskDescription, task.getDuration().getStart().getTime());
                    an.setTextAnchor(TextAnchor.CENTER_LEFT);
                    an.setCategoryAnchor(CategoryAnchor.MIDDLE);
                    xyplot.addAnnotation(an);
                }
                if (taskArray.length == nominal) {
                    indexOfRow++;

                    if (rowList.size() != indexOfRow
                            && ((rowList.get(indexOfRow))[multiDateColumn - 3] == null || (rowList.get(indexOfRow))[multiDateColumn - 2] == null)) {
                        indexOfRow++;
                    }
                }
            }
        }
    }
}

/**
 * 
 * DOC zhaoxinyi class global comment. Detailled comment
 */
class RowColumPair {

    private int row;

    private int column;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String toString() {
        return "row = " + row + ", column = " + column; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
