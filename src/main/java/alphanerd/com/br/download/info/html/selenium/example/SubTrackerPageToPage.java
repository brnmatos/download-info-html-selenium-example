package alphanerd.com.br.download.info.html.selenium.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import alphanerd.com.br.download.info.html.selenium.example.model.ExampleModel;

public class SubTrackerPageToPage {
	
	public static void main(String[] args)
			throws InterruptedException, ExecutionException, TimeoutException, IOException {
		Path pathFiles = Paths.get("src/files");

		System.setProperty("webdriver.chrome.driver", pathFiles + "/chromedriver.exe");

		
		for(int i =1; i <= 250; i++) {
			String nameFile = "/file";
			String extFile = ".txt";
			String numberPage = String.valueOf(i);
			String fileRead = pathFiles + nameFile + numberPage + extFile;

			
			List<String> arrayListStringNames = new ArrayList<String>();

			if (new File(pathFiles+nameFile+numberPage+extFile).exists()) {
				
				FileReader fReader = new FileReader(fileRead);
				BufferedReader bfReader = new BufferedReader(fReader);
				
				try {
				    String line = bfReader.readLine();

				    while (line != null) {
				    	arrayListStringNames.add(line);
				        line = bfReader.readLine();
				    }
				} finally {
					bfReader.close();
				}
			}
			
			List<ExampleModel> arrayListModel = new ArrayList<ExampleModel>();
			
			ChromeDriver driver = new ChromeDriver();

			arrayListStringNames.stream().forEach(action -> {
				
				ExampleModel exampleTemp = new ExampleModel();
				
				String nameExample = "";
				String registerExample = "";
				String linkExample = "";
				
				String[] vetRead = action.toString().split("&&");
				
				nameExample = vetRead[1];
				registerExample = vetRead[2];
				linkExample = vetRead[3];
				
				try {
			
					driver.navigate().to(new URL(linkExample));
					Thread.sleep(3000);
					
					WebElement lgpdButton = driver.findElement(By.id("zsitc_close"));
					lgpdButton.click();
				
					Thread.sleep(1000);

					WebElement searchButton = driver.findElement(By.id("LGPD_ANBIMA_global_sites__text__btn"));
					searchButton.click();
					
					Thread.sleep(1000);

				} catch (Exception e) {

				}
				String strNameExample = "";
				String strRegisterExample = "";
				WebElement weMain = null;
				try {
					weMain = driver.findElement(By.id("output__container--prestadoresGestores"));
					WebElement weNameExample = weMain.findElement(By.className("anbima-ui-output__value"));
					strNameExample = weNameExample.getText().toString();
					
					WebElement weRegisterExample = weMain.findElement(By.className("anbima-ui-output__detail"));
					strRegisterExample = weRegisterExample.getText().toString();

				} catch (Exception e) {
					WebElement wePanelButton = driver.findElement(By.id("output__container--prestadoresGestores"));

					WebElement weButtonClick03 = wePanelButton.findElement(By.className("anbima-ui-output__detail--button"));
					weButtonClick03.click();

					weMain = driver.findElement(By.id("output__container--prestadoresModal-0"));
					WebElement weNameExample = weMain.findElement(By.className("anbima-ui-output__label"));
					strNameExample = weNameExample.getText().toString();

					WebElement weRegisterExample = weMain.findElement(By.className("anbima-ui-output__value"));
					strRegisterExample = weRegisterExample.getText().toString();
					
				}

				exampleTemp.setName(nameExample);
				exampleTemp.setCnpj(registerExample);
				exampleTemp.setLink(linkExample);
				exampleTemp.setManager(strNameExample);
				exampleTemp.setManagerCnpj(strRegisterExample);

				arrayListModel.add(exampleTemp);
				
			});
			
			driver.close();

			FileWriter myWriter = new FileWriter(pathFiles+nameFile+numberPage+"_new"+extFile);
			String body = "";
			StringBuilder sb = new StringBuilder();
			
			arrayListModel.stream().forEach(action -> {
				sb.append(action.getName().toString());
				sb.append("&&"); 
				sb.append(action.getCnpj().toString());
				sb.append("&&"); 
				sb.append(action.getLink().toString());
				sb.append("&&"); 
				sb.append(action.getManager().toString());
				sb.append("&&"); 
				sb.append(action.getManagerCnpj().toString());
				sb.append(System.lineSeparator());
			});
			
			
			body = sb.toString();
			myWriter.write(body);
			myWriter.close();
			
			System.out.println("----------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------");
			System.out.println("------------------------EXPORTED SUCESSFULL!--------------------------");
			System.out.println("----------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------");
			
		}
		
	}
	
}
