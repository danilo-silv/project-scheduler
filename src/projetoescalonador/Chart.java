/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoescalonador;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

/**
 *
 * @author Desktop
 */
public class Chart extends JFrame {

   private static final long serialVersionUID = 1L;

   public Chart(String title) {
      super(title);
      
      IntervalCategoryDataset dataset = getCategoryDataset();
    
      JFreeChart chart = ChartFactory.createGanttChart(
            "Representação em Gráfico de Gantt", // Chart title
            "", // X-Axis Label
            "Timeline", // Y-Axis Label
            dataset);

      ChartPanel panel = new ChartPanel(chart);
      setContentPane(panel);
   }

   private IntervalCategoryDataset getCategoryDataset() {

      TaskSeries series1 = new TaskSeries("Nome do processo 01");
      series1.add(new Task("Requirement",
            Date.from(LocalDate.of(2017, 7, 3).atStartOfDay().toInstant(ZoneOffset.UTC)),
            Date.from(LocalDate.of(2017, 7, 7).atStartOfDay().toInstant(ZoneOffset.UTC))
         ));
      
      series1.add(new Task("Design",
            Date.from(LocalDate.of(2017, 7, 10).atStartOfDay().toInstant(ZoneOffset.UTC)),
            Date.from(LocalDate.of(2017, 7, 14).atStartOfDay().toInstant(ZoneOffset.UTC))
         ));
      
      
      TaskSeries series2 = new TaskSeries("Nome do processo 02");
      series2.add(new Task("Requirement",
            Date.from(LocalDate.of(2017, 7, 3).atStartOfDay().toInstant(ZoneOffset.UTC)),
            Date.from(LocalDate.of(2017, 7, 05).atStartOfDay().toInstant(ZoneOffset.UTC))
         ));
      
      series2.add(new Task("Design",
            Date.from(LocalDate.of(2017, 7, 6).atStartOfDay().toInstant(ZoneOffset.UTC)),
            Date.from(LocalDate.of(2017, 7, 17).atStartOfDay().toInstant(ZoneOffset.UTC))
         ));
      
      TaskSeriesCollection dataset = new TaskSeriesCollection();
      dataset.add(series1);
      dataset.add(series2);
      return dataset;
   }
   
   public static void callPlot(){
       SwingUtilities.invokeLater(() -> {
         Chart actualChart = new Chart("Gantt Chart");
         actualChart.setSize(800, 400);
         actualChart.setLocationRelativeTo(null);
         actualChart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         actualChart.setVisible(true);
      });
   }
   
   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
         Chart example = new Chart("Gantt Chart");
         example.setSize(800, 400);
         example.setLocationRelativeTo(null);
         example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         example.setVisible(true);
      });
   }
}