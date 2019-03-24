/**
 * 
 */
package es.uam.eps.tweetextractorfx.view.analytics.reports;

import java.util.List;
import es.uam.eps.tweetextractor.dao.service.inter.AnalyticsReportRegisterServiceInterface;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReport;
import es.uam.eps.tweetextractor.model.analytics.report.AnalyticsReportRegister;
import es.uam.eps.tweetextractorfx.MainApplication;
import es.uam.eps.tweetextractorfx.view.TweetExtractorFXController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

/**
 * @author jose
 *
 */
public abstract class ShowRawDataControl extends TweetExtractorFXController {
	@FXML
	private TableView<AnalyticsReportRegister> table;
	private ObservableList<AnalyticsReportRegister> registerList = FXCollections.observableArrayList();
	private AnalyticsReport report;
	private AnalyticsReportRegisterServiceInterface rServ;
	private Scene scene;
	private Button doneButton;
	private Text titleText;

	/**
	 * 
	 */
	public ShowRawDataControl(Scene scene,AnalyticsReport report) {
		super();
		this.scene=scene;
		this.report=report;
		table =(TableView)scene.lookup("#table");
		doneButton = (Button)scene.lookup("#doneButton");
		titleText = (Text)scene.lookup("#titleText");
	}

	@Override
	public void setMainApplication(MainApplication mainApplication) {
		super.setMainApplication(mainApplication);
		table.setItems(registerList);
		rServ = this.mainApplication.getSpringContext().getBean(AnalyticsReportRegisterServiceInterface.class);
		refreshRegisterList();
		doneButton.setOnAction(event->this.getMainApplication().showScreenInCenterOfRootLayout("view/analytics/reports/MyReports.fxml"));
		titleText.setText("Raw Data - Report ID: "+report.getId());
	}

	private void refreshRegisterList() {
		if (this.report != null) {
			List<AnalyticsReportRegister> toAdd = rServ.findByReport(this.report);
			if (toAdd != null) {
				registerList.clear();
				registerList.addAll(toAdd);
			}
		}
	}

	/**
	 * @return the report
	 */
	public AnalyticsReport getReport() {
		return report;
	}

	/**
	 * @param report the report to set
	 */
	public void setReport(AnalyticsReport report) {
		this.report = report;
	}

	/**
	 * @return the table
	 */
	public TableView<AnalyticsReportRegister> getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(TableView<AnalyticsReportRegister> table) {
		this.table = table;
	}

	/**
	 * @return the registerList
	 */
	public ObservableList<AnalyticsReportRegister> getRegisterList() {
		return registerList;
	}

	/**
	 * @param registerList the registerList to set
	 */
	public void setRegisterList(ObservableList<AnalyticsReportRegister> registerList) {
		this.registerList = registerList;
	}

	/**
	 * @return the rServ
	 */
	public AnalyticsReportRegisterServiceInterface getrServ() {
		return rServ;
	}

	/**
	 * @param rServ the rServ to set
	 */
	public void setrServ(AnalyticsReportRegisterServiceInterface rServ) {
		this.rServ = rServ;
	}


	/**
	 * @return the scene
	 */
	public Scene getScene() {
		return scene;
	}


	/**
	 * @param scene the scene to set
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}

}
