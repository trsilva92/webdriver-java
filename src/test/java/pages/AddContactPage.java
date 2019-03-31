package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class AddContactPage extends BasePage {

    public AddContactPage(WebDriver navegador) {
        super(navegador);
    }

    public AddContactPage selecionarTipoDeContato(String tipo) {

        WebElement campotype = navegador.findElement(By.id("addmoredata")).findElement(By.name("type"));
        new Select(campotype).selectByVisibleText(tipo);
        return this;
    }

    public AddContactPage digitarContato(String contato){
        //No campo de name "contact" digitar o telefone +5511984426727
        navegador.findElement(By.id("addmoredata")).findElement(By.name("contact")).sendKeys(contato);
        return this;
    }

    public MePage clicarEmSalvar(){

        navegador.findElement(By.linkText("SAVE")).click();
        return new MePage(navegador);
    }

    public MePage adicionarContato(String tipo, String contato) {

        selecionarTipoDeContato(tipo);
        digitarContato(contato);
        clicarEmSalvar();
        return new MePage(navegador);
    }
}
