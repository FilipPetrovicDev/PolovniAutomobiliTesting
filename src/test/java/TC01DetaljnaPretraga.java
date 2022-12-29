import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class TC01DetaljnaPretraga extends CommonStrings{

    @Test
    public void DetaljnaPretraga() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        System.out.println("@Test - DetaljnaPretraga");

        String baseUrl = "https://www.polovniautomobili.com/";

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.get(baseUrl);

        Thread.sleep(3000);

        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        // Izlazak iz reklame
        WebElement reklamaIzlazak = driver.findElement(By.className("_ado-responsiveFooterBillboard-hover"));
        reklamaIzlazak.click();

        // Izlazak iz cookies obavestenja
        WebElement cookiesObavestenje = driver.findElement(By.xpath("//a[@href='javascript:(void(0));']"));
        cookiesObavestenje.click();

        // Pronalazenje dugmeta Detaljna pretraga
        WebElement detPretraga = driver.findElement(By.name("isDetailed"));
        detPretraga.click();
        Assert.assertTrue(true);

        // Ukoliko ima potrebe za popunjavanjem ankete
        //WebElement neZelim = driver.findElement(By.id("btn_poll_no"));
        //neZelim.click();
        //Assert.assertTrue(true);

        // Provera odgovarajuce strane
        String actualDetPretragaUrl = DETALJNA_PRETRAGA_URL;
        String expectedDetPretragaUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualDetPretragaUrl, expectedDetPretragaUrl);

        Thread.sleep(3000);

        // Selektovanje labele audi
        WebElement otvaranjeListe = driver.findElement(By.xpath("//span[.=' Marka']"));
        otvaranjeListe.click();
        WebElement selektovanjeAudi = driver.findElement(By.xpath("//label[.='Audi']"));
        selektovanjeAudi.click();

        Thread.sleep(3000);

        // Selektovanje labele a4
        WebElement odabirModela = driver.findElement(By.xpath("//span[.=' Svi modeli']"));
        odabirModela.click();
        WebElement odabirA4 = driver.findElement(By.cssSelector("#search-form > div > form > div.uk-grid > div.uk-width-medium-3-4.uk-width-1-1 > div > div:nth-child(2) > div > div > ul > li:nth-child(8)"));
        if(!odabirA4.isSelected())odabirA4.click();

        //Thread.sleep(3000);

        // Unosenje pocetne sume
        WebElement cenaOd = driver.findElement(By.id("price_from"));
        cenaOd.sendKeys(CENA_OD);

        // Unosenje krajnje sume
        WebElement cenaDo = driver.findElement(By.id("price_to"));
        cenaDo.sendKeys(CENA_DO);

        // Odabir tipa vozila
        WebElement tipVozila = driver.findElement(By.xpath("//span[.=' Karoserija']"));
        tipVozila.click();
        WebElement tipLimuzina = driver.findElement(By.cssSelector("#search-form > div > form > div.uk-grid > div.uk-width-medium-3-4.uk-width-1-1 > div > div.uk-width-medium-1-3.uk-width-1-1.uk-margin-bottom.chassis-search.position-relative > div > div.uk-width-9-10 > div > div > ul > li:nth-child(1)"));
        if(!tipLimuzina.isSelected())tipLimuzina.click();

        // Odabir vrste goriva
        WebElement vrstaGoriva = driver.findElement(By.xpath("//span[.=' Vrsta goriva']"));
        vrstaGoriva.click();
        WebElement benzinGorivo = driver.findElement(By.xpath("//label[.='Benzin']"));
        if(!benzinGorivo.isSelected())benzinGorivo.click();

        // Odabir regiona
        WebElement odabirRegiona = driver.findElement(By.xpath("//span[.=' Region']"));
        odabirRegiona.click();
        WebElement odabirBeograda = driver.findElement(By.xpath("//*[@id=\"search-form\"]/div/form/div[1]/div[1]/div/div[11]/div/div/ul/li[1]/ul/li[1]/label"));
        if(!odabirBeograda.isSelected())odabirBeograda.click();

        // Odabir godista vozila
        // Pocetna vrednost
        WebElement godisteOd = driver.findElement(By.xpath("//span[.=' Godina od']"));
        godisteOd.click();
        WebElement pocetnaVrednost = driver.findElement(By.xpath("//label[.='2005 god.']"));
        pocetnaVrednost.click();
        // Krajanja vrednost
        WebElement godisteDo = driver.findElement(By.xpath("//span[.=' do']"));
        godisteDo.click();
        WebElement krajnjaVrednost = driver.findElement(By.xpath("//*[@id=\"search-form\"]/div/form/div[1]/div[1]/div/div[6]/div/div[2]/div/div/ul/li[5]/label"));
        krajnjaVrednost.click();

        // Odabir broja vrata
        WebElement brojVrata = driver.findElement(By.xpath("//span[.=' Broj vrata']"));
        brojVrata.click();
        WebElement odabirVrata = driver.findElement(By.xpath("//label[.='4/5 vrata']"));
        odabirVrata.click();

        // Pronalazenje i verifikovanje dugmeta pretraga
        WebElement pretragaVrednosti = driver.findElement(By.id("submit_1"));
        pretragaVrednosti.click();
        // explicit wait
        // elementToBeClickable
        wait.until(ExpectedConditions.elementToBeClickable(By.id("submit_1")));
        Assert.assertTrue(true);

        String actualPretragaUrl = FILTRIRANJE_DETALJNE_PRETRAGE_URL;
        String expectedPretragaUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualPretragaUrl, expectedPretragaUrl);

        driver.quit();
    }
}
