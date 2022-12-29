import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.*;

public class TC02KreiranjeLaznogNaloga extends CommonStrings{

    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
    }

    @BeforeMethod
    void openWebsite() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.polovniautomobili.com/");
    }

    @Test
    public void KreiranjeLaznogNaloga() throws InterruptedException {
        WebElement postavljanjeOglasa = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-label='Postavi oglas']")));
        postavljanjeOglasa.click();

        // Bez Thread.sleep nekada ne nalazi element
        Thread.sleep(3000);

        WebElement registrujSe = driver.findElement(By.xpath("//a[@href='/registracija']"));
        registrujSe.click();

        WebElement unosEmail = driver.findElement(By.id("email"));
        unosEmail.sendKeys(EMAIL);
        WebElement unosSifre = driver.findElement(By.id("password_first"));
        unosSifre.sendKeys(PASSWORD);
        WebElement ponavljanjeSifre = driver.findElement(By.id("password_second"));
        ponavljanjeSifre.sendKeys(PASSWORD);
        WebElement usloviKoriscenja = driver.findElement(By.id("tos"));
        usloviKoriscenja.click();
        WebElement lakaProdaja = driver.findElement(By.id("easySaleConsent"));
        lakaProdaja.click();
        WebElement lakaKupovina = driver.findElement(By.id("easyBuyConsent"));
        lakaKupovina.click();
        WebElement potvrdaRegistracije = driver.findElement(By.xpath("//button[.='Registruj se']"));
        potvrdaRegistracije.click();

        verifikacijaProtonMaila();
        switchTabs(1);



        //WebElement mojProfilPodesavanja = driver.findElement(By.xpath("//a[@href='/login']"));
        //mojProfilPodesavanja.click();
        WebElement zainteresovanostPonude = driver.findElement(By.id("interestedInReviewingOffer"));
        zainteresovanostPonude.click();
        WebElement potvrdjivanjeRegistracije = driver.findElement(By.name("submit_registration_survey"));
        potvrdjivanjeRegistracije.click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.='U redu']"))).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.id("first_name"))).sendKeys(IME);
        WebElement unosPrezimena = driver.findElement(By.id("last_name"));
        unosPrezimena.sendKeys(PREZIME);
        WebElement unosAdrese = driver.findElement(By.id("address"));
        unosAdrese.sendKeys(ADRESA);
        WebElement unosGrada = driver.findElement(By.id("city"));
        unosGrada.sendKeys(GRAD);
        WebElement unosPostanskogBroja = driver.findElement(By.id("zip_code"));
        unosPostanskogBroja.sendKeys(POSTANSKI_BROJ);
        WebElement odabirOkruga = driver.findElement(By.xpath("//span[.=' Odaberite okrug']"));
        odabirOkruga.click();
        WebElement potvrdaOkruga = driver.findElement(By.xpath("//label[.='Pomoravski']"));
        potvrdaOkruga.click();
        WebElement unosTelefona = driver.findElement(By.id("cellphone"));
        unosTelefona.sendKeys(BROJ_TELEFONA);
        WebElement sacuvajKlik = driver.findElement(By.id("submit"));
        sacuvajKlik.click();

        WebElement uspesnaRegistracija = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".uk-alert-success")));
        Assert.assertTrue(uspesnaRegistracija.isDisplayed());
        String dropdownDivContent = "\n" +
                "\t\t\t\t\t\t\t\t\tMOJ PROFIL ";
        String xpathExpression = "//div[contains(text(), '" + dropdownDivContent + "')]";
        WebElement dropDownElement = driver.findElement(By.xpath(xpathExpression));
        Actions actionsNew = new Actions(driver);
        actionsNew.moveToElement(dropDownElement).perform();
        WebElement logOutElement = driver.findElement(By.cssSelector(".uk-button-dropdown")).findElement(By.cssSelector(".uk-dropdown"))
                .findElement(By.cssSelector("li:last-child"));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(logOutElement)).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(".top-menu-profile"))).findElement(By.cssSelector(".js_ga-event")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("username_header"))).sendKeys(EMAIL);
        WebElement sledeciKorak = driver.findElement(By.id("next-step"));
        sledeciKorak.click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("password_header"))).sendKeys(PASSWORD);
        WebElement logIn = driver.findElement(By.name("login"));
        logIn.click();
        WebElement currentEmail = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".ym-hide-content")));
        String currentEmailText = currentEmail.getText();
        Assert.assertEquals(currentEmailText, EMAIL);

        switchTabs(2);
        deleteEmail();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    // Verifikacija mejla
    void verifikacijaProtonMaila(){
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://mail.proton.me/");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("username"))).sendKeys(PROTON_MAIL);
        WebElement unosSifre = driver.findElement(By.id("password"));
        unosSifre.sendKeys(PASSWORD);
        WebElement prijavaKlik = driver.findElement(By.xpath("//button[.='Sign in']"));
        prijavaKlik.click();
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[title='Aktivirajte Vaš nalog']"))).click();
        WebElement iframe = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-subject='Aktivirajte Vaš nalog']")));
        driver.switchTo().frame(iframe);
        WebElement link = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'aktivacija-naloga')]")));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
        link.click();
        driver.switchTo().defaultContent();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.='Confirm']"))).click();
    }

    // Promena tabova
    void switchTabs(int tabnum){
        Set<String> handles = driver.getWindowHandles();
        List<String> handleList = new ArrayList<>(handles);
        driver.switchTo().window(handleList.get(handleList.size() - tabnum));
    }

    // Brisanje Emaila
    void deleteEmail(){
        WebElement brisanje = driver.findElement(By.cssSelector("[data-testid='toolbar:movetotrash']"));
        brisanje.click();
        WebElement dodatno = driver.findElement(By.cssSelector("[title='More']"));
        dodatno.click();
        WebElement brisanjePoruke = driver.findElement(By.cssSelector("[data-testid='navigation-link:trash']"));
        brisanjePoruke.click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='message-column:sender-address']")));
        WebElement cekiranje = driver.findElement(By.cssSelector("[data-testid='toolbar:select-all-checkbox']"));
        cekiranje.click();
        WebElement permanentnoBrisanje = driver.findElement(By.cssSelector("[data-testid='toolbar:deletepermanently']"));
        permanentnoBrisanje.click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='permanent-delete-modal:submit']"))).click();
    }

    // Hvatanje pop-up
    void popupCheck(String elementID) {
        try {
            WebElement popupElement = driver.findElement(By.id(elementID));
            if (popupElement.isDisplayed())
                popupElement.click();

        } catch (NoSuchElementException ignored) {
        }
    }
}
