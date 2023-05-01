package reports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class ValidTIReport extends JFrame {
        public void showReport(Connection conn) throws JRException {
            try {
                String reportSrcFile = getClass().getResource("/reports/ReportValid.jrxml").getFile();
                String reportsDir = getClass().getResource("/reports/").getFile();
                //zbog moje mutanje Emina Basic
                reportSrcFile = reportSrcFile.replace("%20", " ");

                JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
                HashMap<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("reportsDirPath", reportsDir);
                ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                list.add(parameters);
                JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
                JRViewer viewer = new JRViewer(print);
                viewer.setOpaque(true);
                viewer.setVisible(true);
                this.add(viewer);
                this.setSize(700, 500);
                this.setVisible(true);
                this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                        getClass().getResource("/img/mainicon.png")));
            } catch (JRException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getLocalizedMessage());
                throw new JRException(".");
            }
        }


}
