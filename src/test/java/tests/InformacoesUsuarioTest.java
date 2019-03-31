package tests;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;
import suporte.Web;


@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioTestData.csv")
public class InformacoesUsuarioTest {

    private WebDriver navegador;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp() {

        navegador = Web.createChrome();

        //Identificando o formulário "signbox"
        WebElement formulariosigninbox = navegador.findElement(By.id("signinbox"));

        //Digitar nome de usuário
        formulariosigninbox.findElement(By.name("login")).sendKeys("julio0001");

        //Digitar senha de usuáriob
        formulariosigninbox.findElement(By.name("password")).sendKeys("123456");

        //Clicar no botão "SIGN IN"
        navegador.findElement(By.linkText("SIGN IN")).click();

        //Clicar em um link que possui a class "me"
        navegador.findElement(By.className("me")).click();

        //Clicar em um link que possui o texto "MORE DATA ABOUT YOU"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();

    }

    @Test
    public void testAdicionarInformacaoAdicionaldoUsuario(@Param(name="tipo")String tipo, @Param(name="contato")String contato, @Param(name = "mensagem")String mensagemEsperada) {

        //Clicar no botão através do seu xpath //button[@data-target="addmoredata"]
        navegador.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();

        //Identificar a popup onde está o formulário de id addmoredata
        WebElement popupaddmoredata = navegador.findElement(By.id("addmoredata"));

        //Na combo de name "type" escolhe a opção "phone"
        WebElement campotype = popupaddmoredata.findElement(By.name("type"));
        new Select(campotype).selectByVisibleText(tipo);

        //No campo de name "contact" digitar o telefone +5511984426727
        popupaddmoredata.findElement(By.name("contact")).sendKeys(contato);

        //Clicar no link de text "SAVE" que está na popup
        popupaddmoredata.findElement(By.linkText("SAVE")).click();

        //Na mensagem de id "toast-container" validar que o texto é "Your contact has been added!"
        WebElement msgPopUp = navegador.findElement(By.id("toast-container"));

        String mensagem = msgPopUp.getText();
        Assert.assertEquals(mensagemEsperada, mensagem);

    }

    @Test
    public void removerContatoDeUsuario() {

        //Clicar no xpath //span[text()="+5511984426727"]/following-sibling::a
        navegador.findElement(By.xpath("//span[text()=\"+5511984426727\"]/following-sibling::a")).click();

        //Confirmar a janela javascript
        navegador.switchTo().alert().accept();

        //Validar que a mensagem apresentada foi "Reast in peace, dear phone!"
        WebElement msgPopUp = navegador.findElement(By.id("toast-container"));
        String mensagem = msgPopUp.getText();
        Assert.assertEquals("Rest in peace, dear phone!", mensagem);

        String screenshot = "/home/trsilva92/Test-Report/Taskit/" + Generator.dataHoraParaArquivo() + test.getMethodName() + ".png";
        Screenshot.tirar(navegador, screenshot);

        //Aguardar até 10seg para que a janela desapareça
        WebDriverWait aguardar = new WebDriverWait(navegador, 8);
        aguardar.until(ExpectedConditions.stalenessOf(msgPopUp));

        //Fazer logout
        navegador.findElement(By.linkText("Logout")).click();
    }

    @After
    public void tearDown() {

        //Fechar o Google Chrome
        navegador.quit();
    }
}
