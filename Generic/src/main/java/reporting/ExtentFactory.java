package reporting;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentFactory {

    public static ExtentReports getInstance(String pathReports){
        ExtentReports extent;
        String Path = pathReports + ".html";
        extent = new ExtentReports(Path, true);
        extent.addSystemInfo("platform", "windows 10");
        return extent;
    }
}
