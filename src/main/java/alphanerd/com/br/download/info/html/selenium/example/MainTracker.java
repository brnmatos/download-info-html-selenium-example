package alphanerd.com.br.download.info.html.selenium.example;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import alphanerd.com.br.download.info.html.selenium.example.model.ExampleModel;

public class MainTracker {

	public static void main(String[] args)
			throws InterruptedException, ExecutionException, TimeoutException, IOException {
    	Path pathFiles = Paths.get("src/files");

		System.setProperty("webdriver.chrome.driver", pathFiles + "/chromedriver.exe");
		
		ChromeDriver driver = new ChromeDriver();
		
		int codPageCount = 1;
		
		for (int x = 1; x <= 250; x++) {
			codPageCount = x;
			
			String urlStr = "https://data.anbima.com.br/fundos?page="+codPageCount+"&size=100&";
			String patchTxt = pathFiles+"/file"+codPageCount+".txt";

			driver.navigate().to(new URL(urlStr));

			Thread.sleep(15000);
			
			FileWriter myWriter = new FileWriter(patchTxt);
			String body = "";
			StringBuilder sb = new StringBuilder();

			try {
				WebElement lgpdButton = driver.findElement(By.id("zsitc_close"));
				lgpdButton.click();
			
				Thread.sleep(5000);

				WebElement searchButton = driver.findElement(By.id("LGPD_ANBIMA_global_sites__text__btn"));
				searchButton.click();
			} catch (Exception e) {
				System.err.println(e);
			}

			for (int i = 0; i < 100; i++) {
				ExampleModel fd = new ExampleModel();

				WebElement weName01 = driver.findElement(By.id("item-title-" + String.valueOf(i)));
				String strName = weName01.getText().toString().trim().replace(System.lineSeparator(), "");
				fd.setName(strName);
				
				WebElement weCnpj02 = driver.findElement(By.id("cnpj-" + String.valueOf(i)));
				String strCnpj = weCnpj02.getText().trim().substring(0,15);
				strCnpj = strCnpj + "-" + checkType(weCnpj02.getText().trim());
				fd.setCnpj(strCnpj);
				
				String strLink = weName01.getAttribute("href");
				fd.setLink(strLink);

				String strManager03 = "";
				try {
					WebElement weManager03 = driver.findElement(By.id("gestor-" + String.valueOf(i)));
					strManager03 = weManager03.getText().trim().replace("Gestor", "").replace(System.lineSeparator(), "");
				} catch (Exception e) {
					strManager03 = "DOIS GESTORES";
				}
				fd.setManager(strManager03);
				
				WebElement weDetail04 = null;
				String strDetail04 = "";
				try {
					weDetail04 = driver
							.findElement(By.id("caracteristicaInvestidor-" + String.valueOf(i)));
					strDetail04 = weDetail04.getText().trim().
							replace("Característica do investidor", "");
					
				} catch (Exception e) {
					strDetail04 = "DOIS GESTORES".trim().replace(System.lineSeparator(), "");
				}

				fd.setDescription(strDetail04);
				
				sb.append(i);
				sb.append("&&");
				sb.append(strName);
				sb.append("&&"); 
				sb.append(strCnpj);
				sb.append("&&"); 
				sb.append(strLink);
				sb.append(System.lineSeparator());
			}
			
			body = sb.toString();
			myWriter.write(body);
			myWriter.close();
			
			System.out.println("----------------------------------------------------------------------");
			System.out.println("---------------------TXT SUCCESSFULL EXPORTED-------------------------");
			System.out.println("----------------------------------------------------------------------");
			
		}
		
		driver.close();
	}
	
	private static String checkType(String type) {
		if (type.contains("PREVIDÊNCIA")) {
			return "PREVIDÊNCIA";
		}else if (type.contains("RENDA FIXA")) {
			return "RENDA FIXA";
		}else if (type.contains("AÇÕES")) {
			return "AÇÕES";
		}else if (type.contains("MULTIMERCADOS")) {
			return "MULTIMERCADOS";
		}else {return "";}
		
	}

}
